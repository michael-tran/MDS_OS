import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();
    private final Queue<PCB> disk = new ConcurrentLinkedQueue<>();
    private final Queue<PCB> main = new ConcurrentLinkedQueue<>();

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

    private int getUsedCurrentMemory() {
        int count = 0;
        for (Page page : MEMORY) {
            if (page.isUsed()) {
                count++;
            }
        }
        return count;
    }

    private int remainingMemory() {
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
        for (PCB pcb : new LinkedList<>(this.disk)) {
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

    private void addDisk(PCB process) {
        process.setPriority(process.getState() - 1);
        this.disk.add(process);
    }

    private boolean removeDisk(PCB process) {
        return this.disk.removeIf(x -> x.getPid() == process.getPid());
    }

    private boolean diskToMain(PCB process) {
        LinkedList<MainMemory.Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        if (pagesUsed.size() > 0) {
            boolean temp = this.removeDisk(process);
            process.setPagesUsed(pagesUsed);
            this.addMain(process);
            return true;
        }
        return false;
    }

    private void mainToDisk(PCB process) {
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

    private void wipe() {
        for (PCB pcb : new LinkedList<>(this.main)) {
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

    void check(PCB process) {
        if (this.disk.contains(process)) { //checks and sees if the peek is in the disk
            boolean allocated = false;
            if (this.remainingMemory() > process.getMemoryRequirement()) //Checks if there is available space
                allocated = Comm.getMemory().diskToMain(process);
            else {
                for (PCB item : this.getMain()) { //Checks to find a process in main and move it to disk
                    if ((item.getMemoryRequirement() + this.remainingMemory()) > item.getMemoryRequirement()) {
                        this.mainToDisk(item);
                        allocated = this.diskToMain(process);
                        break;
                    }
                }
            }
            if (!allocated) { //if failed to find any available space wipes the main and allocates the process
                this.wipe();
                this.diskToMain(process);
            }
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