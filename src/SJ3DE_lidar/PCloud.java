package SJ3DE_lidar;

import SJ3DE_environment.Material;
import SJ3DE_environment.Point;
import SJ3DE_environment.Space;
import com.github.mreutegg.laszip4j.LASPoint;
import com.github.mreutegg.laszip4j.LASReader;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PCloud extends Space {
    private String filepath;
    private float scale = 1;
    public PCloud() {
        super();
    }

    public PCloud(String file, float scale) {
        super();
        this.scale = scale;
        readLAZFile(file, scale);
    }

    public PCloud(Point root_point, String file, float scale) {
        super();
        this.scale = scale;
        this.root_point = root_point;
        readLAZFile(file, scale);
    }

    public boolean readLAZFile(String file, float scale) {
        this.filepath = file;
        LASReader reader = new LASReader(new File(this.filepath));
        List<List<Integer>> tempCoordinatesList = new ArrayList<>();
        List<Color> tempColorsList = new ArrayList<>();
        int[] min = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] max = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

        for (LASPoint p : reader.getPoints()) {
            tempCoordinatesList.add(List.of(p.getX(),p.getY(),p.getZ()));
            try {
                tempColorsList.add(new Color(p.getRed() / 256, p.getGreen() / 256, p.getBlue() / 256));
            } catch (Exception e) {}
        }

        for (List<Integer> row : tempCoordinatesList) {
            for (int col = 0; col < 3; col++) {
                if (row.get(col) < min[col]) {
                    min[col] = row.get(col);
                }
                if (row.get(col) > max[col]) {
                    max[col] = row.get(col);
                }
            }
        }

        if(tempColorsList.size()==tempCoordinatesList.size()) {
            for (int i = 0; i < tempCoordinatesList.size(); i++) {
                List<Integer> pt = tempCoordinatesList.get(i);
                points.add(new Point(
                                Math.round(pt.get(0) - 0.5 * min[0] - 0.5 * max[0] + root_point.x) * scale,
                                Math.round(pt.get(1) - 0.5 * min[1] - 0.5 * max[1] + root_point.y) * scale,
                                Math.round(pt.get(2) - 0.5 * min[2] - 0.5 * max[2] + root_point.z) * scale,
                                new Material(tempColorsList.get(i))
                        )
                );
            }
        } else {
            for (int i = 0; i < tempCoordinatesList.size(); i++) {
                List<Integer> pt = tempCoordinatesList.get(i);
                points.add(new Point(
                                Math.round(pt.get(0) - 0.5 * min[0] - 0.5 * max[0] + root_point.x) * scale,
                                Math.round(pt.get(1) - 0.5 * min[1] - 0.5 * max[1] + root_point.y) * scale,
                                Math.round(pt.get(2) - 0.5 * min[2] - 0.5 * max[2] + root_point.z) * scale
                        )
                );
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "PCloud(root_point: " + root_point + "; Points: " + points.size() + "; Scale: " + this.scale + "; Path: '" + filepath + "')";
    }
}
