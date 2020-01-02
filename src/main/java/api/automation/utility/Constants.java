package api.automation.utility;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	//Variables for logging
	public static String class_Name = null;
	public static String method_Name = null;
	
	//Variables to be used for capturing and storing the start and end time of each of the test cases
	public static double testcase_Start_Time = 0, testcase_End_Time = 0;
	
	public static Map<String, String> test_Time_Details = new HashMap<String, String>();
	
	/**
	 * Function to calculate time taken to execute test case
	 * @return duration of time
	 */
	public static double getTestcaseTimeDiff() {
		return (testcase_End_Time-testcase_Start_Time)/1000;			
	}
}
