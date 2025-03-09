import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataView extends JPanel{

    private JPanel titlePanel; //1a
    private JLabel titleText; //1b

    private JPanel tablePanel; //2a
    private JTable table;//2b

    private JPanel statsPanel; //3a

    private JPanel chartPanel; //4a

    private JPanel detailsPanel; //5a

    private DataSet dataSet;

    private Map<String, Dimension> dimensions = new HashMap<>() {{
        put("Main Panel", new Dimension(1200, 1000));
        put("Title Panel", new Dimension(1200, 100));
        put("Table Panel", new Dimension(1200, 600));
        put("Scroll Panel", new Dimension(1200, 550));
    }};

    public DataView(String filePath) {
        this.dataSet = new DataSet(filePath);

        // this config
        setPreferredSize(dimensions.get("Main Panel"));
        setBackground(Color.WHITE);

        // title config
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setPreferredSize(dimensions.get("Title Panel"));
        this.add(titlePanel);

        // titleText config
        titleText = new JLabel();
        titleText.setFont(new Font("Serif", Font.PLAIN, 30));
        titleText.setText("US Govt Debt");
        titlePanel.add(titleText);

        // tablepanel config
        tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setPreferredSize(dimensions.get("Table Panel"));
        this.add(tablePanel);


        //jtable config
        table = new JTable();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(300);
        }

        // Populate JTable
        String[] columnNames = {"Record Date", "Debt Held by the Public", "Intragovernmental Holdings", "Total Public Debt Outstanding"};
        setTableData(columnNames);


        // Add table inside a JScrollPane for scrolling support
        // scroll pane config
        JScrollPane scrollPane = new JScrollPane(table);//no add needed for jtable because this
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(dimensions.get("Scroll Panel"));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));


        // scroll bar config
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(150, 150, 150); // Scrollbar color
            }
        });

        tablePanel.add(scrollPane);//add scroll pane to tablePanel
    }

    private void setTableData(String[] columnNames) {
        // Convert List<List<String>> to Object[][] using streams
        Object[][] tableData = dataSet.getData().stream()
                .map(List::toArray) // Convert each List<String> to Object[]
                .toArray(Object[][]::new);

        // Set model for JTable
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        table.setModel(model);
    }


}
