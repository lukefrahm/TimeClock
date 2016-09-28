package messages;

/**
 * Enumeration for different states/types of messages returned by Message object
 * @author Luke Frahm
 * 
 * PUNCH_IN represents the punch in state: an ID has been entered.
 * GOOD represents the punch has been successfully applied.
 * INCORRECT_ID represents the ID entered does not contain an Employee record.
 * SERVER_PROBLEM represents an error with the server.
 * INITIALIZED represents the server is now started and has received no punches
 */
public enum MsgState {
    PUNCH_IN,
    GOOD,
    INCORRECT_ID,
    SERVER_PROBLEM,
    INITIALIZED
}
