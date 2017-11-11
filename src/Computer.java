import java.io.File;
import java.io.FileNotFoundException;
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
        int memoryRequirement, burstCycle, priority, totalCycleCount, ioCycle, yieldCycle;
        try {
            Scanner in = new Scanner(file);
            name = in.nextLine();
            memoryRequirement = in.nextInt();
            burstCycle = in.nextInt();
            priority = in.nextInt();
            totalCycleCount = in.nextInt();
            ioCycle = in.nextInt();
            yieldCycle = in.nextInt();
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle, priority, totalCycleCount,
                ioCycle, yieldCycle);
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

    private class ProcessGenerator {

        public ProcessGenerator() {
        }

        private void wordProcessor() {
            String name = "word" + processid;
            int memoryRequirement;
            int burstCycle;
            int priority = 3;
            int totalCycleCount;
            int ioCycle;
            dispatch(name, asrgfae4rgfr;k);
        }

        private void gen() {
            int randomInt = ThreadLocalRandom.current().nextInt(5);
            switch (randomInt) {
                case 1: wordProcessor();
                case 2:
            }
        }

        private void dispatch(String name, int memoryRequirement, int burstCycle, int priority,
                              int totalCycleCount, int ioCycle, int yieldCycle) {
            PCB newProcess = new PCB(name, processid, memoryRequirement, burstCycle, priority, totalCycleCount,
                    ioCycle, yieldCycle);
            dispatcher.dispatch(newProcess);
            processid++;
        }
    }
}