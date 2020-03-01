//BSIT - 2A
//Angelo A. Baclaan
//Christopher P. Napoles
//De Guzman Allen Miguel
//Frannz S. Suaverdez
//Franco Miguel Arambulo

import java.awt.Point;
import java.util.ArrayList;

public class Human extends Player{
    
    UserInterface ui;
    String name;
    ArrayList <Point> points = new ArrayList <Point>();
    
    public Human(UserInterface ui)
    {
        super(ui,"Human");
        this.ui = ui;
        this.name = "Human";
    }
    
    public Human(UserInterface ui, String name)
    {
        super(ui,name);
        this.ui = ui;
        this.name = name;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getOrientation()
    {
        int orientation;
        orientation = ui.getOrientation();
        return orientation;
    }

    /**
     *
     * @return
     */
    @Override
    public Point getStartCoordinate()
    {
        Point p;
        p = ui.getCoordinates("Enter the coordinate to build a ship (X,Y): ");
        return p;
    }

    /**
     *
     * @return
     */
    @Override
    public Point takeTurn()
    {
        Point p;
        do{
            
            p = ui.getCoordinates("Enter attack coordinates (X,Y): ");
            if(points.contains(p)){
                System.out.println("You already attacked that coordinates. Please Try Again.");
            }
            
        }while(points.contains(p));

        points.add(p);

        return p;
    }
}
