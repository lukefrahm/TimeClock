package timeclockserver.frahm.employee;

import java.util.Date;

/** 
 *  Contains data and methods associated with employees.
 * 
 * @author Luke Frahm
 */
public class Employee {
  /** 
   * A unique identifier for the employee. 
   * Is alphanumeric and no more than twenty characters long.
   */
  private String employeeId;

  /** 
   * Contains the last or family name of the employee, 
   * can be no more than 255 characters in length.
   */
  private String lastName;

  /** 
   * Contains the first or given name of the employee. The name can be null, 
   * but it cannot be an empty string and can be no longer than 255 characters.  
   * None of the characters can be numeric.
   */
  private String firstName;

  /** 
   * The ID of the department associated with this employee. 
   * Can be null, but cannot be an empty string and cannot be 
   * more than twenty characters.
   */
  private String departmentId;

  /** 
   *  This is the date when the employee began employment.
   */
  private Date startDate;

  /** 
   * This is the date when the employment ended for this employee.
   * If the value is null or in the future, the employee is assumed 
   * to still be employed.
   */
  private Date termDate;


  /** 
   * The accessor method for the employee ID.
   * 
   * @return 
   */
  public String getEmployeeId() {
    return employeeId;
  }

  /** 
   * Acts as the mutator for the employee Id.
   * 
   * @param employeeId
   */
  public void setEmployeeId(String employeeId) {
      this.employeeId = employeeId;
  }

  /** 
   * Acts as the accessor for the employee last name.
   * @return 
   */
  public String getLastName() {

    return lastName;
  }

  /** 
   * Acts as the mutator for the employee last name. 
   * The last name can be null, but it cannot be an empty string and 
   * it must be no more than 255 characters.
   * None of the characters can be numeric.
   * @param lastName
   */
  public void setLastName(String lastName) {
      this.lastName = lastName;
  }

  /** 
   * Acts as the accessor for the first name attribute.
   * 
   * @return 
   */
  public String getFirstName() {
    return firstName;
  }

  /** 
   * The mutator for the first name.
   * 
   * @param firstName
   */
  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }

  /** 
   * Acts as the accessor for departmentId.
   * 
   * @return 
   */
  public String getDepartmentId() {
    return departmentId;
  }

  /** 
   * Acts as an accessor for start date.
   * 
   * @return 
   */
  public Date getStartDate() {
    return startDate;
  }

  /** 
   * Acts as a mutator for the start date.
   * 
   * @param startDate
   */
  public void setStartDate(Date startDate) {
      this.startDate = startDate;
  }

  /** 
   * Acts as the accessor for the term data.
   * @return 
   */
  public Date getTermDate() {
    return termDate;
  }

  /** 
   * Sets the termination date for the employee.
   * @param termDate
   */
  public void setTermDate(Date termDate) {
      this.termDate = termDate;
  }

  /** 
   * This is the default constructor for an Employee.
   */
  public Employee() {
  }

  /** 
   * This constructor is for use when the employeeId is not known.
   * @param lastName
   * @param firstName
   * @param departmentId
   * @param startDate
   */
  public Employee(String lastName, String firstName, String departmentId
                        , Date startDate) {
      this.lastName = lastName;
      this.firstName = firstName;
      this.departmentId = departmentId;
      this.startDate = startDate;
  }

  /** 
   * This constructor is used when the employeeId is known. 
   * @param employeeId
   * @param lastName
   * @param firstName
   * @param departmentId
   * @param startDate
   * @param termDate
   */
  public Employee(String employeeId, String lastName, String firstName
                        , String departmentId, Date startDate, Date termDate) {
      this.employeeId = employeeId;
      this.lastName = lastName;
      this.firstName = firstName;
      this.departmentId = departmentId;
      this.startDate = startDate;
      this.termDate = termDate;
  }
}