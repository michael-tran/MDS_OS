import javax.swing.*;

public class OS_Simulation {

    public static void main(String args[]) {
        MainMemory mainMemory = new MainMemory();
        CPU cpu = new CPU();

        Scheduler scheduler = new Scheduler(cpu, "Scheduler 1");
        Thread thread1 = new Thread(scheduler);
        thread1.start();

        Dispatcher dispatcher = new Dispatcher(mainMemory, scheduler);
        Computer computer = new Computer(dispatcher, mainMemory);

        Comm comm = new Comm(scheduler, dispatcher);

        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame(computer);
            mf.runGUI();
        });
    }
}