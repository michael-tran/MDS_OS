import java.util.LinkedList;
import java.util.PriorityQueue;

public class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();
    private PriorityQueue<PCB> disk = new PriorityQueue<>();
    private PriorityQueue<PCB> main = new PriorityQueue<>();

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
            for (Page page : process.getPagesUsed()) {
                page.toggleUsed();
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

    public PriorityQueue<PCB> getMain() {
        return main;
    }

    public void addMain (PCB process){
        process.setPriority(process.getState()-1);
        this.main.add(process);
    }

    public void removeMain (PCB process){
        this.main.removeIf(x -> x.getPid() == process.getPid());
    }

    public PriorityQueue<PCB> getDisk() {
        return disk;
    }

    public void addDisk (PCB process){
        process.setPriority(process.getState()-1);
        this.disk.add(process);
    }

    public void removeDisk (PCB process){
        this.disk.removeIf(x -> x.getPid() == process.getPid());
    }

    public void diskToMain(PCB process){
        this.removeDisk(process);
        LinkedList<MainMemory.Page> pagesUsed = this.allocateMemory(process.getMemoryRequirement());
        process.setPagesUsed(pagesUsed);
        this.addMain(process);
    }

    public void mainToDisk(PCB process){
        this.deallocateMemory(process, false);
        process.setPagesUsed(null);
        this.removeMain(process);
        this.addDisk(process);
    }

    public String getTable(){
        return "Disk: \n" + this.getDisk() + "\n\n Main: \n" + this.getMain();
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
    }
}