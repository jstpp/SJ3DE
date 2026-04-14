package SJ3DE_environment;

import java.io.Serializable;

public class Line extends Environment implements Serializable {
    public Point p1;
    public Point p2;
    public Material material = new Material("#ffdd00", 1);

    public Line(Point p1, Point p2, Material material)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.material = material;
    }

    public Line(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }
}
