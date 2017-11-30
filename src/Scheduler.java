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
                if (pancake.size() > 0 && !cpu.isOccupied()) { //PANCAKE
                    if (Comm.getMemory().getDisk().contains(pancake.peek())) { //checks and sees if the peek is in the disk
                        boolean allocated = false;
                        for (PCB process : Comm.getMemory().getMain()) { //if so then find a process in main and move it to disk
                            if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > pancake.peek().getMemoryRequirement()) {
                                Comm.getMemory().mainToDisk(process);
                                allocated = Comm.getMemory().diskToMain(pancake.peek());
                                if (Comm.getMemory().mainMemoryUsage() != Comm.getMemory().getUsedCurrentMemory()) {
                                    System.out.println(Comm.getMemory().getTable());
                                    System.out.println(Comm.getMemory().mainMemoryUsage());
                                    System.out.println(Comm.getMemory().getUsedCurrentMemory());
                                    System.out.println("FOUND YOU");
                                }
                                break;
                            }
                        }
                        if (!allocated) {
                            System.out.println(Comm.getMemory().getTable());
                            System.out.println(Comm.getMemory().remainingMemory());
                            System.out.println(waffle.peek().getMemoryRequirement());
                            System.out.println(Comm.getMemory());
                            System.out.println("WELL FUCK");
                            for (PCB process : Comm.getMemory().getMain()) {
                                Comm.getMemory().mainToDisk(process);
                            }
                            Comm.getMemory().diskToMain(waffle.peek());
                            System.out.println(Comm.getMemory().getTable());
                            System.out.println(Comm.getMemory().remainingMemory());
                            System.out.println(waffle.peek().getMemoryRequirement());
                            System.out.println(Comm.getMemory());
                            System.out.println("WELL OK");
                        }
                    }


                    PCB temp = pancake.poll();
                    System.out.println("Scheduling " + temp.getName());
                    start(temp, 0);
                } else { //WAFFLE
                    if (waffle.size() > 0 && !cpu.isOccupied()) {
                        if (Comm.getMemory().getDisk().contains(waffle.peek())) {
                            boolean allocated = false;
                            System.out.println(Comm.getMemory().getTable());
                            for (PCB process : Comm.getMemory().getMain()) {
                                if ((process.getMemoryRequirement() + Comm.getMemory().remainingMemory()) > waffle.peek().getMemoryRequirement()) {
                                    Comm.getMemory().mainToDisk(process);
                                    allocated = Comm.getMemory().diskToMain(waffle.peek());
                                    if (Comm.getMemory().mainMemoryUsage() != Comm.getMemory().getUsedCurrentMemory()) {
                                        System.out.println(Comm.getMemory().getTable());
                                        System.out.println(Comm.getMemory().mainMemoryUsage());
                                        System.out.println(Comm.getMemory().getUsedCurrentMemory());
                                        System.out.println("FOUND YOU");
                                        allocated = Comm.getMemory().diskToMain(waffle.peek());
                                    }
                                    break;
                                }
                            }
                            System.out.println(Comm.getMemory().getTable());
                            if (!allocated) {
                                System.out.println(Comm.getMemory().getTable());
                                System.out.println(Comm.getMemory().remainingMemory());
                                System.out.println(waffle.peek().getMemoryRequirement());
                                System.out.println(Comm.getMemory());
                                System.out.println("WELL FUCK");
                                while (!Comm.getMemory().getMain().isEmpty()){
                                    Comm.getMemory().mainToDisk(Comm.getMemory().getMain().peek());
                                }
                                Comm.getMemory().diskToMain(waffle.peek());
                                System.out.println(Comm.getMemory().getTable());
                                System.out.println(Comm.getMemory().remainingMemory());
                                System.out.println(waffle.peek().getMemoryRequirement());
                                System.out.println(Comm.getMemory());
                                System.out.println("WELL OK");
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