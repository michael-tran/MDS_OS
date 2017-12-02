import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

public class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();
    private Queue<PCB> disk = new ConcurrentLinkedQueue<>();
    private Queue<PCB> main = new ConcurrentLinkedQueue<>();

    MainMemory() {
        for (int i = 0; i < 4096; i++) {
            MEMORY.add(new Page());
        }
    }

    private LinkedList<Page> allocateMemory(int mem) {
        LinkedList<Page> pagesUsed = new LinkedList<>();
        if (remainingMemory() > mem) {
            int count = 0;
            for (Page page : MEMORY) {
                if (count == mem) break;
                if (!page.isUsed() && !(count > mem)) {
                    page.toggleUsed();
                    pagesUsed.add(page);
                    count++;
                }
            }
        }
        return pagesUsed;
    }

    void deallocateMemory(PCB process, boolean remove) {
        if (!(process.getPagesUsed() == null)) {
            for (Page page : process.getPagesUsed()) {
                page.toggleUsed();
            }
        } else {
            this.disk.remove(process);
        }
        if (remove) {
            this.removeMain(process);
        }
    }

    public int mainMemoryUsage() {
        int count = 0;
        for (PCB pcb : this.main) {
            count += pcb.getMemoryRequirement();
        }
        return count;
    }

    private int getUsedCurrentMemory() {
        int count = 0;
        for (Page page : MEMORY) {
            if (page.isUsed()) {
                count++;
            }
        }
        return count;
    }

    int remainingMemory() {
        return MEMORY.size() - getUsedCurrentMemory();
    }

    @Override
    public String toString() {
        return "Current memory usage: " + this.getUsedCurrentMemory() + "/4096 MB";
    }

    void map(PCB process) {
        LinkedList<Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        if (pagesUsed.size() > 0) {
            main.add(process);
            process.setPagesUsed(pagesUsed);
        } else {
            disk.add(process);
        }
    }

    void map() {
        for (PCB pcb : new LinkedList<PCB>(this.disk)) {
            this.diskToMain(pcb);
        }
    }

    Queue<PCB> getMain() {
        return main;
    }

    private void addMain(PCB process) {
        this.main.add(process);
    }

    private boolean removeMain(PCB process) {
        return this.main.removeIf(x -> x.getPid() == process.getPid());
    }

    Queue<PCB> getDisk() {
        return disk;
    }

    private void addDisk(PCB process) {
        process.setPriority(process.getState() - 1);
        this.disk.add(process);
    }

    private boolean removeDisk(PCB process) {
        return this.disk.removeIf(x -> x.getPid() == process.getPid());
    }

    boolean diskToMain(PCB process) {
        LinkedList<MainMemory.Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        if (pagesUsed.size() > 0) {
            boolean temp = this.removeDisk(process);
            process.setPagesUsed(pagesUsed);
            this.addMain(process);
            return true;
        }
        return false;
    }

    void mainToDisk(PCB process) {
        this.deallocateMemory(process, false);
        process.setPagesUsed(null);
        boolean temp = this.removeMain(process);
        this.addDisk(process);
    }

    String getTable() {
        StringBuilder output = new StringBuilder();

        output.append("RAM:\n");
        if (!this.main.isEmpty()) {
            for (PCB pcb : this.main) {
                output.append(pcb.toString());
            }
        }

        output.append("\nDisk:\n");
        if (!this.disk.isEmpty()) {
            for (PCB pcb : this.disk) {
                output.append(pcb.toString());
            }
        }

        return (output.length() == 0) ? "No process loaded" : output.toString();
    }

    void wipe() {
        for (PCB pcb : new LinkedList<PCB>(this.main)) {
            this.mainToDisk(pcb);
        }
    }

    class Page {
        private boolean used;

        Page() {
            used = false;
        }

        boolean isUsed() {
            return used;
        }

        void toggleUsed() {
            this.used = !this.used;
        }
    }

    void reset() {
        for (Page page : MEMORY) {
            if (page.isUsed()) {
                page.toggleUsed();
            }
        }
        this.disk.clear();
        this.main.clear();
    }
}