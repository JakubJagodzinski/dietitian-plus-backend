IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'dietitian_plus')
    BEGIN
        CREATE DATABASE dietitian_plus;
    END
GO

USE dietitian_plus;
GO

IF NOT EXISTS (
    SELECT * FROM sys.schemas WHERE name = 'app'
)
    BEGIN
        EXEC('CREATE SCHEMA app');
    END
GO