package SJ3DE_ui;

import SJ3DE_environment.Material;
import SJ3DE_environment.Point;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Toolbox extends JPanel {
    private List<JToggleButton> buttons = new ArrayList<>();
    public static String mode = "cursor";
    public static boolean isPainting = false;
    private static List<Brush> brushes = new ArrayList<>();
    public static Brush brush;

    public static Toolbox toolbox = new Toolbox();

    public Toolbox() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5,5,5,5));

        JToggleButton CursorButton = new JToggleButton("C");
        CursorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        CursorButton.addActionListener(e -> {
            setMode("cursor");
        });
        CursorButton.setSelected(true);
        Toolbox.brushes.add(new Brush(new Material(), 25));
        buttons.add(CursorButton);

        JToggleButton Brush1Button = new JToggleButton("B");
        Brush1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(5));
        Brush1Button.addActionListener(e -> {
            setMode("brush1");
        });
        Toolbox.brushes.add(new Brush(new Material(Color.RED), 25));
        buttons.add(Brush1Button);

        for (JToggleButton btt : buttons) {
            add(btt, BorderLayout.SOUTH);
        }

        brush = Toolbox.brushes.getFirst();
    }

    public void setMode(String mode) {
        switch (mode) {
            case "cursor" -> {
                System.out.println("cursor");
                for (JToggleButton btt : buttons) {
                    btt.setSelected(false);
                }
                buttons.getFirst().setSelected(true);
                Toolbox.brush = brushes.getFirst();
                Toolbox.mode = mode;
            }
            case "brush1" -> {
                System.out.println("brush1");
                for (JToggleButton btt : buttons) {
                    btt.setSelected(false);
                }
                buttons.get(1).setSelected(true);
                Toolbox.brush = brushes.get(1);
                Toolbox.mode = mode;
            }
        }
    }
}
