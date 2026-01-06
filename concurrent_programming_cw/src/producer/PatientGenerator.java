package producer;

import enums.Speciality;
import model.Patient;
import queue.WaitingRoom;

import java.util.Random;

public class PatientGenerator implements Runnable {
    private WaitingRoom waitingRoom;
    private int patientNumber;
    private volatile boolean running;
    private Random random;

    public PatientGenerator(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
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

                waitingRoom.addPatient(patient);

                int delay = 500 + random.nextInt(2000);
                Thread.sleep(delay);

            }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[System] Generator interrupted. Stopping before creating Patient " + patientNumber);
            break;
        }
        }

    }

    public void stop() {
        this.running = false;
    }
}
