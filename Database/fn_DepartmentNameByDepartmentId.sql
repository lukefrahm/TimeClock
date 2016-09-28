/*
	FILE: fn_DepartmentNameByDepartmentId.sql
	DATE: 2015-10-19
	AUTHOR: Bob Trapp
	TARGET DBMS: MySQL
	DESCRIPTION:	Creates a function that returns the full department name 
					associated with the supplied department ID.
					
	EXAMPLE RUN(S):
	SELECT fn_DepartmentNameByDepartmentId('sales') AS 'Department Name';
	-- returns 'Sales Department'
	
	SELECT fn_DepartmentNameByDepartmentId('penguin') AS 'Department Name';
	-- Returns NULL because there is no department with id of 'penguin'
					
*/

USE TimeClock;

DELIMITER $$
CREATE FUNCTION `fn_DepartmentNameByDepartmentId`( p_DeparmentId VARCHAR(20)) 
RETURNS varchar(255)
BEGIN
	/* Create a variable to hold the department name until we are ready to return it */
	DECLARE v_result VARCHAR(255);

	/* Get the department name, if it exists */
	SELECT DepartmentName INTO v_result
	FROM department
	WHERE DepartmentId = p_DeparmentId
	;

	/* return the departmentName */
    RETURN v_result;
END$$
DELIMITER ;