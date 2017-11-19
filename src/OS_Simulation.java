public class OS_Simulation {
    public static void main(String args[]) {
        MainMemory mainMemory = new MainMemory();
        CPU cpu = new CPU();
        Scheduler scheduler = new Scheduler(cpu, "Scheduler 1");
        Dispatcher dispatcher = new Dispatcher(mainMemory, scheduler);
        Computer computer = new Computer(dispatcher, mainMemory);
        MainFrame mf = new MainFrame(computer);
        mf.runGUI();


    }
}