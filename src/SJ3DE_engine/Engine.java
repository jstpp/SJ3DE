package SJ3DE_engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import SJ3DE_environment.*;
import SJ3DE_environment.Point;
import SJ3DE_lidar.PCloud;
import SJ3DE_nature.flora.Tree1.Tree1;
import SJ3DE_nature.hydro.Sea1;
import SJ3DE_nature.soil.Lawn1;
import SJ3DE_ui.LoadedStructuresPanel;
import SJ3DE_ui.RenderTab;
import SJ3DE_ui.SettingsPanel;
import SJ3DE_nature.soil.Desert1;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import java.util.Timer;

public class Engine extends JPanel {
    public List<SJ3DE_environment.Point> points = new ArrayList<SJ3DE_environment.Point>();
    public List<SJ3DE_environment.Space> objects = new ArrayList<SJ3DE_environment.Space>();
    public Camera camera = new Camera(0,0,100,0,90);
    public double f = 1000;

    public BufferedImage canvas;
    public int[] pixels;
    private int cWidth, cHeight;

    public Timer time = new Timer();

    public Engine() {
        Environment.parent_engine = this;
        time.schedule(new TimeUpdate(), 300, Environment.time_tick_duration);
        // Initial setup
        loadExample();
        setFocusable(true);
        requestFocusInWindow();
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        // Mouse movements interpretation
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (camera.last_cursor_X != -1 && camera.last_cursor_Y != -1)
                {
                    int dx = e.getX() - camera.last_cursor_X;
                    int dy = e.getY() - camera.last_cursor_Y;
                    camera.rotate_XY += dx * 0.5;
                    camera.rotate_Z += dy * 0.5;
                    repaint();
                }
                camera.last_cursor_X = e.getX();
                camera.last_cursor_Y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                camera.last_cursor_Y = e.getY();
                camera.last_cursor_X = e.getX();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    f /= 1.1;
                } else {
                    f *= 1.1;
                }
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                float step = 10f;

                double radY = Math.toRadians(camera.rotate_XY);
                float sinY = (float) Math.sin(radY);
                float cosY = (float) Math.cos(radY);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D -> {
                        camera.camera_x += cosY * step;
                        camera.camera_y += sinY * step;
                    }
                    case KeyEvent.VK_W -> {
                        camera.camera_x += sinY * step;
                        camera.camera_y -= cosY * step;
                    }
                    case KeyEvent.VK_S -> {
                        camera.camera_x -= sinY * step;
                        camera.camera_y += cosY * step;
                    }
                    case KeyEvent.VK_A -> {
                        camera.camera_x -= cosY * step;
                        camera.camera_y -= sinY * step;
                    }
                    case KeyEvent.VK_E -> camera.camera_z -= step;
                    case KeyEvent.VK_Q -> camera.camera_z += step;

                    case KeyEvent.VK_F1 -> camera.debug_available = (camera.debug_available+1)%2;
                }
                repaint();
            }
        });

    }

    public void addObject(Space obj)
    {
        for (Point pt : obj.points) {
            pt.parent_engine = this;
        }
        objects.add(obj);
    }

    public void updateObjects() {
        try {
            for (Space obj : objects) {
                obj.updateObject();
            }
        } catch (ConcurrentModificationException cme) {
            System.out.println("Concurrent modification. Ignored.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        points.clear();
        for (Space object : objects)
        {
            points.addAll(object.points);
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);

        if (canvas == null || getWidth() != cWidth || getHeight() != cHeight) {
            initCanvas();
        }
        double[] zBuffer = new double[width*height];
        java.util.Arrays.fill(zBuffer, Double.POSITIVE_INFINITY);
        java.util.Arrays.fill(pixels, 0x000000);

        for (Point p : points) {
            if(Math.sqrt((p.x-camera.camera_x)*(p.x-camera.camera_x)+(p.y-camera.camera_y)*(p.y-camera.camera_y)+(p.z-camera.camera_z)*(p.z-camera.camera_z))>Environment.render_radius)
            {
                continue;
            }
            Point pp = new Point(
                    p.x - camera.camera_x,
                    p.y - camera.camera_y,
                    p.z - camera.camera_z
            );

            pp.parent_engine = this;

            pp.rotateXY(camera.rotate_XY);
            pp.rotateZ(-camera.rotate_Z);

            // Scale and depth
            double depth = pp.z;
            if (depth <= 0) continue;
            double scale = f / depth;
            int x2d = (int) (pp.x * scale + width / 2);
            int y2d = (int) (-pp.y * scale + height / 2);
            if (x2d < 0 || x2d >= width || y2d < 0 || y2d >= height) continue;

            if (depth < zBuffer[y2d*width+x2d]) {
                zBuffer[y2d*width+x2d] = depth;

                int size = (int) p.material.thickness;
                double distanceSq = (Math.pow(camera.last_cursor_X - x2d, 2) + Math.pow(camera.last_cursor_Y - y2d, 2));
                int baseColorRGB = p.material.color.getRGB();

                if (distanceSq < camera.default_brush_radius) {
                    Color c = p.material.color;
                    baseColorRGB = new Color(
                            Math.min((int)(c.getRed() * 1.3), 255),
                            Math.min((int)(c.getGreen() * 1.3), 255),
                            Math.min((int)(c.getBlue() * 1.3), 255)
                    ).getRGB();
                }

                int radius = size / 2;

                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {

                        int px = x2d + dx;
                        int py = y2d + dy;

                        if (px < 0 || px >= width || py < 0 || py >= height) continue;


                        if (depth < zBuffer[py * width + px]) {
                            zBuffer[py * width + px] = depth;
                            pixels[py * width + px] = baseColorRGB;
                        }
                    }
                }
            }
        }
        g.drawImage(canvas, 0,0,null);

        if(camera.debug_available==1) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            g.drawString("Structures: " + objects.size(), 10, 20);
            g.drawString("Points: " + points.size(), 10, 35);
            g.drawString("Render radius: " + Environment.render_radius, 10, 50);
            g.drawString("Render gap: " + Environment.gap, 10, 65);
            g.drawString("Time (s): " + Environment.time_ticks * Environment.time_tick_duration / 1000, 10, 95);
            g.drawString("Coordinates: x: " + Math.round(camera.camera_x) + " y: " + Math.round(camera.camera_y) + " z: " + Math.round(camera.camera_z), 10, 125);
            g.drawString("Rotation: " + Math.abs(camera.rotate_Z % 360) + "°, " + Math.abs(camera.rotate_XY % 360) + "°", 10, 140);
            g.drawString("Mouse position: x: " + camera.last_cursor_X + " (max: " + cWidth + "), y: " + camera.last_cursor_Y + " (max: " + cHeight + ")", 10, 155);
        }
    }

    public void initCanvas() {
        this.cWidth = getWidth();
        this.cHeight = getHeight();
        this.canvas = new BufferedImage(this.cWidth, this.cHeight, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) this.canvas.getRaster().getDataBuffer()).getData();
    }

    public void loadExample()
    {
        // Initialize simple coast
        //addObject(new Line(new Point(0,0,0), new Point(0,0,1000)));
        //addObject(new PCloud("Palac_Moszna.laz", 0.0002));
        addObject(new Lawn1(0,0,100));
        addObject(new Tree1(0,0,100));
        addObject(new Tree1(50,50,80));
        addObject(new Desert1(0,0,0));
        addObject(new Sea1(-800,0,0));
    }

    @Override
    public String toString() {
        return "Engine(Camera: " + new Point(camera.camera_x, camera.camera_y, camera.camera_z) + ")";
    }

    public static void main(String[] args) {

        // Render tab setup
        RenderTab render_tab = new RenderTab();

        // Main Swing objects setup
        JFrame frame = new JFrame("SJ3DE");
        JPanel settings = new SettingsPanel(render_tab.getRender());

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("View", render_tab);
        tabs.addTab("Settings", settings);
        tabs.addTab("Structures", new LoadedStructuresPanel(render_tab.getRender()));

        frame.add(tabs, BorderLayout.CENTER);

        frame.setSize(1600,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
