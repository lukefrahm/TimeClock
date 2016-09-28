package timeclockserver.frahm.timepunch;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import timeclockserver.frahm.data.DatabaseConnectionFactory;
import timeclockserver.frahm.data.DatabaseType;
import timeclockserver.frahm.employee.Employee;

/**
 * The TimePunch Data Access Object handles persistent storage of data related
 * to TimePunch objects.
 * 
 * @author Luke Frahm
 */
public class TimePunchDAO {

    /**
     * The URL for the database holding the Employee data.
     */
    private String dbUrl;
    
    /**
     * The name of the database holding the Employee data.
     */
    private String dbName;
    
    /**
     * The database user name to use when connecting to the database.
     */
    private String dbUserName;
    
    /**
     * The password for the database user.
     */
    private String dbUserPassword;
    
    /**
     * Records a time punch with the specified employeeId and source, using the
     * current time.
     * 
     * @param employeeId 
     * @param source 
     * @throws java.sql.SQLException 
     */
    public void recordTimePunch(String employeeId, String source)
                                throws SQLException  {
        try{
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            
            DatabaseConnectionFactory connFactory = DatabaseConnectionFactory
                    .getInstance();
            Connection conn = connFactory.getConnection(DatabaseType.MYSQL, 
                    dbName, dbUserName, dbUserPassword);
            
            CallableStatement callableStatement = conn.prepareCall
                    ("{ call sp_RecordTimePunch(?,?,?) }");
            callableStatement.setString(1, employeeId);
            callableStatement.setTimestamp
                    (2, new java.sql.Timestamp(now.getTime()));
            callableStatement.setString(3, source);
            callableStatement.execute();
        }catch(ClassNotFoundException ex){
        }
    }
    
    /**
     * Records a time punch with the specified Employee and source, using the
     * current time.
     * 
     * @param employee
     * @param source 
     * @throws java.sql.SQLException 
     */
    public void recordTimePunch(Employee employee, String source) 
                                throws SQLException {
        try{
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            
            DatabaseConnectionFactory connFactory = DatabaseConnectionFactory
                    .getInstance();
            Connection conn = connFactory.getConnection(DatabaseType.MYSQL, 
                    dbName, dbUserName, dbUserPassword);
            
            CallableStatement callableStatement = conn.prepareCall
                    ("{ call sp_RecordTimePunch(?,?,?) }");
            callableStatement.setString(1, employee.getEmployeeId());
            callableStatement.setTimestamp
                    (2, new java.sql.Timestamp(now.getTime()-1000));
            callableStatement.setString(3, source);
            callableStatement.execute();
        }catch(ClassNotFoundException ex){
        }
    }
    
    /**
     * Marks a time punch record as invalid.
     * 
     * @param employeeId
     * @param punchTime 
     * @param invalidatedBy 
     * @param invalidatedReason 
     * @throws java.sql.SQLException 
     */
    public void invalidateTimePunch(String employeeId
                                , Date punchTime
                                , String invalidatedBy
                                , String invalidatedReason)
                                throws SQLException {
        
    }
    
    /**
     * Returns a list of the time punch records between start and end dates.
     * 
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public ArrayList<TimePunch> getTimePunchsByDateRange(Date startDate
                                    , Date endDate) throws SQLException {
        return null;
    }
    
    /**
     * Returns a list of the time punch records between start and end dates.
     * 
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public ArrayList<TimePunch> getTimePunchsByDateRange(String startDate
                                    , String endDate) throws SQLException {
        return null;
    }
    
    /**
     * Returns a list of the time punch records between start and end dates 
     * for the specified employee.
     * 
     * @param employee
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public ArrayList<TimePunch> getTimePunchsByEmployeeDateRange(
                                    Employee employee
                                    , Date startDate
                                    , Date endDate) throws SQLException {
        return null;
    }
    
    /**
     * Returns a list of the time punch records between start and end dates
     * for the specified employee ID.
     * 
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public ArrayList<TimePunch> getTimePunchsByEmployeeDateRange(
                                    String employeeId
                                    , String startDate
                                    , String endDate) throws SQLException {
        return null;
    }

    /**
     * The URL for the database holding the Employee data.
     * 
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * The URL for the database holding the Employee data.
     * 
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * The name of the database holding the Employee data.
     * 
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * The name of the database holding the Employee data.
     * 
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * The database user name to use when connecting to the database.
     * 
     * @return the dbUserName
     */
    public String getDbUserName() {
        return dbUserName;
    }

    /**
     * The database user name to use when connecting to the database.
     * 
     * @param dbUserName the dbUserName to set
     */
    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    /**
     * The password for the database user.
     * 
     * @return the dbUserPassword
     */
    public String getDbUserPassword() {
        return dbUserPassword;
    }

    /**
     * The password for the database user.
     * 
     * @param dbUserPassword the dbUserPassword to set
     */
    public void setDbUserPassword(String dbUserPassword) {
        this.dbUserPassword = dbUserPassword;
    }
    
}
