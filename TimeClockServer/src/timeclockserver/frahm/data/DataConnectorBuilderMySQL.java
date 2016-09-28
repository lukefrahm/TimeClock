package timeclockserver.frahm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This utility class extends the DataConnectorBuilder interface but only for 
 * the MySQL databases.
 * 
 * @author Luke Frahm
 */
public class DataConnectorBuilderMySQL  implements DataConnectorBuilder {
    
    /**
     * 
     * @param databaseName The name of the database to which to connect
     * @param userName The name of the user allowed to connect
     * @param password The password of the database user
     * @return A SQL Connection object
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @SuppressWarnings("override")
    public Connection getConnection(String databaseName, String userName
                                    , String password) throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException {
            return this.getConnection("localhost", databaseName, userName, password);
    }

    /**
     * 
     * @param databaseUrl The URL for the host of the DBMS
     * @param databaseName The name of the database to which to connect
     * @param userName The name of the user allowed to connect
     * @param password The password of the database user
     * @return A SQL Connection object
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Connection getConnection(String databaseUrl, String databaseName
                                    , String userName, String password) 
                                      throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException {
        
            return this.getConnection(databaseUrl, 3306, databaseName, userName, password);
    }

    /**
     * 
     * @param databaseUrl The URL for the host of the DBMS
     * @param databasePort The port for the DBMS on its host
     * @param databaseName The name of the database to which to connect
     * @param userName The name of the user allowed to connect
     * @param password The password of the database user
     * @return A SQL Connection object
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Connection getConnection(String databaseUrl, Integer databasePort
                                    , String databaseName, String userName
                                    , String password) throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException {
            Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection("jdbc:mysql://"+databaseUrl+":"+Integer.toString(databasePort)+"/"+databaseName+"?noAccessToProcedureBodies=true", userName, password);
    }
}
