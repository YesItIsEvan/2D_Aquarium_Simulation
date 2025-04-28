public class Bubbles {

    double x;
    double y;
    int initialX;
    int initialY;
    int Y_restriction;
    double floatRate;

    Bubbles(int Starting_X, int Starting_Y, int Y_Boundary){
        x = Starting_X;
        y = Starting_Y;
        initialX = Starting_X;
        initialY = Starting_Y;
        floatRate = 0.25;
        Y_restriction = Y_Boundary;
    }

    public void floating(){
        x += (1.5*Math.random())-.75;
        floatRate += 0.03*Math.random();
        y -= floatRate;
        if(y<Y_restriction) {
            y = initialY;
            x = initialX;
            floatRate = 0.25;
        }
    }
}
