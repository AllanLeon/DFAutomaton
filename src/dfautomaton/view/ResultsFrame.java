/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.view;

import dfautomaton.data.Constants;
import dfautomaton.model.Automaton;
import dfautomaton.model.Configuration;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Allan Leon
 */
public class ResultsFrame extends JFrame {
    
    private JScrollPane scroll;
    private JPanel panel;
    private Automaton automaton;
    private int maxY;
    
    public ResultsFrame(Automaton automaton) {
        setVisible(true);
        this.automaton = automaton;
        this.maxY = 1;
        //this.automaton.read(word);
        initialize();
    }
    
    private void initialize() {
        setTitle("Results");
        setSize(Constants.RESULTS_WIDTH, Constants.RESULTS_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        
        scroll = new JScrollPane();
        scroll.setBackground(Color.DARK_GRAY);
        scroll.setPreferredSize(new Dimension(Constants.RESULTS_WIDTH, Constants.RESULTS_HEIGHT));
        //scroll.setBounds(0, 0, Constants.RESULTS_WIDTH, Constants.RESULTS_HEIGHT);
        scroll.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (automaton.next()) {
                    addConfigurationsPanel(automaton.getConfigurations(), automaton.getCurrentIteration());
                }
            }

            @Override
            public void mousePressed(MouseEvent me) { }

            @Override
            public void mouseReleased(MouseEvent me) { }

            @Override
            public void mouseEntered(MouseEvent me) { }

            @Override
            public void mouseExited(MouseEvent me) { }
        });
        
        panel = new JPanel();
        //panel.setLayout(null);
        panel.setBackground(Color.DARK_GRAY);
        createConfigurationsPanel(automaton.getConfigurations());
        scroll.setViewportView(panel);
        setContentPane(scroll);
        //getContentPane().add(scroll);
        //getContentPane().add(stepButton);
    }
    
    private void createConfigurationsPanel(List<List<Configuration>> configurations) {
        int sizeY = 1;
        for (int j = 0; j < configurations.size(); j++) {
            if (sizeY <  configurations.get(j).size()) {
                sizeY = configurations.get(j).size();
            }
            for (int i = 0; i <  configurations.get(j).size(); i++) {
                JPanel aux = new ConfigurationPanel(configurations.get(j).get(i));
                aux.setSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
                aux.setBounds(Constants.CONFIGURATION_WIDTH * j, Constants.CONFIGURATION_HEIGHT * i, Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT);
                panel.add(aux);
            }
        }
        //panel.setPreferredSize(new Dimension(configurations.size() * Constants.CONFIGURATION_WIDTH, sizeY * Constants.CONFIGURATION_HEIGHT));
    }
    
    private void addConfigurationsPanel(List<List<Configuration>> configurations, int current) {
        if (maxY < configurations.get(current).size()) {
            maxY = configurations.get(current).size();
        }
        for (int i = 0; i <  configurations.get(current).size(); i++) {
            JPanel aux = new ConfigurationPanel(configurations.get(current).get(i));
            aux.setBounds(Constants.CONFIGURATION_WIDTH * current, Constants.CONFIGURATION_HEIGHT * i, Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT);
            panel.add(aux);
        }
        //panel.setPreferredSize(new Dimension(current * Constants.CONFIGURATION_WIDTH, maxY * Constants.CONFIGURATION_HEIGHT));
        panel.setSize(new Dimension((current + 1) * Constants.CONFIGURATION_WIDTH, maxY * Constants.CONFIGURATION_HEIGHT));
        panel.setBounds(0, 0, (current + 1) * Constants.CONFIGURATION_WIDTH, maxY * Constants.CONFIGURATION_HEIGHT);
    }
}
