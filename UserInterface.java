/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.awt.Point;


/**
 *
 * @author JMDC
 */

public class UserInterface implements GameConstants{
    
    private static final String ERROR = "Invalid range (A to J, 0 to 9).";
    
    private BufferedReader in;
    
    public UserInterface()
    {
        in = new BufferedReader(new InputStreamReader(System.in), 1);
    }
    
    public String convertPoint(Point p)
    {
        StringBuffer location;
        
        location = new StringBuffer();
        location.append((char)('A' + p.getY()));
        location.append((int)p.getX());
        
        return location.toString();
    }
    
    public void displayFeedback(String feedback)
    {
        System.out.println(feedback);
    }
    
    public void displayGrid(char[][] grid)
    {
        for(int i = 0; i < grid.length; i++)
        {
            System.out.print("      ");
            for(int j = 0; j < grid.length; j++)
            {
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println((char)('A' + i));
        }
        System.out.println("      0 1 2 3 4 5 6 7 8 9");
    }
    
    public Point getCoordinates(String prompt)
    {
        int x, y;
        String line;
        
        while(true)
        {
            System.out.print(prompt);
            
            try
            {
                line = in.readLine();
                line = line.trim();
                
                if(line.equals("Q"))
                {
                    System.out.println("Bye :-)");
                    System.exit(1);
                }
                if(line.equals(""))
                {
                    continue;
                }
                
                y = 9 - ('J' - line.charAt(0));
                x = Integer.parseInt(line.substring(1,line.length()));
                
                if(0 <= y && y <= 9 && 0 <= x && x <= 9)
                    break;
                else
                    System.out.println(ERROR);
            }
            catch(IOException e)
            {
                System.out.println(ERROR + "1");
            }
            catch(NumberFormatException e)
            {
                System.out.println(ERROR +"2");
            }
        }
        return new Point(x, y);
    }
    
    public int getOrientation()
    {
        int orientation;
        
        System.out.println(HORIZONTAL + ". Horizontal");
        System.out.println(VERTICAL + ". Vertical");
        System.out.println(DIAGONAL_RIGHT + ". Diagonal right (down)");
        System.out.println(DIAGONAL_LEFT + ". Diagonal left (down)");
        
        while(true)
        {
            System.out.println("Enter orientation (" + HORIZONTAL + " - " + DIAGONAL_LEFT + "): ");
            try
            {
                orientation = Integer.parseInt(in.readLine());
                if(HORIZONTAL <= orientation && orientation <= DIAGONAL_LEFT)
                    return orientation;
            }
            catch(IOException e){}
        }
    }
    
    public void pause()
    {
        System.out.println("Please hit <Return> to continue.");
        
        try
        {
            in.readLine();
        }
        catch(IOException e){}
    }
    
    public static void main(String[] args)
    {
        char[][] grid =
        {
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
            {'A','A','A','A','A','A','A','A','A','A'},
        };
        
        UserInterface ui;
        Point coordinates;
        
        ui = new UserInterface();
        ui.displayGrid(grid);
        coordinates = ui.getCoordinates("Enter coordinates: ");
        System.out.println((int)coordinates.getX() + " " + (int)coordinates.getY());
    }
    
}
