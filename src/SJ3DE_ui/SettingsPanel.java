package SJ3DE_ui;

import javax.swing.*;
import java.util.Hashtable;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Settings"));
        JSlider f_slider = new JSlider(1, 20);
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 1 ), new JLabel("1") );
        labelTable.put( new Integer( 20 ), new JLabel("20") );
        f_slider.setLabelTable( labelTable );
        f_slider.setPaintLabels(true);
        add(f_slider);
    }
}
