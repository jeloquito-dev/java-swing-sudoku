import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Quadrant extends JPanel {

    private int number;
    private List<Cell> cells;

    private List<Quadrant> quadrantsOnTheSameColumn;

    private List<Quadrant> quadrantsOnTheSameRow;

    public Quadrant(int number) {
        this.number = number;
        this.cells = new ArrayList<>();

        this.setLayout(new GridLayout(3,3,1,1));
        this.setBackground(Color.BLACK);
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
        this.add(cell);
    }

    private void highlightAllCells() {
        cells.forEach(Cell::highlightBackground);
    }

    private void removeHighlightOnAllCells() {
        cells.forEach(Cell::removeHighlightedBackground);
    }

    public List<Cell> getAllCellsInSameRow(int row) {
        return cells.stream().filter(r -> r.getRow() == row).collect(Collectors.toList());
    }

    public List<Cell> getAllCellsInSameColumn(int column) {
        return cells.stream().filter(c -> c.getColumn() == column).collect(Collectors.toList());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Quadrant> getQuadrantsOnTheSameColumn() {
        return quadrantsOnTheSameColumn;
    }

    public void setQuadrantsOnTheSameColumn(List<Quadrant> quadrantsOnTheSameColumn) {
        this.quadrantsOnTheSameColumn = quadrantsOnTheSameColumn;
    }

    public List<Quadrant> getQuadrantsOnTheSameRow() {
        return quadrantsOnTheSameRow;
    }

    public void setQuadrantsOnTheSameRow(List<Quadrant> quadrantsOnTheSameRow) {
        this.quadrantsOnTheSameRow = quadrantsOnTheSameRow;
    }

    public Runnable addHighlightOnQuadrant() {
        return this::highlightAllCells;
    }
    public Runnable removeHighlightOnQuadrant() {
        return this::removeHighlightOnAllCells;
    }
}
