# Windows Authentication setup

The application now uses the Windows account running Java instead of the SQL Server `sa` account.

## 1. SQL Server

In SSMS, connect using **Windows Authentication**.

Make sure SQL Server is listening on TCP port 1433 and TCP/IP is enabled.

Open `setup-windows-auth.sql` in SSMS and execute it while connected with the Windows account that will run the Java application. It grants that Windows account access to `DeliveryApp`.

## 2. JDBC native authentication DLL

The Microsoft JDBC driver uses the Windows native authentication library for this mode. Microsoft documents that `integratedSecurity=true` loads `mssql-jdbc_auth-<version>-<arch>.dll` from a directory on PATH or from `java.library.path`.

This project uses JDBC Driver 12.8.1, so download the **Microsoft JDBC Driver 12.8.1 for SQL Server** package and copy:

`auth\x64\mssql-jdbc_auth-12.8.1.x64.dll`

to this project's:

`DeliveryMangment\auth\`

The computer should use a 64-bit JDK. If `java -version` reports a 64-bit VM, use the x64 DLL.

## 3. Run

In VS Code, open this `DeliveryMangment` folder, then choose:

**Run and Debug → DeliveryMangment - Windows Authentication**

Or double-click `run-windows-auth.bat`.

No SQL Server `sa` password is stored in the Java source anymore.
