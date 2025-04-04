import javax.swing.*;

public class Frame extends JFrame{


    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,520);
        this.setTitle("Aquarium");
        Aquarium aqua = new Aquarium();
        this.add(aqua);
        this.setVisible(true);
    }
}
