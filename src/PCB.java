public class PCB {
    private String name;
    private int clockIn, clockOut, breakTime, pid, state, CPU, beginTime, Deadlin;

    public PCB(String name, int clockIn, int clockOut, int breakTime, int pid, int state, int CPU, int beginTime, int deadlin) {
        this.name = name;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.breakTime = breakTime;
        this.pid = pid;
        this.state = state;
        this.CPU = CPU;
        this.beginTime = beginTime;
        Deadlin = deadlin;
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

    public int getDeadlin() {
        return Deadlin;
    }

    public void setDeadlin(int deadlin) {
        Deadlin = deadlin;
    }
}
