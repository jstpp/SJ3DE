package SJ3DE_environment;

import java.awt.*;
import java.io.Serializable;

public class Material <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public Color color;
    public int thickness = 3;

    public Material(T color, int thickness) {
        if(color instanceof Color) {
            this.color = (Color)color;
        } else if (color instanceof String) {
            this.color = Color.decode((String)color);
        }
        this.thickness = thickness;
    }
    public Material(T color) {
        if(color instanceof Color) {
            this.color = (Color)color;
        } else if (color instanceof String) {
            this.color = Color.decode((String)color);
        }
    }
    public Material(int thickness) {
        this.thickness = thickness;
    }
    public Material() {
        this.color = Color.decode("#ffffff");
    }
}
