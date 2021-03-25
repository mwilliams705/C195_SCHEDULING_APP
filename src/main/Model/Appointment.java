package main.Model;

public class Appointment {
    private int apptId;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private int apptContact;
    private String apptType;
    private String apptStart;
    private String apptEnd;
    private int apptCustomerId;

    public Appointment(int apptId, String apptTitle, String apptDesc, String apptLocation, int apptContact, String apptType, String apptStart, String apptEnd, int apptCustomerId) {
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

    public Appointment(String apptTitle, String apptDesc, String apptLocation, int apptContact, String apptType, String apptStart, String apptEnd, int apptCustomerId) {
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

    public String getApptStart() {
        return apptStart;
    }

    public void setApptStart(String apptStart) {
        this.apptStart = apptStart;
    }

    public String getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(String apptEnd) {
        this.apptEnd = apptEnd;
    }

    public int getApptCustomerId() {
        return apptCustomerId;
    }

    public void setApptCustomerId(int apptCustomerId) {
        this.apptCustomerId = apptCustomerId;
    }
}
