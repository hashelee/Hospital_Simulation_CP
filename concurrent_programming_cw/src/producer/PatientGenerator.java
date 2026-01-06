package producer;

import enums.Speciality;
import model.Patient;
import queue.WaitingRoom;

import java.util.Random;

public class PatientGenerator implements Runnable {
    private WaitingRoom paediatricianWaitingRoom;
    private WaitingRoom cardiologistWaitingRoom;
    private WaitingRoom sergeonWaitingRoom;
    private int patientNumber;
    private volatile boolean running;
    private Random random;

    private PatientGenerator(WaitingRoom p, WaitingRoom c, WaitingRoom s) {
        this.paediatricianWaitingRoom = p;
        this.cardiologistWaitingRoom = c;
        this.sergeonWaitingRoom = s;
        this.running = true;
        this.patientNumber = 1;
        this.random = new Random();
    }
    @Override
    public void run() {
        while (running) {
            try {
                Speciality speciality = Speciality.getRandom();
                Patient patient = new Patient(patientNumber++, speciality);

                if(speciality == Speciality.PAEDIATRICIAN){
                    paediatricianWaitingRoom.addPatient(patient);
                } else if (speciality == Speciality.CARDIOLOGIST) {
                    cardiologistWaitingRoom.addPatient(patient);
                } else if (speciality == Speciality.SURGEON) {
                    sergeonWaitingRoom.addPatient(patient);
                }

                int delay = 500 + random.nextInt(2000);
                Thread.sleep(delay);

            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Patient " + patientNumber + " has been interrupted");
                break;
            }
        }

    }

    public void stop() {
        this.running = false;
    }
}
