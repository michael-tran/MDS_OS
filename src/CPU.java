import javax.swing.*;
import java.io.PrintStream;

public class CPU {
    private Clock ticktock;
    private int pauseCycles;
    private PCB process;

    public CPU() {
        this.ticktock = new Clock();
    }

    public Clock getTicktock() {
        return ticktock;
    }

    public boolean startProcess(PCB pcb, int QUANTUM) {
        process = pcb;
        this.crunch(QUANTUM);

        return true;
    }

    private PCB crunch(int QUANTUM) {
//Increment the clock for each part I/O etc...
        System.out.println(process);

        return this.process;
    }

    public void setPauseCycles(int pauseCycles) {
        this.pauseCycles = pauseCycles;
    }
}
