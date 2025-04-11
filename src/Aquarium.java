import javax.swing.*;
import java.awt.*;

public class Aquarium extends JPanel implements Runnable {

    Thread thread;
    Fish[] fish = new Fish[20];
    Bubbles[] GlupGlup = new Bubbles[10];
    int tick = 0;


    public Aquarium() {
        thread = new Thread(this);
        for(int i=0;i<fish.length;i++)
            fish[i] = new Fish(100+(100*Math.random()),100+(100*Math.random()),690,450);
        for(int i=0;i<GlupGlup.length;i++)
            GlupGlup[i] = new Bubbles(620,385,-10);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));


        // Background
        g.setColor(new Color(0, 119, 190));
        g.fillRect(0, 0, 700, 500);

        // sandstone layer
        g.setColor(new Color(240, 180, 184));
        int[] secondLayerX = new int[]{0,0,100,200,300,400,500,600,700,700};
        int[] secondLayerY = new int[]{500,350,390,400,405,430,420,410,370,500};
        g.fillPolygon(secondLayerX,secondLayerY,10);

        // Sandy floor
        g.setColor(new Color(237, 201, 175));
        g.fillRect(0, 450, 700, 50);

        // Sea grass clump (animated sway)
        int baseX = 2;
        for (int i = 0; i < 550; i++) {
            g.setColor(new Color(34, 139+15*(int)Math.round((Math.pow(Math.sin(i),2)+Math.cos(i*i))), 34));
            int sway = (int)(Math.sin(((tick) + i * 5) * 0.1) * 5);
            g.drawLine(baseX + i, 452-(int)Math.round(Math.pow(Math.sin(i),2)+Math.cos(i*i)), baseX + i + sway + (int)Math.round(3*Math.sin(tick/128.0)), 422+((int)Math.round(4*(Math.pow(Math.sin(i),2)+Math.cos(i*i)))));
        }

        // vent
        g.setColor(new Color(60, 50, 50));
        g.fillOval(610,377,20,6);
        g.setColor(new Color(180, 70, 50));
        g.fillOval(615,379,10,2);

        // BUBBLES!!!!!!!!!!!!!!!!!!
        g.setColor(Color.lightGray);
        for(Bubbles bubbles : GlupGlup)
            g.drawOval((int)Math.round(bubbles.x)-2,(int)Math.round(bubbles.y)-2,5,5);

        // Rockscape
        g.setColor(new Color(100, 100, 100)); // dark gray
        int[] rockpointsX = new int[]{520,515,540,545,560,580,600,610,630,645,640};
        int[] rockpointsY = new int[]{460,450,410,390,420,390,410,380,380,440,460};
        g.fillPolygon(rockpointsX,rockpointsY,11);

        // Rock holes (using background color)
        g.setColor(new Color(50, 50, 60));
        int[] holepointsX = new int[]{535,555,550};
        int[] holepointsY = new int[]{435,425,450};
        g.fillPolygon(holepointsX,holepointsY,3);

        // Little fish hiding in a hole
        g.setColor(Color.ORANGE);
        g.fillOval(595, 435, 12, 6); // small clown-like fish body
        g.setColor(Color.WHITE);
        g.fillRect(599, 435, 2, 6); // white stripe

        // Anemone on the left
        int baseAnem = 107;
        g.setColor(new Color(255, 105, 180)); // hot pink
        for (int i = 1; i <= 27; i++) {
            g.drawLine(baseAnem - i, 450, baseAnem - i + (int)(Math.sin((tick/2.0) * 0.1 + i) * 5), 415 - (int)Math.round(5*Math.sin(i/3.0)));
            if(i == 13) {
                // Clownfish
                g.setColor(Color.ORANGE);
                int bobbing = (int) Math.round(3 * Math.sin(tick / 32.0));
                g.fillOval(90, 425 + bobbing, 12, 6);
                g.setColor(Color.WHITE);
                g.fillRect(94, 425 + bobbing, 2, 6);
                g.setColor(Color.BLACK);
                g.fillOval(98, 427 + bobbing, 2, 2);
                g.setColor(new Color(255, 105, 180)); // hot pink
            }
        }
        g.setColor(new Color(139, 69, 19)); // brownish for rock/root base
        g.fillOval(baseAnem-29,440,30,20);


        // More reef details could go here like shells, rocks, or bubbles

        for (int i=0;i< fish.length;i++) {
            int x = (int) Math.round(fish[i].x_coordinate);
            int y = (int) Math.round(2*Math.sin((Math.PI/32)*(tick+(2*i)))+fish[i].y_coordinate);
                // Fish body (filled oval)
            g.setColor(fish[i].scales);
            g.fillOval(x, y, 12, 7); // 12x7 body

                // Fish tail (filled triangle)
            int[] tailX = new int[2];
            int[] tailY = {y + 3, y, y + 7};
            if(fish[i].fish_facing > 0) {
                tailX = new int[]{x, x - 4, x - 4};
            }
            if(fish[i].fish_facing < 0) {
                tailX = new int[]{x + 12, x + 16, x + 16};
            }
            g.fillPolygon(tailX, tailY, 3);

                // Fish eye (tiny dot)
            g.setColor(Color.BLACK);
            if(fish[i].fish_facing > 0)
                g.fillRect(x + 9, y + 3, 1, 2);
            if(fish[i].fish_facing < 0)
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

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            currentTime = System.currentTimeMillis();
            beginTime = currentTime;
        }
    }

    public void Animate(){
        for(int i=0;i<fish.length;i++)
            fish[i].freeroam();
        for(Bubbles bubbles : GlupGlup)
            bubbles.floating();
    }
}