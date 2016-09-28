package timeclockclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import messages.Messages; 

/**
 * This class contains the code that will display to the end user.
 * Users will enter their Employee ID here to use the time clock.
 * 
 * @author Luke Frahm
 */
public class TimeClockClient {
    private static final long serialVersionUID = 1L;
    
    /**
     * The main method. Initiates the program and loops through
     * punches until the end user presses Q to quit
     * 
     * @param args 
     */
    public static void main(String [] args) throws ClassNotFoundException {
        /**
         * get input from the user
         */
        String response = getChoice();
        
        /**
         * while loop for the client to cycle through to wait for employees
         * to input an ID to punch in.
         */
        while(!response.equalsIgnoreCase("Q")) {
            try {
                /**
                 * Get message data from server reply after sending data
                 */
                Messages message = contactServer(response);
                if(message == null){
                    /**
                     * Server returned null, signifying a ClassNotFoundException
                     */
                    throw new ClassNotFoundException();
                }
                
                /**
                 * Messages given to user after response
                 */
                switch(message.getMsgState()) {
                    case GOOD:
                        System.out.println("Punch is successful. "
                                + "Enjoy your day at work!");
                        break;
                    case INCORRECT_ID:
                        System.out.println("Incorrect ID. Please try again.");
                        break;
                    case SERVER_PROBLEM:
                        System.out.println("ERROR: Server encountered a "
                                + "problem.");
                        System.out.println("\tPlease try again later.");
                        break;
                    case INITIALIZED:
                        System.out.println("ERROR: Server has not initialized" +
                                "properly.");
                        System.out.println("\tPlease try again later.");
                        break;                        
                    default:
                        System.out.println("FATAL: No response from server.");
                        System.out.println("\tPlease try again later.");
                        break;
                }
                
                /**
                 * Get input from the user: perpetuates the loop
                 */
                response = getChoice();
            } catch(UnknownHostException uhe) {
                System.out.println("FATAL: Unable to resolve host."
                        + "\nPlease contact your system administrator."
                        + "\nProgram will now exit.");
                System.out.println(uhe.getMessage());
                System.exit(-1);
            } catch(IOException ioe) {
                System.out.println("FATAL: The server is currently not working."
                        + "\nPlease contact the system administrator."
                        + "\nProgram will now exit.");
                System.out.println(ioe.getMessage());
                System.exit(-1); // exit with code signifying failure
            } catch(ClassNotFoundException cnf){
                // exception printout already handled in contactServer()
                System.exit(-1); // exit with code signifying failure
            }// end try-catch
        } // end while
        System.exit(0); // exit with code signifying success/proper functioning
    } // end method main()
    
    /**
     * Creates a socket, connects to the server and sends the radius.  Then it
     * reads the value returned from the server.
     * 
     * @param input Input from users
     * @return 
     * @throws UnknownHostException
     * @throws IOException 
     */
    public static Messages contactServer(String input) throws 
                                UnknownHostException, IOException {
        try{
            Messages message;
            
            /**
             * Create a socket for communication
             */
            Socket socket = new Socket("localhost", 5555);

            /**
             * Create streams to send and receive data
             */
            ObjectOutputStream outputStreamObject = new ObjectOutputStream(
                    socket.getOutputStream());
            outputStreamObject.writeObject(input);
            outputStreamObject.flush();

            ObjectInputStream inputStreamObject = new ObjectInputStream(
                    socket.getInputStream());
            message = (Messages)inputStreamObject.readObject();

            /**
             * Close streams
             */
            inputStreamObject.close();
            outputStreamObject.close();

            return message;
        } catch(ClassNotFoundException cnf) {
            System.out.println("FATAL: Server cannot continue operation."
                        + "\nPlease contact the system administrator."
                        + "\nProgram will now exit.");
            System.out.println(cnf);
            return null;
        }
    } // end method getAreaFromServer()

    /**
    * Prompts the user for input, or Q to quit. Returns the
    * string typed by the user.
    *
    * @return String entered by the user
    */
    public static String getChoice(){
        System.out.println("\nPlease enter your Employee ID, or \"Q\" to quit:");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
} // end class Client