IF DB_ID(N'DeliveryApp') IS NULL CREATE DATABASE DeliveryApp;
GO
USE DeliveryApp;
GO

IF OBJECT_ID(N'dbo.Customer', N'U') IS NULL
CREATE TABLE dbo.Customer(
 CustomerId int IDENTITY(1,1) PRIMARY KEY,
 CustomerFirstName varchar(20) NOT NULL, CustomerMiddleName varchar(20), CustomerLastName varchar(20) NOT NULL,
 CustomerDOB date NOT NULL, CustomerPhoneNum char(13) NOT NULL UNIQUE, CustomerEmail nvarchar(255) UNIQUE NOT NULL,
 CustomerPasswordHash varbinary(64) NOT NULL,
 CONSTRAINT check_customer_age CHECK (DATEDIFF(year,CustomerDOB,GETDATE())>=18 AND DATEDIFF(year,CustomerDOB,GETDATE())<100),
 CONSTRAINT check_customer_phone CHECK (CustomerPhoneNum LIKE '+251[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
 CONSTRAINT check_customer_email CHECK (CustomerEmail LIKE '%_@_%._%')
);
GO

IF OBJECT_ID(N'dbo.IndividualContractor', N'U') IS NULL
CREATE TABLE dbo.IndividualContractor(
 IndividualContractorId int IDENTITY(1,1) PRIMARY KEY,
 IndividualContractorFirstName varchar(20) NOT NULL, IndividualContractorMiddleName varchar(20), IndividualContractorLastName varchar(20) NOT NULL,
 IndividualContractorDOB date NOT NULL, IndividualContractorPhoneNum char(13) NOT NULL UNIQUE, IndepContractorVehicleType int NOT NULL,
 IndividualContractorStatus varchar(8) NOT NULL, IndividualContractorEmail nvarchar(255) UNIQUE NOT NULL,
 IndividualContractorPasswordHash varbinary(64) NOT NULL,
 CONSTRAINT check_IndividualContractor_age CHECK (DATEDIFF(year,IndividualContractorDOB,GETDATE())>=18 AND DATEDIFF(year,IndividualContractorDOB,GETDATE())<100),
 CONSTRAINT check_IndividualContractor_phone CHECK (IndividualContractorPhoneNum LIKE '+251[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
 CONSTRAINT check_IndividualContractor_email CHECK (IndividualContractorEmail LIKE '%_@_%._%')
);
GO

IF OBJECT_ID(N'dbo.Package', N'U') IS NULL
CREATE TABLE dbo.Package(
 PackageId int IDENTITY(1,1) PRIMARY KEY, PackageInitialLocation varchar(60) NOT NULL, PackageFinalDestination varchar(60) NOT NULL,
 PackageWeight float NOT NULL, PackageVolume float NOT NULL, PackagePrice decimal(19,4) NOT NULL, PackageDescription varchar(100) NOT NULL,
 PackageShipped int NOT NULL, CustomerId int NOT NULL,
 FOREIGN KEY(CustomerId) REFERENCES dbo.Customer(CustomerId),
 CONSTRAINT check_package_weight CHECK(PackageWeight>=0 AND PackageWeight<2000),
 CONSTRAINT check_package_volume CHECK(PackageVolume>=0 AND PackageVolume<16),
 CONSTRAINT check_package_price CHECK(PackagePrice>=0)
);
GO

IF OBJECT_ID(N'dbo.Transactions', N'U') IS NULL
CREATE TABLE dbo.Transactions(
 TransactionId int IDENTITY(1,1) PRIMARY KEY, TransactionPrice decimal(19,4) NOT NULL, TransactionStatus int NOT NULL,
 CustomerId int NOT NULL, IndividualContractorId int NOT NULL, PackageId int NOT NULL,
 FOREIGN KEY(CustomerId) REFERENCES dbo.Customer(CustomerId), FOREIGN KEY(IndividualContractorId) REFERENCES dbo.IndividualContractor(IndividualContractorId),
 FOREIGN KEY(PackageId) REFERENCES dbo.Package(PackageId)
);
GO

IF NOT EXISTS(SELECT 1 FROM dbo.Customer WHERE CustomerEmail=N'test.customer@example.com')
INSERT dbo.Customer(CustomerFirstName,CustomerMiddleName,CustomerLastName,CustomerDOB,CustomerPhoneNum,CustomerEmail,CustomerPasswordHash)
VALUES('Test','Demo','Customer','1995-01-01','+251911111111',N'test.customer@example.com',HASHBYTES('SHA2_512','password'));
GO
IF NOT EXISTS(SELECT 1 FROM dbo.IndividualContractor WHERE IndividualContractorEmail=N'test.contractor@example.com')
INSERT dbo.IndividualContractor(IndividualContractorFirstName,IndividualContractorMiddleName,IndividualContractorLastName,IndividualContractorDOB,IndividualContractorPhoneNum,IndepContractorVehicleType,IndividualContractorStatus,IndividualContractorEmail,IndividualContractorPasswordHash)
VALUES('Test','Demo','Contractor','1994-01-01','+251922222222',1,'Active',N'test.contractor@example.com',HASHBYTES('SHA2_512','password'));
GO
DECLARE @CustomerId int=(SELECT TOP 1 CustomerId FROM dbo.Customer WHERE CustomerEmail=N'test.customer@example.com');
IF @CustomerId IS NOT NULL AND NOT EXISTS(SELECT 1 FROM dbo.Package WHERE CustomerId=@CustomerId AND PackageInitialLocation='bole' AND PackageFinalDestination='megenagna' AND PackageDescription='documents' AND PackageShipped=0)
INSERT dbo.Package(PackageInitialLocation,PackageFinalDestination,PackageWeight,PackageVolume,PackagePrice,PackageDescription,PackageShipped,CustomerId)
VALUES('bole','megenagna',0,0,10,'documents',0,@CustomerId);
GO

CREATE OR ALTER VIEW dbo.Vpendingpackage AS SELECT CustomerId,PackageId,PackageInitialLocation,PackageFinalDestination FROM dbo.Package WHERE PackageShipped=0;
GO
CREATE OR ALTER VIEW dbo.Vsentpackages AS
SELECT t.PackageId,p.CustomerId,c.IndividualContractorFirstName,c.IndividualContractorLastName,p.PackageInitialLocation,p.PackageFinalDestination
FROM dbo.IndividualContractor c INNER JOIN dbo.Transactions t ON t.IndividualContractorId=c.IndividualContractorId
INNER JOIN dbo.Package p ON p.PackageId=t.PackageId AND p.PackageShipped=1;
GO
CREATE OR ALTER VIEW dbo.vw_ActiveContractors AS
SELECT IndividualContractorId,IndividualContractorFirstName,IndividualContractorLastName,IndividualContractorPhoneNum,IndepContractorVehicleType,IndividualContractorStatus,IndividualContractorEmail
FROM dbo.IndividualContractor WHERE IndividualContractorStatus='Active';
GO
PRINT 'DeliveryApp is ready. Test accounts use password: password';
