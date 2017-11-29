import java.util.LinkedList;
import java.util.PriorityQueue;

class Dispatcher {
    private MainMemory memory;
    private Scheduler scheduler;
    private PriorityQueue<PCB> mainProcessQueue; // All new mainProcessQueue// go here
    private PriorityQueue<PCB> unallocatedProcessQueue; // All unallocated processes go here

    Dispatcher(MainMemory memory, Scheduler scheduler) {
        this.memory = memory;
        this.mainProcessQueue = new PriorityQueue<>();
        this.unallocatedProcessQueue = new PriorityQueue<>();
        this.scheduler = scheduler;
    }

    String displayProcesses() {
        StringBuilder output = new StringBuilder();

        if (!mainProcessQueue.isEmpty()) {
            for (PCB readyProcess : this.mainProcessQueue) {
                output.append(readyProcess.toString());
            }
        }
        if (!unallocatedProcessQueue.isEmpty()) {
            for (PCB newProcess : this.unallocatedProcessQueue) {
                output.append(newProcess.toString());
            }
        }

        return (output.length() == 0) ? "No process loaded" : "Name \t pid \t state \t priority \t burstCycle " +
                "\t memory \n" + output.toString();
    }

    void dispatch(PCB process, int option) {
        switch (process.getState()) {
            // NEW
            case 0:
                LinkedList<MainMemory.Page> pagesUsed = memory.allocateMemory(process.getMemoryRequirement());
                if (pagesUsed.size() > 0) {
                    process.setState(1);
                    process.setPagesUsed(pagesUsed);
                    mainProcessQueue.add(process);
                } else {
                    if(option == 0){
                        process.setPriority(process.getPriority() - 1);
                        unallocatedProcessQueue.add(process);
                    }
                    else if(option == 1){
                        process.setPriority(process.getPriority() - 1);
                    }
                }
                break;

            // READY
            case 1:
                scheduler.addPCB(process);
                break;

            // TERMINATING
            case 4:
                memory.deallocateMemory(process.getPagesUsed());
                mainProcessQueue.remove(process);
                break;
        }
    }

    void start(int n) {
        scheduler.setPauseCycle(n);
        while(!this.mainProcessQueue.isEmpty()) {
            for (PCB process : this.mainProcessQueue) {
                scheduler.addPCB(process);
            }
            scheduler.run();
        }
//        for (PCB process : this.mainProcessQueue) {
//            this.dispatch(process, 0);
//        }
        //this.mainProcessQueue
        //.removeIf(i -> (i.getState() == 4));
    }

    void reset() {
        mainProcessQueue.clear();
        unallocatedProcessQueue.clear();
        scheduler.reset();
        memory.reset();
    }

    void additionalDispatch() {
        // dispatches NEW processes whenever a process is terminated
        for (PCB process : unallocatedProcessQueue) {
            dispatch(process, 1);
        }
        this.unallocatedProcessQueue.removeIf(i -> (i.getState() == 1));
    }
}