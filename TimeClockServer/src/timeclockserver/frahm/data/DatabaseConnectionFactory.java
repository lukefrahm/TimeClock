package timeclockserver.frahm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a convenient way to get a database Connection for the appropriate 
 * database.  Note that this uses the Singleton pattern.
 * 
 * @author Luke Frahm
 */
public class DatabaseConnectionFactory {

    /** 
     * This is the current instance of the DatabaseConnectionFactory.  It is 
     * held here as part of the Singleton pattern.
     */
    private static DatabaseConnectionFactory instance = null;


    /** 
     * Instead of instantiating a new instance of the DatabaseConnectionFactory, 
     * client objects will use the getInstance() method to get access to the 
     * only instance of this object.
     * 
     * @return 
     */
    public static DatabaseConnectionFactory getInstance() {
        if(instance == null) {
            instance = new DatabaseConnectionFactory();
      }
      return instance;
    }

    /** 
     * Note that the constructor for DatabaseConnectionFactory is private.  
     * Nothing outside of the class can instantiate one.  Instead, the first 
     * time something calls the getInstance() method, it will determine whether 
     * an instance must be instantiated and will take care of doing so.
     */
    private DatabaseConnectionFactory() {
    }

    /** 
     * The simplest form of this method, assumes that it should use default 
     * values for the type of database.
     * @param databaseType
     * @param databaseName
     * @param userName
     * @param password
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static Connection getConnection(DatabaseType databaseType, String databaseName
                                            , String userName, String password) throws SQLException, ClassNotFoundException {
        DataConnectorBuilder dbConnectionBuilder;
        
        if(databaseType == DatabaseType.MYSQL){
            dbConnectionBuilder = new DataConnectorBuilderMySQL();
        }else{
            dbConnectionBuilder = new DataConnectorBuilderMSSQL();
        }
        return dbConnectionBuilder.getConnection(databaseName, userName, password);
    }

    /**
     * 
     * @param databaseType
     * @param databaseUrl
     * @param databaseName
     * @param userName
     * @param password
     * @return 
     */
    public static Connection getConnection(DatabaseType databaseType, String databaseUrl
                    , String databaseName, String userName, String password) throws SQLException, ClassNotFoundException {
        DataConnectorBuilder dbConnectionBuilder;
        
        if(databaseType == DatabaseType.MYSQL){
            dbConnectionBuilder = new DataConnectorBuilderMySQL();
        }else{
            dbConnectionBuilder = new DataConnectorBuilderMSSQL();
        }
        return dbConnectionBuilder.getConnection(databaseUrl, databaseName, userName, password);
    }

    /**
     * 
     * @param databaseType
     * @param databaseUrl
     * @param dataabasePort
     * @param databaseName
     * @param userName
     * @param password
     * @return 
     */
    public static Connection getConnection(DatabaseType databaseType, String databaseUrl
                    , Integer dataabasePort, String databaseName, String userName
                    , String password) {
        return null;
    }
}