import java.util.Iterator;
import java.util.PriorityQueue;

public class Dispatcher {
    private MainMemory memory;
    private Scheduler scheduler;
    private PriorityQueue<PCB> readyProcesses; // All new readyProcesses go here
    private PriorityQueue<PCB> otherProcesses; // All new readyProcesses go here

    public Dispatcher(CPU cpu, MainMemory memory) {
        this.memory = memory;
        this.readyProcesses = new PriorityQueue<PCB>();
        this.scheduler = new Scheduler(memory);
    }

    public void addProcess(PCB process) {
    }

    public String displayProcesses() {
        Iterator it = readyProcesses.iterator();
        String output = "";
        if (readyProcesses.isEmpty()) {
            return "No readyProcesses loaded";
        } else {
            while (it.hasNext()) {
                output = output + it.next().toString();
            }
            return "Displaying all readyProcesses:\n" + output;
        }
    }

    public void dispatch(PCB process) {
        switch (process.getState()) {
            // NEW
            case 0:
                    if(memory.allocateMemory(process.getMemoryRequirement(), process.getPid())) {
                    process.setState(1);
                    readyProcesses.add(process);
                } else {
                    process.setPriority(process.getPriority() - 1);
                    otherProcesses.add(process);
                    // add to whatever queue
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
                memory.deallocateMemory(process.getMemoryRequirement(), process.getPid());
                break;
        }
    }

    public void start (int n){
        for (PCB readyProcess : this.readyProcesses) {
            scheduler.start(readyProcess,n);
        }
        int count = 0;
        for (PCB readyProcess : this.readyProcesses) {
            if (count ==1){
                readyProcess.setState(4);
                dispatch(readyProcess);
            }
            count++;
        }
    }

    public int getAmount(){
       return readyProcesses.size();
    }
}