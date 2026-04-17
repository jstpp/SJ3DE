package SJ3DE_environment;

import SJ3DE_engine.Engine;

import java.awt.*;
import java.io.Serializable;

public class Point extends Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;
    public Material material = new Material();
    public Engine parent_engine;

    public Point(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(float x, float y, float z, Material material)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
    }

    // rotation along the X axis
    public void rotateX(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double yNew = y * cos - z * sin;
        double zNew = y * sin + z * cos;
        y = (float)yNew;
        z = (float)zNew;
    }

    // rotation along the Y axis
    public void rotateY(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double xNew = x * cos + z * sin;
        double zNew = -x * sin + z * cos;
        x = (float)xNew;
        z = (float)zNew;
    }

    // rainbow displaying
    public void rainbow() {
        float hue = (float)((z + y + x + parent_engine.radius_from_point_zero) / (2*parent_engine.radius_from_point_zero));
        this.material = new Material(Color.getHSBColor(hue, 1f, 1f));
    }

    @Override
    public String toString() {
        return "Point(x = " + x + "; y = " + y + "; z = " + z + ")";
    }
}
