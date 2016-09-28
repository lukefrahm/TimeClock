package timeclockserver.frahm.timepunch;

import java.util.Date;

/**
 * Represents time punch records from the database
 * 
 * @author Luke Frahm
 */
public class TimePunch {

    /**
     * This is the ID of the Employee punching the clock.
     */    
    private String employeeId;

    /**
     * This is the time and date of the punch activity.
     */
    private Date punchTime;

    /**
     * This is a note about the source of the punch activity.
     */
    private String source;

    /**
     * This field denotes whether this punch record is valid. A value of Y means 
     * yes and a value of N means N.  Y/N
     */
    private String valid;

    /**
     * If the record is marked as not valid, then this will denote when that 
     * change happened.  For valid records, this field will be null.
     */
    private Date invalidatedDate;

    /**
     * If this record has been invalidated, this field will hold the employee ID 
     * of the person who marked it as invalid. For valid records, this field 
     * will be null.
     */
    private String invalidatedBy;

    /**
     * If the record has been invalidated, this field will contain a text 
     * description of why. For valid records, this field will be null.
     */
    private String invalidatedReason;

    /**
     * This is the ID of the Employee punching the clock.
     * 
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * This is the ID of the Employee.
     * 
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This is the time and date of the punch.
     * 
     * @return the punchTime
     */
    public Date getPunchTime() {
        return punchTime;
    }

    /**
     * This is the time and date of the punch.
     * 
     * @param punchTime the punchTime to set
     */
    public void setPunchTime(Date punchTime) {
        this.punchTime = punchTime;
    }

    /**
     * This is a note about the source of the punch.
     * 
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * This is a note about the source of the punch.
     * 
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * This field denotes whether this punch record is valid. A value of Y means
     * yes and a value of N means N.  Y/N
     * 
     * @return the valid
     */
    public String getValid() {
        return valid;
    }

    /**
     * This field denotes whether this punch record is valid. A value of Y means
     * yes and a value of N means N.  Y/N
     * 
     * @param valid the valid to set
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    /**
     * If the record is marked as not valid, this date will denote when that
     * change happened. For valid records, this field will be null.
     * 
     * @return the invalidatedDate
     */
    public Date getInvalidatedDate() {
        return invalidatedDate;
    }

    /**
     * If the record is marked as not valid, this date will denote when that
     * change happened. For valid records, this field will be null.
     * @param invalidatedDate the invalidatedDate to set
     */
    public void setInvalidatedDate(Date invalidatedDate) {
        this.invalidatedDate = invalidatedDate;
    }

    /**
     * If this record has been invalidated, this field will hold the employee ID
     * of the person who marked it as invalid.  For valid records, this field
     * will be null.
     * 
     * @return the invalidatedBy
     */
    public String getInvalidatedBy() {
        return invalidatedBy;
    }

    /**
     * If this record has been invalidated, this field will hold the employee ID
     * of the person who marked it as invalid. For valid records, this field
     * will be null.
     * 
     * @param invalidatedBy the invalidatedBy to set
     */
    public void setInvalidatedBy(String invalidatedBy) {
        this.invalidatedBy = invalidatedBy;
    }

    /**
     * If the record has been invalidated, this field will contain a text
     * description of why. For valid records, this field will be null.
     * 
     * @return the invalidatedReason
     */
    public String getInvalidatedReason() {
        return invalidatedReason;
    }

    /**
     * If the record has been invalidated, this field will contain a text
     * description of why. For valid records, this field will be null.
     * 
     * @param invalidatedReason the invalidatedReason to set
     */
    public void setInvalidatedReason(String invalidatedReason) {
        this.invalidatedReason = invalidatedReason;
    }

       
}
