import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Runnable {
    private CPU cpu;
    private final int QUANTUM = 15;
    private Queue<PCB> pancake = new LinkedList<>(); // everything else round robin
    private Queue<PCB> waffle = new LinkedList<>(); //I/O Round Robin
    private String threadName;

    Scheduler(CPU cpu, String threadName) {
        this.cpu = cpu;
        this.threadName = threadName;
    }

    synchronized void addPCB(PCB pcb) {
        pancake.add(pcb);
    }

    public synchronized void run() {
        System.out.println(threadName + " running");
        try {
            while (true) {
                if (pancake.size() > 0 && !cpu.isOccupied()) { //PANCAKE
                    if (Comm.getMemory().getDisk().contains(pancake.peek())) { //checks and sees if the peek is in the disk
                        boolean allocated = false;
                        if (Comm.getMemory().remainingMemory() > pancake.peek().getMemoryRequirement()) //Checks if there is available space
                            allocated = Comm.getMemory().diskToMain(pancake.peek());
                        else {
                            for (PCB process : Comm.getMemory().getMain()) { //Checks to find a process in main and move it to disk
                                if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > pancake.peek().getMemoryRequirement()) {
                                    Comm.getMemory().mainToDisk(process);
                                    allocated = Comm.getMemory().diskToMain(pancake.peek());
                                    break;
                                }
                            }
                        }
                        if (!allocated) { //if failed to find any available space wipes the main and allocates the process
                            Comm.getMemory().wipe();
                            Comm.getMemory().diskToMain(pancake.peek());
                        }
                    }

                    PCB temp = pancake.poll(); //Takes the top of the stack
                    System.out.println("Scheduling " + temp.getName());
                    start(temp, 0);
                } else { //WAFFLE
                    if (waffle.size() > 0 && !cpu.isOccupied()) {
                        if (Comm.getMemory().getDisk().contains(waffle.peek())) { //checks and sees if the peek is in the disk
                            boolean allocated = false;
                            if (Comm.getMemory().remainingMemory() > waffle.peek().getMemoryRequirement()) //Checks if there is available space
                                allocated = Comm.getMemory().diskToMain(waffle.peek());
                            else {
                                for (PCB process : Comm.getMemory().getMain()) { //Checks to find a process in main and move it to disk
                                    if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > waffle.peek().getMemoryRequirement()) {
                                        Comm.getMemory().mainToDisk(process);
                                        allocated = Comm.getMemory().diskToMain(waffle.peek());
                                        break;
                                    }
                                }
                            }
                            if (!allocated) { //if failed to find any available space wipes the main and allocates the process
                                Comm.getMemory().wipe();
                                Comm.getMemory().diskToMain(waffle.peek());
                            }
                        }

                        PCB temp = waffle.poll(); //takes the top of the stack
                        System.out.println("I/O Scheduling " + temp.getName());
                        start(temp, 1);
                    } else {
                        System.out.println(cpu.getClock().getClockCycle());
                        System.out.println("Waiting for processes");
                        break;
                    }
                }
            }
        } catch (
                InterruptedException e)

        {
            System.out.println("Thread interrupted");
        }

    }

    void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    private void start(PCB pcb, int option) throws InterruptedException {
        int done = cpu.startProcess(pcb, QUANTUM, option);
        switch (done) {
            case -1:
                Comm.reset();
                break;
            case 0:
                pancake.add(pcb);//calc
                break;
            case 1:
                waffle.add(pcb);//i/o
                break;
            case 2:
                pancake.add(pcb);//yield
                break;
            case 3: //terminate
                Comm.callDispatcherToDelete(pcb);
                Comm.callDispatcherForMore();
                break;
            case 4:
                Comm.genChildProcess(pcb);
                break;
        }
    }

    void reset() {
        pancake.clear();
        waffle.clear();
        cpu.getClock().reset();
    }
}