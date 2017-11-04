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

    public void addPCB(PCB process) {
        memory.allocateMemory(process.getMemoryRequirement());
        PCBs.add(process);
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "PCBs=" + PCBs +
                '}';
    }

    public String displayProcesses() {
        Iterator it = PCBs.iterator();
        String output = "";
        if (PCBs.isEmpty()) {
            return "No processes loaded";
        } else {
            while (it.hasNext()) {
                output = output + it.next().toString();
            }
            return "Displaying all processes:\n" + output;
        }
    }
}