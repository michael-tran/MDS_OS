public class Memory {

    private int memory;
    private final int MAX_RAM = 4096;

    public Memory() {
        this.memory = 0;
    }

    public void allocateMemory(int mem) {
        memory =+ mem;
    }

    public void resetMemory() {
        memory = 0;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}