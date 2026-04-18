package SJ3DE_ui;

import SJ3DE_engine.Engine;
import SJ3DE_environment.Point;
import SJ3DE_environment.RenderExpression;
import SJ3DE_environment.Space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenderTab extends JPanel {
    private Engine render = new Engine();
    public RenderTab() {
        setLayout(new BorderLayout());
        add(render, BorderLayout.CENTER);
        JPanel expression_pane_box = new JPanel();
        expression_pane_box.setLayout(new BorderLayout(5, 5));
        JTextPane expression_pane = new JTextPane();
        JButton process_expression = new JButton("Add object");
        process_expression.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Space shape = new Space(0,0,0, new RenderExpression(expression_pane.getText(), new Point(0,0,0)));
                for (Point po : shape.points) {
                    po.parent_engine = render;
                    po.rainbow();
                }
                render.objects.add(shape);
                repaint();
                LoadedStructuresPanel.update();
            }
        });
        expression_pane_box.add(expression_pane, BorderLayout.CENTER);
        expression_pane_box.add(process_expression, BorderLayout.EAST);
        add(expression_pane_box, BorderLayout.SOUTH);
    }

    public Engine getRender() {
        return render;
    }
}
