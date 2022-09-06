import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class DisplayGrid extends JPanel {

    private SudokuGrid sudokuGrid;
    public DisplayGrid(SudokuGrid sudokuGrid) {

        this.sudokuGrid = sudokuGrid;
        createNumberButtons();
        createDeleteButton();

        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);
    }

    private void createNumberButtons() {
        for(int number = 1; number <= 9; number++) {
            JButton numberButton = createButton(String.valueOf(number));

            numberButton.setBackground(Color.WHITE);
            numberButton.setFont(new Font("Arial", Font.BOLD, 18));

            String value = String.valueOf(number);

            numberButton.addActionListener(e -> {
                updateActiveCell(value);
            });

            this.add(numberButton);
        }
    }

    private void createDeleteButton() {
        JButton deleteButton = createButton("X");

        deleteButton.addActionListener(e -> {
            updateActiveCell("");
        });

        this.add(deleteButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(new Font("Arial", Font.BOLD, 18));

        return button;
    }

    private void updateActiveCell(String text) {
        Cell currentCell = this.sudokuGrid.getActiveCell();
        if(currentCell.isModifiable()) {

            removeHighlightOnText();

            String value = String.valueOf(text);

            this.sudokuGrid.updateCellValue(currentCell.getRow(), currentCell.getColumn(), value);

            if (highlightTextOfDuplicateValues(currentCell, value)) {
                currentCell.highlightText();
            }
        }
    }

    private boolean highlightTextOfDuplicateValues(Cell cell, String value) {
        Quadrant quadrant = this.sudokuGrid.getQuadrant(cell);

        AtomicBoolean hasDuplicate = new AtomicBoolean(false);
        int number = quadrant.getNumber();
        int column = cell.getColumn();
        int row = cell.getRow();

        Consumer<Cell> highlightText = c -> {
            if (c.getValue().equalsIgnoreCase(value)) {
                c.highlightText();
                if (!hasDuplicate.get()) {
                    hasDuplicate.set(true);
                }
            }
        };

        //Highlights text of duplicate values within the quadrant
        applyToCells(quadrant.getCells(), highlightText);

        //Highlights text of duplicate values within the same column
        this.sudokuGrid.getColumnGroup(number)
                .forEach(q -> applyToCells(q.getAllCellsInSameColumn(column), highlightText));

        //Highlights text of duplicate values within the same row
        this.sudokuGrid.getRowGroup(number)
                .forEach(q -> applyToCells(q.getAllCellsInSameRow(row), highlightText));

        return hasDuplicate.get();
    }

    private void removeHighlightOnText() {
        this.sudokuGrid.getQuadrants().forEach(q -> applyToCells(q.getCells(), Cell::removeTextHighlight));
    }

    private void applyToCells(List<Cell> cells, Consumer<Cell> consumer) {
        cells.forEach(consumer);
    }

}
