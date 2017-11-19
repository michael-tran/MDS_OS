import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PCB implements Comparable<PCB> {
    private String name;
    private int pid;
    private int state;
    private int priority;
    private int burstCycle;
    private int remainingBurstCycle;
    private int memoryRequirement;
    private int commandsIndex;
    private List<int[]> commands;
    private LinkedList<MainMemory.Page> pagesUsed;
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
     * line 2 - priority
     * line 3 - burstCycle
     * line 4 - totalCycle
     * calculation, 0
     * output, 3
     */
    public PCB(String name, int processid, int memoryRequirement, int burstCycle, int priority, List<int[]> commands) {
        this.name = name;
        this.pid = processid;
        this.memoryRequirement = memoryRequirement;
        this.burstCycle = burstCycle;
        this.remainingBurstCycle = 0;
        this.priority = priority;
        this.commands = commands;
        commandsIndex = 0;
        this.state = 0;
    }

    @Override
    public int compareTo(PCB process) {
        return priority - process.priority;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
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

    public int getBurstCycle() {
        return burstCycle;
    }

    public int getMemoryRequirement() {
        return memoryRequirement;
    }

    public LinkedList<MainMemory.Page> getPagesUsed() {
        return pagesUsed;
    }

    public List<int[]> getCommands() {
        return commands;
    }

    public void setPagesUsed(LinkedList<MainMemory.Page> pagesUsed) {
        this.pagesUsed = pagesUsed;
    }

    public int getRemainingBurstCycle() {
        return remainingBurstCycle;
    }

    public void setRemainingBurstCycle(int remainingBurstCycle) {
        this.remainingBurstCycle = remainingBurstCycle;
    }

    public int getCommandsIndex() {
        return commandsIndex;
    }

    public void setCommandsIndex(int commandsIndex) {
        this.commandsIndex = commandsIndex;
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