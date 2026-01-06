package queue;

import enums.Speciality;
import model.Patient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitingRoom {
    private BlockingQueue<Patient> queue;
    private Speciality speciality;

    public WaitingRoom(Speciality speciality){
        this.queue = new LinkedBlockingQueue<>();
        this.speciality = speciality;
    }

    public void addPatient(Patient patient) throws InterruptedException{
        this.queue.put(patient);
        System.out.println("Patient " + patient.getPatientId() + " added to the " + this.speciality+" queue ( Queue size: " + getQueueSize() + ")");
    }

    public Patient takePatient() throws InterruptedException{
        return  this.queue.take();
    }

    public int getQueueSize(){
        return this.queue.size();
    }
}
