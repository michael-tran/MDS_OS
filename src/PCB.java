public class PCB {
    private String name;
    private int clockIn, clockOut, breakTime, pid, state, CPU, beginTime, deadline;

    /**
     * Program file format:
     * line 1 - name
     * line 2 - total cycle count
     * line 3 - burst time
     * line 4 - mem needs
     * line 5 - priority, lower number is higher priority
     * ???
     */
    public PCB(String name, int clockIn, int clockOut, int breakTime, int processid, int state, int CPU, int beginTime, int deadline) {
        this.name = name;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.breakTime = breakTime;
        this.pid = processid; //calculated from incrementing int processid from Commands.java
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
}
