import javax.swing.*;
import javax.swing.border.Border;
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

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Panel(), BorderLayout.NORTH);
        panel.add(new SudokuGrid(test), BorderLayout.CENTER);
        panel.add(new DisplayGrid(), BorderLayout.SOUTH);

        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setTitle("Sample");
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }
}
