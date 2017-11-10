import java.util.LinkedList;

public class MainMemory {

    private final LinkedList<BitAndBytes> MEMORY = new LinkedList<>();

    public MainMemory() {
        for (int i = 0; i < 4096; i++) {
            MEMORY.add(new BitAndBytes());
        }
    }

    public boolean allocateMemory(int mem, int pid) {
        if (remainingMemory() > mem) {
            int count = 0;
            for (BitAndBytes bitAndBytes : MEMORY) {
                if (count == mem) break;
                if(!bitAndBytes.isUsed() && !(count > mem)){
                    bitAndBytes.toggleUsed();
                    bitAndBytes.setPid(pid);
                    count++;
                }
            }
            return true;
        } else return false;
    }

    public void deallocateMemory(int mem, int pid) {
        int count = 0;
        for (BitAndBytes bitAndBytes : MEMORY) {
            if (count == mem) break;
            if(bitAndBytes.isUsed() && (pid == bitAndBytes.getPid()) && !(count > mem)){
                bitAndBytes.toggleUsed();
                bitAndBytes.setPid(Integer.MIN_VALUE);
                count++;
            }
        }
    }

    public int getUsedCurrentMemory() {
        int count = 0;
        for (BitAndBytes bit : MEMORY) {
            if (bit.isUsed()) {
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

    public class BitAndBytes {
        private boolean used;
        private int pid;

        public BitAndBytes() {
            used = false;
            pid = Integer.MIN_VALUE;
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

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}


//Counter for PCB and make process currentMemory usage never goes above MAX