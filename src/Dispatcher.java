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
    }

    public String displayProcesses() {
        Iterator it = processes.iterator();
        String output = "";
        if (processes.isEmpty()) {
            return "No processes loaded";
        } else {
            while (it.hasNext()) {
                output = output + it.next().toString();
            }
            return "Displaying all processes:\n" + output;
        }
    }

    public void dispatch(PCB process) {
        switch (process.getState()) {
            // NEW
            case 0:
                if(memory.allocateMemory(process.getMemoryRequirement())) {
                    processes.add(process);
                    process.setState(1);
                    // adds to waiting queue?
                } else {
                    process.setPriority(process.getPriority() - 1);
                };
                break;

            // READY
            case 1:
                break;

            // RUNNING
            case 2:
                break;

            // WAITING/BLOCKED
            case 3:
                break;

            // TERMINATING
            case 4:
                memory.deallocateMemory(process.getMemoryRequirement());
                break;
        }
    }
}