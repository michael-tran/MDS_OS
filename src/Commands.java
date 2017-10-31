import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Commands {
    private Memory memory;
    private Scheduler scheduler;
    private int processid;
    private

    public Commands() {
        memory = null;
        scheduler = null;
    }

    public void load(String input) {
        File file = new File("pornhub.txt");
        try {
            Scanner in = new Scanner(file);
            PCB in.nextLine() =
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Pass into PCB

    }
}
