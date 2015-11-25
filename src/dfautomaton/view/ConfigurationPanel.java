package dfautomaton.view;

import dfautomaton.data.Constants;
import dfautomaton.model.ConfigurationStack;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Allan Leon
 */
public class ConfigurationPanel extends JPanel {
    
    private final ConfigurationStack configuration;
    private JLabel label;
    
    public ConfigurationPanel(ConfigurationStack configuration) {
        this.configuration = configuration;
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.LIGHT_GRAY);
        if (configuration.isValid()) {
            setBackground(Color.GREEN);
        } else if (configuration.isDead()) {
            setBackground(Color.RED);
        }
        
        setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.DARK_GRAY));
        setPreferredSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        
        label = new JLabel(configuration.toString());
        /*if (configuration.getWord().equals("")) {
            label.setText(String.format("%s, %s", configuration.getState().getName(), Constants.EPSILON));
        } else {
            label.setText(String.format("%s, %s", configuration.getState().getName(), configuration.getWord()));
        }*/
        
        setToolTipText(label.getText());
        add(label);
    }
}
