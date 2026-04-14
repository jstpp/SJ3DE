package SJ3DE_ui;

import SJ3DE_engine.Engine;
import SJ3DE_environment.Environment;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

public class SettingsPanel extends JPanel {
    public SettingsPanel(Engine engine) {
        // Layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Header
        add(new JLabel("Settings"));

        // f slider
        JPanel f_slider_panel = new JPanel();
        f_slider_panel.add(new JLabel("f:"));
        JSlider f_slider = new JSlider(1, 1000);
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 1 ), new JLabel("1") );
        labelTable.put( new Integer( 1000 ), new JLabel("1000") );
        f_slider.setLabelTable( labelTable );
        f_slider.setValue((int)engine.f);
        f_slider.setPaintLabels(true);
        f_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double sliderValue = (double)((int)((JSlider)e.getSource()).getValue());
                engine.f = sliderValue;
            }
        });
        f_slider_panel.add(f_slider);
        add(f_slider_panel);

        // Max. render radius slider
        JPanel max_r_r_slider_panel = new JPanel();
        max_r_r_slider_panel.add(new JLabel("Max. render radius:"));
        JSlider max_r_r_slider = new JSlider(10, 2000);
        Hashtable labelRRTable = new Hashtable();
        labelRRTable.put( new Integer( 10 ), new JLabel("10") );
        labelRRTable.put( new Integer( 2000 ), new JLabel("2000") );
        max_r_r_slider.setLabelTable( labelRRTable );
        max_r_r_slider.setValue((int) Environment.render_radius);
        max_r_r_slider.setPaintLabels(true);
        max_r_r_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float sliderRRValue = (float)((int)((JSlider)e.getSource()).getValue());
                Environment.render_radius = sliderRRValue;
                repaint();
            }
        });
        max_r_r_slider_panel.add(max_r_r_slider);
        add(max_r_r_slider_panel);

    }
}
