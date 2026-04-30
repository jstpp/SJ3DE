package SJ3DE_environment;

public class Line extends Space {
    public Point p1;
    public Point p2;

    public Line(Point p1, Point p2, Material material)
    {
        this.p1 = p1;
        this.p2 = p2;
        super((p1.x+p2.x)/2, (p1.y+p2.y)/2, (p1.z+p2.z)/2);
        for(int i = 0; i <= length(); i++) {
            points.add(
                    new Point(
                            root_point.x + i*dist_x()/length(),
                            root_point.y + i*dist_y()/length(),
                            root_point.z + i*dist_z()/length()
                    )
            );
        }
        materialSet(material);
    }
    public Line(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        super((p1.x+p2.x)/2, (p1.y+p2.y)/2, (p1.z+p2.z)/2);

        for(int i = 0; i <= length(); i++) {
            points.add(
                    new Point(
                            root_point.x + i*dist_x()/length(),
                            root_point.y + i*dist_y()/length(),
                            root_point.z + i*dist_z()/length()
                    )
            );
        }
    }

    public int length() {
        return (int)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y) + (p1.z-p2.z)*(p1.z-p2.z));
    }
    private int dist_x() {
        return (int)(p1.x-p2.x);
    }
    private int dist_y() {
        return (int)(p1.y-p2.y);
    }
    private int dist_z() {
        return (int)(p1.z-p2.z);
    }
}
