import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SudokuGrid extends JPanel {

    List<Quadrant> quadrants;

    public SudokuGrid(int[][] integerArray) {

        this.setLayout(new GridLayout(3,3, 2, 3));
        this.setBackground(Color.BLACK);
        construct(integerArray);
    }

    private void construct(int[][] integerArray) {
        this.quadrants = initializeQuadrants();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                addToQuadrant(x, y, integerArray[x][y]);
            }
        }
        quadrants.forEach(this::add);
    }

    private List<Quadrant> initializeQuadrants() {

        List<Quadrant> quadrantList = new ArrayList<>();
        for (int no_of_quadrant = 1; no_of_quadrant <= 9; no_of_quadrant++) {
            quadrantList.add(new Quadrant(no_of_quadrant));
        }

        quadrantList.forEach(q -> {
            q.setQuadrantsOnTheSameColumn(identifySameColumnQuadrants(q.getNumber(), quadrantList));
            q.setQuadrantsOnTheSameRow(identifySameRowQuadrants(q.getNumber(), quadrantList));
        });

        return quadrantList;
    }

    private void addToQuadrant(int x, int y, int value) {
        int row = x + 1;
        int column = y + 1;

        String number = String.valueOf(value);
        Cell cell = new Cell(number, row, column);
        Quadrant quadrant = identifyQuadrant(cell);
        quadrant.addCell(cell);

        addListeners(quadrant, cell);
    }

    private void addListeners(Quadrant quadrant, Cell cell) {

        //Button Click Listener
        cell.addActionListener(e -> {
            performAction(quadrant, cell, quadrant.addHighlightOnQuadrant(), Cell::highlightBackground);
            DisplayGrid.activeCell = cell;
        });

        //Remove Focus Listener
        cell.addFocusListener((ButtonFocusListenerInterface) e-> {
            performAction(quadrant, cell, quadrant.removeHighlightOnQuadrant(), Cell::removeHighlightedBackground);
        });

    }

    private void performAction(Quadrant quadrant, Cell cell, Runnable runnable, Consumer<Cell> cellConsumer) {
        runnable.run();
        quadrant.getQuadrantsOnTheSameColumn().forEach(q -> {
            q.getAllCellsInSameColumn(cell.getColumn()).forEach(cellConsumer);
        });

        quadrant.getQuadrantsOnTheSameRow().forEach(q -> {
            q.getAllCellsInSameRow(cell.getRow()).forEach(cellConsumer);
        });
    }

    private Quadrant identifyQuadrant(Cell cell) {

        int row = cell.getRow();
        int column = cell.getColumn();

        QuadrantIdentifier quadrantIdentifier = (q1, q2, q3, var) -> {
            if (var >= 1 && var <= 3) {
                return q1;
            } else if (var >= 3 && var <= 6) {
                return q2;
            } else if (var >= 7 && var <= 9) {
                return q3;
            } throw new RuntimeException("No quadrant");
        };

        Quadrant left = quadrantIdentifier.identify(quadrants.get(0), quadrants.get(3), quadrants.get(6), row);
        Quadrant mid = quadrantIdentifier.identify(quadrants.get(1), quadrants.get(4),quadrants.get(7), row);
        Quadrant right = quadrantIdentifier.identify(quadrants.get(2), quadrants.get(5), quadrants.get(8), row);

        return quadrantIdentifier.identify(left, mid, right, column);
    }

    private List<Quadrant> identifySameRowQuadrants(int number, List<Quadrant> quadrants) {
        switch (number) {
            case 1:
            case 2:
            case 3:
                //return 1st, 2nd and 3rd
                return Arrays.asList(quadrants.get(0), quadrants.get(1), quadrants.get(2));
            case 4:
            case 5:
            case 6:
                //return 4th, 5th and 6th
                return Arrays.asList(quadrants.get(3), quadrants.get(4), quadrants.get(5));
            case 7:
            case 8:
            case 9:
                //return 7th, 8th and 9th
                return Arrays.asList(quadrants.get(6), quadrants.get(7), quadrants.get(8));
            default:
                throw new RuntimeException("No quadrants");
        }
    }

    private List<Quadrant> identifySameColumnQuadrants(int number, List<Quadrant> quadrants) {
        switch (number) {
            case 1:
            case 4:
            case 7:
                //return 1st, 4th and 7th
                return Arrays.asList(quadrants.get(0),quadrants.get(3), quadrants.get(6));
            case 2:
            case 5:
            case 8:
                //return 2nd, 5th and 8th
                return Arrays.asList(quadrants.get(1), quadrants.get(4), quadrants.get(7));
            case 3:
            case 6:
            case 9:
                //return 3rd, 6th and 9th
                return Arrays.asList(quadrants.get(2), quadrants.get(5), quadrants.get(8));
            default:
                throw new RuntimeException("No quadrants");
        }
    }
}
