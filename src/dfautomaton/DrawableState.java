package dfautomaton;

import dfautomaton.model.State;

/**
 *
 * @author Franco Montiel
 */
public class DrawableState extends State {

    private int posx;
    private int posy;
    private int radius;

    public DrawableState(int x, int y, int r) {
        super(null, true);
        this.posx = x;
        this.posy = y;
        this.radius = r;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getDistanceTo(DrawableState ds) {
        double dx = posx - ds.getPosx();
        double dy = posy - ds.getPosy();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
