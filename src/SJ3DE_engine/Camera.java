package SJ3DE_engine;

import SJ3DE_environment.Point;

public class Camera extends Point {
    public double rotate_Z = 0;
    public double rotate_XY = 0;
    public float camera_x = 0;
    public float camera_y = 0;
    public float camera_z = 0;
    public int debug_available = 1;

    public Camera(float camera_x, float camera_y, float camera_z, double rotate_XY, double rotate_Z)
    {
        super(camera_x, camera_y, camera_z);
        this.camera_x = camera_x;
        this.camera_y = camera_y;
        this.camera_z = camera_z;
        this.rotate_XY = rotate_XY;
        this.rotate_Z = rotate_Z;
    }
}
