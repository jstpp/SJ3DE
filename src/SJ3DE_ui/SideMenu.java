package SJ3DE_ui;

import SJ3DE_engine.Engine;
import SJ3DE_environment.Environment;
import SJ3DE_environment.Space;

import javax.swing.*;
import java.awt.*;

public class SideMenu extends JPanel {
    private static JPanel details = new JPanel();
    private static Engine render;
    public SideMenu(Engine renderx) {
        render = renderx;
        setLayout(new BorderLayout());

        details.setBorder(BorderFactory.createTitledBorder("Details & stats"));

        int objects_count = render.objects.size();
        int points_count = 0;
        for (Space sp : render.objects)
        {
            points_count += sp.points.size();

        }

        update();
        details.setLayout(new GridLayout(details.getComponentCount(),1));
        add(details, BorderLayout.SOUTH);
    }

    public static void update() {
        details.removeAll();

        int objects_count = render.objects.size();
        int points_count = 0;
        for (Space sp : render.objects)
        {
            points_count += sp.points.size();

        }

        details.add(new JLabel("Structures: " + objects_count));
        details.add(new JLabel("Points: " + points_count));
        details.add(new JLabel("Render radius: " + Environment.render_radius));
        details.add(new JLabel("Render gap: " + Environment.gap));
        details.add(new JLabel(""));
        details.add(new JLabel("Rotation (z): " + render.camera.rotate_Z + "°"));
        details.add(new JLabel("Rotation (xy): " + render.camera.rotate_XY + "°"));
        details.updateUI();
    }
}
