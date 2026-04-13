package SJ3DE_stereometry;

import SJ3DE_environment.*;

public class Cylinder extends Space {
    private float a;
    private float b;
    private float h;
    private Point root_point = new Point(0,0,0);

    public Cylinder(float a, float b, float h, float root_x, float root_y, float root_z)
    {
        int phiSteps = 64;
        this.a = a;
        this.b = b;
        this.h = h;
        this.root_point = new Point(root_x, root_y, root_z);

        for (float k = -h/2; k < h/2; k += gap) {
            for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
                points.add(new Point((float)(root_x + a * Math.cos(j)), (float)(root_y + b * Math.sin(j)), k+root_z));
                points.add(new Point((float)(root_x + a * Math.cos(-j)), (float)(root_y + b * Math.sin(-j)), k+root_z));
            }
        }
        for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
            for (float m = 0; m < 1; m += gap/Math.min(a, b)) {
                points.add(new Point((float) (root_x + m * a * Math.cos(j)), (float) (root_y + m * b * Math.sin(j)), root_z + h / 2));
                points.add(new Point((float) (root_x + m * a * Math.cos(-j)), (float) (root_y + m * b * Math.sin(-j)), root_z + h / 2));
                points.add(new Point((float) (root_x + m * a * Math.cos(j)), (float) (root_y + m * b * Math.sin(j)), root_z - h / 2));
                points.add(new Point((float) (root_x + m * a * Math.cos(-j)), (float) (root_y + m * b * Math.sin(-j)), root_z - h / 2));
            }
        }
    }
    public Cylinder(float a, float b, float h)
    {
        int phiSteps = 64;
        this.a = a;
        this.b = b;
        this.h = h;

        for (float k = -h/2; k < h/2; k += gap) {
            for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
                points.add(new Point((float)(a * Math.cos(j)), (float)(b * Math.sin(j)), k));
                points.add(new Point((float)(a * Math.cos(-j)), (float)(b * Math.sin(-j)), k));
            }
        }
        for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
            for (float m = 0; m < 1; m += gap/Math.min(a, b)) {
                points.add(new Point((float) (m * a * Math.cos(j)), (float) (m * b * Math.sin(j)), h / 2));
                points.add(new Point((float) (m * a * Math.cos(-j)), (float) (m * b * Math.sin(-j)), h / 2));
                points.add(new Point((float) (m * a * Math.cos(j)), (float) (m * b * Math.sin(j)), -h / 2));
                points.add(new Point((float) (m * a * Math.cos(-j)), (float) (m * b * Math.sin(-j)), -h / 2));
            }
        }
    }

    @Override
    public String toString() {
        return "Cylinder(root_point: " + root_point + "; a: " + a + "; b: " + b + "; h: " + h + ")";
    }
}
