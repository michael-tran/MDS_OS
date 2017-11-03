import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Computer {
    private Memory memory;
    private Scheduler scheduler;
    private int processid = 0;

    public Computer() {
        memory = null;
        scheduler = new Scheduler();
    }

    public String load(String input) {
        File file = new File(input);
        try {
            Scanner in = new Scanner(file);
            PCB process = new PCB();
            process.setPid(processid++);
            process.setState(0);
            process.setMemory(in.nextInt());
            scheduler.addPCB(process);
            System.out.println(scheduler);
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        return "0";
        // Pass into PCB

    }
}
