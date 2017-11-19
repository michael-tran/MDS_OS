import static java.lang.Math.min;

public class CPU {
    private Clock clock;
    private int pauseCycles;
    private PCB process;
    private boolean occupied;

    public CPU() {
        pauseCycles = Integer.MAX_VALUE;
        this.clock = new Clock();
        occupied = false;
    }

    public Clock getClock() {
        return clock;
    }

    public boolean startProcess(PCB pcb, int QUANTUM) {
        setOccupied(true);
        process = pcb;
        int state = this.crunch(QUANTUM);
        setOccupied(false);
        switch (state) {
            case -1:
                return false; //pause
            case 0:
                return false; //true
        }
        return true;
    }

    private int crunch(int QUANTUM) {
        int burstCycle;
        System.out.println("Crunching " + process.getName());
        if (process.getRemainingBurstCycle() > 0) {
            burstCycle = process.getRemainingBurstCycle();
        } else burstCycle = process.getBurstCycle();

        for (int i = min(QUANTUM, burstCycle); i > 0; i--) {
            switch (process.getCommands().get(process.getCommandsIndex())[0]) {
                case 0:
                    // calculate
                    System.out.println("Calculating");
                    process.getCommands().get(process.getCommandsIndex())[1]--;
                    clock.tick();
                    break;
                case 1:
                    // I/O
                    System.out.println("IO");
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

            if ((clock.getClockCycle() != 0) && (clock.getClockCycle() % this.pauseCycles) == 0) {
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}