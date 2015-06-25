package dfautomaton;

import dfautomaton.view.MainFrame;
import dfautomaton.data.Constants;
import dfautomaton.model.Configuration;
import dfautomaton.model.State;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The principal class
 * @author Franco Montiel
 */
public class Main {

    public static void main(String[] args) {
        MainFrame ui = new MainFrame();
        //JFrame frame = new JFrame();
        //JPanel panel = new ConfigurationPanel(new Configuration(new State("lol", true), "ok"));
        //panel.setPreferredSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        //frame.add(panel);
        //frame.add(new ConfigurationPanel(new Configuration(new State("lol", true), "ok")));
        //frame.setVisible(true);
        //frame.pack();
    }    
}