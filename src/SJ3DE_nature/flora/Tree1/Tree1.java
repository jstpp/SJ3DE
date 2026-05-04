package SJ3DE_nature.flora.Tree1;

import SJ3DE_environment.Line;
import SJ3DE_environment.Material;
import SJ3DE_environment.Point;
import SJ3DE_environment.Space;
import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Tree1 extends Space {
    protected List<TreeBranch> branches = new ArrayList<TreeBranch>();
    private int goal_age = 250;
    public Tree1(float root_x, float root_y, float root_z) {
        super(root_x, root_y, root_z);
        branches.add(new TreeBranch(branches, root_point, 1.0f, 0.0f, new Color(137, 63, 55)));
    }
    @Override
    public void updateObject() {
        age++;
        updateAllBranches();
    }

    private void updateAllBranches() {
        List<TreeBranch> new_branches = new ArrayList<TreeBranch>();
        if(age<=goal_age)
        {
            for (TreeBranch br : branches) {
                float randomFloat = random.nextFloat();
                br.length_x += random.nextFloat()*2-1;
                br.length_y += random.nextFloat()*2-1;
                br.length_z += random.nextFloat() * 2 - 0.85;
                br.age++;

                if(randomFloat < br.a) {
                    Point newpoint = new Point((float)(br.root_point.x + br.length_x), (float)(br.root_point.y + br.length_y), (float)(br.root_point.z + br.length_z),
                            new Material(br.color));
                    //System.out.println(newpoint);
                    points.add(newpoint);
                    br.length++;
                } else if (randomFloat < br.b) {
                    new_branches.add(new TreeBranch(branches,
                            new Point((float)(br.root_point.x + br.length_x), (float)(br.root_point.y + br.length_y), (float)(br.root_point.z + br.length_z)),
                            1.0f, 0.0f, new Color(119, 165, 132)));
                    br.length++;
                }
                br.a = max(0, min(1,1-(float)br.length/br.max_length));
                br.b = max(0, min(1,(float)br.length/br.max_length));

                //System.out.println(br.a + " / " + br.b);
            }
        }
        for (TreeBranch br : new_branches) {
            points.addAll(br.getPoints());
            br.points.clear();
        }
        branches.addAll(new_branches);
    }

    @Override
    public String toString() {
        return "Tree1(root: " + root_point + "; branches: " + branches.size() + ")";
    }
}
