import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

//https://stackoverflow.com/questions/2622804/how-to-indefinitely-pause-a-thread-in-java-and-later-resume-it

public class Scheduler extends Thread {
    private CPU cpu;
    private final int QUANTUM = 15;
    private Queue<PCB> pancake = new LinkedList<>();
    private Queue<PCB> waffle = new LinkedList<>();
    private Thread thread;
    private String threadName;

    public Scheduler(CPU cpu, String threadName) {
        this.cpu = cpu;
        this.threadName = threadName;
    }

    public void addPCB(PCB pcb) {
        pancake.add(pcb);
    }

    public void run() {
        System.out.println("Thread " + threadName + " running");
        try {
            while (true) {
                if (pancake.size() > 0 && !cpu.isOccupied()) {
                    PCB temp = pancake.poll();
                    System.out.println("Scheduling " + temp.getName());
                    start(temp, 0);
                } else {
                    if (waffle.size() > 0 && !cpu.isOccupied()) {
                        PCB temp = waffle.poll();
                        System.out.println("I/O Scheduling " + temp.getName());
                        start(temp, 1);
                    }else {
                        System.out.println(cpu.getClock().getClockCycle());
                        System.out.println("Waiting for processes");
                        break;
                    }
                }
                thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void setPauseCycle(int pauseCycle) {
        cpu.setPauseCycles(pauseCycle);
    }

    public void start(PCB pcb, int option) throws InterruptedException {
        int done = cpu.startProcess(pcb, QUANTUM, option);
        switch (done){
            case -1:
                System.out.println("PAUSED");
                Thread.sleep(100);
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
                break;
        }
    }

//    public void finish() {
//        while (!pancake.isEmpty()) {
//            int done = cpu.startProcess(pancake.peek(), QUANTUM);
//            switch (done){
//                case -1:
//                    break;
//                case 0:
//                    break;
//                case 1:
//                    pancake.add(pancake.remove());
//                    break;
//            }
//        }
//    }

    public void reset() {
        pancake.clear();
    }
}