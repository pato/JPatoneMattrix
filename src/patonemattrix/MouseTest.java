package patonemattrix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MouseTest extends JFrame {
  int startX, startY, endX, endY;

  Color color = Color.BLACK;

  public MouseTest() {
    super();

    final JPopupMenu pop = new JPopupMenu();
    pop.add(new JMenuItem("Cut"));
    pop.add(new JMenuItem("Copy"));
    pop.add(new JMenuItem("Paste"));
    pop.addSeparator();
    pop.add(new JMenuItem("Select All"));
    pop.setInvoker(this);

    MouseListener popup = new MouseListener() {
      public void mouseClicked(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
          showPopup(e);
        }
      }

      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
          showPopup(e);
        }
      }

      private void showPopup(MouseEvent e) {
        pop.show(e.getComponent(), e.getX(), e.getY());
      }
    };
    addMouseListener(popup);

    MouseListener drawing1 = new MouseListener() {
      public void mouseClicked(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
        color = Color.RED;
        startX = endX = e.getX();
        startY = endY = e.getY();
        repaint();
      }

      public void mouseReleased(MouseEvent e) {
        color = Color.BLACK;
        repaint();
      }
    };
    addMouseListener(drawing1);

    MouseMotionListener drawing2 = new MouseMotionListener() {
      public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        repaint();
      }

      public void mouseMoved(MouseEvent e) {
      }
    };
    addMouseMotionListener(drawing2);

  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(color);
    g.drawLine(startX, startY, endX, endY);
  }

  public static void main(String args[]) {
    JFrame frame = new MouseTest();
    frame.setSize(300, 300);
    frame.show();
  }
}