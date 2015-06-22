package dfautomaton;

import dfautomaton.data.Constants;
import dfautomaton.drawer.Drawer;
import dfautomaton.model.Automaton;
import dfautomaton.model.State;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A JFrame containing all elements
 *
 * @author Franco Montiel
 */
public class UI extends JFrame implements ActionListener {

    private static Automaton automaton;
    
    private JPanel panel;
    private JButton newState;
    private JButton newFState;
    private JButton newTransition;
    private MouseHandler mouseHandler;
    private Graphics dbg;
    private BufferedImage doubleBuffer;

    /**
     * Create the frame.
     */
    public UI() {
        automaton = new Automaton();
        initialize();
        setVisible(true);
    }

    /**
     * Initialize and set up the basic components of the frame.
     */
    private void initialize() {
        setTitle("DFA");
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 10, Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
        mouseHandler = new MouseHandler();
        panel.addMouseListener(mouseHandler);
        panel.addMouseMotionListener(mouseHandler);

        newState = new JButton("New State");
        setMaterialLNF(newState);
        newState.setFocusable(false);
        newState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //drawInitState();
            }
        });

        newTransition = new JButton("New Transition");
        setMaterialLNF(newTransition);
        newTransition.setFocusable(false);

        newFState = new JButton("New Final State");
        setMaterialLNF(newFState);
        newFState.setFocusable(false);

        newState.setBounds(Constants.WINDOW_WIDTH / 4 - 150, Constants.WINDOW_HEIGHT - 60, 100, 25);
        newTransition.setBounds(Constants.WINDOW_WIDTH / 4 - 40, Constants.WINDOW_HEIGHT - 60, 130, 25);
        newFState.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT - 60, 130, 25);

        getContentPane().add(panel);
        getContentPane().add(newState);
        getContentPane().add(newTransition);
        getContentPane().add(newFState);
        
        doubleBuffer = new BufferedImage(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        start();
    }

    public void setMaterialLNF(Component comp) {
        comp.setBackground(Color.BLACK);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setForeground(Color.WHITE);
    }
    
    public void start() {
		Timer timer = new Timer(1000/60, this);
		timer.start();
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        paint();
    }

    private void paint() {
        dbg = doubleBuffer.getGraphics();
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
        Drawer.drawStates(dbg, automaton.getStates());
        panel.getGraphics().drawImage(doubleBuffer, 0, 0, this);
    }
    
    public static Automaton getAutomaton() {
        return automaton;
    }
}
