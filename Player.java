
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;


/**
 *
 * @author JMDC
 */
public abstract class Player implements GameConstants {

    private static final int NUM_SHIPS = 5;

    private static final int CARRIER = 0;
    private static final int BATTLESHIP = 1;
    private static final int CRUISER = 2;
    private static final int DESTROYER1 = 3;
    private static final int DESTROYER2 = 4;

    private static final int CARRIER_SIZE = 5;
    private static final int BATTLESHIP_SIZE = 4;
    private static final int CRUISER_SIZE = 3;
    private static final int DESTROYER_SIZE = 2;

    private static final char SPACE = ' ';
    private static final char MISS = '-';
    private static final char DESTROYED = '*';

    private String name;
    private Ship[] ships;
    private char[][] offensiveGrid;
    private char[][] defensiveGrid;
    protected UserInterface ui;
    private boolean hit;


    public Player(UserInterface ui, String name) {
        this.ui = ui;
        this.name = name;

        offensiveGrid = new char[GRID_WIDTH][GRID_HEIGHT];
        initialiseGrid(offensiveGrid);
        defensiveGrid = new char[GRID_WIDTH][GRID_HEIGHT];
        initialiseGrid(defensiveGrid);
    }
    
    public char checkSite(Point p) {
        int i;
        i = 0;

        while (i < ships.length && !ships[i].hit(p)) {
            i++;
        }

        if (i < ships.length) {
            defensiveGrid[(int) p.getY()][(int) p.getX()] = DESTROYED;
            ui.displayFeedback(name + ": you hit my " + ships[i].type() + "!");
            if (ships[i].sunk()) {
                ui.displayFeedback(name + ": you sunk my " + ships[i].type() + "!");
            }
            return ships[i].type().charAt(0);
        } else {
            ui.displayFeedback(name + ": Miss");
            defensiveGrid[(int) p.getY()][(int) p.getX()] = MISS;
            return MISS;
        }

    }

    public void displayDefensiveGrid() {
        ui.displayGrid(defensiveGrid);
    }

    public void displayOffensiveGrid() {
        ui.displayGrid(offensiveGrid);
    }

    public boolean loser() {
        int i = 0;

        while (i < ships.length && ships[i].sunk()) {
            i++;
        }
        if (i < ships.length) {
            return false;
        } else {
            return true;
        }

    }

    public String name() {
        return name;
    }

    public void positionShips(){
        ships = new Ship[NUM_SHIPS];
        ui.displayFeedback(name + " positioning ships.");
        displayDefensiveGrid();

        ships[CARRIER] = constructShip("AIRCRAFT CARRIER", "Aircraft Carrier", CARRIER_SIZE);
        ships[BATTLESHIP] = constructShip("BATTLESHIP", "Battleship", BATTLESHIP_SIZE);
        ships[CRUISER] = constructShip("CRUISER", "Cruiser", CRUISER_SIZE);
        ships[DESTROYER1] = constructShip("DESTROYER 1", "Destroyer", DESTROYER_SIZE);
        ships[DESTROYER2] = constructShip("DESTROYER 2", "Destroyer", DESTROYER_SIZE);
    }

    public boolean recordFeedback(Point p,char feedback){
        if(offensiveGrid[(int)p.getY()][(int)p.getX()] == SPACE){
            offensiveGrid[(int)p.getY()][(int)p.getX()] = feedback;
        }

        return feedback !=MISS;
    }

    private Ship constructShip(String feedback, String type , int size){
        boolean done;
        int orientation;
        Point[] points;
        Ship ship;

        ui.displayFeedback(feedback);
        points = new Point[size];
        orientation = -1;
        done = false;

        while(!done){
            points[0] = getStartCoordinate();
            orientation = getOrientation();
            
            for(int i = 1; i <points.length;i++){
                points[i]=getPoint(points[i-1],orientation);
            }
            done = validPoints(points);

        }

        ship = new Ship(type,orientation,points);
        updateDefensiveGrid(ship);

        return ship;
    }

    private Point getPoint(Point old, int orientation){
        int x,y;

        switch (orientation) {
            case HORIZONTAL:
                x = (int)old.getX()+1;
                y = (int)old.getY();
                return new Point(x,y);
            case VERTICAL:
                x = (int)old.getX();
                y = (int)old.getY() + 1;
                return new Point(x,y);
            case DIAGONAL_RIGHT:
                x = (int)old.getX() + 1;
                y = (int)old.getY() + 1;
                return new Point(x,y);
            case DIAGONAL_LEFT:
                x = (int)old.getX() - 1;
                y = (int)old.getY() + 1;
                return new Point(x,y);
        }
        return null;
    }

    public void setHit(boolean hit){
        this.hit = hit;
    }

    private void initialiseGrid(char[][] grid){
        for(int i = 0; i < grid.length;i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = SPACE;
            }
        }
    }


    private void updateDefensiveGrid(Ship ship){
        ship.addToGrid(defensiveGrid);
        displayDefensiveGrid();
    }

    private boolean validPoints(Point[] points){
        int x,y,j;

        for(int i =0; i <points.length;i++){
            x = (int)points[i].getX();
            y = (int)points[i].getY();

            if(x<0 || GRID_HEIGHT - 1 < x || y< 0|| GRID_WIDTH - 1 < y){
                return false;
            }
        }

        j=0;
        while(j<ships.length && ships[j]!=null){
            if(ships[j].clashes(points)){
                return false;
            }
            j++;
        }

        return true;
    }

    public abstract int getOrientation();

    public abstract Point getStartCoordinate();

    public abstract Point takeTurn();



}