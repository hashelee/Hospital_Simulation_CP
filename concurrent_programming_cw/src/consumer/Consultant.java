package consumer;

import enums.ShiftType;
import enums.Speciality;
import model.Patient;
import queue.WaitingRoom;

import java.util.Random;

public class Consultant implements Runnable {
    private final String name;
    private final Speciality speciality;
    private final ShiftType shift;
    private final WaitingRoom waitingRoom;
    private volatile boolean running;
    private int patientsServed;
    private final Random random;

    public Consultant(String name, Speciality speciality, ShiftType shift, WaitingRoom waitingRoom) {
        this.name = name;
        this.speciality = speciality;
        this.shift = shift;
        this.waitingRoom = waitingRoom;
        this.running = true;
        this.patientsServed = 0;
        this.random = new Random();
    }

    public void run() {
        System.out.println(speciality.name() +" " + name + " started " + shift.name() + " shift");

        while (running) {
            try {
                Patient patient = waitingRoom.takePatient(speciality);

                double waitingTime = (System.currentTimeMillis() - patient.getArrivalTime()) / 1000;

                System.out.println(speciality.name() +" " + name + " is treating patient: " + patient.getPatientId()+" . Patient Waiting time was " + waitingTime + " s");

                int treatmentTime = 1000 + random.nextInt(2000);
                Thread.sleep(treatmentTime);

                patientsServed++;
                System.out.println( speciality.name() +" " + name + " finished treating patient: " + patient.getPatientId() + ". Served: " + patientsServed);

            } catch (InterruptedException e) {
                System.out.println(speciality.name() +" " + name + " shift ended. Patients served: " + patientsServed);
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public String getName() {
        return name;
    }

    public int getPatientsServed() {
        return patientsServed;
    }
}
