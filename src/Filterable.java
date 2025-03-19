import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface Filterable {
    String[] columnNames = {"Record Date", "Debt Held by the Public", "Intragovernmental Holdings", "Total Public Debt Outstanding"};


    default DefaultTableModel setTableData(List<List<String>> data, List<Integer> columns) {
        // Convert List<List<String>> to Object[][] using streams, but only include specified columns
        Object[][] tableData = data.stream()
                .map(row -> {
                    // Create a new array with only the specified columns
                    Object[] filteredRow = new Object[columns.size()];
                    for (int i = 0; i < columns.size(); i++) {
                        int columnIndex = columns.get(i);
                        if (columnIndex >= 0 && columnIndex < row.size()) {
                            filteredRow[i] = row.get(columnIndex);
                        } else {
                            filteredRow[i] = ""; // if integer listed in columns param is outside of applicable columns
                        }
                    }
                    return filteredRow;
                })
                .toArray(Object[][]::new);

        // Create a new array of column names based on the specified columns
        String[] filteredColumnNames = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            int columnIndex = columns.get(i);
            if (columnIndex >= 0 && columnIndex < columnNames.length) {
                filteredColumnNames[i] = columnNames[columnIndex];
            } else {
                filteredColumnNames[i] = "Column " + columnIndex; // if column listed in columnNames param is outside of applicable columns
            }
        }

        // Set model for JTable
        DefaultTableModel model = new DefaultTableModel(tableData, filteredColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all fields read-only
                return false;
            }
        };
        return model;
    }

    void setModel(DefaultTableModel model);
}
