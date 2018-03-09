import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Scheduler implements Runnable {
    private final CPU cpu;
    private final Queue<PCB> pancake = new LinkedList<>(); // everything else round robin
    private final Queue<PCB> waffle = new LinkedList<>(); //I/O Round Robin

    Scheduler(CPU cpu) {
        this.cpu = cpu;
    }

    synchronized void addPCB(PCB pcb) {
        pancake.add(pcb);
        if (pcb.getParent() != null) {
            pcb.getParent().addChildren(pcb);
        }
    }

    public synchronized void run() {
        while (true) {
            if (pancake.size() > 0 && cpu.isOccupied()) { //PANCAKE
                Comm.getMemory().check(pancake.peek());
                start(pancake.poll(), 0);
            } else { //WAFFLE
                if (waffle.size() > 0 && cpu.isOccupied()) {
                    Comm.getMemory().check(waffle.peek());
                    start(waffle.poll(), 1);
                } else {
                    System.out.println(cpu.getClock().getClockCycle());
                    System.out.println("Waiting for processes");
                    break;
                }
            }
        }
    }

    void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    private void start(PCB pcb, int option) {
        int QUANTUM = 15;
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
                ArrayList<PCB> killList = pcb.killChildren();
                for (PCB children : killList) {
                    pancake.remove(children);
                    waffle.remove(children);
                    Comm.callDispatcherToDelete(children);
                }
                Comm.callDispatcherToDelete(pcb);
                break;
            case 4:
                Comm.genChildProcess(pcb);
                pancake.add(pcb);
                break;
        }
    }

    void reset() {
        pancake.clear();
        waffle.clear();
        cpu.reset();
    }

    String displayProcesses() {
        StringBuilder output = new StringBuilder();

        Queue<PCB> pancake = new ConcurrentLinkedQueue<>(this.pancake);
        Queue<PCB> waffle = new ConcurrentLinkedQueue<>(this.waffle);

        output.append("RR:\n");
        if (!pancake.isEmpty()) {
            for (PCB readyProcess : pancake) {
                output.append(readyProcess.toString());
            }
        }
        output.append("\nIO:\n");
        if (!waffle.isEmpty()) {
            for (PCB readyProcess : waffle) {
                output.append(readyProcess.toString());
            }
        }

        return (output.length() == 0) ? "No process loaded" : "Name \t pid \t state \t priority \t burstCycle " +
                "\t memory \n" + output.toString();
    }
}