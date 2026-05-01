package SJ3DE_nature.flora.Tree1;

import SJ3DE_environment.Material;
import SJ3DE_environment.Point;
import SJ3DE_environment.Space;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.abs;

public class TreeBranch extends Space {
    protected float a; // .00 growth probability per age
    protected float b; // .00 branching probability
    protected float c = 0.5f; // leaves
    protected float length = 0;
    protected float max_length = 100;
    public Color color = new Color(119, 165, 132);

    protected float length_x = 0;
    protected float length_y = 0;
    protected float length_z = 0;

    public List<TreeBranch> branches;

    public TreeBranch(List<TreeBranch> branches, Point rp, float a, float b, Color color) {
        this.branches = branches;
        super(rp.x, rp.y, rp.z);
        this.a = a;
        this.b = b;
        this.color = color;
        points.add(new Point(root_point.x, root_point.y, root_point.z + length));
    }

    public List<Point> getPoints() {
        List<Point> result = points;
        return result;
    }

    @Override
    public String toString() {
        return "Tree1.TreeBranch(root: " + root_point + "; a: " + a + "; b: " + b + ")";
    }
}
