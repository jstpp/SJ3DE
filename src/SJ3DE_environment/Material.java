package SJ3DE_environment;

public class Material {
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
