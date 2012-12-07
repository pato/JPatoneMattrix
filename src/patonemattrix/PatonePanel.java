package patonemattrix;
import static java.lang.System.*;
import java.util.*;
import java.awt.event.*;
import org.jfugue.*;
import javax.swing.*;
import java.awt.*;
import javax.sound.midi.*;

public class PatonePanel extends JPanel{
    private static final int rows = 12;
    private static final int columns = 26;
    private static final int delay = 150;
    private static final Color inactive = Color.blue;
    private static final Color active = Color.red;
    private static final int xoffset = 10;
    private static final int yoffset = 10;
    
    private static boolean[][] keys = new boolean[columns][rows];
    private static java.util.Timer timer;
    public static int position;
    public static Synthesizer synth;
    public static MidiChannel midiChannel;
    private static int[] notes = new int[keys[0].length];
    public static int channel = 0;
    private static final int[] pato = {2,2,3};
    private static final int[] matt = {2,3,2,2,3};
    private static final int[] mattr = {3,2,2,3,2};
    private static final int[] simple = {1,1};
    private static final int[] scale = mattr;
    private static int scaleStart = 80;
    

    public PatonePanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        MouseListener popup = new MouseListener() {
          public void mouseClicked(MouseEvent e){
          }
          public void mouseEntered(MouseEvent e){}
          public void mouseExited(MouseEvent e){}
          public void mousePressed(MouseEvent e){
            if (e.getY()<(rows*35+xoffset)){
                activate(e.getPoint());
            }else{
                click(e.getPoint());
            }
            PatonePanel.this.repaint();
          }
          public void mouseReleased(MouseEvent e){}
        };
        addMouseListener(popup);
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
            midiChannel = synth.getChannels()[channel];
        }catch (Exception e){err.println(e);}
        generateNotes();
        position = 0;
        startPlaying();
    }
    public static void generateNotes(){
        int start = scaleStart;
        for (int i=0;i<notes.length;i++){
            notes[i] = start-=scale[i%(scale.length)];
        }
    }
    public static void generateNotes(boolean drums){
        int start = scaleStart;
        for (int i=0;i<notes.length;i++){
            notes[i] = start-=1;
        }
    }
    private void startPlaying() {
        timer = new java.util.Timer();
        timer.schedule( new TimerTask(){
            public void run(){
                position = (position<keys.length)?++position:1;
                PatonePanel.this.repaint();
                PatonePanel.play();
            }
        }, 0, delay);
    }
    public static void play(){
        for (int i=0;i<keys[position-1].length;i++){
            if (keys[position-1][i]==true){
                midiChannel.noteOn(notes[i], 300);
            }
        }
    }
    public static void activate(Point p){
        int x = (int)(p.getX()-xoffset)/35;
        int y = (int)(p.getY()-yoffset)/35;
        keys[x][y] = !keys[x][y];
    }
    public static void random(){
        for (int i=0;i<keys.length;i++)
            for (int y=0;y<keys[i].length;y++)
                keys[i][y] = false;
        for (int i=0;i<keys.length;i++)
            for (int y=0;y<keys[i].length;y++)
                keys[i][y] = Math.random()<0.1;
    }
    public static void click(Point p){
        int x = (int)(p.getX()-xoffset)/70;
        if (x==0){ //reset
            for (int i=0;i<keys.length;i++)
                for (int y=0;y<keys[i].length;y++)
                    keys[i][y] = false;
        }else if (x==1){ //inverse
            for (int i=0;i<keys.length;i++)
                for (int y=0;y<keys[i].length;y++)
                    keys[i][y] = !keys[i][y];
        }else if (x==2){ //pitch up
            scaleStart += 5;
            generateNotes();
        }else if (x==3){ //pitch down
            scaleStart -= 5;
            generateNotes();
        }else if (x==5){ //change instrument
            try{
                if (channel==9){
                    channel=0;
                    generateNotes();
                }else{
                    channel=9;
                    generateNotes(true);
                }
                midiChannel = synth.getChannels()[channel];
            }catch (Exception e){};
        }else if (x==6){ //random
            random();
        }
    }
    public Dimension getPreferredSize() {
        return new Dimension(myWidth(),myHeight());
    }

    public static int myWidth(){
        return keys.length*35+(2)*xoffset;
    }
    public static int myHeight(){
        return (keys[0].length)*35+(2)*yoffset+30;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString("Reset", 30, myHeight()-15);
        g.drawString("Inverse", 30+70*1, myHeight()-15);
        g.drawString("Pitch Up", 30+70*2, myHeight()-15);
        g.drawString("Pitch Down", 30+70*3, myHeight()-15);
        g.drawString("Pitch: "+scaleStart, 30+70*4, myHeight()-15);
        g.drawString((channel==9)?"Drums":"Piano", 30+70*5, myHeight()-15);
        g.drawString("Random", 30+70*6, myHeight()-15);
        
        for (int i=1;i<=keys.length;i+=1){
            for (int y=1;y<=keys[0].length;y+=1){
                if (position==i)
                    g.setColor(active);
                else
                    g.setColor(inactive);
                if (keys[i-1][y-1])
                    //g.setColor(active);
                    g.fillRect(xoffset+35*(i-1),yoffset+35*(y-1), 30, 30);
                else
                    //g.setColor(inactive);
                    g.drawRect(xoffset+35*(i-1),yoffset+35*(y-1), 30, 30);
            }
        }
    }
}