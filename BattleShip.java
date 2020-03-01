//BSIT - 2A
//Angelo A. Baclaan
//Christopher P. Napoles
//De Guzman Allen Miguel
//Frannz S. Suaverdez
//Franco Miguel Arambulo
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class BattleShip implements GameConstants {

    private static final int NUM_PLAYERS = 2;
    private static final int HUMAN = 0;
    private static final int COMPUTER = 1;

    private UserInterface ui;
    private ArrayList<Player> players;
    private static boolean load = false;

    public BattleShip() {
        ui = new UserInterface();
        players = new ArrayList<Player>();
        players.add(new Human(ui));
        players.add(new Computer(ui));

    }

    public void play() {
        int turn, opponent, temp;
        char feedback;
        boolean hit;
        Point p;

        ui.displayFeedback("Game started.");

        turn = 0;
        opponent = 1;
        if (load != true) {
            players.get(HUMAN).positionShips();
            players.get(COMPUTER).positionShips();
        }

        do {
            if (turn == HUMAN) {
                ui.displayFeedback("Human's turn to attack.\n\tHuman's Offensive Grid.");
                players.get(HUMAN).displayOffensiveGrid();
            } else {
                ui.displayFeedback("Computer's turn to attack.\n\tHuman's Defensive Grid.");
                players.get(HUMAN).displayDefensiveGrid();
            }

            p = players.get(turn).takeTurn();
            feedback = players.get(opponent).checkSite(p);
            hit = players.get(turn).recordFeedback(p, feedback);

            if (turn == COMPUTER) {
                players.get(COMPUTER).setHit(hit);

            } else {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("compDefensiveGrid.txt"));
                    char[][] tempDef = players.get(COMPUTER).getDefensiveGrid();
                    String tempDefString = "";
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            tempDefString += tempDef[i][j];
                        }
                    }

                    bw.write("" + tempDefString);
                    bw.close();
                    BufferedWriter bw2 = new BufferedWriter(new FileWriter("compOffensiveGrid.txt"));
                    char[][] tempOf = players.get(COMPUTER).getOffensiveGrid();
                    String tempOfString = "";

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            tempOfString += tempOf[i][j];
                        }
                    }

                    bw2.write("" + tempOfString);

                    bw2.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("humanDefensiveGrid.txt"));
                    char[][] tempDef = players.get(HUMAN).getDefensiveGrid();
                    String tempDefString = "";

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            tempDefString += tempDef[i][j];
                        }
                    }

                    bw.write("" + tempDefString);

                    bw.close();
                    BufferedWriter bw2 = new BufferedWriter(new FileWriter("humanOffensiveGrid.txt"));
                    char[][] tempOf = players.get(HUMAN).getOffensiveGrid();
                    String tempOfString = "";

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            tempOfString += tempOf[i][j];
                        }
                    }

                    bw2.write("" + tempOfString);
                    bw2.close();

                
                } catch (Exception e) {
                    // TODO: handle exception
                }


                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("compShips.txt"));
                    ArrayList <Point> tempPoint = players.get(COMPUTER).getShipPoints();
                    String humShipP = "";
                    
                    for (Point point : tempPoint) {
                        humShipP+=(int)point.getX()+" "+(int)point.getY() + " ";
                    }

                    bw.write(humShipP);
                    bw.close();

                } catch (Exception e) {
                    //TODO: handle exception
                }
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("humanShips.txt"));
                    ArrayList <Point> tempPoint = players.get(HUMAN).getShipPoints();
                    String humShipP = "";
                    
                    for (Point point : tempPoint) {
                        humShipP+=(int)point.getX()+" "+(int)point.getY() + " ";
                    }

                    bw.write(humShipP);
                    bw.close();

                } catch (Exception e) {
                    //TODO: handle exception
                }
            }

            if (!hit || players.get(opponent).loser()) {

                temp = turn;
                turn = opponent;
                opponent = temp;
            }

            if (!DEBUG)
                ui.pause();
        } while (!players.get(turn).loser());

        ui.displayFeedback("Human's offensive grid:");
        players.get(HUMAN).displayOffensiveGrid();
        ui.displayFeedback("Human's Defensive grid:");
        players.get(HUMAN).displayDefensiveGrid();
        ui.displayFeedback("*** " + players.get(opponent).name() + " WINS! ***");

        System.exit(1);
    }

    public static void main(String[] args) {
        int argCtr = 0;
        String ok = "";
        BattleShip game;
        game = new BattleShip();
        for (int i = 0; i < args.length; i++) {
            ok += args[i];
            System.out.println(args[i]);
            argCtr += 1;
        }

        if (argCtr < 1) {

            game.play();
        } else if (ok.equals("oldgame")) {
            load = true;
            System.out.println("Loading Game.");
            game.loadGame();

        }
    }

    public void loadGame() {
    	players.get(HUMAN).initialiseShips();
    	players.get(COMPUTER).initialiseShips();
        try {
            BufferedReader br = new BufferedReader(new FileReader("compDefensiveGrid.txt"));
            char[][] newDef = new char[10][10];
            int charNum;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    charNum = br.read();
                    newDef[i][j] = (char) charNum;
                }
            }
            players.get(COMPUTER).setDefensiveGrid(newDef);
            br.close();
            BufferedReader br2 = new BufferedReader(new FileReader("compOffensiveGrid.txt"));
            char[][] newOf = new char[10][10];
            int charNum2;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    charNum2 = br2.read();
                    newOf[i][j] = (char) charNum2;
                }
            }
            players.get(COMPUTER).setOffensiveGrid(newOf);
            br2.close();
            BufferedReader br3 = new BufferedReader(new FileReader("humanDefensiveGrid.txt"));
            char[][] newDef2 = new char[10][10];
            int charNum3;

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    charNum3 = br3.read();
                    newDef2[i][j] = (char) charNum3;
                }
            }

            players.get(HUMAN).setDefensiveGrid(newDef2);
            br3.close();
            BufferedReader br4 = new BufferedReader(new FileReader("humanOffensiveGrid.txt"));
            char[][] newOf2 = new char[10][10];
            int charNum4;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    charNum4 = br4.read();
                    newOf2[i][j] = (char) charNum4;
                }
            }
            players.get(HUMAN).setOffensiveGrid(newOf2);
            br4.close();



            
        } catch (Exception e) {
        }

        try {
            BufferedReader brR = new BufferedReader(new FileReader("compShips.txt"));

            String[] integersInString2 = brR.readLine().split(" ");
            int a3[] = new int[integersInString2.length];
            for (int i = 0; i < integersInString2.length; i++) {
                a3[i] = Integer.parseInt(integersInString2[i]);
            }
            int ctr = 0;
            int j =0;
            int[] odd = new int[16];
            int[] even = new int[16];
            for(ctr=0;ctr<a3.length;ctr++){
					even[j]=a3[ctr];
					ctr++;
					odd[j]=a3[ctr];
					j++;
            }
            j=0;
            ArrayList<Point> compShips = new ArrayList<Point>();

            for (int i : even) {
                compShips.add(new Point(i,odd[j++]));
			}

            players.get(COMPUTER).setShipPoints(compShips);
            brR.close();
        } catch (Exception e) {
        }
        try {
            BufferedReader brR = new BufferedReader(new FileReader("humanShips.txt"));

            String[] integersInString2 = brR.readLine().split(" ");
            int a3[] = new int[integersInString2.length];
            for (int i = 0; i < integersInString2.length; i++) {
                a3[i] = Integer.parseInt(integersInString2[i]);
            }
            int ctr = 0;
            int j =0;
            int[] odd = new int[16];
            int[] even = new int[16];
            for(ctr=0;ctr<a3.length;ctr++){
					even[j]=a3[ctr];
					ctr++;
					odd[j]=a3[ctr];
					j++;
            }
            j=0;
            ArrayList<Point> humanShips = new ArrayList<Point>();

            for (int i : even) {
                humanShips.add(new Point(i,odd[j++]));
			}

            players.get(HUMAN).setShipPoints(humanShips);
            brR.close();
        } catch (Exception e) {
        }
        players.get(COMPUTER).initialiseHits();
        players.get(HUMAN).initialiseHits();
        play();
    }

}
