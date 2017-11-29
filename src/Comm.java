public class Comm {
    private static Scheduler scheduler;
    private static Dispatcher dispatcher;

    Comm(Scheduler scheduler, Dispatcher dispatcher) {
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
    }

    static void callDispatcherToDelete(PCB pcb) {
        dispatcher.dispatch(pcb, 0);
    }

    static void callDispatcherForMore() {
        dispatcher.additionalDispatch();
    }

    static void reset() {
        dispatcher.reset();
        scheduler.reset();
    }

}