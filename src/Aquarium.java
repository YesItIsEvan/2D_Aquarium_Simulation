import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Aquarium extends JPanel implements Runnable {

    Thread thread;
    Fish[] fish = new Fish[20];
    Bubbles[] bubbles = new Bubbles[10];
    double tick = 0;
    int frameRate = 120;
    Mouse mouse = new Mouse();


    public Aquarium() {
        this.addMouseListener(mouse);
        thread = new Thread(this);
        for(int i=0;i<fish.length;i++)
            fish[i] = new Fish(100+(100*Math.random()),100+(100*Math.random()),690,450);
        for(int i = 0; i< bubbles.length; i++)
            bubbles[i] = new Bubbles(621,367,-10);
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

        // Sea-grass clump (animated sway)
        int baseX = 2;
        for (int i = 0; i < 550; i++) {
            int organicVariation = (int)Math.round((Math.pow(Math.sin(i),2)+Math.cos(i*i)));
            g.setColor(new Color(34, 139+15*organicVariation, 34));
            int sway = (int)(Math.sin(((tick) + i * 5) * 0.1) * 5);
            g.drawLine(baseX + i, 452-organicVariation, baseX + i + sway + (int)Math.round(3*Math.sin(tick/128.0)), 422+(4*(organicVariation)));
        }

        // Inner cave
        g.setColor(new Color(50, 50, 60));
        int[] holePointsX = new int[]{499,497,519,548,568,581,591,599,609,634,655,678,683,651,583,516,499};
        int[] holePointsY = new int[]{459,455,445,441,411,408,401,387,360,360,387,409,460,468,470,467,459};
        g.fillPolygon(holePointsX,holePointsY,17);

        // Anemone on the left
        int AnemoneStartingX = 107;
        g.setColor(new Color(255, 105, 180)); // hot pink
        for (int i = 1; i <= 27; i++) {
            g.drawLine(AnemoneStartingX - i, 450, AnemoneStartingX - i + (int)(Math.sin((tick/2.0) * 0.1 + i) * 5), 420 - (int)Math.round(5*Math.sin(i/3.0)));
            if(i == 13) {
                // Clownfish
                g.setColor(Color.ORANGE);
                int bobbing = (int) Math.round(3 * Math.sin(tick / 32.0));
                g.fillOval(90, 434 + bobbing, 12, 6);
                g.setColor(Color.WHITE);
                g.fillRect(94, 434 + bobbing, 2, 6);
                g.setColor(Color.BLACK);
                g.fillOval(98, 436 + bobbing, 2, 2);
                g.setColor(new Color(255, 105, 180)); // hot pink
            }
        }
        g.setColor(new Color(139, 69, 19)); // brownish for rock/root base
        int[] PointsX = new int[]{AnemoneStartingX+1,AnemoneStartingX-29,AnemoneStartingX-31,AnemoneStartingX+3};
        int[] PointsY = new int[]{450,450,465,465};
        g.fillPolygon(PointsX,PointsY,4);

        // More reef details could go here like shells, rocks, or bubbles

        for (int i=0;i< fish.length;i++) {
            int x = (int) Math.round(fish[i].x_coordinate);
            int y = (int) Math.round(2*Math.sin((Math.PI/32)*(tick+(2*i)))+fish[i].y_coordinate);
                // Fish body (filled oval)
            g.setColor(fish[i].scales);
            g.fillOval(x, y, 12, 7); // 12x7 body

                // Fishtail (filled triangle)
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
                g.fillRect(x + 9, y + 2, 1, 2);
            if(fish[i].fish_facing < 0)
                g.fillRect(x + 3, y + 2, 1, 2);
        }

        // vent
        g.setColor(new Color(60, 50, 50));
        g.fillOval(609,357,25,6);
        g.setColor(new Color(180, 70, 50));
        g.fillOval(614,358,15,4);

        // BUBBLES!!!!!!!!!!!!!!!!!!
        g.setColor(Color.lightGray);
        for(Bubbles bubbles : bubbles)
            g.drawOval((int)Math.round(bubbles.x)-2,(int)Math.round(bubbles.y)-2,5,5);

        // Rock scape
        g.setColor(new Color(100, 100, 100)); // dark gray
        int[] rockPointsX = new int[]{499,497,519,548,568,581,591,599,609,634,655,678,683,651,583,516,499,
                                      576,571,576,582,595,597,585,576,
                                      659,667,663,652,638,632,641,651,659,
                                      576};
        int[] rockPointsY = new int[]{459,455,445,441,411,408,401,387,360,360,387,409,460,468,470,467,459,
                                      425,432,440,443,445,440,428,425,
                                      416,421,428,432,433,425,419,418,416,
                                      425};
        g.fillPolygon(rockPointsX,rockPointsY,35);
    }

        public void run() {
        long period = 1000 / 100;
        long beginTime = System.currentTimeMillis();
        long currentTime;
        while (true) {
            tick += 50.0 / frameRate;

            if (mouse.event[m.mouse.clicked]){
                System.out.println(new Point(mouse.x, mouse.y));
            mouse.event[m.mouse.clicked] = false;
            }

            Animate();

            repaint();

            currentTime = System.currentTimeMillis();
            try {
                long sleepTime = period - (currentTime - beginTime);
                if (sleepTime > 0) sleep(sleepTime);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            currentTime = System.currentTimeMillis();
            beginTime = currentTime;
        }
    }

    public void Animate(){
        for (Fish fish1 : fish)
            fish1.freeroam();
        for(Bubbles bubbles : bubbles)
            bubbles.floating();
    }

    public enum m {
        mouse(0,1,2,3,4,5,6);

        public final int pressed;
        public final int released;
        public final int entered;
        public final int exited;
        public final int clicked;
        public final int dragged;
        public final int moved;

        m(int pressed, int released, int entered, int exited, int clicked, int dragged, int moved) {
            this.pressed = pressed;
            this.released = released;
            this.entered = entered;
            this.exited = exited;
            this.clicked = clicked;
            this.dragged = dragged;
            this.moved = moved;
        }
    }
}