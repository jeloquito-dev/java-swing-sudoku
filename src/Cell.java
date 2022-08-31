import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Cell extends JButton {

    private int row;
    private int column;
    private String value;

    public Cell(String value, int row, int column) {
        this.row = row;
        this.column = column;
        this.value = value;

        setProperties();
    }

    private void setProperties() {
        this.setText(this.value);
        this.setBackground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void highlightBackground() {
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void highlightSelected() {
        this.setBackground(Color.CYAN);
    }

    public void removeHighlightedBackground() {
        this.setBackground(Color.WHITE);
    }

    public void highlightText() {
        this.setForeground(Color.RED);
    }

    public void removeTextHighlight() {
        this.setForeground(Color.BLACK);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}



