import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JPanel{
    private JPanel os_display;
    private final Computer computer;
    private int count;
    private boolean on = true;
    private boolean generated = false;
    private JButton exitButton;
    private JButton procButton;
    private JButton cleanButton;
    private JButton resetButton;
    private JButton memButton;
    private JButton helpButton;
    private JTextPane mainDisplay;
    private JTextField inputField;
    private JTextPane monitorDisplay;
    private JButton pauseButton;
    private JButton genButton;
    private JLabel imageLabel;
    private JButton exeButton;
    private StyledDocument mddoc = mainDisplay.getStyledDocument();
    private StyledDocument mtdoc = monitorDisplay.getStyledDocument();
    private SimpleAttributeSet keyWord = new SimpleAttributeSet();

    //The main constructor for the JFrame
    public MainFrame(Computer computer) {
        this.computer = computer;

        //Adds initial Text
        addText(mainDisplay, mddoc, "Welcome to MDS OS");
        addText(monitorDisplay, mtdoc, "System Resource Monitor\n" + computer.proc());

        // List of buttons
        // action for help button
        helpButton.addActionListener((e) -> {
            if (on) {
                if (count >= 4) {
                    addText(mainDisplay, mddoc, "\n\n\n\nPlease stop\n\n\n\n");
                    count = 0;
                } else {
                    addText(mainDisplay, mddoc, "List of Commands:" +
                            "\n Proc: Shows all unfinished Processes" +
                            "\n Mem: Shows the current Usage of memory space" +
                            "\n Exe: Lets the simulation run on its own" +
                            "\n Load: loads a program" +
                            "\n Reset: Resets everything");
                }
                count++;
            }

        });

        //action for Proc button
        procButton.addActionListener((e) -> {
            addText(mainDisplay, mddoc, computer.proc());
        });

        //action for Gen button
        genButton.addActionListener((e) -> {
            addText(mainDisplay, mddoc, "Generating 5 processes");
            computer.gen(5);
            monitorDisplay.setText(computer.mem() + "\n" + computer.proc());
        });

        //action for mem button
        memButton.addActionListener((e) -> {
            addText(mainDisplay, mddoc, computer.mem());
        });

        //action for reset button
        resetButton.addActionListener((e) -> {
            mainDisplay.setText("TO DO: Implement computer.reset()\n");
            computer.reset();
            on = true;
            generated = false;
            monitorDisplay.setText(computer.mem() + "\n" + computer.proc());
        });

        //action for exit button
        exitButton.addActionListener((e) -> {
            System.exit(0);
        });

        //action for clean button
        cleanButton.addActionListener((e) -> {
            mainDisplay.setText("Welcome to MDS OS \n");
        });

        exeButton.addActionListener((e) -> {
            addText(mainDisplay, mddoc, "----------------------\n"+ "Starting Simulation\n" +
                    "----------------------\n");
            computer.exe(0);
        });

        //input text box
        inputField.addActionListener((e) -> {
            String input = e.getActionCommand();
            inputField.setText("");
            addText(mainDisplay, mddoc, input);
            parseCommand(input.toLowerCase());
        });
    }

    // Initializes GUI
    public void runGUI() {

        // Changes UI appearance to not ugly
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        // UI Initialize
        JFrame frame = new JFrame("MDS OS");
        frame.setContentPane(new MainFrame(computer).os_display);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("MDS OS");
        frame.setSize(1200, 768);
        frame.setResizable(false);
    }

    // Appends text to specified JTextPane
    public void addText(JTextPane jtp, StyledDocument sd, String s) {
        jtp.setEditable(true);
        try {
            sd.insertString(sd.getLength(), s + "\n", keyWord);
        } catch (Exception e) {
            System.out.println(e);
        }
        jtp.setEditable(false);
    }

    // Parses user inputs in JTextField
    private int parseCommand(String input) {
        String[] args = input.split(" ");

        // Parse exe command
        switch (args[0]) {
            case "exe":
                if (args.length > 1) {
                    int n = 0;
                    try {
                        n = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        addText(mainDisplay, mddoc, "Not a number");
                        return 0;
                    }
                    addText(mainDisplay, mddoc, "----------------------\n"+ "Starting Simulation\n" +
                            "----------------------\n");
                    computer.exe(n);
                    return 1;
                }
                return 0;
            case "proc":
                addText(mainDisplay, mddoc, computer.proc());
                return 1;
            case "mem":
                addText(mainDisplay, mddoc, computer.mem());
                return 1;
            case "load":
                if (args.length > 1) {
                    addText(mainDisplay, mddoc, computer.load(args[1]));
                    monitorDisplay.setText(computer.mem() + "\n" + computer.proc());
                    return 1;
                } else {
                    addText(mainDisplay, mddoc, "No arguments");
                    return 0;
                }
            case "gen":
                addText(mainDisplay, mddoc, "Generating 5 processes");
                computer.gen(5);
                monitorDisplay.setText(computer.mem() + "\n" + computer.proc());
                return 1;
            case "reset":
                mainDisplay.setText("TO DO: Implement computer.reset()\n");
                on = true;
                generated = false;
                monitorDisplay.setText(computer.mem() + "\n" + computer.proc());
                return 0;
            case "exit":
                System.exit(0);
                return 0;
            case "help":
                if (on) {
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "exe":
                                addText(mainDisplay, mddoc, "\n\nLets the simulation run on its own\n" +
                                        "exe #\n" +
                                        "# \t The number of cycles to run before pausing\n");
                                break;
                            case "proc":
                                addText(mainDisplay, mddoc, "\n\nProc: Shows all unfinished Processes\n" +
                                        "Proc\n");
                                break;
                            case "mem":
                                addText(mainDisplay, mddoc, "\n\nMem: Shows the current Usage of memory space\n" +
                                        "mem\n");
                                break;
                            case "load":
                                addText(mainDisplay, mddoc, "\n\nLoads a program\n" +
                                        "load names\n" +
                                        "names \t The name of the file used to load into the OS\n" +
                                        "Example: Load Test");
                                break;
                            case "reset":
                                addText(mainDisplay, mddoc, "\n\nReset: Resets everything\"\n" +
                                        "reset\n");
                                break;
                            case "exit":
                                System.exit(0);
                                break;
                            case "help":
                                addText(mainDisplay, mddoc, "Disabling help");
                                on = false;
                                break;
                            default:
                                addText(mainDisplay, mddoc, "Please enter a valid command.");
                                break;
                        }
                    } else {
                        addText(mainDisplay, mddoc, "List of Commands:" +
                                "\n Proc: Shows all unfinished Processes" +
                                "\n Mem: Shows the current Usage of memory space" +
                                "\n Exe: Lets the simulation run on its own" +
                                "\n Load: loads a program" +
                                "\n Reset: Resets everything");
                    }
                }
                return 0;
            default:
                addText(mainDisplay, mddoc, "Please enter a valid command.");
                return 0;
        }
    }

    private void createUIComponents() {
        imageLabel = new JLabel(new ImageIcon("aaa.jpg"));
    }
}
