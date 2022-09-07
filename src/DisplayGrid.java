import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DisplayGrid extends JPanel {

    private PuzzleGrid puzzleGrid;

    public DisplayGrid(PuzzleGrid puzzleGrid) {

        this.puzzleGrid = puzzleGrid;
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        createButtons();
    }

    private void createButtons() {

        //Create an integer list from 1 to 10 then loop
        IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList())
                .forEach(number -> {

                    //1-9 as Number Buttons and 10 for Delete Button
                    String text = (number != 10) ? String.valueOf(number) : "X";
                    this.add(createButton(text));

                });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(new Font("Arial", Font.BOLD, 18));

        button.addActionListener(e -> {
            if (text.equals("X")) {
                updateActiveCell("");
            } else {
                updateActiveCell(text);
            }
        });

        return button;
    }

    private void updateActiveCell(String text) {
        //Get current active cell and validate if it is modifiable
        Cell currentCell = this.puzzleGrid.getActiveCell();

        if (currentCell.isModifiable()) {
            //Remove all highlighted text
            removeHighlightOnText();

            //Update the value of the cell

            int column = currentCell.getColumn();
            int row = currentCell.getRow();
            this.puzzleGrid.updateCellValue(row, column, text);

            //Validate puzzle grid for duplicates
            highlightOnTextOfDuplicateValues();
        }
    }

    private void highlightOnTextOfDuplicateValues() {
        this.puzzleGrid.getQuadrants().forEach(q -> applyToCells(q.getCells(), this::checkDuplicateValuesForCell));
    }

    private void removeHighlightOnText() {
        this.puzzleGrid.getQuadrants().forEach(q -> applyToCells(q.getCells(), Cell::removeTextHighlight));
    }

    private void checkDuplicateValuesForCell(Cell cell) {

        Quadrant quadrant = this.puzzleGrid.getQuadrant(cell);
        String value = cell.getValue();

        int number = quadrant.getNumber();
        int column = cell.getColumn();
        int row = cell.getRow();

        //Check if current cell is not equal to argument variable cell
        //then validate if the value is duplicate and then highlight it
        Consumer<Cell> highlightText = c -> {
            if (!cell.equals(c)) {
                if (c.getValue().equalsIgnoreCase(value)) {
                    c.highlightText();
                }
            }
        };

        //Highlights text of duplicate values within the quadrant
        applyToCells(quadrant.getCells(), highlightText);

        //Highlights text of duplicate values within the same column
        this.puzzleGrid.getColumnGroup(number)
                .forEach(q -> applyToCells(q.getAllCellsInSameColumn(column), highlightText));

        //Highlights text of duplicate values within the same row
        this.puzzleGrid.getRowGroup(number)
                .forEach(q -> applyToCells(q.getAllCellsInSameRow(row), highlightText));
    }

    private void applyToCells(List<Cell> cells, Consumer<Cell> consumer) {
        cells.forEach(consumer);
    }

}
