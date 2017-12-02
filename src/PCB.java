import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PCB implements Comparable<PCB> {
    private final String name;
    private final int processType;
    private final int pid;
    private int state;
    private int priority;
    private final int burstCycle;
    private int remainingBurstCycle;
    private final int memoryRequirement;
    private int commandsIndex;
    private final List<int[]> commands;
    private final List<PCB> children = new ArrayList<>();
    private final PCB parent;
    private LinkedList<MainMemory.Page> pagesUsed;

    /**
     * process state:
     * 0 NEW
     * 1 READY
     * 2 RUN
     * 3 WAIT/BLOCKED
     * 4 EXIT
     * <p>
     * Program file format:
     * line 0 - name
     * line 1 - memory requirements
     * line 2 - priority
     * line 3 - burstCycle
     * line 4 - totalCycle
     * calculation, 0
     * output, 3
     */
    PCB(String name, int processid, int processType, int memoryRequirement, int burstCycle, int priority,
        List<int[]> commands, PCB parent) {
        this.name = name;
        this.pid = processid;
        this.processType = processType;
        this.memoryRequirement = memoryRequirement;
        this.burstCycle = burstCycle;
        this.remainingBurstCycle = 0;
        this.priority = priority;
        this.commands = commands;
        commandsIndex = 0;
        this.state = 0;
        this.parent = parent;
    }

    @Override
    public int compareTo(PCB process) {
        return priority - process.priority;
    }

    String getName() {
        return name;
    }

    int getProcessType() {
        return processType;
    }

    int getState() {
        return state;
    }

    void setState(int state) {
        this.state = state;
    }

    PCB getParent() {
        return parent;
    }

    List<PCB> getChildren() {
        return children;
    }

    void addChildren(PCB pcb) {
        this.children.add(pcb);
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    int getBurstCycle() {
        return burstCycle;
    }

    int getMemoryRequirement() {
        return memoryRequirement;
    }

    LinkedList<MainMemory.Page> getPagesUsed() {
        return pagesUsed;
    }

    List<int[]> getCommands() {
        return commands;
    }

    void setPagesUsed(LinkedList<MainMemory.Page> pagesUsed) {
        this.pagesUsed = pagesUsed;
    }

    int getRemainingBurstCycle() {
        return remainingBurstCycle;
    }

    void setRemainingBurstCycle(int remainingBurstCycle) {
        this.remainingBurstCycle = remainingBurstCycle;
    }

    int getCommandsIndex() {
        return commandsIndex;
    }

    void setCommandsIndex(int commandsIndex) {
        this.commandsIndex = commandsIndex;
    }

    int getPid() {
        return pid;
    }

    private String getStateName() {
        switch (state) {
            case 0:
                return "NEW";
            case 1:
                return "READY";
            case 2:
                return "RUNNING";
            case 3:
                return "WAITING";
            case 4:
                return "TERMINATED";
            default:
                return "You shouldn't see this";
        }
    }

    ArrayList<PCB> killChildren() {
        ArrayList<PCB> killList = new ArrayList<>();
        while (!this.children.isEmpty()) {
            for (PCB process : new ArrayList<>(this.children)) {
                ArrayList<PCB> temp = process.killChildren();
                killList.addAll(temp);
                process.setState(4);
                killList.add(process);
            }
            this.children.clear();
        }
        return killList;
    }

    @Override
    public String toString() {
        return name + "\t " + pid + "\t" + getStateName() + "\t     " + priority + "\t       " +
                burstCycle + "\t    " + memoryRequirement + "\n";
    }
}