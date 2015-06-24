/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.view;

import dfautomaton.data.Constants;
import dfautomaton.model.Configuration;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Allan Leon
 */
public class ConfigurationPanel extends JPanel {
    
    private final Configuration configuration;
    private JLabel label;
    
    public ConfigurationPanel(Configuration configuration) {
        this.configuration = configuration;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        if (configuration.isValid()) {
            setBackground(Color.GREEN);
        } else if (configuration.isDead()) {
            setBackground(Color.RED);
        }
        setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.DARK_GRAY));
        /*setPreferredSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        setMaximumSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        setMinimumSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));*/
        //setBounds(Constants.CONFIGURATION_WIDTH * x, Constants.CONFIGURATION_HEIGHT * y, Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT);
        label = new JLabel();
        if (configuration.getWord().equals("")) {
            label.setText(String.format("%s, %s", configuration.getState().getName(), '\u03B5'));
        } else {
            label.setText(String.format("%s, %s", configuration.getState().getName(), configuration.getWord()));
        }
        //label.setSize(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT);
        label.setBounds(0, 0, Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT);
        add(label);
        /*label.setPreferredSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(label));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
            .addComponent(label)));
        add(panel);*/
        //draw();
    }
    
    /*private void draw() {
        Drawer.drawConfiguration(getGraphics(), configuration);
    }*/
    
    /*@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Drawer.drawConfiguration(g, configuration);
        System.out.println("ok");
    }*/
}
