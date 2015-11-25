package dfautomaton.view;

import dfautomaton.data.Constants;
import dfautomaton.model.Automaton;
import dfautomaton.model.ConfigurationStack;
import static dfautomaton.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
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
    private JButton stepBtn;
    
    public ResultsFrame(Automaton automaton) {
        setVisible(true);
        this.automaton = automaton;
        initialize();
    }
    
    private void initialize() {
        setTitle("Results");
        setSize(Constants.RESULTS_WINDOW_WIDTH, Constants.RESULTS_WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        
        panel = new JPanel(new GridLayout(1, 0));
        panel.setBackground(Color.DARK_GRAY);
        addConfigurationsPanel(automaton.getConfigurations());
        
        scroll = new JScrollPane(panel);
        scroll.setBackground(Color.DARK_GRAY);
        scroll.setBounds(2, 0, Constants.RESULTS_WIDTH, Constants.RESULTS_HEIGHT);
        
        stepBtn = new JButton(">>>>>>>>");
        stepBtn.setFocusable(false);
        setMaterialLNF(stepBtn);
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (automaton.next()) {
                    addConfigurationsPanel(automaton.getConfigurations());
                } else {
                    stepBtn.setEnabled(false);
                }
            }
        });
        MainFrame.setMaterialLNF(stepBtn);
        stepBtn.setBounds(Constants.RESULTS_WINDOW_WIDTH / 2 - 60, Constants.RESULTS_WINDOW_HEIGHT - 65, 120, 30);
        
        getContentPane().add(scroll);
        getContentPane().add(stepBtn);
    }
    
    private void addConfigurationsPanel(List<Set<ConfigurationStack>> configurations) {
        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        for (ConfigurationStack conf : configurations.get(0)) {
            newPanel.add(new ConfigurationPanel(conf));
        }
        panel.add(newPanel);
        panel.revalidate();
    }
}
