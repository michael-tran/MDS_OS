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
        boolean done = cpu.startProcess(pcb, QUANTUM);
        if (!done) pancake.add(pcb);
    }

    public void finish() {
        while (!pancake.isEmpty()) {
            boolean done = cpu.startProcess(pancake.peek(), QUANTUM);
            if (!done) pancake.add(pancake.remove());
        }
    }

    public void reset() {
        pancake.clear();
    }
}