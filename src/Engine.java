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
        objects.add(new Sphere(20, 20, 50, 100));
        objects.add(new Space(new RenderExpression("-20")));
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
        g2.fillRect(0,0,width,height);

        for (SJ3DE_environment.Point p : points) {
            SJ3DE_environment.Point pp = new SJ3DE_environment.Point(p.x, p.y, p.z);
            pp.rotateX(rotate_X);
            pp.rotateY(rotate_Y);

            double scale = f / (pp.z + f + radius_from_point_zero*2);
            int x2d = (int)(pp.x * scale + width/2);
            int y2d = (int)(pp.y * scale + height/2);

            float hue = (float)((pp.z + radius_from_point_zero) / (2*radius_from_point_zero));
            g2.setColor(Color.getHSBColor(hue, 1f, 1f));

            g2.fillOval(x2d, y2d, 5,5);
        }
    }

    public static void main(String[] args) {
        // Final Swing object
        JFrame frame = new JFrame("SJ3DE - Rendering result");
        Engine panel = new Engine();

        frame.add(panel);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
