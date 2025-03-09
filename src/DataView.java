import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataView extends JPanel{
    private JPanel tablePanel;
    private JTable table;
    private JPanel statsPanel;
    private JPanel chartPanel;
    private JPanel detailsPanel;

    private DataSet dataSet;

    public DataView(String filePath) {
        this.dataSet = new DataSet(filePath);

        // this config
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GRAY);


        // tablepanel config
        tablePanel = new JPanel();
        tablePanel.setBackground(Color.GREEN);
        tablePanel.setPreferredSize(new Dimension(400, 300));
        this.add(tablePanel);


        //jtable config
        table = new JTable();
        String[] columnNames = {"Date", "Personal Debt", "Govt Debt", "Total"};


        // Populate JTable
        setTableData(columnNames);

        // Add table inside a JScrollPane for scrolling support
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(380, 280));
        tablePanel.add(scrollPane);
    }

    private void setTableData(String[] columnNames) {
        // Convert List<List<String>> to Object[][]
        Object[][] tableData = new Object[dataSet.getData().size()][columnNames.length];

        for (int i = 0; i < dataSet.getData().size(); i++) {
            tableData[i] = dataSet.getData().get(i).toArray(); // Convert List<String> to Object[]
        }

        // Set model for JTable
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        table.setModel(model);
    }


}
