package manager;

import consumer.Consultant;
import enums.ShiftType;
import enums.Speciality;
import queue.WaitingRoom;

import java.util.HashMap;
import java.util.Map;

public class ShiftManager {
    private final WaitingRoom waitingRoom;

    public ShiftManager(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public Map<Consultant, Thread> startShift(ShiftType shift) {
        System.out.println("============================================");
        System.out.println("Starting Shift: " + shift.name().toUpperCase());
        System.out.println("============================================");

        Map<Consultant, Thread> consultantMap = new HashMap<>();

        for (Speciality s : Speciality.values()) {
            Consultant consultant = new Consultant("Dr." + s.name(), s, shift, waitingRoom);

            Thread thread = new Thread(consultant, s.name() + " Thread");

            consultantMap.put(consultant, thread);

            thread.start();
        }

        return consultantMap;
    }

    public void endShift(Map<Consultant, Thread> consultantMap, ShiftType shift) {
        System.out.println("=================================================");
        System.out.println("   ENDING SHIFT: " + shift.name().toUpperCase());
        System.out.println("=================================================");

        for (Consultant consultant : consultantMap.keySet()) {
            consultant.stop();
        }

        for (Thread consultantThread : consultantMap.values()) {
            try {
                consultantThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("[Error] Thread interruption during handover for " + shift.name());
            }
        }

        System.out.println("[System] " + shift.name() + " Shift Complete: All consultants have clocked out successfully.");
        System.out.println("=================================================\n");
    }
}