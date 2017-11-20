class Clock {

    private int clockCycle;

    Clock() {
        clockCycle = 0;
    }

    synchronized void tick() {
        clockCycle++;
    }

    int getClockCycle() {
        return clockCycle;
    }

    void reset() {
        clockCycle = 0;
    }

}