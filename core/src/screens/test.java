package screens;

import java.util.*;

class Point {

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

class PointsAndScores {

    int score;
    Point point;

    PointsAndScores(int score, Point point) {
        this.score = score;
        this.point = point;
    }
}



public class test {

    public static void main(String[] args) {
        Board b = new Board();
        Random rand = new Random();
        
        b.displayBoard();

        System.out.println("Who's gonna move first? (1)Computer (2)User: ");
        int choice = b.scan.nextInt();
        if(choice == 1){
            Point p = new Point(rand.nextInt(3), rand.nextInt(3));
            b.placeAMove(p, 1);
            b.displayBoard();
        }
        
        while (!b.isGameOver()) {
            System.out.println("Your move: ");
            Point userMove = Board.intToPoint(b.scan.nextInt());

            b.placeAMove(userMove, 2); //2 for O and O is the user
            b.displayBoard();
            if (b.isGameOver()) {
                break;
            } 
            b.callMinimax(0, 1);
            for (PointsAndScores pas : b.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }
            b.placeAMove(b.returnBestMove(), 1);
            b.displayBoard();
        }
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win! This is not going to get printed.");
        } else {
            System.out.println("It's a draw!");
        }
    }
}