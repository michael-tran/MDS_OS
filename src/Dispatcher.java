import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Dispatcher {
    private MainMemory memory;
    private Scheduler scheduler;
    private PriorityQueue<PCB> mainProcessQueue; // All new mainProcessQueue// go here

    Dispatcher(MainMemory memory, Scheduler scheduler) {
        this.memory = memory;
        this.mainProcessQueue = new PriorityQueue<>();
        this.scheduler = scheduler;
    }

    String displayProcesses() {
        StringBuilder output = new StringBuilder();

        Queue<PCB> main = new ConcurrentLinkedQueue<>(this.mainProcessQueue);

        if (!mainProcessQueue.isEmpty()) {
            for (PCB readyProcess : main) {
                output.append(readyProcess.toString());
            }
        }
//        if (!unallocatedProcessQueue.isEmpty()) {
//            for (PCB newProcess : this.unallocatedProcessQueue) {
//                output.append(newProcess.toString());
//            }
//        }

        return (output.length() == 0) ? "No process loaded" : "Name \t pid \t state \t priority \t burstCycle " +
                "\t memory \n" + output.toString();
    }

    void dispatch(PCB process) {
        switch (process.getState()) {
            // NEW
            case 0:
                memory.map(process);
                process.setState(1);
                mainProcessQueue.add(process);
                break;

            // READY
            case 1:
                scheduler.addPCB(process);
                break;

            // TERMINATING
            case 4:
                if (!process.getChildren().isEmpty()) {
                    for (PCB pcb : process.getChildren()) {
                        pcb.setState(4);
                        dispatch(pcb);
                    }
                }
                memory.deallocateMemory(process, true);
                mainProcessQueue.remove(process);
                memory.map();
                break;
        }
    }

    void start(int n) {
        scheduler.setPauseCycle(n);
        while (!this.mainProcessQueue.isEmpty()) {
            for (PCB process : this.mainProcessQueue) {
                this.dispatch(process);
            }
            scheduler.run();
        }
    }

    void reset() {
        mainProcessQueue.clear();
        memory.reset();
    }
}