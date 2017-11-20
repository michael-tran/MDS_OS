import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dispatcher implements Runnable {
    private MainMemory memory;
    private Scheduler scheduler;
    private PriorityQueue<PCB> readyProcesses; // All new readyProcesses go here
    private PriorityQueue<PCB> newProcesses; // All unallocated processes go here
    private Thread thread;

    Dispatcher(MainMemory memory, Scheduler scheduler) {
        this.memory = memory;
        this.readyProcesses = new PriorityQueue<>();
        this.newProcesses = new PriorityQueue<>();
        this.scheduler = scheduler;
    }

    public void run() {

    }

    public void start() {

    }

    String displayProcesses() {
        StringBuilder output = new StringBuilder();

        if (!readyProcesses.isEmpty()) {
            output.append("Displaying ready process(es): \n");
            for (PCB readyProcess : this.readyProcesses) {
                output.append(readyProcess.toString());
            }
        }
        if (!newProcesses.isEmpty()) {
            output.append("Displaying new process(es): \n");
            for (PCB newProcess : this.newProcesses) {
                output.append(newProcess.toString());
            }
        }

        return (output.length() == 0) ? "No process loaded" : output.toString();
    }

    void dispatch(PCB process) {
        switch (process.getState()) {
            // NEW
            case 0:
                LinkedList<MainMemory.Page> pagesUsed = memory.allocateMemory(process.getMemoryRequirement());
                if (pagesUsed.size() > 0) {
                    process.setState(1);
                    process.setPagesUsed(pagesUsed);
                    readyProcesses.add(process);
                } else {
                    process.setPriority(process.getPriority() - 1);
                    newProcesses.add(process);
                }
                break;

            // READY
            case 1:
                scheduler.addPCB(process);
                break;

            // TERMINATING
            case 4:
                // How should the scheduler terminate process and deallocate memory?
                memory.deallocateMemory(process.getPagesUsed());
                break;
        }
    }

    void start(int n) {
        scheduler.setPauseCycle(n);
        for (PCB readyProcess : this.readyProcesses) {
            scheduler.addPCB(readyProcess);
        }
        scheduler.run();
        for (PCB readyProcess : this.readyProcesses) {
            this.dispatch(readyProcess);
        }
        this.readyProcesses.removeIf(i -> (i.getState() == 4));
    }

    void reset() {
        readyProcesses.clear();
        newProcesses.clear();
        scheduler.reset();
    }

}