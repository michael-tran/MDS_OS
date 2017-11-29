import java.util.Scanner;

import static java.lang.Math.min;

class CPU {
    private Clock clock;
    private int pauseCycles;
    private PCB process;
    private boolean occupied;

    CPU() {
        pauseCycles = Integer.MAX_VALUE;
        this.clock = new Clock();
        occupied = false;
    }

    Clock getClock() {
        return clock;
    }

    int startProcess(PCB pcb, int QUANTUM, int option) throws InterruptedException {
        setOccupied(true);
        process = pcb;
        process.setState(2);
        Thread.sleep(10);
        int state = this.crunch(QUANTUM, option);
        setOccupied(false);
        switch (state) {
            case -1:
                return -1; //pause
            case 0:
                process.setState(1);
                return 0; //not finished with Calc
            case 1:
                process.setState(3);
                return 1;//not finished with I/O
            case 2:
                process.setState(1);
                return 2;//not finished with Yield
            case 3:
                process.setState(4);
                return 3;//Finished
            case 4:
                process.setState(1);
                return 4;
        }
        return 0;
    }

    private int crunch(int QUANTUM, int option) {
        int burstCycle;
        System.out.println("Crunching " + process.getName());
        if (process.getRemainingBurstCycle() > 0) {
            burstCycle = process.getRemainingBurstCycle();
        } else burstCycle = process.getBurstCycle();

        for (int i = min(QUANTUM, burstCycle); i > 0; i--) {
            switch (process.getCommands().get(process.getCommandsIndex())[0]) {
                case 0:
                    // calculate
                    System.out.println("Calc");
                    process.getCommands().get(process.getCommandsIndex())[1]--;
                    clock.tick();
                    break;
                case 1:
                    // I/O
                    if (option == 0) {
                        return 1;
                    } else {
                        System.out.println("IO");
                        process.getCommands().get(process.getCommandsIndex())[1]--;
                        clock.tick();
                        break;
                    }
                case 2:
                    // Yield
                    System.out.println("Yield");
                    clock.tick();
                    process.setRemainingBurstCycle(i);
                    return 2;
                case 3:
                    // Terminate
                    System.out.println("Terminating");
                    process.setState(4);
                    clock.tick();
                    return 3;
                case 4:
                    // Generate child process
                    System.out.println("Generating child process");
                    clock.tick();
                    return 4;
            }

            if (process.getCommands().get(process.getCommandsIndex())[1] == 0)
                process.setCommandsIndex(process.getCommandsIndex() + 1);

            if ((clock.getClockCycle() != 0) && (clock.getClockCycle() % this.pauseCycles) == 0) {
                Scanner scan = new Scanner(System.in);
                String input;
                System.out.println("Continue(Y/N)");
                input = scan.next().toLowerCase();
                if (input.equals("n")) return -1;
            }
        }
        return 0;
    }

    void setPauseCycles(int pauseCycles) {
        if (pauseCycles == 0) {
            this.pauseCycles = Integer.MAX_VALUE;
        } else {
            this.pauseCycles = pauseCycles;
        }
    }

    boolean isOccupied() {
        return occupied;
    }

    private void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}