package SJ3DE_stereometry;

import SJ3DE_environment.Point;
import SJ3DE_environment.Space;

public class Cone extends Space { //TBD
    private float r1;
    private float r2;
    private float h;

    public Cone(float r1, float r2, float h, float root_x, float root_y, float root_z) {
        super(root_x, root_y, root_z);
        int phiSteps = 64;
        this.h = h;
        this.r1 = r1;
        this.r2 = r2;

        for (float k = -h/2; k < h/2; k += gap) {
            float scale = (h/2 - k) / h;

            float current_a = r1 * scale;
            float current_b = r2 * scale;

            for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
                points.add(new Point((float)(root_x + current_a * Math.cos(j)), (float)(root_y + current_b * Math.sin(j)), k + root_z));
                points.add(new Point((float)(root_x + current_a * Math.cos(-j)), (float)(root_y + current_b * Math.sin(-j)), k + root_z));
            }
        }

        float minRadius = Math.min(r1, r2);
        for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
            for (float m = 0; m < 1; m += gap / minRadius) {
                points.add(new Point((float) (root_x + m * r1 * Math.cos(j)), (float) (root_y + m * r2 * Math.sin(j)), root_z - h / 2));
                points.add(new Point((float) (root_x + m * r1 * Math.cos(-j)), (float) (root_y + m * r2 * Math.sin(-j)), root_z - h / 2));
            }
        }
    }

    public Cone(float r1, float r2, float h) {
        super();
        int phiSteps = 64;
        this.h = h;
        this.r1 = r1;
        this.r2 = r2;

        for (float k = -h/2; k < h/2; k += gap) {
            float scale = (h/2 - k) / h;

            float current_a = r1 * scale;
            float current_b = r2 * scale;

            for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
                points.add(new Point((float)(current_a * Math.cos(j)), (float)(current_b * Math.sin(j)), k));
                points.add(new Point((float)(current_a * Math.cos(-j)), (float)(current_b * Math.sin(-j)), k));
            }
        }

        float minRadius = Math.min(r1, r2);
        for (double j = 0; j <= Math.PI; j += Math.PI/phiSteps) {
            for (float m = 0; m < 1; m += gap / minRadius) {
                points.add(new Point((float) (m * r1 * Math.cos(j)), (float) (m * r2 * Math.sin(j)),  - h / 2));
                points.add(new Point((float) (m * r1 * Math.cos(-j)), (float) (m * r2 * Math.sin(-j)),  - h / 2));
            }
        }
    }
}
