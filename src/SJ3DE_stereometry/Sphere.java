package SJ3DE_stereometry;

import SJ3DE_environment.*;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Space {
    private float radius = 10;
    public Sphere(float root_x, float root_y, float root_z, float r) {
        super(root_x, root_y, root_z, new RenderExpression("sqrt(" + r + "^2 - x^2 - y^2)", new Point(root_x, root_y, root_z)));
        this.radius = r;
        render_radius = r*1.1f;
        generate();
        List<SJ3DE_environment.Point> second_half = new ArrayList<SJ3DE_environment.Point>();
        for (Point pt : points)
        {
            System.out.println(pt);
            second_half.add(new Point(pt.x, pt.y, -pt.z));
        }
        points.addAll(second_half);
    }

    public Sphere(float r) {
        super(new RenderExpression("sqrt(" + r + "^2 - x^2 - y^2)"));
        this.radius = r;
        generate();
        List<SJ3DE_environment.Point> second_half = new ArrayList<SJ3DE_environment.Point>();
        for (Point pt : points)
        {
            second_half.add(new Point(pt.x, pt.y, -pt.z));
        }
        points.addAll(second_half);
    }

    @Override
    public String toString() {
        return "Sphere(root_point: " + root_point + "; radius: " + radius + ")";
    }
}
