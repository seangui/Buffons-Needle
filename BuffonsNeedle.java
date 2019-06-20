class Point
{
    double x;
    double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}

class Line
{
    Point A;
    Point B;

    public Line(Point A, Point B)
    {
        this.A = A;
        this.B = B;
    }
}

class EquilateralTriangle
{
    Point A;
    Point B;
    Point C;

    public EquilateralTriangle()
    {
        // Construct a random point somewhere within our plane
        A = new Point(Math.random() * 4.0,Math.random() * 4.0);

        // Generate B which is horizontal to A to construct the Vector AB
        B = new Point((.5 - A.x)/-1, A.y);

        // Now we will Generate C via a 120 degree angle from A - In Radians
        double theta = (Math.PI * 120) / 180;
        C = new Point(A.x + .5 * Math.cos(theta), B.y - .5 * Math.sin(theta));
    }
}

public class BuffonsNeedle
{
    // Create an array to store lines
    static Line[] lines = new Line[5];

    public static void constructLines()
    {
        for(int i = 0; i < lines.length; i++)
            lines[i] = new Line(new Point(0, i), new Point(i, 1));
    }

    public static boolean isCrossing(EquilateralTriangle triangle, Line line)
    {
        // Compute the cross product
        double crossProductA = (line.B.x - line.A.x) * (triangle.A.y - line.A.y) - (line.B.y - line.A.y) * (triangle.A.x - line.A.x);
        double crossProductB = (line.B.x - line.A.x) * (triangle.B.y - line.A.y) - (line.B.y - line.A.y) * (triangle.B.x - line.A.x);
        double crossProductC = (line.B.x - line.A.x) * (triangle.C.y - line.A.y) - (line.B.y - line.A.y) * (triangle.C.x - line.A.x);

        if(hasSameSigns(crossProductA, crossProductB, crossProductC))
            return false;
        else
            return true;
    }

    public static boolean hasSameSigns(double A, double B, double C)
    {
        if(A > 0 && B > 0 && C > 0)
            return true;
        else if (A <0 && B < 0 && C < 0)
            return true;
        else
            return false;
    }

    public static void main(String[] args)
    {
        constructLines();

        int numberOfCrosses = 0;

        for(int i = 0; i < 300000000; i++)
        {
            // Generate a equilateral triangle within the bounds of the lines.
            EquilateralTriangle triangle = new EquilateralTriangle();

            for(int j = 0; j < lines.length; j++)
            {
               if(isCrossing(triangle, lines[j]))
               {
                   numberOfCrosses++;
                   break;
               }
            }
        }

        double probability = (double)numberOfCrosses/300000000;

        System.out.println(probability);
    }
}