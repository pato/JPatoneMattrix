package patonemattrix;
import static java.lang.System.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class PatoneMattrix{
    public static JFrame f;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                start();
            }
        });
    }
    public static void start() {
        f = new JFrame("Patone Mattrix [Tone Matrix] - Pato Edition");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PatonePanel panel = new PatonePanel();
        panel.setSize(panel.getPreferredSize());
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }
}
