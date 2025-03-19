import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public interface ScrollPane {
    default void formatScrollPane(JScrollPane scrollPane) {
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(600, 550));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        // Scroll Bar config
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(150, 150, 150); // Scrollbar color
            }
        });
    }


}
