package timeclockserver.frahm.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This utility class extends the DataConnectionBuilder interface but applies 
 * only to Microsoft SQL Server databases.
 * 
 * @author Luke Frahm
 */
public class DataConnectorBuilderMSSQL  implements DataConnectorBuilder {
    
    /**
     * 
     * @param databaseName The name of the database to which to connect
     * @param userName The name of the user allowed to connect
     * @param password The password of the database user
     * @return A SQL Connection object
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    public Connection getConnection(String databaseName, String userName
                                    , String password) throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException{
        return null;
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
    @Override
    public Connection getConnection(String databaseUrl, String databaseName
                                    , String userName, String password) 
                                      throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException {
        return null;
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
    @Override
    public Connection getConnection(String databaseUrl, Integer databasePort
                                    , String databaseName, String userName
                                    , String password) throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException{
        return null;
    }   
}