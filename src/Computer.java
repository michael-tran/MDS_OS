import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Computer implements Runnable{
    CPU cpu = new CPU();
    MainMemory memory = new MainMemory();
    Dispatcher dispatcher = new Dispatcher(cpu, memory);
    ProcessGenerator progen = new ProcessGenerator();
    private int processid = 0;

    @Override
    public void run() {
        dispatcher.start();
    }

    public Computer() {
    }

    // List of OS Commands below: load, exe, mem, proc

    public String load(String input) {
        File file = new File(input + ".txt");
        String name;
        int memoryRequirement, burstCycle, priority;
        ArrayList<int[]> commands = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            name = in.nextLine();
            memoryRequirement = Integer.parseInt(in.nextLine());
            burstCycle = Integer.parseInt(in.nextLine());
            priority = Integer.parseInt(in.nextLine());
            while (in.hasNextLine()) {
                int inner[] = new int[2];
                String line = in.nextLine();
                String command[] = line.split(",");
                inner[0] = Integer.parseInt(command[0].toString());
                inner[1] = Integer.parseInt(command[1].toString());
                commands.add(inner);
            }

        } catch (FileNotFoundException e) {
            return "File not found";
        }
        PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle, priority, commands);
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

    public int getAmount() {
        return dispatcher.getAmount();
    }




    // Inner class to generate processes
    private class ProcessGenerator {

        int fileID = 0;

        public ProcessGenerator() {
        }

        private void wordProcessor() {
            String name = "word" + fileID;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(100);
            int burstCycle = ThreadLocalRandom.current().nextInt(19);
            int priority = ThreadLocalRandom.current().nextInt(4);
            fileID++;
            ArrayList<int[]> commands = new ArrayList<>();
            int[] tempCommands = new int[2];
            tempCommands[0] = 0;
            tempCommands[1] = 20 + ThreadLocalRandom.current().nextInt(30);
            commands.add(tempCommands);
            tempCommands[0] = 1; // I/O
            tempCommands[1] = 25 + ThreadLocalRandom.current().nextInt(25);
            commands.add(tempCommands);
            tempCommands[0] = 0;
            tempCommands[1] = 20 + ThreadLocalRandom.current().nextInt(30);
            commands.add(tempCommands);
            tempCommands[0] = 0;
            tempCommands[1] = 20 + ThreadLocalRandom.current().nextInt(30);
            commands.add(tempCommands);
            tempCommands[0] = 1;
            tempCommands[1] = 25 + ThreadLocalRandom.current().nextInt(25);
            commands.add(tempCommands);
            tempCommands[0] = 0;
            tempCommands[1] = 20 + ThreadLocalRandom.current().nextInt(30);
            commands.add(tempCommands);

            PCB tempPCB = new PCB(name, processid, memoryRequirement, burstCycle, priority, commands);
            dispatcher.dispatch(tempPCB);
        }

        private void game() {

        }

        private void videoEditor() {

        }

        private void musicPlayer() {

        }

        private void physicEngine() {

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
    }
}