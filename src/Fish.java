import java.awt.*;

public class Fish {

    public double x_coordinate;
    public double y_coordinate;
    public int x_bound;
    public int y_bound;
    int repeatDirection = (int)Math.round(30*Math.random())+15;
    double[] vector = new double[2];
    double fish_speed;
    double fish_direction;
    int repeatCycle = 0;
    int fish_facing = 1;
    Color scales;

    public Fish(double x, double y, int x_Boundary, int y_Boundary){
        x_coordinate = x;
        y_coordinate = y;
        fish_direction = 2*Math.random()*Math.PI;
        fish_speed = 2*Math.random();
        x_bound = x_Boundary;
        y_bound = y_Boundary;
        scales = getScales();
    }

    public void freeroam(){
        if(repeatCycle == 0) {
            fish_direction += (Math.random()-0.5)*Math.PI;
            //fish_speed += Math.random()-0.5;
            if(fish_speed > 2||fish_speed < 0)
                fish_speed = 1;
            if(Math.cos(fish_direction)<0)
                fish_facing = -1;
            else
                fish_facing = 1;

            vector[0] = fish_speed*Math.cos(fish_direction);
            vector[1] = fish_speed*Math.sin(fish_direction);
        }
        move(vector);
        if(repeatCycle == repeatDirection)
            repeatCycle = 0;
        else {
            vector = new double[]{(vector[0]/1.05),(vector[1]/1.05)};
            repeatCycle++;
        }
    }

    public void move(double[] Vector){
        x_coordinate += Vector[0];
        y_coordinate += Vector[1];
        if(x_coordinate < -5) {
            x_coordinate = -5;
            fish_direction = -fish_direction/2;// add this to the others
        }
        if(y_coordinate < -5)
            y_coordinate = -5;
        if(x_coordinate > x_bound)
            x_coordinate = x_bound;
        if(y_coordinate > y_bound)
            y_coordinate = y_bound;

    }

    public Color getScales(){
        int i = (int)Math.round(4*Math.random());
        switch(i){
            case 1:
                return new Color(50, 205, 50);
            case 2:
                return new Color(220, 20, 60);
            case 3:
                return new Color(255, 230, 0);
            case 4:
                return new Color(0, 102, 204);
            default:
                return new Color(255, 140, 0);
        }
    }
}
