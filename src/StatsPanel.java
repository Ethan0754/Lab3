import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel {
    private JLabel stdDevTotal;
    private JLabel stdDevIntragovernmental;
    private JLabel stdDevPublic;
    private JPanel textPanel;

    StatsPanel() {
        setLayout(new GridLayout(3, 1));



        stdDevTotal = new JLabel();
        stdDevIntragovernmental = new JLabel();
        stdDevPublic = new JLabel();

        stdDevTotal.setFont(new Font("Arial", Font.PLAIN, 20));
        stdDevIntragovernmental.setFont(new Font("Arial", Font.PLAIN, 20));
        stdDevPublic.setFont(new Font("Arial", Font.PLAIN, 20));




        add(stdDevTotal);
        add(stdDevIntragovernmental);
        add(stdDevPublic, BorderLayout.WEST);
    }

    public double calculateStandardDeviation(double[] array) {

        // get the sum of array
        double sum = 0.0;
        for (double i : array) {
            sum += i;
        }

        // get the mean of array
        int length = array.length;
        double mean = sum / length;

        // calculate the standard deviation
        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    public static double[][] convertColumnsToDouble(List<List<String>> data, int[] columns) {
        if (data == null || data.isEmpty()) {
            return new double[0][0];
        }

        int numRows = data.size();
        int numCols = columns.length;
        double[][] result = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            List<String> row = data.get(i);
            for (int j = 0; j < numCols; j++) {
                int colIndex = columns[j];
                if (colIndex < row.size()) {
                    try {
                        result[i][j] = Double.parseDouble(row.get(colIndex));
                    } catch (NumberFormatException e) {
                        // Handle the case where the conversion fails
                        result[i][j] = 0.0; // or some default value
                    }
                } else {
                    // Handle the case where the column index is out of bounds
                    result[i][j] = 0.0; // or some default value
                }
            }
        }

        return result;
    }

    public void updateStats(List<List<String>> data) {
        stdDevTotal.setText("Std Dev of Total Public Debt: " +
                calculateStandardDeviation(convertColumnsToDouble(data, new int[]{1, 2, 3})[2]) + "\n"); // get stdDev for Total Public Debt Outstanding
        stdDevIntragovernmental.setText("Std Dev of Intragovernmental Holdings: " +
                calculateStandardDeviation(convertColumnsToDouble(data, new int[]{1, 2, 3})[1]) + "\n");
        stdDevPublic.setText("Std Dev of Debt held by Public: " +
                calculateStandardDeviation(convertColumnsToDouble(data, new int[]{1, 2, 3})[0]));

    }

}
