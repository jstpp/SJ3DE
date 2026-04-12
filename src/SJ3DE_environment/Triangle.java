package SJ3DE_environment;

public class Triangle {
    public Point p1;
    public Point p2;
    public Point p3;
    private Material material;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.material = p1.material;
    }

    // TBD
}
