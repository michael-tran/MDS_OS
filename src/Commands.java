import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Commands {
    private Memory memory;
    private Scheduler scheduler;
    private int processid = 0;
    private ArrayList<PCB> PCBs = new ArrayList<PCB>();

    public Commands() {
        memory = null;
        scheduler = null;
    }

    public String load(String input) {
        File file = new File(input);
        try {
            Scanner in = new Scanner(file);
            PCB process = new PCB();
            process.setName(in.nextLine());
            process.setPid(processid++);
            process.setCycle(in.nextInt());
            process.setBurstTime(in.nextInt());
            process.setMemory(in.nextInt());
            PCBs.add(process);
            System.out.println(PCBs);
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        return "0";
        // Pass into PCB

    }
}
