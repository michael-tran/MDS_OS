import java.util.LinkedList;

public class MainMemory {

    private final LinkedList<Page> MEMORY = new LinkedList<>();

    public MainMemory() {
        for (int i = 0; i < 4096; i++) {
            MEMORY.add(new Page(i));
        }
    }

    public LinkedList<Page> allocateMemory(int mem) {
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

    public void deallocateMemory(int mem, int pid) {
        int count = 0;
        for (Page page : MEMORY) {
            if (count == mem) break;
            if (page.isUsed() && (pid == page.getPageid()) && !(count > mem)) {
                page.toggleUsed();
                page.setPageid(Integer.MIN_VALUE);
                count++;
            }
        }
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
        return "Current memory usage: " + this.getUsedCurrentMemory() + "/4096 MB\n" +
                "idk fucking virtual memory something";
    }

    public class Page {
        private boolean used;

        private int pageid;

        public Page(int i) {
            used = false;
            pageid = i;
        }

        public boolean isUsed() {
            return used;
        }

        public void toggleUsed() {
            if (this.used) {
                this.used = false;
            } else {
                this.used = true;
            }
        }

        public int getPageid() {
            return pageid;
        }

        public void setPageid(int pageid) {
            this.pageid = pageid;
        }
    }

    private class PageTable {
        private final LinkedList<Page> PAGETABLE = new LinkedList<>();

        public PageTable() {
            for (int i = 0; i < 4096; i++) {
                PAGETABLE.add(MEMORY.get(i));
            }
        }
    }
}