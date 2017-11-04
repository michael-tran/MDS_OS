public class MainMemory {

    private final int MAX_RAM = 4096;
    private int currentMemory;

    public MainMemory() {
        this.currentMemory = 0;
    }

    public boolean allocateMemory(int mem) {
        if (remainingMemory() > mem) {
            currentMemory = currentMemory + mem;
            return true;
        } else return false;
    }

    public void deallocateMemory(int mem) {
        currentMemory = currentMemory - mem;
    }

    public int getCurrentMemory() {
        return currentMemory;
    }

    public void setCurrentMemory(int currentMemory) {
        this.currentMemory = currentMemory;
    }

    public int remainingMemory() {
        return MAX_RAM - currentMemory;
    }

    @Override
    public String toString() {
        return "Current memory usage: " + currentMemory + "/4096 MB\n" +
                "idk fucking virtual memory something";
    }
}

//Counter for PCB and make process currentMemory usage never goes above MAX