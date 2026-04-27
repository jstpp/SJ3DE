package SJ3DE_nature.soil;

import SJ3DE_environment.Material;
import SJ3DE_environment.RenderExpression;
import SJ3DE_environment.Space;

public class Desert1 extends Space {
    public Desert1(float root_x, float root_y, float root_z) {
        super(root_x, root_y, root_z, new RenderExpression("sin(x/1000)*cos(y/1000)*300+sin(x/10)*cos(y/10)*2"));
        materialSet(new Material("#d6c29a"));
    }
}
