import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Computer {
    CPU cpu = new CPU();
    Memory memory = new Memory();
    Scheduler rrScheduler = new Scheduler();

    private int processid = 0;
    private ArrayList<PCB> PCBs = new ArrayList<PCB>();

    public Computer() {
    }

    public String load(String input) {
        File file = new File(input);
        try {
            Scanner in = new Scanner(file);
            PCB process = new PCB();
            process.setPid(processid++);
            process.setState(0);
            process.setMemory(in.nextInt());
            PCBs.add(process);
            System.out.println(PCBs);
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        return "0";
        // Pass into PCB
    }

    public void exe(int n) {

    }

    public void mem() {
        memory.toString();
    }

}
