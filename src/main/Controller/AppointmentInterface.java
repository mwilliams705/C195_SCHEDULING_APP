package main.Controller;

import main.Model.Appointment;

import java.time.LocalDateTime;

/**
 * Appointment Functional Interface
 */
public interface AppointmentInterface {
    Appointment newAppointment(String Type,LocalDateTime start, LocalDateTime end);
}
