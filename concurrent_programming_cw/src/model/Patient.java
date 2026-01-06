package model;

import enums.Speciality;

public class Patient {
    private final int patientId;
    private final Speciality speciality;
    private final long arrivalTime;

    public Patient(int patientId, Speciality speciality) {
        this.patientId = patientId;
        this.speciality = speciality;
        this.arrivalTime = System.currentTimeMillis();
    }

    public int getPatientId() {
        return patientId;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }
}
