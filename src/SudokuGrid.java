import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SudokuGrid extends JPanel {

    static Cell activeCell;
    List<Quadrant> quadrants;

    public SudokuGrid(int[][] integerArray) {

        this.quadrants = initializeQuadrants();
        this.setLayout(new GridLayout(3,3, 2, 3));
        this.setBackground(Color.BLACK);
        construct(integerArray);

    }

    private List<Quadrant> initializeQuadrants() {
        return new ArrayList<>() {{
            for (int no_of_quadrant = 1; no_of_quadrant <= 9; no_of_quadrant++) {
                add(new Quadrant(no_of_quadrant));
            }
        }};
    }

    private List<Quadrant> getRowGroupings(int number) {

        List<Quadrant> upperGroup = createQuadrantList(quadrants.get(0), quadrants.get(1), quadrants.get(2));
        List<Quadrant> middleGroup = createQuadrantList(quadrants.get(3), quadrants.get(4), quadrants.get(5));
        List<Quadrant> bottomGroup = createQuadrantList(quadrants.get(6),quadrants.get(7),quadrants.get(8));

        IdentifierInterface<List<Quadrant>> rowGroupIdentifier = (g1, g2, g3, var) -> {
            if (var >= 1 && var <= 3) {
                return g1;
            } else if (var >= 3 && var <= 6) {
                return g2;
            } else if (var >= 7 && var <= 9) {
                return g3;
            }
            throw new RuntimeException("No quadrant");
        };

        return rowGroupIdentifier.identify(upperGroup, middleGroup, bottomGroup, number);
    }

    private List<Quadrant> getColumnGroupings(int number) {

        List<Quadrant> leftGroup = createQuadrantList(quadrants.get(0), quadrants.get(3), quadrants.get(6));
        List<Quadrant> centerGroup = createQuadrantList(quadrants.get(1), quadrants.get(4), quadrants.get(7));
        List<Quadrant> rightGroup = createQuadrantList(quadrants.get(2),quadrants.get(5),quadrants.get(8));

        IdentifierInterface<List<Quadrant>> columnGroupIdentifier = (g1, g2, g3, var) -> {
            if (var == 1 || var == 4 || var == 7) {
                return g1;
            } else if (var == 2 || var == 5 || var == 8) {
                return g2;
            } else if (var == 3 || var == 6 || var == 9) {
                return g3;
            }
            throw new RuntimeException("No quadrant");
        };

        return columnGroupIdentifier.identify(leftGroup, centerGroup, rightGroup, number);
    }

    private List<Quadrant> createQuadrantList(Quadrant... input) {
        return new ArrayList<>() {{
            this.addAll(Arrays.asList(input));
        }};
    }

    private void construct(int[][] integerArray) {

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                addToQuadrant(x, y, integerArray[x][y]);
            }
        }

        quadrants.forEach(this::add);
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

    private Quadrant identifyQuadrant(Cell cell) {

        int row = cell.getRow();
        int column = cell.getColumn();

        IdentifierInterface<Quadrant> quadrantIdentifier = (q1, q2, q3, var) -> {
            if (var >= 1 && var <= 3) {
                return q1;
            } else if (var >= 3 && var <= 6) {
                return q2;
            } else if (var >= 7 && var <= 9) {
                return q3;
            } throw new RuntimeException("No quadrant");
        };

        //return the
        Quadrant left = quadrantIdentifier.identify(quadrants.get(0), quadrants.get(3), quadrants.get(6), row);
        Quadrant mid = quadrantIdentifier.identify(quadrants.get(1), quadrants.get(4),quadrants.get(7), row);
        Quadrant right = quadrantIdentifier.identify(quadrants.get(2), quadrants.get(5), quadrants.get(8), row);

        //return the
        return quadrantIdentifier.identify(left, mid, right, column);
    }

    private void addListeners(Quadrant quadrant, Cell cell) {

        //Button Click Listener
        cell.addActionListener(e -> {
            //Highlight
            performAction(quadrant, cell, quadrant.addHighlightOnQuadrant(), Cell::highlightBackground);

            cell.highlightSelected();

            activeCell = cell;
        });

        //Remove Focus Listener
        cell.addFocusListener((ButtonFocusListenerInterface) e-> {
            performAction(quadrant, cell, quadrant.removeHighlightOnQuadrant(), Cell::removeHighlightedBackground);
        });

    }

    private void performAction(Quadrant quadrant, Cell cell, Runnable runnable, Consumer<Cell> consumer) {
        runnable.run();

        getColumnGroupings(quadrant.getNumber()).forEach(q -> {
            q.getAllCellsInSameColumn(cell.getColumn()).forEach(consumer);
        });

        getRowGroupings(quadrant.getNumber()).forEach(q -> {
            q.getAllCellsInSameRow(cell.getRow()).forEach(consumer);
        });
    }



}
