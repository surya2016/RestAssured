package api.automation.utility;

import java.util.Calendar;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import api.automation.utility.Constants;

public class TestNGListener implements IInvokedMethodListener{

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if((method.getTestMethod().getMethodName()).equalsIgnoreCase("browerOpen")) {
    		Constants.testcase_Start_Time = Calendar.getInstance().getTimeInMillis();
    	}
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if((method.getTestMethod().getMethodName()).equalsIgnoreCase("browserClose")) {
    		Constants.testcase_End_Time = Calendar.getInstance().getTimeInMillis();
    		Constants.test_Time_Details.put(Constants.class_Name, Constants.method_Name+Constants.getTestcaseTimeDiff());
    	}
		
	}
}
