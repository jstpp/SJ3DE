package SJ3DE_environment;

import java.io.Serializable;

public class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    public String color;
    public float thickness = 5;

    public Material(String color, float thickness) {
        this.color = color;
        this.thickness = thickness;
    }
    public Material(String color) {
        this.color = color;
    }
    public Material() {
        this.color = "#ffffff";
    }
}
