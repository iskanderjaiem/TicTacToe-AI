package screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board2 {
	 
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[3][3];

    	
    
    public Board2() {
    }

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }

    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            // System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                //  System.out.println("O Row or Column win");
                return true;
            }
        }

        return false;
    }

    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<Point>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public static Point intToPoint (int x){
    	switch (x){
    	case 1:{return new Point(2,0);}
    	case 2:{return new Point(2,1);}
    	case 3:{return new Point(2,2);}
    	case 4:{return new Point(1,0);}
    	case 5:{return new Point(1,1);}
    	case 6:{return new Point(1,2);}
    	case 7:{return new Point(0,0);}
    	case 8:{return new Point(0,1);}
    	case 9:{return new Point(0,2);}
    	}
    	return null;
    }
    
    public boolean placeAMove(Point point, int player) {
    	//if( board[point.x][point.y] == 0){
    		board[point.x][point.y] = player;   //player = 1 for X, 2 for O
    		return true;
    	/*}
    	else{
    		return false;
    	}*/
    }

    public List<Point> getAvailablePoints() {
		return availablePoints;
	}

	public void setAvailablePoints(List<Point> availablePoints) {
		this.availablePoints = availablePoints;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScores.get(best).point;
    }

    void takeHumanInput() {
        System.out.println("Your move: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        Point point = new Point(x, y);
        placeAMove(point, 2); 
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    List<PointsAndScores> rootsChildrenScores;
 
    public void callMinimax(int depth, int turn){
        rootsChildrenScores = new ArrayList<PointsAndScores>();
        minimax(depth, turn);
    }
    
    public int minimax(int depth, int turn) {

        if (hasXWon()) return +1;
        if (hasOWon()) return -1;

        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0; 

        List<Integer> scores = new ArrayList<Integer>(); 

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);  

            if (turn == 1) { //X's turn select the highest from below minimax() call
                placeAMove(point, 1); 
                int currentScore = minimax(depth + 1, 2);
                scores.add(currentScore);

                if (depth == 0) 
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                
            } else if (turn == 2) {//O's turn select the lowest from below minimax() call
                placeAMove(point, 2); 
                scores.add(minimax(depth + 1, 1));
            }
            board[point.x][point.y] = 0; //Reset this point
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }
}
