public class Clock {

    private int clockCycle;

    public Clock() {
        clockCycle = 0;
    }

    public void tick() {
        clockCycle++;
    }

    public void tock(int cycles) {
        clockCycle = +cycles;
    }

    public int getClockCycle() {
        return clockCycle;
    }

    public void reset() {
        clockCycle = 0;
    }
}