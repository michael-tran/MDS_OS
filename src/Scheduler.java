import java.util.*;

public class Scheduler {
    private CPU cpu;
    private final int QUANTUM = 15;
    private PriorityQueue<PCB> PCBs;
    private MainMemory memory;
    private Queue<PCB> pancake = new LinkedList<PCB>();

    public Scheduler(MainMemory memory) {
        cpu = new CPU();
        this.PCBs = new PriorityQueue<PCB>();
        this.memory = memory;
    }

    public void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    public PriorityQueue<PCB> getPCBs() {
        return PCBs;
    }

    public void start(PCB pcb) {
        boolean done = cpu.startProcess(pcb, QUANTUM);
        if (done){
            pancake.add(pcb);
        }
    }

    public void finish() {
        while(!pancake.isEmpty()){
           boolean done = cpu.startProcess(pancake.peek(), QUANTUM);
            if (done){
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