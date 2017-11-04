public class PCB implements Comparable<PCB> {
    private String name;
    private int clockIn;
    private int clockOut;
    private int breakTime;
    private int pid;
    private int state;
    private int ioCycle;
    private int yieldCycle;
    private int priority;
    private int totalCycleCount;
    private int burstCycle;
    private int memoryRequirement;

    /**
     * process state:
     * 0 NEW
     * 1 READY
     * 2 RUN
     * 3 WAIT/BLOCKED
     * 4 EXIT
     */

    /**
     * Program file format:
     * line 0 - name
     * line 1 - memory requirements
     * line 2 - burstCycle
     * line 3 - priority
     * line 4 - totalCycle
     * line 5 - ioCycle
     * line 6 - yieldCycle
     */
    public PCB(String name, int processid, int memoryRequirement, int burstCycle, int priority, int totalCycleCount,
               int ioCycle, int yieldCycle) {
        this.name = name;
        this.pid = processid;
        this.memoryRequirement = memoryRequirement;
        this.burstCycle = burstCycle;
        this.priority = priority;
        this.state = 0;
        this.totalCycleCount = totalCycleCount;
        this.ioCycle = ioCycle;
        this.yieldCycle = yieldCycle;
    }

    @Override
    public int compareTo(PCB process) {
        return priority - process.priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClockIn() {
        return clockIn;
    }

    public void setClockIn(int clockIn) {
        this.clockIn = clockIn;
    }

    public int getClockOut() {
        return clockOut;
    }

    public void setClockOut(int clockOut) {
        this.clockOut = clockOut;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getState() {
        return state;
    }

    public int getIoCycle() {
        return ioCycle;
    }

    public void setIoCycle(int ioCycle) {
        this.ioCycle = ioCycle;
    }

    public int getYieldCycle() {
        return yieldCycle;
    }

    public void setYieldCycle(int yieldCycle) {
        this.yieldCycle = yieldCycle;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTotalCycleCount() {
        return totalCycleCount;
    }

    public void setTotalCycleCount(int totalCycleCount) {
        this.totalCycleCount = totalCycleCount;
    }

    public int getBurstCycle() {
        return burstCycle;
    }

    public void setBurstCycle(int burstCycle) {
        this.burstCycle = burstCycle;
    }

    public int getMemoryRequirement() {
        return memoryRequirement;
    }

    public void setMemoryRequirement(int memoryRequirement) {
        this.memoryRequirement = memoryRequirement;
    }

    public String getStateName() {
        switch (state) {
            case 0:
                return "NEW";
            case 1:
                return "READY";
            case 2:
                return "RUNNING";
            case 3:
                return "WAITING/BLOCKED";
            case 4:
                return "TERMINATED";
            default:
                return "You shouldn't see this";
        }
    }

    @Override
    public String toString() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", pid=" + pid +
                ", state=" + getStateName() +
                ", priority=" + priority +
                ", burstCycle=" + burstCycle +
                ", memoryRequirement=" + memoryRequirement +
                "}\n";
    }
}