import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        int[][] test = new int[][] {
                {1,2,3,4,5,6,7,8,9},
                {0,8,7,6,5,4,3,2,1},
                {1,2,3,4,5,6,7,8,9},
                {9,8,7,6,5,4,3,2,1},
                {1,2,3,4,5,6,7,8,9},
                {9,8,7,6,5,4,0,2,1},
                {1,2,3,4,5,6,7,8,9},
                {9,8,7,6,5,4,3,2,1},
                {1,2,3,4,5,6,7,8,9}
        };

        int[][] test1 = new int[][] {
                {0,0,0,0,0,0,2,0,0},
                {0,8,0,0,0,7,0,9,0},
                {6,0,2,0,0,0,5,0,0},
                {0,7,0,0,6,0,0,0,0},
                {0,0,0,9,0,1,0,0,0},
                {0,0,0,0,2,0,0,4,0},
                {0,0,5,0,0,0,6,0,3},
                {0,9,0,4,0,0,0,7,0},
                {0,0,6,0,0,0,0,0,0}
        };

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Panel(), BorderLayout.NORTH);

        PuzzleGrid puzzleGrid = new PuzzleGrid(test1);
        panel.add(puzzleGrid, BorderLayout.CENTER);
        panel.add(new DisplayGrid(puzzleGrid), BorderLayout.SOUTH);

        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setTitle("Sample");
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }
}
