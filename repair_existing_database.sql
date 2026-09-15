USE DeliveryApp;
GO
ALTER TABLE dbo.Customer ALTER COLUMN CustomerPasswordHash varbinary(64) NOT NULL;
GO
ALTER TABLE dbo.IndividualContractor ALTER COLUMN IndividualContractorPasswordHash varbinary(64) NOT NULL;
GO
PRINT 'Password hash columns changed to varbinary(64). Create fresh accounts if old hashes were truncated.';
