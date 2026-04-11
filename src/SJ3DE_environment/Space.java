package SJ3DE_environment;

import java.util.List;

public class Space extends Environment {
    protected Point root_point;
    private RenderExpression expr;
    private Material material = new Material();

    public List<Point> points;

    // Constructor
    public Space(float root_x, float root_y, float root_z, RenderExpression expr) {
        this.root_point = new Point(root_x, root_y, root_z);
        this.expr = expr;
        generate();
    }
    public Space(RenderExpression expr) {
        this.root_point = new Point(0,0,0);
        this.expr = expr;
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
}
