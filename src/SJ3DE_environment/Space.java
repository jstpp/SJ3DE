package SJ3DE_environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Space extends Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Point root_point;
    private RenderExpression expr;

    public List<Point> points = new ArrayList<Point>();

    // Constructor
    public Space(float root_x, float root_y, float root_z, RenderExpression expr) {
        this.root_point = new Point(root_x, root_y, root_z);
        this.expr = expr;
        generate();
    }
    public Space(float root_x, float root_y, float root_z) {
        this.root_point = new Point(root_x, root_y, root_z);
        generate();
    }
    public Space(RenderExpression expr) {
        this.root_point = new Point(0,0,0);
        this.expr = expr;
        generate();
    }
    public Space() {
        this.root_point = new Point(0,0,0);
        generate();
    }

    // Generate points representing Space object
    public boolean generate() {
        try {
            points = expr.represent();
            return true;
        } catch (Throwable whatever) {
            return false;
        }
    }

    public boolean materialSet(Material material) {
        for (Point p : points) {
            p.material = material;
        }
        return true;
    }

    public String toString() {
        return "Space(root_point: " + root_point + "; expression: '" + expr + "')";
    }
}
