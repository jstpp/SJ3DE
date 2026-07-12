package SJ3DE_environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Space extends Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    public Point root_point;
    protected RenderExpression expr;

    protected boolean isStatic = false;
    public int age = 0;

    public List<Point> points = new ArrayList<Point>();
    //public List<Triangle> triangles = new ArrayList<Triangle>();

    // Constructor
    public Space(float root_x, float root_y, float root_z, RenderExpression expr) {
        this.root_point = new Point(root_x, root_y, root_z);
        this.expr = expr;
        generate();
    }
    public Space(float root_x, float root_y, float root_z) {
        this.root_point = new Point(root_x, root_y, root_z);
    }
    public Space(RenderExpression expr) {
        this.root_point = expr.root_point;
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
    public boolean generate(Material material) {
        try {
            points = expr.represent();
            materialSet(material);
            return true;
        } catch (Throwable whatever) {
            return false;
        }
    }

    public boolean materialSet(Material material) {
        for (Point p : points) {
            p.setMaterial(new Material(material));
        }
        return true;
    }

    public void updateObject() {
        age++;
    }

    public String toString() {
        return "Space(root_point: " + root_point + "; expression: '" + expr + "')";
    }
}
