package timeclockserver.frahm.employee;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import timeclockserver.frahm.data.DatabaseConnectionFactory;
import timeclockserver.frahm.data.DatabaseType;

/**
 * The Employee Data Access Object handles persistent storage of data related to
 * Employee objects.
 * 
 * @author Luke Frahm
 */
public class EmployeeDAO {

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
     * Creates an employee record in the database.
     *
     * @param employee
     * @throws java.sql.SQLException
     */
    public void createEmployee(Employee employee) 
                                throws SQLException{

    }

    /**
     * Returns an employee record based on the employee ID.
     *
     * @param employeeId
     * @return
     * @throws java.sql.SQLException
     */
    public Employee getEmployeeById(String employeeId) throws SQLException {
        Employee emp = null;
        try{
            DatabaseConnectionFactory connFactory = DatabaseConnectionFactory
                    .getInstance();
            Connection conn = connFactory.getConnection(DatabaseType.MYSQL, 
                    dbName, dbUserName, dbUserPassword);
            
            CallableStatement callableStatement = conn.prepareCall
                    ("{ call sp_getEmployee(?) }");
            callableStatement.setString(1, employeeId);
            ResultSet resultSet = callableStatement.executeQuery();
            
            if(resultSet.next()){
                String id = resultSet.getString(1);
                String lastName = resultSet.getString(1);
                String firstName = resultSet.getString(1);
                String departmentId = resultSet.getString(1);
                
                Date start = new Date(resultSet.getDate(5).getTime());
                java.sql.Date sqlTerm = resultSet.getDate(6);
                Date term = null;
                if(null != sqlTerm) {
                    term = new Date(sqlTerm.getTime());
                }
                emp = new Employee(id, lastName, firstName, departmentId, 
                        start, term);
            }
            
            return emp;
        }catch(ClassNotFoundException ex){
            return null;
        }
    }

    /**
     * Returns a collection of Employees in the database that are assigned to
     * the specified department ID.
     *
     * @param departmentId
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<Employee> getEmployeesByDepartmentId(String departmentId)
                                throws SQLException {
        return null;
    }

    /**
     * Returns a collection of all Employees in the database.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<Employee> getEmployees()
                                throws SQLException {
        return null;
    }

    /**
     * Uses the data in the Employee object to update the associated record in
     * the database
     *
     * @param employee
     * @throws java.sql.SQLException
     */
    public void updateEmployee(Employee employee)
                                throws SQLException{
    }

    /**
     * Removes the Employee record from the database.
     *
     * @param employee
     * @throws java.sql.SQLException
     */
    public void deleteEmployee(Employee employee)
                                throws SQLException {
    }

    /**
     * Removes the associated Employee record from the database.
     *
     * @param employeeId
     * @throws java.sql.SQLException
     */
    public void deleteEmployee(String employeeId)
                                throws SQLException {
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
