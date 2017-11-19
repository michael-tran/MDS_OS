import static java.lang.Math.min;

public class CPU {
    private Clock clock;
    private int pauseCycles;
    private PCB process;

    public CPU() {
        this.clock = new Clock();
    }

    public Clock getClock() {
        return clock;
    }

    public boolean startProcess(PCB pcb, int QUANTUM) {
        process = pcb;
        int state = this.crunch(QUANTUM);
        switch (state) {
            case -1:
                return false; //pause
            case 0:
                return true; //true
        }
        return true;
    }

    private int crunch(int QUANTUM) {
        int burstCycle;
        if (process.getRemainingBurstCycle() > 0) {
            burstCycle = process.getRemainingBurstCycle();
        } else burstCycle = process.getBurstCycle();

        for (int i = min(QUANTUM, burstCycle); i > 0; i--) {
            switch (process.getCommands().get(process.getCommandsIndex())[0]) {
                case 0:
                    // calculate
                    process.getCommands().get(process.getCommandsIndex())[1]--;
                    clock.tick();
                    break;
                case 1:
                    // I/O
                    process.getCommands().get(process.getCommandsIndex())[1]--;
                    clock.tick();
                    break;
                case 2:
                    // Yield
                    clock.tick();
                    process.setRemainingBurstCycle(i);
                    return 2;
                case 3:
                    clock.tick();
                    return 3;
            }

            if (process.getCommands().get(process.getCommandsIndex())[1] == 0) {
                process.setCommandsIndex(process.getCommandsIndex() + 1);
            }

            if ((clock.getClockCycle() != 0) &&(clock.getClockCycle() % this.pauseCycles) == 0) {
                return -1;
            }
        }
        return 0;
    }

    public void setPauseCycles(int pauseCycles) {
        if (pauseCycles == 0) {
            this.pauseCycles = Integer.MAX_VALUE;
        } else {
            this.pauseCycles = pauseCycles;
        }
    }
}