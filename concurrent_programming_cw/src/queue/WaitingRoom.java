package queue;

import enums.Speciality;
import model.Patient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitingRoom {
    private final Map<Speciality, BlockingQueue<Patient>> queue;

    public WaitingRoom() {
        queue = new HashMap<>();
        for (Speciality s : Speciality.values()) {
            queue.put(s, new LinkedBlockingQueue<>());
        }
    }

    public void addPatient(Patient patient) throws InterruptedException{
        this.queue.get(patient.getSpeciality()).put(patient);
        Speciality speciality = patient.getSpeciality();
        System.out.println("Patient " + patient.getPatientId() + " added to the " + speciality +" queue ( Queue size: " + getQueueSize(speciality) + ")");
    }

    public Patient takePatient(Speciality speciality) throws InterruptedException{
        return  this.queue.get(speciality).take();
    }

    public int getQueueSize(Speciality speciality){
        return this.queue.get(speciality).size();
    }

    public Map<Speciality, BlockingQueue<Patient>> getQueue() {
        return queue;
    }
}
