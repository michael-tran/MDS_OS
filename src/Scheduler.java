import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

public class Scheduler {
    private CPU cpu;
    private final int QUANTUM = 15;
    private PriorityQueue<PCB> PCBs;
    private MainMemory memory;

    public Scheduler(MainMemory memory) {
        cpu = new CPU();
        this.PCBs = new PriorityQueue<PCB>();
        this.memory = memory;
    }

    public PriorityQueue<PCB> getPCBs() {
        return PCBs;
    }

    public void start(PriorityQueue<PCB> pcbs, int n){
        Stack<PCB> pancake = new Stack<PCB>();
        cpu.setPauseCycles(n);
        for (PCB pcb: pcbs) {
            cpu.startProcess(pcb, QUANTUM);
        }
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "PCBs=" + PCBs +
                '}';
    }


}