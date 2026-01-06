import consumer.Consultant;
import enums.ShiftType;
import enums.Speciality;
import manager.ShiftManager;
import producer.PatientGenerator;
import queue.WaitingRoom;

import java.util.Map;

public class HospitalSimulator {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   ROYAL MANCHESTER HOSPITAL SIMULATION SYSTEM   ");
        System.out.println("=================================================");
        WaitingRoom waitingRoom = new WaitingRoom();

        System.out.println("[System] Initializing Patient Generator Service...");
        PatientGenerator patientGenerator = new PatientGenerator(waitingRoom);
        Thread patientGeneratorThread = new Thread(patientGenerator);
        patientGeneratorThread.start();
        System.out.println("[System] Patient Generator is active. Patients are arriving.");


        ShiftManager shiftManager = new ShiftManager(waitingRoom);

        // 12 seconds per shift (represents 12 hours)
        int shiftDuration = 12000;

        for(ShiftType shiftType : ShiftType.values()){
             Map<Consultant,Thread> consultants = shiftManager.startShift(shiftType);

            try{
                Thread.sleep(shiftDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            shiftManager.endShift(consultants, shiftType);
        }

        System.out.println("\n=================================================");
        System.out.println("[System] Simulation cycle complete. Initiating shutdown...");
        patientGenerator.stop();

        patientGeneratorThread.interrupt();

        try {
            patientGeneratorThread.join();
            System.out.println("[System] Patient Generator stopped successfully.");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("[System] Hospital System Shutdown Complete.");
        System.out.println("=================================================");

        System.out.println("\n================================================");
        System.out.println("           SIMULATION COMPLETE");
        System.out.println("================================================");
        for (Speciality s : waitingRoom.getQueue().keySet()) {
            System.out.println("Remaining in "+s+" Queue: " + waitingRoom.getQueueSize(s));
        }
        System.out.println("================================================\n");
    }
}