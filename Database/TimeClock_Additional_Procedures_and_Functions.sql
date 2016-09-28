/*
	FILE: TimeClock_Additional_Procedures_and_Functions.sql
    DATE: 2015-10-21
	AUTHOR: Bob Trapp
	DESCRIPTION:
		This script adds an assortment of extra stored procedures to the the 
		TimeClock database.
		
		
	USAGE:
	Both of these require that mysql.exe is part of your PATH environment 
	variable on Windows.  You can always add the full path to the program name
	to get the execution to work.
	
	Method 1: open a command window where the script resides
	> mysql -u root -p TimeClock < TimeClock_Additional_Procedures_And_Functions.sql
	Then enter the password when prompted
	
	Method 2: open the mysql tool from the command line, then use
	> use timeclock;
	> SOURCE TimeClock_Additional_Procedures_And_Functions.sql;
		
	
*/

USE TimeClock;

/*
	Add a new employee record and add its EmployeeId.  Return the new 
	EmployeeId.
*/
DROP PROCEDURE IF EXISTS sp_CreateEmployee;
DELIMITER $$
CREATE PROCEDURE sp_CreateEmployee( p_LastName VARCHAR(255) , p_FirstName VARCHAR(255)
									, p_DepartmentId VARCHAR(20), p_StartDate DATE)
BEGIN
	/* Create a variable to hold the new employee ID */
	DECLARE v_employeeId VARCHAR(20);
	
	/* Lock the employee table so we can get a new employee ID without conflict */
	/*LOCK TABLES employee WRITE; */
	
	/* Get the highest numeric value and add 1 to it */
	SELECT MAX(CAST(EmployeeId AS UNSIGNED)) + 1 INTO v_employeeId
	FROM employee
	WHERE EmployeeId REGEXP('^[0-9]') 
	;
	
	/* Insert the new values */
	INSERT INTO employee(
		EmployeeId
		, LastName
		, FirstName
		, DepartmentId
		, StartDate
	)
	VALUES (
		v_employeeId
		, p_LastName
		, p_FirstName
		, p_DepartmentId
		, p_StartDate
	)
	;
	
	/* Unlock the table so others can use it */
	/* UNLOCK TABLES; */
	
	/* Return the new employee ID */
	SELECT v_employeeId AS 'EmployeeId';
	
END $$
DELIMITER ;

/*
	Updating employees assumes that the employee ID is valid and that all the 
	fields are supplied.  This is the easiest way to update the fields without
	having to check all of them first.
*/
DROP PROCEDURE IF EXISTS sp_UpdateEmployee;
DELIMITER $$
CREATE PROCEDURE sp_UpdateEmployee( p_EmployeeId VARCHAR(10), p_LastName VARCHAR(255)
									, p_FirstName VARCHAR(255), p_DepartmentId VARCHAR(20)
									, p_StartDate DATE)
BEGIN
	DECLARE v_rows INT;
	
	UPDATE employee
	SET LastName = p_LastName
	, FirstName = p_FirstName
	, DepartmentId = p_DepartmentId
	, StartDate = p_StartDate
	WHERE EmployeeId = p_EmployeeId;
	
	/*
		Make sure rows were affected
	*/
	SELECT ROW_COUNT() INTO v_rows;
	
	IF v_rows < 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such employee ID';
	END IF;
    
END $$
DELIMITER ;

/*
	Get all employees who have the specified department ID.  Only returns 
	current employees (TermDate is null or in the future)
*/
DROP PROCEDURE IF EXISTS sp_EmployeesByDepartmentId;
DELIMITER $$
CREATE PROCEDURE sp_EmployeesByDepartmentId( p_Department_ID VARCHAR(20) )
BEGIN
	SELECT 
		EmployeeId
		, LastName
		, FirstName
		, DepartmentId
		, StartDate
		, TermDate
	FROM employee
	WHERE DepartmentId = p_Department_ID
	AND (
		TermDate Is NULL
		OR 
		TermDate > NOW()
	)
	AND isDeleted = 'N'
	;
END $$
DELIMITER ;

/*  
	Get a list of all current employees
*/
DROP PROCEDURE IF EXISTS sp_GetAllEmployees;
DELIMITER $$
CREATE PROCEDURE sp_GetAllEmployees()
BEGIN
	SELECT 
		EmployeeId
		, LastName
		, FirstName
		, DepartmentId
		, StartDate
		, TermDate
	FROM employee
	WHERE (
		TermDate Is NULL
		OR 
		TermDate > NOW()
	)
	AND isDeleted = 'N'
    ORDER BY DepartmentId, LastName, FirstName
	;
END $$
DELIMITER ;

/*
	Deleted employees are not actually deleted.  Instead, they are marked as deleted. 
    This requires that we have a column to keep track of deletions.
	
	The original definition of the Employee table did not include a column for 
	isDeleted, so we have to add that if it hasn't already been added.
    
    There is no good way to do this outside of a procedure, so we ahve to create alter
    procedure, use the procedure and then drop the procedure.
*/
DROP PROCEDURE IF EXISTS sp_TempAddEmployeeIsDeleted;
DELIMITER $$
CREATE PROCEDURE sp_TempAddEmployeeIsDeleted()
BEGIN
	IF NOT EXISTS (
		SELECT * 
		FROM information_schema.COLUMNS
		WHERE TABLE_SCHEMA = 'timeclock'
		AND TABLE_NAME = 'employee'
		AND COLUMN_NAME = 'isDeleted'
	) THEN
		ALTER TABLE employee
		ADD COLUMN isDeleted CHAR(1) DEFAULT 'N'
		AFTER TermDate;
	END IF
	;
END $$
DELIMITER ;
CALL sp_TempAddEmployeeIsDeleted();
DROP PROCEDURE IF EXISTS sp_TempAddEmployeeIsDeleted;


DROP PROCEDURE IF EXISTS sp_DeleteEmployee;
DELIMITER $$
CREATE PROCEDURE sp_DeleteEmployee( p_EmployeeId VARCHAR(10) )
BEGIN
	DECLARE v_rows INT;
	
	UPDATE employee
	SET isDeleted = 'Y' 
	WHERE EmployeeId = p_EmployeeId;
	
	/*
		Make sure rows were affected
	*/
	SELECT ROW_COUNT() INTO v_rows;
	
	IF v_rows < 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such employee ID';
	END IF;
	
END $$
DELIMITER ;


/*
	Terminate an employee
*/
DROP PROCEDURE IF EXISTS sp_TerminateEmployee;
DELIMITER $$
CREATE PROCEDURE sp_TerminateEmployee(p_EmployeeId VARCHAR(20), p_TermDate DATE)
BEGIN
	DECLARE v_rows INT;
	
	UPDATE employee
	SET TermDate = p_TermDate
	WHERE EmployeeId = p_EmployeeId;
	
	/*
		Make sure rows were affected
	*/
	SELECT ROW_COUNT() INTO v_rows;
	
	IF v_rows < 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such employee ID';
	END IF;
	
END $$
DELIMITER ;

/*
	Get all departments
*/
DROP PROCEDURE IF EXISTS sp_GetAllDepartments;
DELIMITER $$
CREATE PROCEDURE sp_GetAllDepartments()
BEGIN
	SELECT 
		department.DepartmentId
		, department.DepartmentName
		, department.ManagerId
		, employee.LastName
		, employee.FirstName
	FROM department
	LEFT OUTER JOIN employee
	ON department.ManagerId = employee.EmployeeId
	ORDER BY
		DepartmentId
	;
END $$
DELIMITER ;

/*
	Get department be department ID.
	This one returns more than just the department name, so it is more complex
	than just the function.
*/
DROP PROCEDURE IF EXISTS sp_GetDepartmentById;
DELIMITER $$
CREATE PROCEDURE sp_GetDepartmentById(P_DepartmentId VARCHAR(20))
BEGIN
	SELECT 
		department.DepartmentId
		, department.DepartmentName
		, department.ManagerId
		, employee.LastName
		, employee.FirstName
	FROM department
	LEFT OUTER JOIN employee
	ON department.ManagerId = employee.EmployeeId
	WHERE department.DepartmentId = p_DepartmentId
	;
END $$
DELIMITER ;

/*
	Assign an employee to be the manager of a department
*/
DROP PROCEDURE IF EXISTS sp_AssignManagerToDepartment;
DELIMITER $$
CREATE PROCEDURE sp_AssignManagerToDepartment( p_DepartmentId VARCHAR(20)
												, p_ManagerId VARCHAR(20))
BEGIN
	/* Make sure the department ID is valid*/
	IF NOT EXISTS (SELECT * FROM department WHERE DepartmentId = p_DepartmentId) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such Department ID';
	END IF;
	
	/* Make sure the manager ID is valid*/
	IF NOT EXISTS (SELECT * FROM employee WHERE EmployeeId = p_ManagerId) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such Manager ID';
	END IF;
	
	/* Update the department record */
	UPDATE department
	SET ManagerId = p_ManagerId
	WHERE DepartmentId = p_DepartmentId
	;
		
END $$
DELIMITER ;

/*
	Insert a TimePunch record.
	The employee ID must exist, not be terminated, and not be deleted.
	The punchTime must not be in the future.
	The record is assumed to be valid at this time.
*/
DROP PROCEDURE IF EXISTS sp_RecordTimePunch;
DELIMITER $$
CREATE PROCEDURE sp_RecordTimePunch(p_EmployeeId VARCHAR(20), p_PunchTime DATETIME
									, p_Source VARCHAR(255) )
BEGIN
	/* Make sure the employee is valid */
	IF NOT EXISTS (SELECT * FROM employee 
					WHERE EmployeeId = p_EmployeeId 
					AND (TermDate IS NULL 
						OR
						TermDate > NOW()
						)
					AND isDeleted = 'N'
				) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such Employee ID';
	END IF;
	
	/* Make sure the time is not in the future */
	IF p_PunchTime > NOW() THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Punch Time cannot be in the future';
	END IF;
	
	/* Insert the new record */
	INSERT INTO TimePunch( 
		EmployeeId
		, PunchTime
		, Source
		, Valid
	)
	VALUES (
		p_EmployeeId
		, p_PunchTime
		, p_Source
		, 'Y'
	)
	;
END $$
DELIMITER ;

/*
	Invalidate a time punch record.
	This would be used if someone punched in incorrectly and needed it fixed
*/
DROP PROCEDURE IF EXISTS sp_InvalidateTimePunch;
DELIMITER $$
CREATE PROCEDURE sp_InvalidateTimePunch(p_EmployeeId VARCHAR(20)
										, p_PunchTime DATETIME
										, p_InvalidatedById VARCHAR(20)
										, p_InvalidatedReason VARCHAR(1024) )
BEGIN
	DECLARE v_rows INT;
	
	UPDATE TimePunch
	SET Valid = 'N'
	, InvalidatedDate = NOW()
	, InvalidatedBy = p_InvalidatedById
	, InvalidatedReason = p_InvalidatedReason
	WHERE EmployeeId = p_EmployeeId
	AND PunchTime = p_PunchTime
	;
	
	/*
		Make sure rows were affected
	*/
	SELECT ROW_COUNT() INTO v_rows;
	
	IF v_rows < 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such Time Punch record';
	END IF;
END $$
DELIMITER ;

/*
	Get a list of time punch records by date range.
	Only returns records that are marked as valid.
*/
DROP PROCEDURE IF EXISTS sp_TimePunchByDateRange;
DELIMITER $$
CREATE PROCEDURE sp_TimePunchByDateRange( p_StartDate DATETIME, p_EndDate DATETIME)
BEGIN
	SELECT 
		EmployeeId
		, PunchTime
		, Source
	FROM timepunch
	WHERE PunchTime BETWEEN p_StartDate AND p_EndDate
	AND Valid = 'Y'
	ORDER BY PunchTime
	;
END $$
DELIMITER ;

/*
	Get the time punch records for a particular employee and date range, ordered
	by date.
	This will return invalidated records as well as valid ones.
*/
DROP PROCEDURE IF EXISTS sp_TimePunchByEmployeeAndDateRange;
DELIMITER $$
CREATE PROCEDURE sp_TimePunchByEmployeeAndDateRange( p_EmployeeId VARCHAR(20)
													, p_StartDate DATETIME
													, p_EndDate DATETIME)
BEGIN
	SELECT 
		timepunch.EmployeeId
		, E1.LastName
		, E1.FirstName
		, timepunch.PunchTime
		, timepunch.Source
		, timepunch.Valid
		, timepunch.InvalidatedDate
		, timepunch.InvalidatedBy
		, E2.LastName
		, E2.FirstName
		, timepunch.InvalidatedReason
	FROM timepunch
	LEFT OUTER JOIN employee E1
	ON timepunch.EmployeeId = E1.EmployeeId
	LEFT OUTER JOIN employee AS E2
	ON timepunch.InvalidatedBy = E2.EmployeeId
	WHERE timepunch.Employee_Id = p_EmployeeId
	AND timepunch.PunchTime BETWEEN p_StartDate AND p_EndDate
	ORDER BY PunchTime
	;
END $$
DELIMITER ;

/* *****************************************************************************
                                 END OF SCRIPT
***************************************************************************** */
