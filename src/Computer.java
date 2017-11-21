import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Computer {
    private Dispatcher dispatcher;
    private MainMemory mainMemory;
    private ProcessGenerator progen = new ProcessGenerator();
    private int processid = 0;

    Computer(Dispatcher dispatcher, MainMemory mainMemory) {
        this.dispatcher = dispatcher;
        this.mainMemory = mainMemory;
    }

    // List of OS Commands below: load, exe, mem, proc

    String load(String input) {
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
                inner[0] = Integer.parseInt(command[0]);
                inner[1] = Integer.parseInt(command[1]);
                commands.add(inner);
            }

        } catch (FileNotFoundException e) {
            return "File not found";
        }
        PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle,
                priority, commands, null);
        dispatcher.dispatch(newProcess);
        processid++;
        return "Program " + name + " successfully loaded.";
    }

    void exe(int n) {
        if (dispatcher.displayProcesses().equals("No process loaded")) {
            gen(5);
        }
        dispatcher.start(n);
    }

    String mem() {
        return mainMemory.toString();
    }

    String proc() {
        return dispatcher.displayProcesses();
    }

    String gen(int numberOfProcesses) {
        for (int i = 0; i < numberOfProcesses; i++) {
            progen.gen();
        }
        return "Generated " + numberOfProcesses + " process(es).";
    }

    String reset() {
        dispatcher.reset();
        mainMemory.reset();
        return "Welcome to MDS OS\n";
    }

    // Inner class to generate processes
    private class ProcessGenerator {

        ProcessGenerator() {
        }

        private void wordProcessor() {
            String name = "word" + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 400);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
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

            PCB tempPCB = new PCB(name, processid, memoryRequirement, burstCycle, priority, commands, null);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void game() {
            String name = "game" + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 800);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
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

            PCB tempPCB = new PCB(name, processid, memoryRequirement, burstCycle, priority, commands,null);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void videoEditor() {

        }

        private void musicPlayer() {

        }

        private void anti_virus() {

        }

        private void gen() {
            int randomInt = ThreadLocalRandom.current().nextInt(5);
            switch (randomInt) {
                case 0:
                    wordProcessor();
                    break;
                case 1:
                    game();
                    break;
                case 2:
                    videoEditor();
                    break;
                case 3:
                    musicPlayer();
                    break;
                case 4:
                    anti_virus();
                    break;
            }
        }
    }
}