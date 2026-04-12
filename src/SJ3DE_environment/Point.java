package SJ3DE_environment;

public class Point extends Environment {
    public float x;
    public float y;
    public float z;
    public Material material = new Material();

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

    @Override
    public String toString() {
        return "Point(x = " + x + "; y = " + y + "; z = " + z + ")";
    }
}
