import java.util.LinkedList;
import java.util.Queue;

//https://stackoverflow.com/questions/2622804/how-to-indefinitely-pause-a-thread-in-java-and-later-resume-it

public class Scheduler implements Runnable {
    private CPU cpu;
    private final int QUANTUM = 15;
    private Queue<PCB> pancake = new LinkedList<>();
    private Queue<PCB> waffle = new LinkedList<>();
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
                if (pancake.size() > 0 && !cpu.isOccupied()) {
                    if (Comm.getMemory().getDisk().contains(pancake.peek())) {
                        boolean allocated = false;
                        for (PCB process : Comm.getMemory().getMain()) {
                            if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > pancake.peek().getMemoryRequirement()) {
//                                System.out.println(Comm.getMemory().getTable());
                                Comm.getMemory().mainToDisk(process);
//                                System.out.println(Comm.getMemory().getTable());
                                Comm.getMemory().diskToMain(pancake.peek());
//                                System.out.println(Comm.getMemory().getTable());
                                allocated = true;
                                break;
                            }
                        }
                        if (!allocated) {
                            System.out.println(Comm.getMemory().remainingMemory());
                            System.out.println(waffle.peek().getMemoryRequirement());
                            System.out.println("WELL FUCK");
                        }
                    }
                    PCB temp = pancake.poll();
                    System.out.println("Scheduling " + temp.getName());
                    start(temp, 0);
                } else {
                    if (waffle.size() > 0 && !cpu.isOccupied()) {
                        if (Comm.getMemory().getDisk().contains(waffle.peek())) {
                            boolean allocated = false;
                            for (PCB process : Comm.getMemory().getMain()) {
                                if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > waffle.peek().getMemoryRequirement()) {
//                                    System.out.println(Comm.getMemory().getTable());
                                    Comm.getMemory().mainToDisk(process);
//                                    System.out.println(Comm.getMemory().getTable());
                                    Comm.getMemory().diskToMain(waffle.peek());
//                                    System.out.println(Comm.getMemory().getTable());
                                    allocated = true;
                                    break;
                                }
                            }
                            if (!allocated) {
                                System.out.println(Comm.getMemory().remainingMemory());
                                System.out.println(waffle.peek().getMemoryRequirement());
                                System.out.println("WELL FUCK");
                            }
                        }
                        PCB temp = waffle.poll();
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
        }
    }

    void reset() {
        pancake.clear();
        waffle.clear();
        cpu.getClock().reset();
    }
}