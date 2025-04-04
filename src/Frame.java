import javax.swing.*;

public class Frame extends JFrame{


    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,500);
        Aquarium aqua = new Aquarium();
        this.add(aqua);
        this.setTitle("Aquarium");
        this.setVisible(true);
    }
}
