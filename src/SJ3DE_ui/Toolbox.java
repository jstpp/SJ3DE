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

    public static JPanel colorPreview = new JPanel();
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
        Brush1Button.addActionListener(e -> {
            setMode("brush1");
        });
        Toolbox.brushes.add(new Brush(new Material(Color.RED), 25));
        buttons.add(Brush1Button);

        JToggleButton MeshMergerButton = new JToggleButton("M");
        MeshMergerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(5));
        MeshMergerButton.addActionListener(e -> {
            setMode("meshmerger");
        });
        Toolbox.brushes.add(new Brush(new Material(Color.RED), 200));
        buttons.add(MeshMergerButton);

        brush = Toolbox.brushes.getFirst();

        Toolbox.colorPreview.setMaximumSize(new Dimension(50, 30));
        Toolbox.colorPreview.setPreferredSize(new Dimension(50, 30));
        Toolbox.colorPreview.setBackground(brush.material.color);
        Toolbox.colorPreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        Toolbox.colorPreview.setAlignmentX(Component.CENTER_ALIGNMENT);

        Toolbox.colorPreview.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Color selectedColor = JColorChooser.showDialog(
                        Toolbox.this,
                        "Select brush color",
                        Toolbox.brush.material.color
                );
                if (selectedColor != null) {
                    Toolbox.brush.material.color = selectedColor;
                    Toolbox.colorPreview.setBackground(selectedColor);
                }
            }
        });

        for (JToggleButton btt : buttons) {
            btt.setMaximumSize(new Dimension(50, 30));
            btt.setPreferredSize(new Dimension(50, 30));
            add(btt, BorderLayout.SOUTH);
        }

        add(Box.createVerticalGlue());
        add(colorPreview, BorderLayout.SOUTH);
    }

    public void setMode(String mode) {
        switch (mode) {
            case "cursor" -> {
                for (JToggleButton btt : buttons) {
                    btt.setSelected(false);
                }
                buttons.getFirst().setSelected(true);
                Toolbox.brush = brushes.getFirst();
                Toolbox.mode = mode;
            }
            case "brush1" -> {
                for (JToggleButton btt : buttons) {
                    btt.setSelected(false);
                }
                buttons.get(1).setSelected(true);
                Toolbox.brush = brushes.get(1);
                Toolbox.mode = mode;
            }
            case "meshmerger" -> {
                for (JToggleButton btt : buttons) {
                    btt.setSelected(false);
                }
                buttons.get(2).setSelected(true);
                Toolbox.brush = brushes.get(2);
                Toolbox.mode = mode;
            }
        }
        Toolbox.colorPreview.setBackground(Toolbox.brush.material.color);
    }
}
