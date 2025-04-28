import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{
   
   int x;
   int y;
   Point position;
   boolean[] event = new boolean[7];
   
   Mouse(){

   }

   @Override
   public void mousePressed(MouseEvent e) {
      event[0]=true;
      event[1]=false;

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      event[1]=true;
      event[0]=false;

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      event[2]=true;
      event[3]=false;
   }

   @Override
   public void mouseExited(MouseEvent e) {
      event[3]=true;
      event[2]=false;
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      event[4]=true;

      x=e.getX();
      y =e.getY();
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      event[5]=true;

      position = e.getPoint();
      x=e.getX();
      y =e.getY();
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      event[6]=true;

      position = e.getPoint();
      x=e.getX();
      y =e.getY();
   }
}
