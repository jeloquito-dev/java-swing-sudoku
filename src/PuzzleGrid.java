import Interface.ButtonFocusListenerInterface;
import Interface.IdentifierInterface;
import Enums.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class PuzzleGrid extends JPanel {

    private Cell activeCell;
    private List<Quadrant> quadrants;
    private HashMap<Integer, List<Quadrant>> rowGroupings = new HashMap<>();
    private HashMap<Integer, List<Quadrant>> columnGroupings = new HashMap<>();

    public PuzzleGrid(int[][] integerArray) {

        this.quadrants = initializeQuadrants();
        this.setLayout(new GridLayout(3,3, 2, 3));
        this.setBackground(Color.BLACK);

        construct(integerArray);
    }

    private List<Quadrant> initializeQuadrants() {

        HashMap<String, List<Quadrant>> quadrantGroups = new HashMap<>() {{
            EnumSet.allOf(QuadrantGroups.class).forEach(group -> put(group.name(), new ArrayList<>()));
        }};

        return new ArrayList<>() {{
            for (int number = 1; number <= 9; number++) {
                Quadrant quadrant = new Quadrant(number);

                addQuadrantToGroups(number, quadrant, quadrantGroups);
                add(quadrant);
            }
        }};
    }

    private void addQuadrantToGroups(int number, Quadrant quadrant, HashMap<String, List<Quadrant>> quadrantGroups) {

        List<Quadrant> rowGroup = getRowGroup(number, quadrantGroups);
        rowGroup.add(quadrant);
        this.rowGroupings.put(number, rowGroup);

        List<Quadrant> columnGroup = getColumnGroup(number, quadrantGroups);
        columnGroup.add(quadrant);
        this.columnGroupings.put(number, columnGroup);

    }

    private List<Quadrant> getRowGroup(int number, HashMap<String, List<Quadrant>> quadrantGroups) {

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

        return rowGroupIdentifier.identify(
                quadrantGroups.get(QuadrantGroups.UPPER.name()),
                quadrantGroups.get(QuadrantGroups.MID.name()),
                quadrantGroups.get(QuadrantGroups.BOTTOM.name()),
                number);
    }

    private List<Quadrant> getColumnGroup(int number, HashMap<String, List<Quadrant>> quadrantGroups) {

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

        return columnGroupIdentifier.identify(
                quadrantGroups.get(QuadrantGroups.LEFT.name()),
                quadrantGroups.get(QuadrantGroups.CENTER.name()),
                quadrantGroups.get(QuadrantGroups.RIGHT.name()),
                number);
    }

    private void construct(int[][] integerArray) {

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                addToQuadrant(x, y, integerArray[x][y]);
            }
        }

        this.quadrants.forEach(this::add);
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

    public Quadrant getQuadrant(Cell cell) {
        return identifyQuadrant(cell);
    }

    private Quadrant identifyQuadrant(Cell cell) {

        int row = cell.getRow();
        int column = cell.getColumn();

        IdentifierInterface<Quadrant> quadrantIdentifier = (q1, q2, q3, var) -> {
            if (var >= 1 && var <= 3) {
                return q1;
            } else if (var >= 4 && var <= 6) {
                return q2;
            } else if (var >= 7 && var <= 9) {
                return q3;
            } throw new RuntimeException("No quadrant");
        };

        //Identify the correct quadrant using the column value according to the output based on the left, mid, and right quadrant identifier
        return quadrantIdentifier.identify(
                //Identify the left quadrant on the row
                quadrantIdentifier.identify(quadrants.get(0), quadrants.get(3), quadrants.get(6), row),
                //Identify the middle quadrant on the row
                quadrantIdentifier.identify(quadrants.get(1), quadrants.get(4),quadrants.get(7), row),
                //Identify the right quadrant on the row
                quadrantIdentifier.identify(quadrants.get(2), quadrants.get(5), quadrants.get(8), row),
                column);
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

        columnGroupings.get(quadrant.getNumber()).forEach(q -> {
            q.getAllCellsInSameColumn(cell.getColumn()).forEach(consumer);
        });

        rowGroupings.get(quadrant.getNumber()).forEach(q -> {
            q.getAllCellsInSameRow(cell.getRow()).forEach(consumer);
        });
    }

    public Cell getActiveCell() {
        return activeCell;
    }

    public List<Quadrant> getQuadrants() {
        return this.quadrants;
    }

    public List<Quadrant> getColumnGroup(int number) {
        return this.columnGroupings.get(number);
    }

    public List<Quadrant> getRowGroup(int number) {
        return this.rowGroupings.get(number);
    }

    public void updateCellValue(int row, int column, String value) {
        this.getQuadrants().forEach(quadrant -> {
            quadrant.getCells().forEach(c -> {
                if (c.getRow() == row && c.getColumn() == column) {
                    c.setValue(value);
                }
            });
        });
    }
}
