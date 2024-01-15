package models;
import java.util.List;

// POJO for employee details
public class EmployeeDetails {
  private String positionId;
  private String employeeName;
  private List<ShiftDetails> shiftDetailsList;

  public EmployeeDetails() {
  }

  public String getPositionId() {
    return positionId;
  }

  public void setEmployeeId(String positionId) {
    this.positionId = positionId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public List<ShiftDetails> getShiftDetailsList() {
    return shiftDetailsList;
  }

  public void setShiftDetailsList(List<ShiftDetails> shiftDetailsList) {
    this.shiftDetailsList = shiftDetailsList;
  }
}
