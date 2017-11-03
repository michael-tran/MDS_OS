import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Computer {
    CPU cpu = new CPU();
    MainMemory memory = new MainMemory();
    Scheduler rrScheduler = new Scheduler();
    Dispatcher swag = new Dispatcher(cpu, memory);
    private int processid = 0;

    public Computer() {
    }

    // List of OS Commands below: load, exe, mem, proc

    public String load(String input) {
        File file = new File(input + ".txt");
        String name;
        int memoryRequirement;
        int burstCycle;
        int priority;
        try {
            Scanner in = new Scanner(file);
            name = in.nextLine();
            memoryRequirement = in.nextInt();
            burstCycle = in.nextInt();
            priority = in.nextInt();
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle, priority);
        rrScheduler.addPCB(newProcess);
        return "Program " + name + " successfully loaded.";
    }

    public void exe(int n) {

    }

    public String mem() {
        return memory.toString();
    }

    public String proc() {
        return rrScheduler.displayProcesses();
    }

}
