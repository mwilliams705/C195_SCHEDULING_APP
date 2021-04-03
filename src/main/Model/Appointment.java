package main.Model;

import main.Exception.BusinessHoursException;
import main.Exception.ValidationException;
import main.Util.TimeConverter;

import java.sql.Timestamp;
import java.time.*;

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

    public Appointment() {
    }

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
                ", apptStart=" + apptStart +
                ", apptEnd=" + apptEnd +
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

    public boolean isValidTime() throws BusinessHoursException {
//        Local DateTime
        LocalDate apptStartDate = this.apptStart.toLocalDateTime().toLocalDate();
        LocalTime apptStartTime = this.apptStart.toLocalDateTime().toLocalTime();
        LocalDate apptEndDate = this.apptEnd.toLocalDateTime().toLocalDate();
        LocalTime apptEndTime = this.apptEnd.toLocalDateTime().toLocalTime();
//        EST DateTime
        LocalDateTime apptStartToEST = TimeConverter.localToEST(this.apptStart.toLocalDateTime());
        LocalDateTime apptEndToEST = TimeConverter.localToEST(this.apptEnd.toLocalDateTime());

        System.out.print("Local Start--");
        System.out.println(apptStart.toLocalDateTime());
        System.out.print("Local End----");
        System.out.println(apptEnd.toLocalDateTime());
        System.out.print("EST Start----");
        System.out.println(apptStartToEST);
        System.out.print("EST End------");
        System.out.println(apptEndToEST);



        LocalDateTime midnightLocalDateTime = LocalDateTime.of(apptStartDate,LocalTime.MIDNIGHT);
        LocalDateTime afterConversion = TimeConverter.localToEST(midnightLocalDateTime);
        LocalTime EST_Midnight = afterConversion.toLocalTime();


        int weekDay = apptStartDate.getDayOfWeek().getValue();

        if (!apptStartDate.isEqual(apptEndDate)) {
            throw new BusinessHoursException("An appointment can only be a single day.");
        }
        if (weekDay == 6 || weekDay == 7) {
            throw new BusinessHoursException("An appointment can only be scheduled on weekdays.");
        }
        if (apptStartTime.equals(apptEndTime)){
            throw new BusinessHoursException(("An appointment end time cannot be the same as its start"));
        }

        if (apptStart.before(Timestamp.valueOf(LocalDateTime.of(apptStartDate,EST_Midnight).plusHours(8)))){
            throw new BusinessHoursException("An appointment cannot be scheduled before normal business hours.");
        }
        if (apptEnd.after(Timestamp.valueOf(LocalDateTime.of(apptEndDate,EST_Midnight).plusHours(22)))){
            throw new BusinessHoursException("An appointment cannot be scheduled after normal business hours.");
        }

        if (apptStartDate.isBefore(LocalDate.now()) || apptStartTime.isBefore(LocalTime.MIDNIGHT)) {
            throw new BusinessHoursException("An appointment cannot be scheduled in the past!");
        }
        return true;
    }
}
