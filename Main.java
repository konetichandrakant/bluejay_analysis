import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import analysis.Analysis;
import models.EmployeeDetails;
import models.ShiftDetails;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	private static Map<String, EmployeeDetails> employeeDetails;

	// Function for changing the given Time format to MM-dd-yyyy HH:mm:ss
	private static String changeDateTimeFormat(String dateTime) {
		String arr[] = dateTime.split(" ");
		String date = arr[0].replaceAll("/", "-"), time = arr[1];
		if (arr.length == 3 && arr[2].equals("PM"))
			time = Integer.parseInt(time.split(":")[0]) + time.substring(2);
		if (time.length() == 5)
			time += ":00";
		if (time.length() == 4) {
			time = "0" + time;
			time += ":00";
		}
		return date + " " + time;
	}

	// Function for maintaining each unique employee details of their shifts and metadata in hashmap by filtering unwanted data
	private static EmployeeDetails filterDataIntoDetails(String arr[]) {
		if (arr[2].length() == 0 || arr[3].length() == 0 || arr[4].length() == 0 || arr[4].equals("00:00"))
			return null;

		// EmployeeDetails class to maitnain the employee details like name, id, and shift details
		EmployeeDetails details = employeeDetails.getOrDefault(arr[0], new EmployeeDetails());

		details.setEmployeeId(arr[0]);
		details.setEmployeeName(arr[7] + ", " + arr[8].substring(1));

		// ShiftDetails class to maitnain the shift details
		ShiftDetails shiftDetails = new ShiftDetails();
		shiftDetails.setShiftStartDate(changeDateTimeFormat(arr[2]));
		shiftDetails.setShiftEndDate(changeDateTimeFormat(arr[3]));
		shiftDetails.setShiftTime(arr[4].split(" ")[0]);
		List<ShiftDetails> l = details.getShiftDetailsList();
		if (l == null)
			l = new ArrayList<>();
		l.add(shiftDetails);
		details.setShiftDetailsList(l);

		return details;
	}

	public static void main(String args[]) {
		String file = "input/Assignment_Timecard.csv";
		BufferedReader reader = null;
		String line = "";

		// Analysis object to call various functions to analyse data
		Analysis analysis = new Analysis();

		// Map to store details of employees
		employeeDetails = new HashMap<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] arr = line.split(",");
				EmployeeDetails d = filterDataIntoDetails(arr);
				if (d != null) {
					String employeeId = arr[0];
					if (!employeeDetails.containsKey(employeeId))
						employeeDetails.put(employeeId, new EmployeeDetails());
					employeeDetails.put(employeeId, d);
				}
			}
			List<List<String>> employeeListSevenConsecutive = analysis.employeeListSevenConsecutive(employeeDetails);
			System.out.println("Employees who has worked for 7 consecutive days.\n");
			for (List<String> mid : employeeListSevenConsecutive)
				System.out.println("Employee Name: " + mid.get(0) + ", Position ID: " + mid.get(1));
			System.out.println();
			List<List<String>> employeeShiftsDiffFromOneToTenList = analysis
					.employeeShiftsDiffFromOneToTenList(employeeDetails);
			System.out.println("Employees who have less than 10 hours of time between shifts but greater than 1 hour.\n");
			for (List<String> mid : employeeShiftsDiffFromOneToTenList)
				System.out.println("Employee Name: " + mid.get(0) + ", Position ID: " + mid.get(1));
			System.out.println();
			List<List<String>> employeeShiftTimeGreaterThanFourteenList = analysis
					.employeeShiftTimeGreaterThanFourteenList(employeeDetails);
			System.out.println("Employees who has worked for more than 14 hours in a single shift.\n");
			for (List<String> mid : employeeShiftTimeGreaterThanFourteenList)
				System.out.println("Employee Name: " + mid.get(0) + ", Position ID: " + mid.get(1));
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}