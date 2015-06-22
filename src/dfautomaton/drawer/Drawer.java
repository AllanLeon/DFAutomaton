/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfautomaton.drawer;

import dfautomaton.data.Constants;
import dfautomaton.model.Automaton;
import dfautomaton.model.State;
import dfautomaton.model.Transition;
import dfautomaton.model.basics.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Allan Leon
 */
public class Drawer {

    private static void putPixel(Graphics g, int x, int y) {
        g.drawLine(x, Constants.PANEL_HEIGHT - y, x, Constants.PANEL_HEIGHT - y);
    }

    public static void drawLine(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        g.setColor(color);

        int dx, dy, d, x, y, deltaE, deltaNE, stepx = 0, stepy = 0;
        dx = x1 - x0;
        dy = y1 - y0;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else if (dx > 0) {
            stepx = 1;
        }
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else if (dy > 0) {
            stepy = 1;
        }
        x = x0;
        y = y0;
        putPixel(g, x, y);
        if (dx > dy) {
            d = 2 * dy - dx;
            deltaE = 2 * dy;
            deltaNE = 2 * (dy - dx);
            while (x != x1) {
                x += stepx;
                if (d < 0) {
                    d += deltaE;
                } else {
                    y += stepy;
                    d += deltaNE;
                }
                putPixel(g, x, y);
            }
        } else {
            d = -2 * dx + dy;
            deltaE = -2 * dx;
            deltaNE = 2 * (dy - dx);
            while (y != y1) {
                y += stepy;
                if (d > 0) {
                    d += deltaE;
                } else {
                    x += stepx;
                    d += deltaNE;
                }
                putPixel(g, x, y);
            }
        }
    }

    public static void drawDashedLine(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        g.setColor(color);

        int dx, dy, d, x, y, deltaE, deltaNE, stepx = 0, stepy = 0;
        dx = x1 - x0;
        dy = y1 - y0;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else if (dx > 0) {
            stepx = 1;
        }
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else if (dy > 0) {
            stepy = 1;
        }
        x = x0;
        y = y0;
        int n = 1;
        putPixel(g, x, y);
        if (dx > dy) {
            d = 2 * dy - dx;
            deltaE = 2 * dy;
            deltaNE = 2 * (dy - dx);
            while (x != x1) {
                x += stepx;
                if (d < 0) {
                    d += deltaE;
                } else {
                    y += stepy;
                    d += deltaNE;
                }
                if (n == 1 || n == 2 || n == 3) {
                    putPixel(g, x, y);
                } else {
                    if (n > 6) {
                        n = 0;
                    }
                }
                n++;
            }
        } else {
            d = -2 * dx + dy;
            deltaE = -2 * dx;
            deltaNE = 2 * (dy - dx);
            while (y != y1) {
                y += stepy;
                if (d > 0) {
                    d += deltaE;
                } else {
                    x += stepx;
                    d += deltaNE;
                }
                if (n == 1 || n == 2 || n == 3) {
                    putPixel(g, x, y);
                } else {
                    if (n > 5) {
                        n = 0;
                    }
                }
                n++;
            }
        }
    }

    public static void drawCircle(Graphics g, int centerX, int centerY, int radius, Color color) {
        g.setColor(color);

        int x, y, d, dE, dSE;
        x = 0;
        y = radius;
        d = 1 - radius;
        dE = 3;
        dSE = -2 * radius + 5;
        simetry(g, x, y, centerX, centerY);
        while (y > x) {
            if (d < 0) {
                d += dE;
                dE += 2;
                dSE += 2;
                x += 1;
            } else {
                d += dSE;
                dE += 2;
                dSE += 4;
                x += 1;
                y += -1;
            }
            simetry(g, x, y, centerX, centerY);
        }
    }

    private static void simetry(Graphics g, int x, int y, int centerX, int centerY) {
        putPixel(g, x + centerX, y + centerY);
        putPixel(g, y + centerX, x + centerY);
        putPixel(g, y + centerX, -x + centerY);
        putPixel(g, x + centerX, -y + centerY);
        putPixel(g, -x + centerX, -y + centerY);
        putPixel(g, -y + centerX, -x + centerY);
        putPixel(g, -y + centerX, x + centerY);
        putPixel(g, -x + centerX, y + centerY);
    }
    
    public static void drawTransition(Graphics g, Transition transition) {
        Point start = transition.getInitialState().getPos();
        Point end = transition.getNextState().getPos();
        drawDashedLine(g, start.getX(), start.getY(), end.getX(), end.getY(), Color.WHITE);
        g.drawString("" + transition.getSymbol(), (start.getX() + end.getX()) / 2,
                Constants.PANEL_HEIGHT - ((start.getY() + end.getY()) / 2));
    }
    
    public static void drawInitialStateArrow(Graphics g, State state) {
        if (state != null) {
            drawLine(g, state.getPos().getX() - Constants.STATE_RADIUS - 10,
                    state.getPos().getY(), state.getPos().getX() - Constants.STATE_RADIUS,
                    state.getPos().getY(), Color.yellow);
        }
    }
    
    public static void drawState(Graphics g, State state) {
        if (state.isAccepted()) {
            drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS * 2 / 3, Color.WHITE);
        }
        drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS, Color.WHITE);
        g.drawString(state.getName(), state.getPos().getX(), Constants.PANEL_HEIGHT - state.getPos().getY());
    }
    
    public static void drawTransitions(Graphics g, List<Transition> transitions) {
        for (Transition transition : transitions) {
            drawTransition(g, transition);
        }
    }

    public static void drawStates(Graphics g, Set<State> states) {
        for (State state : states) {
            drawState(g, state);
        }
    }

    public static void drawAutomaton(Graphics g, Automaton automaton) {
        drawStates(g, automaton.getStates());
        drawInitialStateArrow(g, automaton.getInitialState());
        drawTransitions(g, automaton.getTransitions());
    }
}
