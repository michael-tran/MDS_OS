import java.util.LinkedList;
import java.util.PriorityQueue;

public class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();
    private LinkedList<PCB> disk = new LinkedList<>();
    private LinkedList<PCB> main = new LinkedList<>();

    MainMemory() {
        for (int i = 0; i < 4096; i++) {
            MEMORY.add(new Page());
        }
    }

    LinkedList<Page> allocateMemory(int mem) {
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
        if(!(process.getPagesUsed() == null)) {
            for (Page page : process.getPagesUsed()) {
                page.toggleUsed();
            }
        } else {
            System.out.println("WHAT?");
            System.exit(1);
        }
        if (remove) {
            this.removeMain(process);
        }
    }

    public int mainMemoryUsage() {
        int count = 0;
        //Comment
        for (PCB pcb : this.main) {
            count += pcb.getMemoryRequirement();
        }
        return count;
    }

    public int getUsedCurrentMemory() {
        int count = 0;
        for (Page page : MEMORY) {
            if (page.isUsed()) {
                count++;
            }
        }
        return count;
    }

    public int remainingMemory() {
        return MEMORY.size() - getUsedCurrentMemory();
    }

    @Override
    public String toString() {
        return "Current memory usage: " + this.getUsedCurrentMemory() + "/4096 MB";
    }

    public void map(PCB process) {
        LinkedList<Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        if (pagesUsed.size() > 0) {
            main.add(process);
            process.setPagesUsed(pagesUsed);
        } else {
            disk.add(process);
        }
    }

    public void map() {
        for (PCB pcb : new LinkedList<PCB>(this.disk)) {
            this.diskToMain(pcb);
        }
    }

    public LinkedList<PCB> getMain() {
        return main;
    }

    public void addMain(PCB process) {
        this.main.add(process);
    }

    public boolean removeMain(PCB process) {
        return this.main.removeIf(x -> x.getPid() == process.getPid());
    }

    public LinkedList<PCB> getDisk() {
        return disk;
    }

    public void addDisk(PCB process) {
        process.setPriority(process.getState() - 1);
        this.disk.add(process);
    }

    public boolean removeDisk(PCB process) {
        return this.disk.removeIf(x -> x.getPid() == process.getPid());
    }

    public boolean diskToMain(PCB process) {
        LinkedList<MainMemory.Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        if(pagesUsed.size() > 0){
            boolean temp = this.removeDisk(process);
            process.setPagesUsed(pagesUsed);
            this.addMain(process);
            return true;
        }
        return false;
    }

    public void mainToDisk(PCB process) {
        this.deallocateMemory(process, false);
        process.setPagesUsed(null);
        boolean temp = this.removeMain(process);
        this.addDisk(process);
    }

    public String getTable() {
        return "Disk: \n" + this.getDisk() + "\n\n Main: \n" + this.getMain();
    }

    public void wipe(){
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