public class Comm {
    private static Scheduler scheduler;
    private static Dispatcher dispatcher;
    private static MainMemory memory;
    private static Computer computer;

    Comm(Scheduler scheduler, Dispatcher dispatcher, Computer computer, MainMemory memory) {
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.memory = memory;
        this.computer = computer;
    }

    static void callDispatcherToDelete(PCB pcb) {
        dispatcher.dispatch(pcb, 0);
    }

    static void callDispatcherForMore() {
        dispatcher.additionalDispatch();
    }

    static void genChildProcess(PCB parentProcess) {
        computer.genChildProcess(parentProcess);
    }

    static MainMemory getMemory(){
       return memory;
    }

    static void reset() {
        dispatcher.reset();
        scheduler.reset();
    }

}