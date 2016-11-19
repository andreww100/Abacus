call set_env.bat

set DBNAME=abacusdb

java -cp %HSQLDB_JAR% org.hsqldb.server.Server --database.0 file:%HSQLDB_DATA%\%DBNAME%\%DBNAME% --dbname.0 %DBNAME%