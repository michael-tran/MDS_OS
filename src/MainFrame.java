import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class MainFrame extends JPanel {
    private JPanel os_display;
    private final Computer computer;
    private boolean running = false;
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
    JLabel imageLabel;
    private JButton exeButton;
    JLabel imageLabel2;
    private StyledDocument mddoc;
    private SimpleAttributeSet keyWord;
    private SwingWorker<Void, Void> worker;

    //The main constructor for the JFrame
    MainFrame(Computer computer) {
        this.computer = computer;

        // Initializes text pane-related variables
        mddoc = mainDisplay.getStyledDocument();
        keyWord = new SimpleAttributeSet();
        addText(mainDisplay, mddoc, "Welcome to MDS OS");

        monitorDisplay.setEditable(false);

        // Start updating system resource in real time
        updateResourceMonitor();

        // List of buttons
        helpButton.addActionListener((e) ->
                addText(mainDisplay, mddoc, "List of Commands:" +
                        "\n Proc: Shows all unfinished Processes" +
                        "\n Mem: Shows the current Usage of memory space" +
                        "\n Exe: Lets the simulation run on its own" +
                        "\n Load: loads a program" +
                        "\n Reset: Resets everything"));

        procButton.addActionListener((e) -> addText(mainDisplay, mddoc, computer.proc()));

        genButton.addActionListener((e) -> addText(mainDisplay, mddoc, computer.gen(5)));

        memButton.addActionListener((e) -> addText(mainDisplay, mddoc, computer.mem()));

        resetButton.addActionListener((e) -> mainDisplay.setText(computer.reset()));

        exitButton.addActionListener((e) -> System.exit(0));

        cleanButton.addActionListener((e) -> mainDisplay.setText("Welcome to MDS OS \n"));

        exeButton.addActionListener((e) -> {
            if (!running) {
                callExe(0);
            } else {
                addText(mainDisplay, mddoc, "Simulation is already underway");
            }
        });

        pauseButton.addActionListener((e) -> {
            // TO DO
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
    void runGUI() {
        // Changes UI appearance to not ugly
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
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
    private void addText(JTextPane jtp, StyledDocument sd, String s) {
        jtp.setEditable(true);
        try {
            sd.insertString(sd.getLength(), s + "\n", keyWord);
        } catch (BadLocationException e) {
            e.getStackTrace();
        }
        jtp.setEditable(false);
    }

    // Parses user inputs in JTextField
    private void parseCommand(String input) {
        // Separates input into command and argument, if any
        String[] args = input.split(" ");

        switch (args[0]) {
            case "exe":
                if (running) {
                    addText(mainDisplay, mddoc, "Simulation is already underway");
                    break;
                }
                if (args.length > 1) {
                    int n;
                    try {
                        n = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        addText(mainDisplay, mddoc, "Argument must be an integer");
                        break;
                    }
                    callExe(n);
                    break;
                }
                break;
            case "proc":
                addText(mainDisplay, mddoc, computer.proc());
                break;
            case "mem":
                addText(mainDisplay, mddoc, computer.mem());
                break;
            case "load":
                if (args.length > 1) {
                    addText(mainDisplay, mddoc, computer.load(args[1]));
                    break;
                } else {
                    addText(mainDisplay, mddoc, "No arguments");
                    break;
                }
            case "gen":
                if (args.length > 1) {
                    int n;
                    try {
                        n = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        addText(mainDisplay, mddoc, "Argument must be an integer");
                        break;
                    }
                    addText(mainDisplay, mddoc, computer.gen(n));
                    break;
                } else addText(mainDisplay, mddoc, computer.gen(5));
                break;
            case "reset":
                mainDisplay.setText(computer.reset());
                worker.cancel(true);
                computer.reset();
                break;
            case "exit":
                System.exit(0);
            case "help":
                if (args.length > 1) {
                    switch (args[1]) {
                        case "exe":
                            addText(mainDisplay, mddoc, "\n\nRuns the simulation on its own\n" +
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
                break;
            default:
                addText(mainDisplay, mddoc, "Please enter a valid command.");
                break;
        }
    }

    private void createUIComponents() {
        imageLabel2 = new JLabel(new ImageIcon("aaa.jpg"));
        imageLabel = new JLabel(new ImageIcon("bbb.jpg"));
    }

    private void callExe(int n) {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                addText(mainDisplay, mddoc, "----------------------\n" + "Starting Simulation\n" +
                        "----------------------\n");
                computer.exe(n);
                return null;
            }
        };

        worker.execute();
    }

    private void updateResourceMonitor() {
        Timer timer = new Timer(10, (e) -> monitorDisplay.setText("System resource monitor\n\n" +
                computer.mem() + "\n\n" + computer.proc()));
        timer.start();
    }
}