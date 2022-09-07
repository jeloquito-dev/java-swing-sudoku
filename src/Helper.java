import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Helper {

    public static List<Integer> integerList(int start, int end) {
       return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    public static void applyToSameColumnAndRowCells(int number, int row, int column, Consumer<Cell> consumer) {

        PuzzleGrid.getColumnGroup(number).forEach(quadrant -> {
            quadrant.getAllCellsInSameColumn(column).forEach(consumer);
        });

        PuzzleGrid.getRowGroup(number).forEach(quadrant -> {
            quadrant.getAllCellsInSameRow(row).forEach(consumer);
        });

    }
}
