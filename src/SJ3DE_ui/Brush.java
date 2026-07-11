package SJ3DE_ui;

import SJ3DE_engine.Camera;
import SJ3DE_engine.Engine;
import SJ3DE_environment.Environment;
import SJ3DE_environment.Material;

public class Brush {
    public Material material;
    public int radius = 50;
    public Brush(Material material, int radius)
    {
        this.material = material;
        this.radius = radius;
    }
}
