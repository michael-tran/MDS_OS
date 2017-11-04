import java.util.Iterator;
import java.util.PriorityQueue;

public class Scheduler {
    private final int QUANTUM = 15;
    private PriorityQueue<PCB> PCBs;
    private MainMemory memory;

    public Scheduler(MainMemory memory) {
        this.PCBs = new PriorityQueue<PCB>();
        this.memory = memory;
    }

    public PriorityQueue<PCB> getPCBs() {
        return PCBs;
    }


    @Override
    public String toString() {
        return "Scheduler{" +
                "PCBs=" + PCBs +
                '}';
    }


}