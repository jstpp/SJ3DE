package SJ3DE_environment;

import java.io.Serializable;

public class Material <T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public T color;
    public float thickness = 5;

    public Material(T color, float thickness) {
        this.color = color;
        this.thickness = thickness;
    }
    public Material(T color) {
        this.color = color;
    }
    public Material(float thickness) {
        this.thickness = thickness;
    }
    public Material() {
        this.color = (T)"#ffffff";
    }
}
