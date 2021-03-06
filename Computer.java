
//BSIT - 2A
//Angelo A. Baclaan
//Christopher P. Napoles
//De Guzman Allen Miguel
//Frannz S. Suaverdez
//Franco Miguel Arambulo
import java.util.*;
import java.awt.Point;

public class Computer extends Player {

    UserInterface ui;
    String name;
    ArrayList<Point> points = new ArrayList<Point>();
    Point prev;
    boolean hit;
    int hitCtr = 0;
    int prevOrient;

    public Computer(UserInterface ui) {
        super(ui, "Computer");
        this.ui = ui;
        this.name = "Computer";
    }

    public Computer(UserInterface ui, String name) {
        super(ui, name);
        this.ui = ui;
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public int getOrientation() {
        int ret;
        Random rand = new Random();
        ret = rand.nextInt(4);
        return ret + 1;
    }

    /**
     *
     * @return
     */
    @Override
    public Point getStartCoordinate() {
        int x, y;
        Random rand = new Random();
        x = rand.nextInt(10);
        y = rand.nextInt(10);
        return new Point(x, y);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        if (this.hit == true) {
            hitCtr++;
        } else {
            hitCtr = 0;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Point takeTurn() {
        Random rand = new Random();
        int x = 0, y = 0;
        int ctr = 0;
        boolean test = false;
        int timer = 0;
        boolean timerTest = false;
        if (hit == true) {
            if (points.contains(new Point((int) prev.getX() + 1, (int) prev.getY()))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX(), (int) prev.getY() + 1))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX() + 1, (int) prev.getY() + 1))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX() - 1, (int) prev.getY() + 1))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX() + 1, (int) prev.getY() - 1))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX() - 1, (int) prev.getY() - 1))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX() - 1, (int) prev.getY()))) {
                ctr++;
            }
            if (points.contains(new Point((int) prev.getX(), (int) prev.getY() - 1))) {
                ctr++;
            }

            if (ctr == 8) {
                test = false;
            } else {
                test = true;

            }
            ctr = 0;
        }
        if (test == true) {
            do {
                int randOrient = rand.nextInt(3);

                if (hitCtr > 1) {
                    randOrient = prevOrient;
                } else {
                    prevOrient = randOrient;
                }
                if (randOrient == 0) {
                    x = (int) prev.getX() + 1;
                    y = (int) prev.getY();
                } else if (randOrient == 1) {
                    x = (int) prev.getX();
                    y = (int) prev.getY() + 1;
                } else if (randOrient == 2) {
                    x = (int) prev.getX() + 1;
                    y = (int) prev.getY() + 1;
                } else if (randOrient == 3) {
                    x = (int) prev.getX() - 1;
                    y = (int) prev.getY() + 1;
                } else if (randOrient == 4) {
                    x = (int) prev.getX() + 1;
                    y = (int) prev.getY() - 1;
                } else if (randOrient == 5) {
                    x = (int) prev.getX() - 1;
                    y = (int) prev.getY() - 1;
                } else if (randOrient == 6) {
                    x = (int) prev.getX() - 1;
                    y = (int) prev.getY();
                } else if (randOrient == 7) {
                    x = (int) prev.getX();
                    y = (int) prev.getY() - 1;
                }

                timer++;
                if (timer > 50) {
                    timerTest = true;
                    break;

                }
            } while (points.contains(new Point(x, y)) || (x < 0 || 10 - 1 < x || y < 0 || 10 - 1 < y));

        } else {
            do {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            } while (points.contains(new Point(x, y)));
        }

        if (timerTest == true) {
            do {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            } while (points.contains(new Point(x, y)));
        }

        prev = new Point(x, y);
        points.add(new Point(x, y));

        return new Point(x, y);
    }

}
