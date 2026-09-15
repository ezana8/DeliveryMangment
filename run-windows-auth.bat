@echo off
setlocal
cd /d "%~dp0"
if not exist bin mkdir bin
javac -cp "lib\mssql-jdbc-12.8.1.jre11.jar" -d bin src\DeliveryMangment\src\*.java
if errorlevel 1 exit /b 1
java -Djava.library.path="%CD%\auth" -cp "bin;lib\mssql-jdbc-12.8.1.jre11.jar" DeliveryMangment.src.Main
