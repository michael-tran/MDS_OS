import java.util.LinkedList;

public class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();

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

    void deallocateMemory(LinkedList<Page> usedPage) {
        for (Page page : usedPage) {
            page.toggleUsed();
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