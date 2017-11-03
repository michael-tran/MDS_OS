public class PCB {
    private String name;
    private int clockIn, clockOut, breakTime, pid, state, CPU, beginTime, deadline, priority, cycle, burstTime, memory;

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
     * line 1 - memory
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

    public void setState(int state) { this.state = state; }

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

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", clockIn=" + clockIn +
                ", clockOut=" + clockOut +
                ", breakTime=" + breakTime +
                ", pid=" + pid +
                ", state=" + state +
                ", CPU=" + CPU +
                ", beginTime=" + beginTime +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", cycle=" + cycle +
                ", burstTime=" + burstTime +
                ", memory=" + memory +
                '}';
    }
}
