USE master;
GO

-- Run this script while connected to SSMS with Windows Authentication.
-- It grants the Windows account that is running SSMS access to DeliveryApp.
DECLARE @login sysname = SUSER_SNAME();
DECLARE @sql nvarchar(max);

IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = @login)
BEGIN
    SET @sql = N'CREATE LOGIN ' + QUOTENAME(@login) + N' FROM WINDOWS;';
    EXEC sys.sp_executesql @sql;
END;
GO

USE DeliveryApp;
GO

DECLARE @login sysname = SUSER_SNAME();
DECLARE @sql nvarchar(max);

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = @login)
BEGIN
    SET @sql = N'CREATE USER ' + QUOTENAME(@login) + N' FOR LOGIN ' + QUOTENAME(@login) + N';';
    EXEC sys.sp_executesql @sql;
END;

DECLARE @roleSql nvarchar(max) = N'ALTER ROLE db_owner ADD MEMBER ' + QUOTENAME(@login) + N';';
EXEC sys.sp_executesql @roleSql;
GO
