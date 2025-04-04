public class Fish {

    public double x_coordinate;
    public double y_coordinate;
    public int x_bound = 0;
    public int y_bound = 0;
    int repeatDirection = 10;
    double repeated_X;
    double repeated_Y;
    int repeatCycle = 0;

    public Fish(double x, double y, int x_Boundary, int y_Boundary){
        x_coordinate = x;
        y_coordinate = y;
        x_bound = x_Boundary;
        y_bound = y_Boundary;
        System.out.println(x_Boundary + ", " + y_Boundary);
    }

    public void freeroam(){
        if(repeatCycle == 0) {
            repeated_X = (10*Math.random())-5;
            repeated_Y = (10*Math.random())-5;
        }
        move(repeated_X,repeated_Y);
        if(repeatCycle == repeatDirection)
            repeatCycle = 0;
        else
            repeatCycle++;
    }

    public void move(double x_displacement,double y_displacement){
        x_coordinate += x_displacement;
        y_coordinate += y_displacement;
        if(x_coordinate < 0)
            x_coordinate = 0;
        if(y_coordinate < 0)
            y_coordinate = 0;
        if(x_coordinate > x_bound)
            x_coordinate = x_bound;
        if(y_coordinate > y_bound)
            y_coordinate = y_bound;

    }
}
