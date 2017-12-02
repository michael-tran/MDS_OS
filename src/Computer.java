import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Computer {
    private Dispatcher dispatcher;
    private MainMemory mainMemory;
    private Scheduler scheduler;
    private ProcessGenerator progen = new ProcessGenerator();
    private int processid = 0;
    private boolean running = false;

    Computer(Dispatcher dispatcher, MainMemory mainMemory, Scheduler scheduler) {
        this.dispatcher = dispatcher;
        this.mainMemory = mainMemory;
        this.scheduler = scheduler;
    }

    boolean getRunning() {
        return running;
    }

    void toggleRunning() {
        running = !running;
    }

    // List of OS Commands below: load, exe, mem, proc

    String load(String input) {
        File file = new File(input + ".txt");
        String name;
        int processType, memoryRequirement, burstCycle, priority;
        ArrayList<int[]> commands = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            name = in.nextLine();
            processType = Integer.parseInt(in.nextLine());
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
        PCB newProcess = new PCB(name, processid, processType, memoryRequirement, burstCycle,
                priority, commands, null);
        dispatcher.dispatch(newProcess);
        processid++;
        return "Program " + name + " successfully loaded.";
    }

    void exe(int n) {
        if (dispatcher.getMainProcessQueue().isEmpty()) {
            gen(5);
        }
        scheduler.setPauseCycle(n);
        scheduler.run();
    }

    String mem() {
        return mainMemory.toString();
    }

    String table() {
        return mainMemory.getTable();
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

    void genChildProcess(PCB parentProcess) {
        progen.genChildProcess(parentProcess);
    }

    String reset() {
        processid = 0;
        dispatcher.reset();
        mainMemory.reset();
        return "Welcome to MDS OS\n";
    }

    // Inner class to generate processes
    private class ProcessGenerator {

        ProcessGenerator() {
        }

        private void wordProcessor(PCB parentProcess) {
            String name = "Word " + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 400);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
            List<int[]> commands = new ArrayList<>();
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{3, 0});

            PCB tempPCB = new PCB(name, processid, 0, memoryRequirement,
                    burstCycle, priority, commands, parentProcess);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void game(PCB parentProcess) {
            String name = "Game " + processid;
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
            commands.add(new int[]{2, 0});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 20 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{3, 0});

            PCB tempPCB = new PCB(name, processid, 1, memoryRequirement,
                    burstCycle, priority, commands, parentProcess);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void videoPlayer(PCB parentProcess) {
            String name = "Video " + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 800);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
            List<int[]> commands = new ArrayList<>();
            commands.add(new int[]{0, 100 + ThreadLocalRandom.current().nextInt(80)});
            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(60)});
            commands.add(new int[]{0, 150 + ThreadLocalRandom.current().nextInt(70)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{0, 100 + ThreadLocalRandom.current().nextInt(70)});
            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 30 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{3, 0});

            PCB tempPCB = new PCB(name, processid, 2, memoryRequirement,
                    burstCycle, priority, commands, parentProcess);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void browser(PCB parentProcess) {
            String name = "Browser " + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 800);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
            List<int[]> commands = new ArrayList<>();
//            commands.add(new int[]{0, 150 + ThreadLocalRandom.current().nextInt(80)});
//            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(60)});
//            commands.add(new int[]{2, 0});
//            commands.add(new int[]{1, 45 + ThreadLocalRandom.current().nextInt(35)});
//            commands.add(new int[]{1, 35 + ThreadLocalRandom.current().nextInt(25)});
//            commands.add(new int[]{2, 0});
//            commands.add(new int[]{0, 100 + ThreadLocalRandom.current().nextInt(50)});
//            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
//            commands.add(new int[]{2, 0});
//            commands.add(new int[]{0, 70 + ThreadLocalRandom.current().nextInt(150)});
//            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
//            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(30)});
//            commands.add(new int[]{0, 30 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{4, 0}); // Child process
            commands.add(new int[]{3, 0});

            PCB tempPCB = new PCB(name, processid, 3, memoryRequirement,
                    burstCycle, priority, commands, parentProcess);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void antivirus(PCB parentProcess) {
            String name = "Antivirus " + processid;
            int memoryRequirement = ThreadLocalRandom.current().nextInt(200, 800);
            int burstCycle = ThreadLocalRandom.current().nextInt(10, 25);
            int priority = ThreadLocalRandom.current().nextInt(3, 5);
            List<int[]> commands = new ArrayList<>();
            commands.add(new int[]{0, 150 + ThreadLocalRandom.current().nextInt(80)});
            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(60)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{1, 45 + ThreadLocalRandom.current().nextInt(35)});
            commands.add(new int[]{1, 35 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{0, 100 + ThreadLocalRandom.current().nextInt(50)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{2, 0});
            commands.add(new int[]{0, 70 + ThreadLocalRandom.current().nextInt(150)});
            commands.add(new int[]{1, 25 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{0, 120 + ThreadLocalRandom.current().nextInt(30)});
            commands.add(new int[]{1, 30 + ThreadLocalRandom.current().nextInt(25)});
            commands.add(new int[]{3, 0});
            PCB tempPCB = new PCB(name, processid, 4, memoryRequirement,
                    burstCycle, priority, commands, parentProcess);
            processid++;
            dispatcher.dispatch(tempPCB);
        }

        private void gen() {
            int randomInt = ThreadLocalRandom.current().nextInt(5);
            switch (randomInt) {
                case 0:
                    //wordProcessor(null);
                    break;
                case 1:
                    //game(null);
                    break;
                case 2:
                    //videoPlayer(null);
                    break;
                case 3:
                    browser(null);
                    break;
                case 4:
                    //antivirus(null);
                    break;
            }
        }

        private void genChildProcess(PCB parentProcess) {
            switch (parentProcess.getProcessType()) {
                case 0:
                    wordProcessor(parentProcess);
                    break;
                case 1:
                    game(parentProcess);
                    break;
                case 2:
                    videoPlayer(parentProcess);
                    break;
                case 3:
                    browser(parentProcess);
                    break;
                case 4:
                    antivirus(parentProcess);
                    break;
            }
        }
    }
}