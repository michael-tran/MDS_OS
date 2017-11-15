import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Computer {
    CPU cpu = new CPU();
    MainMemory memory = new MainMemory();
    Scheduler rrScheduler = new Scheduler(memory);
    Dispatcher dispatcher = new Dispatcher(cpu, memory);
    ProcessGenerator progen = new ProcessGenerator();
    private int processid = 0;

    public Computer() {
    }

    // List of OS Commands below: load, exe, mem, proc

    public String load(String input) {
        File file = new File(input + ".txt");
        String name;
        int memoryRequirement, burstCycle, priority;
        ArrayList<int[]> commands = new ArrayList<int[]>();
        try {
            Scanner in = new Scanner(file);
            name = in.nextLine();
            memoryRequirement = in.nextInt();
            burstCycle = in.nextInt();
            priority = in.nextInt();

            while(in.hasNextLine()){
                int inner[] = new int[2];
                String line = in.nextLine();
                String command[] = line.split(",");
                inner[0] = Integer.parseInt(command[0]);
                inner[1] = Integer.parseInt(command[1]);
                commands.add(inner);
            }

        } catch (FileNotFoundException e) {
            return "File not found";
        }
        PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle, priority);
        dispatcher.dispatch(newProcess);
        processid++;
        return "Program " + name + " successfully loaded.";
    }

    public String exe(int n) {
        dispatcher.start(n);
        return "Done";
    }

    public String mem() {
        return memory.toString();
    }

    public String proc() {
        return dispatcher.displayProcesses();
    }

    public String gen(int numberOfProcesses) {
        for (int i = 0; i < numberOfProcesses; i++) {
            progen.gen();
        }
        return "Process generation complete.";
    }

    public String genLoad() {
        for (String file: progen.getFiles()) {
            this.load(file);
        }
        return progen.toString();
    }

    public int getAmount() {
        return dispatcher.getAmount();
    }

    private class ProcessGenerator {

        private ArrayList<String> files = new ArrayList<>();

        public ProcessGenerator() {
        }

        public ArrayList<String> getFiles() {
            return files;
        }

        public void setFiles(ArrayList<String> files) {
            this.files = files;
        }

        private void wordProcessor() {
            String name = "word" + processid;
            Random random = new Random();
            int memoryRequirement = random.nextInt(100);
            int burstCycle = random.nextInt(19);
            int priority = random.nextInt(4);
            try {
                PrintWriter writer = new PrintWriter(name +".txt", "UTF-8");
                writer.println(name);
                writer.println(memoryRequirement);
                writer.println(burstCycle);
                writer.println(priority);
                writer.println("1 ," + (20+ random.nextInt(30))); //Type , cycle (IO)
                writer.println("0 ," + random.nextInt(20)); //Type , cycle
                writer.println("1 ," + (20+ random.nextInt(30))); //Type , cycle (IO)
                writer.println("0 ," + random.nextInt(20)); //Type , cycle
                writer.println("1 ," + (20+ random.nextInt(30))); //Type , cycle (IO)
                writer.println("0 ," + random.nextInt(20)); //Type , cycle
                writer.println("1 ," + (20+ random.nextInt(30))); //Type , cycle (IO)
                writer.println("0 ," + random.nextInt(20)); //Type , cycle
                writer.println("1 ," + (20+ random.nextInt(30))); //Type , cycle (IO)
                writer.println("0 ," + random.nextInt(20)); //Type , cycle
                writer.println("3,0");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            files.add(name);
        }

        private void gen() {
            int randomInt = ThreadLocalRandom.current().nextInt(5);
            switch (randomInt) {
                case 0:
                    wordProcessor();
                    break;
                case 1:
                    wordProcessor();
                    break;
                case 2:
                    wordProcessor();
                    break;
                case 3:
                    wordProcessor();
                    break;
                case 4:
                    wordProcessor();
                    break;
            }
        }

        @Override
        public String toString() {
            return "ProcessGenerator{" +
                    "files=" + files +
                    '}';
        }
    }
}