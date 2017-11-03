import java.util.Iterator;
import java.util.PriorityQueue;

public class Dispatcher {
    private CPU cpu;
    private MainMemory memory;
    private PriorityQueue<PCB> processes; // All new processes go here

    public Dispatcher(CPU cpu, MainMemory memory) {
        this.cpu = cpu;
        this.memory = memory;
        this.processes = new PriorityQueue<PCB>();
    }

    public void addProcess(PCB process) {
        memory.allocateMemory(process.getMemoryRequirement());
        processes.add(process);
    }

    public String displayProcesses() {
        Iterator it = processes.iterator();
        String output = "";
        if (processes.isEmpty()) {
            return "No processes loaded";
        } else {
            while (it.hasNext()) {
                it.next().toString();
            }
            return "Displaying all processes:\n" + output;
        }
    }
}