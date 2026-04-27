package SJ3DE_nature.hydro;

import SJ3DE_environment.Material;
import SJ3DE_environment.RenderExpression;
import SJ3DE_environment.Space;

public class Sea1 extends Space {
    public Sea1(float root_x, float root_y, float root_z) {
        super(root_x, root_y, root_z, new RenderExpression("sin(x/500) * 5 + cos(y/700) * 5"));
        materialSet(new Material("#2e616e"));
        this.isStatic = true;
    }

    @Override
    public void updateObject() {
        age++;
        expr = new RenderExpression("sin(x/250 + " + age + ") * 5 + cos(y/350 + " + age + "*0.8) * 5");
        generate(new Material("#2e616e"));
    }
}
