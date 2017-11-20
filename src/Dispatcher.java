import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dispatcher extends Thread{
    private MainMemory memory;
        private Scheduler scheduler;
    private PriorityQueue<PCB> readyProcesses; // All new readyProcesses go here
    private int pause = 0;
    private Thread thread;

    public Dispatcher(MainMemory memory, Scheduler scheduler) {
        this.memory = memory;
        this.readyProcesses = new PriorityQueue<PCB>();
        this.scheduler = scheduler;
    }

    public String displayProcesses() {
        Iterator it = readyProcesses.iterator();
        String output = "";
        if (readyProcesses.isEmpty()) {
            return "No process loaded";
        } else {
            while (it.hasNext()) {
                output = output + it.next().toString();
            }
            return "Displaying all readyProcesses:\n" + output;
        }
    }

    public synchronized void dispatch(PCB process) {
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
                    // add to whatever queue
                }
                ;
                break;

            // READY
            case 1:
                scheduler.addPCB(process);
                break;

            // RUNNING
            case 2:
                break;

            // WAITING/BLOCKED
            case 3:
                break;

            // TERMINATING
            case 4:
                memory.deallocateMemory(process.getPagesUsed());
                break;
        }
    }

    public String start(int n) {
        scheduler.setPauseCycle(n);
        for (PCB readyProcess : this.readyProcesses) {
            scheduler.addPCB(readyProcess);
        }
        scheduler.run();
        System.out.println(this.readyProcesses);
        for (PCB readyProcess : this.readyProcesses) {
            this.dispatch(readyProcess);
            if(readyProcess.getState() == 4){
                this.readyProcesses.remove(readyProcess);
            }
        }
        System.out.println(this.readyProcesses);
        //scheduler.finish();
        return "Done";
    }

    public int getAmount() {
        return readyProcesses.size();
    }


}