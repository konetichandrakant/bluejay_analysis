package models;

// POJO for employee details
public class ShiftDetails {
  private String shiftStartDate;
  private String shiftEndDate;
  private String shiftTime;

  public String getShiftStartDate() {
    return shiftStartDate;
  }

  public void setShiftStartDate(String shiftStartDate) {
    this.shiftStartDate = shiftStartDate;
  }

  public String getShiftEndDate() {
    return shiftEndDate;
  }

  public void setShiftEndDate(String shiftEndDate) {
    this.shiftEndDate = shiftEndDate;
  }

  public String getShiftTime() {
    return shiftTime;
  }

  public void setShiftTime(String shiftTime) {
    this.shiftTime = shiftTime;
  }
}
