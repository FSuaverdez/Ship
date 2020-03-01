//BSIT - 2A
//Angelo A. Baclaan
//Christopher P. Napoles
//De Guzman Allen Miguel
//Frannz S. Suaverdez
//Franco Miguel Arambulo

import java.awt.Point;
import java.util.ArrayList;

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

    private static final char SPACE =' ';
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
    
    public void initialiseShips() {
    	ships = new Ship[NUM_SHIPS];
    }
    public void initialiseHits(){
        Point[] temp;
        for(int i = 0; i <ships.length;i++){
            temp = ships[i].getPoints();

            for (Point p : temp) {
                if(defensiveGrid[(int) p.getY()][(int) p.getX()] == DESTROYED){
                    ships[i].addHitCount();
                }

            }
        }

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

    public ArrayList <Point> getShipPoints(){
        ArrayList <Point> p = new ArrayList<Point>();
        Point[] temp;
        int j = 0;
        for(int i=0;i<ships.length;i++){
            temp = ships[i].getPoints();
            for (Point point : temp) {
                p.add(point);
            }
        }
        return p; 
    }
    public int[] hitCountsss(){
        int[] temp = new int[5];
        for(int i=0;i<ships.length;i++){
            temp[i] = ships[i].getHitCount();
        }
        return temp;
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

	public char[][] getOffensiveGrid() {
		return offensiveGrid;
	}

	public void setOffensiveGrid(char[][] offensiveGrid) {
		this.offensiveGrid = offensiveGrid;
	}

	public char[][] getDefensiveGrid() {
		return defensiveGrid;
	}

	public void setDefensiveGrid(char[][] defensiveGrid) {
		this.defensiveGrid = defensiveGrid;
	}
    
    public void setShipPoints(ArrayList<Point> p) {
    	Point[] arr1 = new Point[5];
    	Point[] arr2 = new Point[4];
    	Point[] arr3 = new Point[3];
    	Point[] arr4 = new Point[2];
    	Point[] arr5 = new Point[2];


    			arr1[0] = new Point((int)p.get(0).getX(),(int)p.get(0).getY());
    			arr1[1] = new Point((int)p.get(1).getX(),(int)p.get(1).getY());
    			arr1[2] = new Point((int)p.get(2).getX(),(int)p.get(2).getY());
    			arr1[3] = new Point((int)p.get(3).getX(),(int)p.get(3).getY());
    			arr1[4] = new Point((int)p.get(4).getX(),(int)p.get(4).getY());
    			
    			arr2[0] = new Point((int)p.get(5).getX(),(int)p.get(5).getY());
    			arr2[1] = new Point((int)p.get(6).getX(),(int)p.get(6).getY());
    			arr2[2] = new Point((int)p.get(7).getX(),(int)p.get(7).getY());
    			arr2[3] = new Point((int)p.get(8).getX(),(int)p.get(8).getY());

    			arr3[0] = new Point((int)p.get(9).getX(),(int)p.get(9).getY());
    			arr3[1] = new Point((int)p.get(10).getX(),(int)p.get(10).getY());
    			arr3[2] = new Point((int)p.get(11).getX(),(int)p.get(11).getY());

    			arr4[0] = new Point((int)p.get(12).getX(),(int)p.get(12).getY());
    			arr4[1] = new Point((int)p.get(13).getX(),(int)p.get(13).getY());

    			
    			arr5[0] = new Point((int)p.get(14).getX(),(int)p.get(14).getY());
    			arr5[1] = new Point((int)p.get(15).getX(),(int)p.get(15).getY());
    			
    			ships[0] = new Ship(arr1,"Aircraft Carrier");
    			ships[1] = new Ship(arr2,"Battleship");
    			ships[2] = new Ship(arr3,"Cruiser");
    			ships[3] = new Ship(arr4,"Destroyer");
    			ships[4] = new Ship(arr5,"Destroyer");
    	}
    		
    	
    	
    
    
    



}