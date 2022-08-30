import javax.swing.*;
import java.awt.*;

public class DisplayGrid extends JPanel {

    static Cell activeCell;

    public DisplayGrid() {

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
        activeCell.setText(String.valueOf(text));
        activeCell = new Cell("", 0, 0);
    }


}
