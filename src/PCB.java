public class PCB {
    private String name;
    private int clockIn, clockOut, breakTime, pid, state, CPU, beginTime, deadline, priority, cycle, burstCycle, memoryRequirement;

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
     * line 1 - memory requirements
     * line 2 - Calculate
     * line 3 - I/O
     * line 4 - Yield
     * line 5 - Out
     */
    public PCB(String name, int clockIn, int clockOut, int breakTime, int processid, int state, int CPU, int beginTime, int deadline) {
        this.name = name;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.breakTime = breakTime;
        this.pid = processid; //calculated from incrementing int processid from Computer.java
        this.state = state;
        this.CPU = CPU;
        this.beginTime = beginTime;
        this.deadline = deadline;
    }

    public PCB() {

    }

    // USING THIS ONE RIGHT NOW FOR TESTING
    public PCB(String name, int processid, int memoryRequirement, int burstCycle, int priority) {
        this.name = name;
        this.pid = processid;
        this.memoryRequirement = memoryRequirement;
        this.burstCycle = burstCycle;
        this.priority = priority;
        this.state = 0;
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

    public void setState(int state) {
        this.state = state;
    }

    public int getCPU() {
        return CPU;
    }

    public void setCPU(int CPU) {
        this.CPU = CPU;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
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
            case 0: return "NEW";
            case 1: return "READY";
            case 2: return "RUNNING";
            case 3: return "WAITING/BLOCKED";
            case 4: return "TERMINATED";
            default: return "You shouldn't see this";
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
                '}';
    }
}
