import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class Cell extends JButton {

    private int row;
    private int column;
    private String value;

    private boolean isModifiable;

    public Cell(String value, int row, int column) {
        this.row = row;
        this.column = column;
        this.isModifiable = value.equalsIgnoreCase("0");
        this.value = (this.isModifiable) ? "" : value;

        setProperties();
    }

    private void setProperties() {
        this.setText(this.value);
        this.setBackground(Color.WHITE);

        Font font = new Font("Arial", Font.BOLD, 18);

        if (this.isModifiable) {
            this.setFont(font);
        } else {
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            this.setFont(font.deriveFont(attributes));
        }

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

    public boolean isModifiable() {
        return isModifiable;
    }
}



