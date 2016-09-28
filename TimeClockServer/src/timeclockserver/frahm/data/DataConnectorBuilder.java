package timeclockserver.frahm.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This interface sets up for multiple classes that can be used to build 
 * database connections. Each allows for different methods of connecting
 * to the database.
 * 
 * @author Luke Frahm
 */
public interface DataConnectorBuilder {
    
    /**
     * @param databaseName The name of the database to which to connect
     * @param userName The name of the user allowed to connect
     * @param password The password of the database user
     * @return A SQL Connection object
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Connection getConnection(String databaseName, String userName
                                    , String password) throws SQLException
                                    , ClassNotFoundException
                                    , IllegalArgumentException;

    /**
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
                                    , IllegalArgumentException;

    /**
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
                                    , IllegalArgumentException;
}