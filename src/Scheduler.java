import java.util.*;

public class Scheduler extends Thread{
    private CPU cpu;
    private final int QUANTUM = 15;
    private PriorityQueue<PCB> PCBs;
    private MainMemory memory;
    private Queue<PCB> pancake = new LinkedList<PCB>();
    private Thread thread;
    private String threadName;

    public Scheduler(MainMemory memory, String threadName) {
        cpu = new CPU();
        this.PCBs = new PriorityQueue<PCB>();
        this.memory = memory;
        this.threadName = threadName;
    }

    public void run() {
        System.out.println("Thread " + threadName + " running");
        try {


            thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    public void start() {
        thread.start();
    }

    public void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    public PriorityQueue<PCB> getPCBs() {
        return PCBs;
    }

    public void start(PCB pcb) {
        boolean done = cpu.startProcess(pcb, QUANTUM);
        if (done) {
            pancake.add(pcb);
        }
    }

    public void finish() {
        while (!pancake.isEmpty()) {
            boolean done = cpu.startProcess(pancake.peek(), QUANTUM);
            if (done) {
                pancake.add(pancake.remove());
            }
        }
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "PCBs=" + PCBs +
                '}';
    }
}