The objective of GenericDB is to fetch the results for the query keyed in and parse the results into an excel from the path specified through argument.
It is developed in such a way that it accepts command line arguments. 
As of now this code is developed for 3 databases (Oracle,Sql, Hive).If required, we can extend this for other DB's as well.

Step 1:
place the jar in your directory and set the path in environment variables.

Step 2:
GenericDB accepts 3 arguments from command line.
 
args[0] -  Name of the DataBase (eg: oracle, sql, Hive)
args[1] -  Query to be passed
args[2] -  Excel file path to be specified (C:\Sample.xlsx)

Run the below command in command prompt by passing the required arguments

java dbUtility.dbUtility.HiveJdbcClienttv1 "sql" "Select * from dbo.Results" "C:\Sample.xlsx"

Step 3:
All the Database connectivity details are given in db.properties file in below format. 
For example later point of time if there is any change in user name and password it should be updated against the corresponding DB attributes as mentioned below.

hive_db_URL=jdbc:hive2://hiveqa.com:1234/default
hive_User= Testuser
hive_Pswd= 12345
hive_db_driver=org.apache.hive.jdbc.HiveDriver

SQL_DB_URL=jdbc:sqlserver://database:1433/TestDB
SQL_DB_DRIVER=com.microsoft.sqlserver.jdbc.SQLServerDriver
SQL_DB_USER= TestUser
SQL_DB_PWD= 12345

ORACLE_DB_URL=jdbc:oracle:thin:@x900-scan:1234/TestDB
ORACLE_DB_DRIVER=oracle.jdbc.driver.OracleDriver
ORACLE_DB_USER= TestUser
ORACLE_DB_PWD= 12345

