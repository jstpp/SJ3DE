import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import SJ3DE_environment.*;
import SJ3DE_environment.Point;
import SJ3DE_stereometry.*;

import java.util.ArrayList;
import java.util.List;

public class Engine extends JPanel {
    public List<SJ3DE_environment.Point> points = new ArrayList<SJ3DE_environment.Point>();
    public List<SJ3DE_environment.Space> objects = new ArrayList<SJ3DE_environment.Space>();
    double rotate_X = 0;
    double rotate_Y = 0;
    double radius_from_point_zero = 50;
    double f = 100;

    public Engine() {
        // Initialize simple space
        Sphere sfera1 = new Sphere(100, 100, 200, 100);
        sfera1.materialSet(new Material("#5a8205"));
        objects.add(sfera1);

        Sphere sfera2 = new Sphere(130, 80, 175, 100);
        sfera2.materialSet(new Material("#5a8205"));
        objects.add(sfera2);

        Sphere sfera3 = new Sphere(190, 60, 240, 100);
        sfera3.materialSet(new Material("#5a8205"));
        objects.add(sfera3);

        Sphere sfera4 = new Sphere(150, 140, 120, 100);
        sfera4.materialSet(new Material("#5a8205"));
        objects.add(sfera4);

        Cylinder pien_drzewa = new Cylinder(30, 30, 400, 100, 80, 0);
        pien_drzewa.materialSet(new Material("#441e16"));
        objects.add(pien_drzewa);


        //objects.add(new Cuboid(50, 150, 300, 0, 0, 0));
        //objects.add(new Cylinder(150, 100, 300));
        objects.add(new Space(100, 80, -180, new RenderExpression("sin(x/10)*cos(y/10)*10", new Point(100, 80, -180))));
        for (Space object : objects)
        {
            System.out.println(object);
            points.addAll(object.points);
        }

        // Mouse movements interpretation
        addMouseMotionListener(new MouseMotionAdapter() {
            int last_cursor_X = -1;
            int last_cursor_Y = -1;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (last_cursor_X != -1 && last_cursor_Y != -1)
                {
                    int dx = e.getX() - last_cursor_X;
                    int dy = e.getY() - last_cursor_Y;
                    rotate_Y += dx * 0.5;
                    rotate_X += dy * 0.5;
                    repaint();
                }
                last_cursor_X = e.getX();
                last_cursor_Y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                last_cursor_Y = e.getY();
                last_cursor_X = e.getX();
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);

        double[][] zBuffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (SJ3DE_environment.Point p : points) {

            SJ3DE_environment.Point pp = new SJ3DE_environment.Point(
                    p.x,
                    p.z,
                    -p.y
            );

            pp.rotateX(rotate_X);
            pp.rotateY(rotate_Y);

            double depth = pp.z;

            if (depth <= -f + 1) continue;

            double scale = f / (depth + f);

            int x2d = (int) (pp.x * scale + width / 2);
            int y2d = (int) (-pp.y * scale + height / 2);

            if (x2d < 0 || x2d >= width || y2d < 0 || y2d >= height) continue;

            if (depth < zBuffer[x2d][y2d]) {
                zBuffer[x2d][y2d] = depth;

                g2.setColor(Color.decode(p.material.color));
                int size = (int) p.material.thickness;

                int radius = size / 2;

                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {

                        int px = x2d + dx;
                        int py = y2d + dy;

                        if (px < 0 || px >= width || py < 0 || py >= height) continue;


                        if (depth < zBuffer[px][py]) {
                            zBuffer[px][py] = depth;

                            g2.setColor(Color.decode(p.material.color));
                            g2.fillRect(px, py, 1, 1);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Final Swing object
        JFrame frame = new JFrame("SJ3DE - Rendering result");
        Engine panel = new Engine();

        JButton gui_open = new JButton("Menu");
        gui_open.setBounds(10,10,100,50);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(gui_open, BorderLayout.SOUTH);

        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
