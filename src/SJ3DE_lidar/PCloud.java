package SJ3DE_lidar;

import SJ3DE_environment.Point;
import SJ3DE_environment.Space;
import com.github.mreutegg.laszip4j.LASPoint;
import com.github.mreutegg.laszip4j.LASReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PCloud extends Space {
    private String filepath;
    public PCloud() {
        super();
    }

    public PCloud(String file) {
        super();
        readLAZFile(file);
    }

    public PCloud(Point root_point, String file) {
        super();
        this.root_point = root_point;
        readLAZFile(file);
    }

    public boolean readLAZFile(String file) {
        this.filepath = file;
        LASReader reader = new LASReader(new File(this.filepath));
        List<List<Integer>> tempCoordinatesList = new ArrayList<>();
        int[] min = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] max = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

        for (LASPoint p : reader.getPoints()) {
            tempCoordinatesList.add(List.of(p.getX(),p.getY(),p.getZ()));
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

        for (List<Integer> pt : tempCoordinatesList) {
            points.add(new Point(Math.round(pt.get(0) - min[0] + root_point.x),
                    Math.round(pt.get(1) - min[1] + root_point.y),
                    Math.round(pt.get(2) - min[2] + root_point.z)));
        }

        return true;
    }

    @Override
    public String toString() {
        return "PCloud(root_point: " + root_point + "; Points: " + points.size() + "; Path: '" + filepath + "')";
    }
}
