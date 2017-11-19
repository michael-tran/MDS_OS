import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Computer extends Thread {
    private Dispatcher dispatcher;
    private MainMemory mainMemory;
    private ProcessGenerator progen = new ProcessGenerator();
    private int processid = 0;
    private Thread computerThread;

    public Computer(Dispatcher dispatcher, MainMemory mainMemory) {
        this.dispatcher = dispatcher;
        this.mainMemory = mainMemory;
    }


    public void run() {

    }

    public void start() {
        if (computerThread == null) {
            computerThread = new Thread(this, "Computer Thread");
            computerThread.start();
        }
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
        return "Starting Simulation";
    }

    public String mem() {
        return mainMemory.toString();
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

    public void reset() {

    }

    // Inner class to generate processes
    private class ProcessGenerator {

        int fileID = 0;

        public ProcessGenerator() {
        }

        private void wordProcessor() {
            String name = "word" + fileID;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 400);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
            fileID++;
            List<int[]> commands = new ArrayList<>();
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{3, 0});

            PCB tempPCB = new PCB(name, processid, memoryRequirement, burstCycle, priority, commands);
            processid++;
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