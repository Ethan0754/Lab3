import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel implements ScrollPane, Filterable{
    private JScrollPane scrollPane;
    private JTable mainTable;
    private List<List<String>> data;
    private DetailsPanel detailsPanel;


    public TablePanel(List<List<String>> data) {
        this.data = data;
        setPreferredSize(new Dimension(1200, 550));

        /*#################Main Table########################*/
        mainTable = new JTable();
        for (int i = 0; i < mainTable.getColumnCount(); i++) {
            mainTable.getColumnModel().getColumn(i).setPreferredWidth(300);
        }

        // Populate JTable
        String[] columnNames = {"Record Date", "Debt Held by the Public", "Intragovernmental Holdings", "Total Public Debt Outstanding"};
        mainTable.setModel(setTableData(data, List.of(0, 3)));

        // Add the table to a scrollPane for scrolling purposes
        scrollPane = new JScrollPane(mainTable);
        formatScrollPane(scrollPane);
        /*###################################################*/


        // details panel
        detailsPanel = new DetailsPanel();


        /*###########GUI Organization##############*/
        setLayout(new GridLayout(1,2));
        add(scrollPane);
        add(detailsPanel);

        // Main Table actionlistener
        mainTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = mainTable.getSelectedRow(); //get selected row
                System.out.println(selectedRow);
                if (selectedRow >= 0) { //make sure a row was selected
                    updateDetailsPanel(selectedRow);
                }
            }
        });
    }

    @Override
    public void setModel(DefaultTableModel model) {

        mainTable.setModel(model);
    }

    public void setData(List<List<String>> data) {
        this.data = data;
        refreshTable();
    }


    private void refreshTable() {
        // Update the mainTable with the new data
        String[] columnNames = {"Record Date", "Debt Held by the Public", "Intragovernmental Holdings", "Total Public Debt Outstanding"};
        mainTable.setModel(setTableData(data, List.of(0, 3)));

        // Repaint the table to reflect the changes
        mainTable.revalidate();
        mainTable.repaint();
    }

    private void updateDetailsPanel(int selectedRow) {
        // Create 2D array for filling table
        List<List<String>> rowData = new ArrayList<>();
        List<String> row = new ArrayList<>();
        // Populate 2D array with values from selected row
        for (int i = 0; i < data.get(0).size(); i++) {
            row.add(data.get(selectedRow).get(i)); // Add data from the selected row
        }
        rowData.add(row);

        // Call setTableData to create DefaultTableModel that will show up in detailsTable
        detailsPanel.setModel(setTableData(rowData, List.of(0, 1, 2, 3)));
    }

}


