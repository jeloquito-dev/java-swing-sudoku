import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.function.Consumer;

public class Cell extends JButton {

    private int row;
    private int column;
    private String value;

    public Cell(String value, int row, int column) {
        this.row = row;
        this.column = column;
        this.value = value;

        this.setText(this.value);
        this.setBackground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBorder(BorderFactory.createEmptyBorder());

    }

    private void validateValue() {
        String current = this.getText();

        if (null != current && current.length() == 1) {
            this.value = this.getText();

            if (null != this.value && !"".equalsIgnoreCase(this.value)) {
                System.out.println("Current value: " + this.getText());
            }
        } else if (current.length() > 1){
            SwingUtilities.invokeLater(() -> {
               this.setText(this.value);
            });
        }
    }

    public void highlightBackground() {
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void removeHighlightedBackground() {
        this.setBackground(Color.WHITE);
    }

    public void highlightText() {
        this.setForeground(Color.red);
    }

    public void removeTextHighlight() {
        this.setForeground(Color.BLACK);
    }

    private Quadrant identifyQuadrant(List<Quadrant> quadrants) {

        QuadrantIdentifier quadrantIdentifier = (q1, q2, q3, var) -> {
            if (var >= 1 && var <= 3) {
                return q1;
            } else if (var >= 3 && var <= 6) {
                return q2;
            } else if (var >= 7 && var <= 9) {
                return q3;
            } throw new RuntimeException("No quadrant");
        };

        Quadrant left = quadrantIdentifier.identify(quadrants.get(0), quadrants.get(3), quadrants.get(6), this.row);
        Quadrant mid = quadrantIdentifier.identify(quadrants.get(1), quadrants.get(4),quadrants.get(7), this.row);
        Quadrant right = quadrantIdentifier.identify(quadrants.get(2), quadrants.get(5), quadrants.get(8), this.row);

        return quadrantIdentifier.identify(left, mid, right, this.column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}



