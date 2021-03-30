package main.Model;

import main.Exception.ValidationException;

import java.sql.Timestamp;

public class Appointment {
    private int apptId;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private int apptContact;
    private String apptType;
    private Timestamp apptStart;
    private Timestamp apptEnd;
    private int apptCustomerId;

    public Appointment(int apptId, String apptTitle, String apptDesc, String apptLocation, int apptContact, String apptType, Timestamp apptStart, Timestamp apptEnd, int apptCustomerId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptContact = apptContact;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptCustomerId = apptCustomerId;
    }

    public Appointment(String apptTitle, String apptDesc, String apptLocation, int apptContact, String apptType, Timestamp apptStart, Timestamp apptEnd, int apptCustomerId) {
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptContact = apptContact;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptCustomerId = apptCustomerId;
    }

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public String getApptDesc() {
        return apptDesc;
    }

    public void setApptDesc(String apptDesc) {
        this.apptDesc = apptDesc;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public int getApptContact() {
        return apptContact;
    }

    public void setApptContact(int apptContact) {
        this.apptContact = apptContact;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public Timestamp getApptStart() {
        return apptStart;
    }

    public void setApptStart(Timestamp apptStart) {
        this.apptStart = apptStart;
    }

    public Timestamp getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(Timestamp apptEnd) {
        this.apptEnd = apptEnd;
    }

    public int getApptCustomerId() {
        return apptCustomerId;
    }

    public void setApptCustomerId(int apptCustomerId) {
        this.apptCustomerId = apptCustomerId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "apptId=" + apptId +
                ", apptTitle='" + apptTitle + '\'' +
                ", apptDesc='" + apptDesc + '\'' +
                ", apptLocation='" + apptLocation + '\'' +
                ", apptContact=" + apptContact +
                ", apptType='" + apptType + '\'' +
                ", apptStart=" + apptStart +
                ", apptEnd=" + apptEnd +
                ", apptCustomerId=" + apptCustomerId +
                '}';
    }

    /**
     * Validation method
     * @return true if no exception is thrown. Otherwise, alert the user (Managed by the controllers)
     * @throws ValidationException
     */
    public boolean isValid() throws ValidationException {
//        Title Required
        if (getApptTitle().equals("")) {
            throw new ValidationException("The title field cannot be empty.");
        }

//        Description Required
        if (getApptDesc().equals("")) {
            throw new ValidationException("The description field cannot be empty.");
        }

//        Location Required
        if (getApptLocation().equals("")) {
            throw new ValidationException("The location field cannot be empty");
        }

        // min must be positive
        if (getApptType().equals("")) {
            throw new ValidationException("The type field cannot be empty.");
        }

        if (getApptStart().after(getApptEnd()) ){
            throw new ValidationException("The appointment start time cannot be before the end time");
        }

        if (getApptEnd().before(getApptStart())){
            throw new ValidationException("The appointment end time cannot be after the start time");
        }

        return true;
    }
}
