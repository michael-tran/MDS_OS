import javax.swing.*;

public class OS_Simulation {

    public static void main(String args[]) {
        MainMemory mainMemory = new MainMemory();
        CPU cpu = new CPU();

        Scheduler scheduler = new Scheduler(cpu);

        Dispatcher dispatcher = new Dispatcher(mainMemory, scheduler);
        Computer computer = new Computer(dispatcher, mainMemory, scheduler);

        Comm comm = new Comm(scheduler, dispatcher, computer, mainMemory); // Communication between components

        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame(computer);
            mf.runGUI();
        });
    }
}