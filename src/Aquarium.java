import javax.swing.*;
import java.awt.*;

public class Aquarium extends JPanel implements Runnable {

    Thread thread;
    Fish[] fish = new Fish[20];
    int tick = 0;

    public Aquarium() {
        thread = new Thread(this);
        for(int i=0;i<fish.length;i++)
            fish[i] = new Fish(100+(100*Math.random()),100+(100*Math.random()),690,450);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));


        // Background
        g.setColor(new Color(0, 119, 190));
        g.fillRect(0, 0, 700, 500);

        // Sandy floor
        g.setColor(new Color(237, 201, 175));
        g.fillRect(0, 450, 700, 50);

        // Sea grass clump (animated sway)
        g.setColor(new Color(34, 139, 34));
        int baseX = 300;
        for (int i = 0; i < 64; i++) {
            int sway = (int)(Math.sin(((tick) + i * 5) * 0.1) * 5);
            g.drawLine(baseX + i, 452-(int)Math.round(2*Math.sin(i/6.0)), baseX + i + sway, 400+((int)Math.round(5*Math.sin(i/10.0))));
        }

        // Rockscape on the right
        g.setColor(new Color(100, 100, 100)); // dark gray
        g.fillOval(520, 410, 120, 60);
        g.fillOval(550, 390, 100, 60);

        // Rock holes (using background color)
        g.setColor(new Color(0, 119, 190));
        g.fillOval(560, 420, 20, 20); // empty hole
        g.fillOval(590, 430, 25, 20); // hole with fish

        // Little fish hiding in a hole
        g.setColor(Color.ORANGE);
        g.fillOval(595, 435, 12, 6); // small clown-like fish body
        g.setColor(Color.WHITE);
        g.fillRect(599, 435, 2, 6); // white stripe

        // Anemone on the left
        int baseAnem = 80;
        g.setColor(new Color(255, 105, 180)); // hot pink
        for (int i = 1; i <= 27; i++) {
            g.drawLine(baseAnem + i, 450, baseAnem + i + (int)(Math.sin((tick/2.0) * 0.1 + i) * 5), 415 - (int)Math.round(5*Math.sin(i/3.0)));
        }
        g.setColor(new Color(139, 69, 19)); // brownish for rock/root base
        g.fillOval(baseAnem,440,30,20);

        // Clownfish nearby (optional)
        g.setColor(Color.ORANGE);
        g.fillOval(90, 420, 12, 6);
        g.setColor(Color.WHITE);
        g.fillRect(94, 420, 2, 6);
        g.setColor(Color.BLACK);
        g.fillOval(98, 422, 2, 2);

        // More reef details could go here like shells, rocks, or bubbles

        for (Fish fish1 : fish) {
            int x = (int) Math.round(fish1.x_coordinate);
            int y = (int) Math.round(fish1.y_coordinate);
                // Fish body (filled oval)
            g.setColor(fish1.scales);
            g.fillOval(x, y, 12, 7); // 12x7 body

                // Fish tail (filled triangle)
            int[] tailX = new int[2];
            int[] tailY = {y + 3, y, y + 7};
            if(fish1.fish_facing > 0) {
                tailX = new int[]{x, x - 4, x - 4};
                tailY = new int[]{y + 3, y, y + 7};
            }
            if(fish1.fish_facing < 0) {
                tailX = new int[]{x + 12, x + 16, x + 16};
                tailY = new int[]{y + 3, y, y + 7};
            }
            g.fillPolygon(tailX, tailY, 3);

                // Fish eye (tiny dot)
            g.setColor(Color.BLACK);
            if(fish1.fish_facing > 0)
                g.fillRect(x + 9, y + 3, 1, 2);
            if(fish1.fish_facing < 0)
                g.fillRect(x + 3, y + 3, 1, 2);
        }
    }

        public void run() {
        long period = 1000 / 50;
        long beginTime = System.currentTimeMillis();
        long currentTime;
        while (true) {
            tick++;

            Animate();

            repaint();

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
        for(int i=0;i<fish.length;i++)
            fish[i].freeroam();
    }
}