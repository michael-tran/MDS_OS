import java.util.ArrayList;

public class Scheduler {
    private ArrayList<PCB> PCBs;

    public Scheduler() {
        this.PCBs = new ArrayList<PCB>();
    }

    public ArrayList<PCB> getPCBs() {
        return PCBs;
    }

    public void addPCB(PCB pcb) {
        this.PCBs.add(pcb);
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "PCBs=" + PCBs +
                '}';
    }
}
//Round Robin