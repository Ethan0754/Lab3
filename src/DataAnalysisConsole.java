import java.util.Arrays;

public class DataAnalysisConsole {
    public static void main(String[] args) {
        String filePath = "DebtPenny_20200307_20250306.csv";
        DataSet dataSet = new DataSet(filePath);
        String[] columnNames = {"Record Date", "Debt Held by the Public", "Intragovernmental Holdings", "Total Public Debt Outstanding"};
        System.out.println(Arrays.toString(columnNames));

        // print first and tenth row data
        dataSet.getData().stream()
                .findFirst()
                .ifPresent(row -> System.out.println("First: " + row));

        dataSet.getData().stream()
                .skip(9)  // Skip first 9 rows
                .findFirst()  // Get the 10th row
                .ifPresent(row -> System.out.println("Tenth: " + row));


        // print the number of entries
        System.out.println("# of entries: " + dataSet.getData().size());

    }
}
