package Model;

import java.time.LocalDate;

public class Manager extends Users {
    private String employeeCode;
    private String officeLocation;
    private LocalDate appointedAt;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public LocalDate getAppointedAt() {
        return appointedAt;
    }

    public void setAppointedAt(LocalDate appointedAt) {
        this.appointedAt = appointedAt;
    }
}
