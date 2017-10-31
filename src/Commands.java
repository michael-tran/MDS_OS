import java.io.File;
import java.util.Scanner;

public class Commands {
    private Memory memory;
    private Scheduler scheduler;

    public Commands() {
        memory = null;
        scheduler = null;
    }

    public String load(String input) {
        String file_content = "";
        try {
            Scanner in = new Scanner(new File(input));
            while (in.hasNextLine()) {
                file_content += in.nextLine() + "\n";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return file_content;
    }
}
