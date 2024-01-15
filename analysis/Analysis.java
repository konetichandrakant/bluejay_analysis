package analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.EmployeeDetails;
import models.ShiftDetails;

public class Analysis {

  // Function to find out number of days between two dates
  private int diffBetweenTwoDates(String s, String t) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    try {
      Date date1 = dateFormat.parse(s);
      Date date2 = dateFormat.parse(t);

      long differenceInMilliseconds = date2.getTime() - date1.getTime();
      long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);

      return (int) differenceInDays;
    } catch (ParseException e) {
      e.printStackTrace();
      return Integer.MAX_VALUE;
    }
  }

  // Function to find out number of hours between two timestamps
  private int diffOfHoursBetweenTwo(String s, String t) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    try {
      Date date1 = dateFormat.parse(s);
      Date date2 = dateFormat.parse(t);

      long differenceInMilliseconds = date2.getTime() - date1.getTime();
      long differenceInHours = differenceInMilliseconds / (60 * 60 * 1000);

      return (int) differenceInHours;
    } catch (ParseException e) {
      e.printStackTrace();
      return Integer.MAX_VALUE;
    }
  }

  // Function to find out number of hours between two timestamps
  private boolean isEmployeeSevenConsecutive(Map<String, EmployeeDetails> employeeDetails, String employeeId) {
    List<ShiftDetails> l = employeeDetails.get(employeeId).getShiftDetailsList();
    int i = 0, cnt = 1;
    while (i < l.size()) {
      int diffDays1 = diffBetweenTwoDates(l.get(i).getShiftStartDate(), l.get(i).getShiftEndDate());
      if (i > 0) {
        int diffDays2 = diffBetweenTwoDates(l.get(i - 1).getShiftEndDate(), l.get(i).getShiftStartDate());
        if (diffDays2 <= 1)
          cnt += diffDays2;
        else
          cnt = 1;
      }
      cnt += diffDays1;
      if (cnt >= 7)
        return true;
      i++;
    }
    return false;
  }

  public List<List<String>> employeeListSevenConsecutive(Map<String, EmployeeDetails> employeeDetails) {
    List<String> positionIds = new ArrayList<>(employeeDetails.keySet());
    Collections.sort(positionIds);
    List<List<String>> employeeList = new ArrayList<>();
    for (String positionId : positionIds) {
      if (isEmployeeSevenConsecutive(employeeDetails, positionId)) {
        employeeList.add(List.of(employeeDetails.get(positionId).getEmployeeName(), positionId));
      }
    }
    return employeeList;
  }

  private boolean isEmployeeShiftsDiffFromOneToTen(Map<String, EmployeeDetails> employeeDetails, String positionId) {
    List<ShiftDetails> shiftDetailsList = employeeDetails.get(positionId).getShiftDetailsList();
    for (int i = 1; i < shiftDetailsList.size(); i++) {
      ShiftDetails shiftDetails1 = shiftDetailsList.get(i - 1);
      ShiftDetails shiftDetails2 = shiftDetailsList.get(i);
      int diffOfHoursBetweenTwoShifts = diffOfHoursBetweenTwo(shiftDetails1.getShiftEndDate(),
          shiftDetails2.getShiftStartDate());
      if (!(diffOfHoursBetweenTwoShifts > 1 && diffOfHoursBetweenTwoShifts < 10))
        return false;
    }
    return true;
  }

  public List<List<String>> employeeShiftsDiffFromOneToTenList(Map<String, EmployeeDetails> employeeDetails) {
    List<List<String>> employeeList = new ArrayList<>();
    List<String> positionIds = new ArrayList<>(employeeDetails.keySet());
    Collections.sort(positionIds);
    for (String positionId : positionIds) {
      if (isEmployeeShiftsDiffFromOneToTen(employeeDetails, positionId)) {
        employeeList.add(List.of(employeeDetails.get(positionId).getEmployeeName(), positionId));
      }
    }
    return employeeList;
  }

  private boolean hasWorkedMoreThanFouteenHours(Map<String, EmployeeDetails> employeeDetails, String positionId) {
    List<ShiftDetails> shiftDetailsList = employeeDetails.get(positionId).getShiftDetailsList();
    for (ShiftDetails shiftDetails : shiftDetailsList) {
      if (Integer.parseInt(shiftDetails.getShiftTime().substring(0, 2)) >= 14)
        return true;
    }
    return false;
  }

  public List<List<String>> employeeShiftTimeGreaterThanFourteenList(Map<String, EmployeeDetails> employeeDetails) {
    List<List<String>> employeeList = new ArrayList<>();
    List<String> positionIds = new ArrayList<>(employeeDetails.keySet());
    Collections.sort(positionIds);
    for (String positionId : positionIds) {
      if (hasWorkedMoreThanFouteenHours(employeeDetails, positionId)) {
        employeeList.add(List.of(employeeDetails.get(positionId).getEmployeeName(), positionId));
      }
    }
    return employeeList;
  }
}
