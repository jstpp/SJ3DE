package SJ3DE_stereometry;

import SJ3DE_environment.*;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Space {
    private float radius = 10;

    public Sphere(float root_x, float root_y, float root_z, float r) {
        super(root_x, root_y, root_z);
        this.radius = r;
        int thetaSteps = 64;
        int phiSteps = 64;

        for (int i = 0; i <= thetaSteps; i++) {
            double theta = Math.PI * i / thetaSteps;

            for (int j = 0; j <= phiSteps; j++) {
                double phi = 2 * Math.PI * j / phiSteps;

                float x = (float)(root_point.x + radius * Math.sin(theta) * Math.cos(phi));
                float y = (float)(root_point.y + radius * Math.sin(theta) * Math.sin(phi));
                float z = (float)(root_point.z + radius * Math.cos(theta));
                if(Float.isFinite(x) && Float.isFinite(y) && Float.isFinite(z)) {
                    points.add(new Point(x, y, z));
                }
            }
        }
    }

    public Sphere(float r) {
        super(0,0,0);
        this.radius = r;
        int thetaSteps = 30;
        int phiSteps = 30;

        for (int i = 0; i <= thetaSteps; i++) {
            double theta = Math.PI * i / thetaSteps;

            for (int j = 0; j <= phiSteps; j++) {
                double phi = 2 * Math.PI * j / phiSteps;

                float x = (float)(radius * Math.sin(theta) * Math.cos(phi));
                float y = (float)(radius * Math.sin(theta) * Math.sin(phi));
                float z = (float)(radius * Math.cos(theta));

                points.add(new Point(x, y, z));
            }
        }
    }


    @Override
    public String toString() {
        return "Sphere(root_point: " + root_point + "; radius: " + radius + ")";
    }
}
