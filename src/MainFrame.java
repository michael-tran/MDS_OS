import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public JPanel os_display;
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

    public MainFrame() {
        addText(mainDisplay, mddoc, "Welcome to Michael Doesn't Do Shit OS");
        addText(monitorDisplay, mtdoc, "System Resource Monitor \n TO-DO: Figure out multithreading so we can" +
                "update system info in real time \n" +
                "https://www.tutorialspoint.com/java/java_multithreading.htm");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = e.getActionCommand();
                inputField.setText("");
                addText(mainDisplay, mddoc, input);
                parseCommand(input.toLowerCase());
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText(mainDisplay, mddoc, "Display help message");
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDisplay.setText("Welcome to Michael Doesn't Do Shit OS \n");
            }
        });
    }

    public void runGUI() {

        // Changes UI appearance to not ugly
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
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
        // Error messages for incomplete commands
        switch (input) {
            case "exe": addText(mainDisplay, mddoc, "Missing arguments"); return 0;
        }

        String[] args = input.split(" ");

        // Parse exe command
        switch (args[0]) {
            case "exe": addText(mainDisplay, mddoc, "Running simulation"); return 0;
            case "proc": addText(mainDisplay, mddoc, "Displaying all running processes"); return 0;
        }

        addText(mainDisplay, mddoc, "Please enter a valid command.");
        return 1;
    }
}
