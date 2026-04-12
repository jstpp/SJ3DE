package SJ3DE_stereometry;

import SJ3DE_environment.*;

public class Cuboid extends Space {
    private float a;
    private float b;
    private float h;

    public Cuboid(float a, float b, float h, float root_x, float root_y, float root_z)
    {
        for (float i = -a/2; i < a/2; i += gap)
        {
            for (float j = -b/2; j < b/2; j += gap)
            {
                points.add(new Point(i+root_x, j+root_y, root_z-h/2));
                points.add(new Point(i+root_x, j+root_y, root_z+h/2));
            }
        }
        for (float k = -h/2; k < h/2; k += gap)
        {
            for (float i = -a/2; i < a/2; i += gap)
            {
                points.add(new Point(i+root_x, b/2+root_y, root_z+k));
                points.add(new Point(i+root_x, -b/2+root_y, root_z+k));
            }
            for (float j = -b/2; j < b/2; j += gap)
            {
                points.add(new Point(-a/2+root_x, j+root_y, root_z+k));
                points.add(new Point(a/2+root_x, j+root_y, root_z+k));
            }
        }
    }

    @Override
    public String toString() {
        return "Cuboid(root_point: " + root_point + "; a: " + a + "; b: " + b + "; h: " + h + ")";
    }
}
