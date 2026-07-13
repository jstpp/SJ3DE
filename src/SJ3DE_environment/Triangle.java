package SJ3DE_environment;

import SJ3DE_ui.Toolbox;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Triangle {
    public Point p1, p2, p3;
    public Material material;

    public Triangle(Point p1, Point p2, Point p3, Material material) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.material = material;
    }

    public static void meshMerge(List<Point> localPoints) {
        if (localPoints.size() < 3) return;

        double maxDistance = Toolbox.brush.radius * 2.0;

        for (int i = 0; i < localPoints.size(); i++) {
            Point pA = localPoints.get(i);

            PriorityQueue<PointDistance> neighbors = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
            for (int j = 0; j < localPoints.size(); j++) {
                if (i == j) continue;
                Point pB = localPoints.get(j);
                double dist = pA.distance(pB);
                if (dist < maxDistance) {
                    neighbors.add(new PointDistance(pB, dist));
                }
            }

            if (neighbors.size() < 2) continue;

            Point pB = neighbors.poll().point;
            Point pC = neighbors.poll().point;

            for (Space obj : Environment.parent_engine.objects) {
                if (obj.points.contains(pA)) {
                    boolean exists = false;
                    for (Triangle t : obj.triangles) {
                        if ((t.p1 == pA || t.p2 == pA || t.p3 == pA) &&
                                (t.p1 == pB || t.p2 == pB || t.p3 == pB) &&
                                (t.p1 == pC || t.p2 == pC || t.p3 == pC)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        Triangle tri = new Triangle(pA, pB, pC, pA.material);
                        obj.triangles.add(tri);
                        Environment.parent_engine.triangles.add(tri);
                    }
                    break;
                }
            }
        }
    }
    private static class PointDistance {
        Point point;
        double distance;
        PointDistance(Point point, double distance) {
            this.point = point;
            this.distance = distance;
        }
    }

    public static void drawTriangle(Triangle t, int width, int height, double[] zBuffer) {
        Point[] screenPts = new Point[3];
        Point[] origPts = {t.p1, t.p2, t.p3};
        double[] depths = new double[3];

        for (int i = 0; i < 3; i++) {
            Point p = origPts[i];
            double rx = p.x - Environment.parent_engine.camera.camera_x;
            double ry = p.y - Environment.parent_engine.camera.camera_y;
            double rz = p.z - Environment.parent_engine.camera.camera_z;

            Point pp = new Point((float)rx, (float)ry, (float)rz);
            pp.rotateXY(Environment.parent_engine.camera.rotate_XY);
            pp.rotateZ(-Environment.parent_engine.camera.rotate_Z);

            if (pp.z <= 0) return;

            depths[i] = pp.z;
            double scale = Environment.parent_engine.f / pp.z;

            float sx = (float) (pp.x * scale + width / 2.0);
            float sy = (float) (-pp.y * scale + height / 2.0);
            screenPts[i] = new Point(sx, sy, (float)pp.z);
        }

        int minX = (int) Math.max(0, Math.min(screenPts[0].x, Math.min(screenPts[1].x, screenPts[2].x)));
        int maxX = (int) Math.min(width - 1, Math.max(screenPts[0].x, Math.max(screenPts[1].x, screenPts[2].x)));
        int minY = (int) Math.max(0, Math.min(screenPts[0].y, Math.min(screenPts[1].y, screenPts[2].y)));
        int maxY = (int) Math.min(height - 1, Math.max(screenPts[0].y, Math.max(screenPts[1].y, screenPts[2].y)));

        int colorRGB = t.material.color.getRGB();

        double area = (screenPts[1].y - screenPts[2].y) * (screenPts[0].x - screenPts[2].x) +
                (screenPts[2].x - screenPts[1].x) * (screenPts[0].y - screenPts[2].y);

        if (Math.abs(area) < 0.000001) return;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                double w1 = ((screenPts[1].y - screenPts[2].y) * (x - screenPts[2].x) +
                        (screenPts[2].x - screenPts[1].x) * (y - screenPts[2].y)) / area;
                double w2 = ((screenPts[2].y - screenPts[0].y) * (x - screenPts[2].x) +
                        (screenPts[0].x - screenPts[2].x) * (y - screenPts[2].y)) / area;
                double w3 = 1.0 - w1 - w2;

                if (w1 >= 0 && w2 >= 0 && w3 >= 0) {
                    double interpolatedDepth = 1.0 / (w1 / depths[0] + w2 / depths[1] + w3 / depths[2]);

                    int pixelIndex = y * width + x;

                    if (interpolatedDepth < zBuffer[pixelIndex]) {
                        zBuffer[pixelIndex] = interpolatedDepth;
                        Environment.parent_engine.pixels[pixelIndex] = colorRGB;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Triangle(p1 = " + this.p1 + "; p2 = " + this.p2 + "; p3 = " + this.p3 + ")";
    }
}


