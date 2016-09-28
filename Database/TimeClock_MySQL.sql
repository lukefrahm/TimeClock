
/******************************************************************************
	Remove the database, if it exists
******************************************************************************/
DROP DATABASE IF EXISTS TimeClock;

/******************************************************************************
	Create the database
******************************************************************************/
CREATE DATABASE IF NOT EXISTS TimeClock;


/******************************************************************************
	Specify the database to use
******************************************************************************/
USE TimeClock;


/******************************************************************************
	TABLE: Employee
******************************************************************************/
CREATE TABLE Employee (
  EmployeeId VARCHAR(20) NOT NULL PRIMARY KEY , -- This is unique identifier for the employee record.
  LastName VARCHAR(255) , -- The last name or family name of the employee.
  FirstName VARCHAR(255) , -- This is the first, or given, name of the employee.
  DepartmentId VARCHAR(20) , -- This is a reference to the Department table showing to which department the employee is assigned.
  StartDate DATE NOT NULL , -- This is the date when the employee was hired.
  TermDate DATE  -- This is the date when employment ended for this employee.  If the date is null or in the future, the employee is still employed., 
)
;

/******************************************************************************
	TABLE: Department
******************************************************************************/
CREATE TABLE Department (
  DepartmentId VARCHAR(20) NOT NULL PRIMARY KEY, -- This is the department ID, usually a short form of the department name.
  DepartmentName VARCHAR(255) NOT NULL , -- This is the official name of the department.
  ManagerId VARCHAR(20)  -- This is the Employee ID of the employee assigned to manage the department.  This references Employee.EmployeeId., 
)
;

/******************************************************************************
	TABLE: TimePunch
******************************************************************************/
CREATE TABLE TimePunch (
  EmployeeId VARCHAR(20) NOT NULL , -- This is the ID of the Employee punching the clock.
  PunchTime DATETIME NOT NULL , -- This is the time and date of the punch activity.
  Source VARCHAR(255) , -- This is a note about the source of the punch activity.
  Valid CHAR(1) NOT NULL , -- This field denotes whether this punch record is valid.   A value of Y means yes and a value of N means N.  Y/N
  InvalidatedDate DATE , -- If the record is marked as not valid, then this date will denote when that change happened.  For valid records, this field will be null.
  InvalidatedBy VARCHAR(20) , -- If this record has been invalidated, this field will hold the employee ID of the person who marked it as invalid.  For valid records, this field will be null.
  InvalidatedReason VARCHAR(1024),  -- If the record has been invalidated, this field will contain a text description of why.  For valid records, this field will be null., 
 PRIMARY KEY (EmployeeId, PunchTime)
)
;

/*******************************************************************************
	Only allow Y or N in TimePunch.Valid.
	Unlike SQL Server, MySQL does not have CHECK CONSTRAINTS.  Instead, it uses
    triggers to do the same thing.
 *******************************************************************************/
delimiter //
CREATE TRIGGER tr_timepunch_valid BEFORE INSERT ON TimePunch
FOR EACH ROW
BEGIN
	IF Valid NOT IN ('Y', 'N')
		signal sqqlstaet '45000' set message_text 'Not a good value for TimePunch.Valid';
    END IF
END

delimiter ;


ALTER TABLE Employee ADD FOREIGN KEY (DepartmentId) REFERENCES Department (DepartmentId);
				
ALTER TABLE TimePunch ADD FOREIGN KEY (EmployeeId) REFERENCES Employee (EmployeeId);

/*******************************************************************************
	Enter initial values into the Department table
*******************************************************************************/
INSERT INTO Department (
	DepartmentId
	, DepartmentName
)
VALUES ('Sales','Sales Department')
	, ('Marketing','Marketing and Promotions')
	, ('R&D','Research and Development')
	, ('Legal','Legal and Liabilities')
	, ('Labor','Labor')
	, ('Accounting','Accounting')
	, ('HR','Human Resourses')
	, ('Admin','Administration')
	, ('Engineering', 'Engineering and Design')
	, ('Service','Service and Repairs')
;


/*******************************************************************************
	Enter initial values into the Employee table
*******************************************************************************/
INSERT INTO Employee (
	EmployeeId
	, LastName
	, FirstName
	, DepartmentId
	, StartDate
)
VALUES ('123456','Smith','John','Sales', CURDATE())
	, ('234567','Jones','Mike','Sales', CURDATE())
	, ('345678','Makeit','Willie','Admin', CURDATE())
	, ('456789','Weasel','Mark','Legal', CURDATE())
	, ('567890','Doitall','Joe','Labor', CURDATE())
	, ('a12345','Smith','Noah','Engineering', CURDATE())
	, ('a23456','Johnson','Emma','Engineering', CURDATE())
	, ('b34567','Williams','Liam','Sales', CURDATE())
	, ('b45678','Brown','Olivia','Sales', CURDATE())
	, ('c90123','Jones','Mason','Service', CURDATE())
;


/*******************************************************************************
	Create the database user for TimeClock
*******************************************************************************/
GRANT ALL ON TimeClock.* 
TO timeclockuser
IDENTIFIED BY 'password_1234'
;


/*******************************************************************************
	Create a stored procedure to get employees
	Execute the procedure as:
	CALL `timeclock`.`sp_GetEmployee`('123456');
*******************************************************************************/
USE `timeclock`;
DROP procedure IF EXISTS `sp_GetEmployee`;

DELIMITER $$
USE `timeclock`$$
CREATE PROCEDURE `sp_GetEmployee` (p_employeeId VARCHAR(20))
BEGIN
	SELECT
		EmployeeId
		, LastName
		, FirstName
		, DepartmentId
		, StartDate
		, TermDate
	FROM Employee
	WHERE EmployeeId = p_employeeId
    ;
END
$$

DELIMITER ;





