package messages;

import java.io.Serializable;

/**
 * Message class handles setting up the message object and 
 * combines a message state/type with it
 * 
 * @author Luke Frahm
 */
public class Messages implements Serializable {
    
    private MsgState msgState;
    private Object object;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Overloaded constructor for message
     * 
     * @param msgState the message state/type
     * @param object the message object
     */
    public Messages(MsgState msgState, Object object) {
        this.msgState = msgState;
        this.object = object;
    }
    
    /**
     * Constructor for setting MsgState
     * 
     * @param msgState The MsgState object
     */
    public Messages(MsgState msgState) {
        this.msgState = msgState;
    }
    
    /**
     * Constructor for setting message content.
     * This is used for passing the user input
     * 
     * @param object The message carrier
     */
    public Messages(Object object) {
        this.object = object;
    }

    /**
     * Gets the message state/type
     * 
     * @return the msgState
     */
    public MsgState getMsgState() {
        return msgState;
    }

    /**
     * Sets the message state/type to the instance attribute
     * 
     * @param msgState the message state/type
     */
    public void setMsgState(MsgState msgState) {
        this.msgState = msgState;
    }

    /**
     * Gets the message object
     * 
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the message object to the instance attribute
     * 
     * @param object the message object
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
