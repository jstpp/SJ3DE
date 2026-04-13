package SJ3DE_environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RenderExpression extends Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String expression;
    public transient Expression e;
    private Point root_point = new Point(0,0,0);
    private List<RenderExpression> subexpressions = new ArrayList<RenderExpression>();
    private String expression_type = "+";

    public RenderExpression(String expr, Point root_point) {
        this.expression = expr;
        this.root_point = root_point;
        e = new ExpressionBuilder(expression)
                .variables("x", "y")
                .build();
    }

    public RenderExpression(String expr) {
        this.expression = expr;
        e = new ExpressionBuilder(expression)
                .variables("x", "y")
                .build();
    }

    private float evaluate_z(float x, float y) {
        e.setVariable("x", x).setVariable("y", y);
        return (float) e.evaluate();
    }



    public List<Point> represent(float new_gap) {
        List<Point> points = new ArrayList<>();
        float z_value;
        for (float x = -render_radius; x < render_radius; x = x+new_gap)
        {
            for (float y = -render_radius; y < render_radius; y = y+new_gap)
            {
                z_value = evaluate_z(x,y);
                if(Float.isFinite(z_value))
                {
                    points.add(new Point(x+root_point.x, y+root_point.y, z_value+root_point.z));
                }
            }
        }
        return points;
    }
    public List<Point> represent() {
        List<Point> points = new ArrayList<>();
        float z_value;
        for (float x = -render_radius; x < render_radius; x = x+gap)
        {
            for (float y = -render_radius; y < render_radius; y = y+gap)
            {
                z_value = evaluate_z(x,y);
                if(Float.isFinite(z_value))
                {
                    points.add(new Point(x+root_point.x, y+root_point.y, z_value+root_point.z));
                }
            }
        }
        return points;
    }

    public String toString() {
        return expression;
    }
}
