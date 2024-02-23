package com.example.lavanya;

public class Appointment_Scheduler {
    public int appointmentId;
    public String appointmentDate;
    public String doctor;
    public String patientName;


    public Appointment_Scheduler(int appointmentId, String appointmentDate, String doctor, String patientName) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.doctor = doctor;
        this.patientName = patientName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


}
