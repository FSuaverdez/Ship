/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Point;
import java.util.*;
/**
 *
 * @author JMDC
 */
public class BattleShip implements GameConstants {
    
    private static final int NUM_PLAYERS = 2;
    private static final int HUMAN = 0;
    private static final int COMPUTER = 1;
    
    private UserInterface ui;
    private ArrayList <Player> players;
    
    public BattleShip()
    {
        ui = new UserInterface();
        players = new ArrayList <Player>();
        players.add(new Human(ui));
        players.add(new Computer(ui));
        
    }
    
    public void play()
    {
        int turn, opponent, temp;
        char feedback;
        boolean hit;
        Point p;
        
        ui.displayFeedback("Game started.");
        
        turn = 0;
        opponent = 1;
        players.get(HUMAN).positionShips();
        players.get(COMPUTER).positionShips();
        
        while(!players.get(turn).loser()){
            if(turn == HUMAN )
            {
                ui.displayFeedback("Human's turn to attack.\n\tHuman's Offensive Grid.");
                players.get(HUMAN).displayOffensiveGrid();
            }
            else
            {
                ui.displayFeedback("Computer's turn to attack.\n\tHuman's Deffensive Grid.");
                players.get(HUMAN).displayDefensiveGrid();
            }
            
            p = players.get(turn).takeTurn();
            feedback = players.get(opponent).checkSite(p);
            hit = players.get(turn).recordFeedback(p, feedback);

            if(turn == COMPUTER ){
                players.get(COMPUTER).setHit(hit);
            }
            if(!hit || players.get(opponent).loser())
            {

                temp = turn;
                turn = opponent;
                opponent = temp;
            }
        
            if(!DEBUG)
                ui.pause();
        }
        
        ui.displayFeedback("Human's offensive grid:");
        players.get(HUMAN).displayOffensiveGrid();
        ui.displayFeedback("Human's Defensive grid:");
        players.get(HUMAN).displayDefensiveGrid();
        ui.displayFeedback("*** " + players.get(opponent).name() + " WINS! ***");
        
        System.exit(1);
    }
    
    public static void main(String[] args)
    {
        int argCtr=0;
        String ok = "";
        for(int i=0;i<args.length;i++)
        {
            ok+=args[i];
            System.out.println(args[i]);
            argCtr+=1;
        }
        
        if(argCtr<1)
        {
            BattleShip game;
            game = new BattleShip();
            game.play();
        }else if(ok.equals("oldgame")){
            System.out.println("Loading Game.");
        }
    }
    
}
