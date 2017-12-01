class Comm {
    private static Scheduler scheduler;
    private static Dispatcher dispatcher;
    private static MainMemory memory;
    private static Computer computer;

    Comm(Scheduler scheduler, Dispatcher dispatcher, Computer computer, MainMemory memory) {
        Comm.scheduler = scheduler;
        Comm.dispatcher = dispatcher;
        Comm.memory = memory;
        Comm.computer = computer;
    }

    static void callDispatcherToDelete(PCB pcb) {
        dispatcher.dispatch(pcb);
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