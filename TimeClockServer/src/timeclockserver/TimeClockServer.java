package timeclockserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import timeclockserver.frahm.employee.Employee;
import timeclockserver.frahm.employee.EmployeeDAO;
import timeclockserver.frahm.timepunch.TimePunchDAO;
import java.sql.SQLException;
import messages.Messages; 
import messages.MsgState;

/**
 * This class represents the code necessary to receive, apply, and respond to
 * a user entering their EmployeeID on the time clock client.
 * 
 * @author Luke Frahm
 */
public class TimeClockServer {
    static Messages message = new Messages(MsgState.INITIALIZED);

    private static final String dbUrl = "localhost";
    private static final String dbName = "timeclock";
    private static final String dbUserName = "timeclockuser";
    private static final String dbPassword = "password_1234";
    
    /**
     * Main method. This begins the process of adding a new record into the DB
     * 
     * @param args 
     */
    public static void main(String[] args) {
        /**
         * Specify the port
         */
        int port = 5555;

        /**
         * Create a variable for the ServerSocket and one for the Server
         */
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            /**
             * Create the ServerSocket and pass it the port number
             */
            serverSocket = new ServerSocket(port);

            while(true) {
                /**
                 * Accept the request and assign to a Socket
                 */
                socket = serverSocket.accept();

                /**
                 * Get the streams from the socket to read and write
                 */ 
                ObjectInputStream inputStreamObject = new 
                                    ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStreamObject = new 
                                    ObjectOutputStream(socket.getOutputStream());
                String messageString = inputStreamObject.readObject().toString();
                
                /**
                 * Server processing method
                 */
                serverProcessing(messageString);
                
                /**
                 * Reply to the client with the status of the punch
                 */
                outputStreamObject.writeObject(message);
                
                outputStreamObject.flush();
                inputStreamObject.close();
                outputStreamObject.close();
            } // end while
        } catch(Exception ex) {}
    }

    /**
     * This is the code that accesses the database to check and apply the 
     * new punch in record.
     * 
     * @param messageString The user input sent from the client
     */
    private static void serverProcessing(String messageString){
        String employeeId = messageString;
        
        /**
         * Create a new EmployeeDAO and populate it
         */
        EmployeeDAO employeeDao = new EmployeeDAO();
        employeeDao.setDbUrl(TimeClockServer.dbUrl);
        employeeDao.setDbName(TimeClockServer.dbName);
        employeeDao.setDbUserName(TimeClockServer.dbUserName);
        employeeDao.setDbUserPassword(TimeClockServer.dbPassword);
        
        Employee employee = null;
        
        /**
         * Attempt to find the employee record
         */
        try {
            employee = employeeDao.getEmployeeById(employeeId);
        
        } catch(SQLException ex) {
            message.setMsgState(MsgState.SERVER_PROBLEM);
            System.exit(-1);
        }
        
        /**
         * Attempt to write the punch in time 
         */
        if(employee != null) {
            /**
             * Save time stamp to the new employee punch in record
             */
            TimePunchDAO timePunchDao = new TimePunchDAO();
            timePunchDao.setDbUrl(TimeClockServer.dbUrl);
            timePunchDao.setDbName(TimeClockServer.dbName);
            timePunchDao.setDbUserName(TimeClockServer.dbUserName);
            timePunchDao.setDbUserPassword(TimeClockServer.dbPassword);
            try{
                timePunchDao.recordTimePunch(employee, "TimeClock");
                message.setMsgState(MsgState.GOOD);
            } catch(SQLException ex){
                message.setMsgState(MsgState.SERVER_PROBLEM);
            }
        } else {
            message.setMsgState(MsgState.INCORRECT_ID);
        }
    }      
}
