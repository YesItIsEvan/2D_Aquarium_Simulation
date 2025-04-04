import javax.swing.*;
import java.awt.*;

public class Aquarium extends JPanel implements Runnable {

    Thread thread;
    Fish fish = new Fish(15,15,700,500);
    ;


    public Aquarium() {
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.red);
        g.fillOval((int)Math.round(fish.x_coordinate),(int)Math.round(fish.y_coordinate),10,10);
    }

        public void run() {
        long period = 1000 / 60;
        long beginTime = System.currentTimeMillis();
        long currentTime;
        while (true) {

            repaint();

            Animate();

            currentTime = System.currentTimeMillis();
            try {
                long sleepTime = period - (currentTime - beginTime);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }

            } catch (InterruptedException ex) {}
            currentTime = System.currentTimeMillis();
            beginTime = currentTime;
        }
    }

    public void Animate(){
        fish.freeroam();
    }
}