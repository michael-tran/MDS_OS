import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class MainFrame extends JFrame {
    public JPanel os_display;
    private int count;
    private boolean on = true;
    private JButton exitButton;
    private JButton procButton;
    private JButton cleanButton;
    private JButton resetButton;
    private JButton memButton;
    private JButton helpButton;
    private JTextPane mainDisplay;
    private JTextField inputField;
    private JTextPane monitorDisplay;
    private StyledDocument mddoc = mainDisplay.getStyledDocument();
    private StyledDocument mtdoc = monitorDisplay.getStyledDocument();
    private SimpleAttributeSet keyWord = new SimpleAttributeSet();

    //The main constructor for the JFrame
    public MainFrame() {
        //Adds initial Text
        addText(mainDisplay, mddoc, "Welcome to Michael Doesn't Do Shit OS");
        addText(monitorDisplay, mtdoc, "System Resource Monitor \nTO-DO: Figure out multithreading so we can " +
                "update system info in real time \n" +
                "https://www.tutorialspoint.com/java/java_multithreading.htm \n" +
                "We may not need to do this in literal real time. The description made it sound like we can just " +
                "update this screen when the CPU processes one cycle");

        ///List of buttons
        //action for help button
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        //action for Proc button
        procButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText(mainDisplay, mddoc, "TODO: Displaying all running processes");
            }
        });

        //action for mem button
        memButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText(mainDisplay, mddoc, "TODO: Clears memory");
            }
        });

        //action for reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText(mainDisplay, mddoc, "TODO: RESETS EVERYTHING");
                on = true;
            }
        });

        //action for exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //action for clean button
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDisplay.setText("Welcome to Michael Doesn't Do Shit OS \n");
            }
        });

        //input text box
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = e.getActionCommand();
                inputField.setText("");
                addText(mainDisplay, mddoc, input);
                parseCommand(input.toLowerCase());
            }
        });
    }

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
        frame.setContentPane(new MainFrame().os_display);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("OOOOOOS");
        frame.setSize(800, 600);
        frame.setResizable(true);
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
        Commands commands = new Commands();

        // Error messages for incomplete commands
        switch (input) {
            case "exe":
                addText(mainDisplay, mddoc, "Missing arguments");
                return 0;
        }

        String[] args = input.split(" ");

        // Parse exe command
        switch (args[0]) {
            case "exe":
                addText(mainDisplay, mddoc, "Running simulation");
                return 0;
            case "proc":
                addText(mainDisplay, mddoc, "Displaying all running processes");
                return 0;
            case "mem":
                addText(mainDisplay, mddoc, "Memory");
                return 0;
            case "load":
                if (args.length > 1) {
                    addText(mainDisplay, mddoc, commands.load(args[1]));
                } else {
                    addText(mainDisplay, mddoc, "No arguments");
                }
                return 0;
            case "reset":
                addText(mainDisplay, mddoc, "Resets");
                on = true;
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
                                        "load\n");
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
                                        "names \t The name of the file used to load into the OS");
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
                break;
        }
        addText(mainDisplay, mddoc, "Please enter a valid command.");
        return 1;
    }
}
