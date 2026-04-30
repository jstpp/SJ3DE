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
    public transient Engine parent_engine;

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

    public void rotateZ(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double yNew = y * cos - z * sin;
        double zNew = y * sin + z * cos;

        this.y = (float) yNew;
        this.z = (float) zNew;
    }

    public void rotateXY(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        double xNew = x * cos + y * sin;
        double zNew = -x * sin + y * cos;

        this.x = (float) xNew;
        this.y = (float) zNew;
    }

    // rainbow displaying
    public void rainbow() {
        float hue = (float)((z + y + x + 50) / 100);
        this.material = new Material(Color.getHSBColor(hue, 1f, 1f));
    }

    @Override
    public String toString() {
        return "Point(x = " + x + "; y = " + y + "; z = " + z + ")";
    }
}
