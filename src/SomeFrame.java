import javax.swing.*;

public class SomeFrame
{
    public static void main( String[] args )
    {
        JFrame frame = new JFrame( );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JTextPane tp = new JTextPane();
        JScrollPane sp = new JScrollPane(tp);
        frame.getContentPane().add( sp );

        frame.pack( );
        frame.setVisible( true );
    }
}