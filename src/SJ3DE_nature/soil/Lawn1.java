package SJ3DE_nature.soil;

import SJ3DE_environment.Material;
import SJ3DE_environment.Point;
import SJ3DE_environment.RenderExpression;
import SJ3DE_environment.Space;

public class Lawn1 extends Space {
    public Lawn1(float root_x, float root_y, float root_z) {
        super(root_x, root_y, root_z, new RenderExpression("-(x/20)^2-(y/20)^2+sin(x/20)*cos(y/20)*1.5", new Point(root_x, root_y, root_z)));
        materialSet(new Material("#348c31"));
    }
}
