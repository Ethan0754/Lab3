import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DetailsPanel extends JPanel implements ScrollPane, Filterable{
    private JTable detailsTable;
    private JScrollPane detailsScrollPane;


    public DetailsPanel() {
        detailsTable = new JTable();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 550));

        //Details Scroll Pane Config
        detailsScrollPane = new JScrollPane(detailsTable);
        formatScrollPane(detailsScrollPane);
        add(detailsScrollPane);


        for (int i = 0; i < detailsTable.getColumnCount(); i++) {
            detailsTable.getColumnModel().getColumn(i).setPreferredWidth(300);
        }
    }

    @Override
    public void setModel(DefaultTableModel model) {
        detailsTable.setModel(model);
    }
}
