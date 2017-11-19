import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler extends Thread {
    private CPU cpu;
    private final int QUANTUM = 15;
    private Queue<PCB> pancake = new LinkedList<>();
    private Thread thread;
    private String threadName;

    public Scheduler(CPU cpu, String threadName) {
        this.cpu = cpu;
        this.threadName = threadName;
    }

    public void addPCB(PCB pcb) {
        pancake.add(pcb);
    }

    public void run() {
        System.out.println("Thread " + threadName + " running");
        try {
            while (true) {
                if (pancake.size() > 0 && !cpu.isOccupied()) {
                    PCB temp = pancake.poll();
                    System.out.println("Scheduling " + temp.getName());
                    start(temp);
                } else {
                    System.out.println(cpu.getClock().getClockCycle());
                    System.out.println("Waiting for processes");
                    break;
                }
                thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    public void start(PCB pcb) {
        int done = cpu.startProcess(pcb, QUANTUM);
        switch (done){
            case -1:
                break;
            case 0:
            case 1:
            case 2:
                pancake.add(pcb);
                break;
            case 3:
                break;
        }
    }

//    public void finish() {
//        while (!pancake.isEmpty()) {
//            int done = cpu.startProcess(pancake.peek(), QUANTUM);
//            switch (done){
//                case -1:
//                    break;
//                case 0:
//                    break;
//                case 1:
//                    pancake.add(pancake.remove());
//                    break;
//            }
//        }
//    }

    public void reset() {
        pancake.clear();
    }
}