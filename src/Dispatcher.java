import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Dispatcher {
    private final MainMemory memory;
    private final Scheduler scheduler;

    Dispatcher(MainMemory memory, Scheduler scheduler) {
        this.memory = memory;
        this.scheduler = scheduler;
    }

    void dispatch(PCB process) {
        switch (process.getState()) {
            // NEW
            case 0:
                memory.map(process);
                process.setState(1);
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
                memory.map();
                break;
        }
    }

    void reset() {
        memory.reset();
    }

}