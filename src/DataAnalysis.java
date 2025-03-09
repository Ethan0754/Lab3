import javax.swing.*;


public class DataAnalysis {
    public static void main(String[] args) {
        // get data from the filepath
        String filePath = "DebtPenny_20200307_20250306.csv";

        // create initial jframe
        JFrame frame = new JFrame("Data Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create master jpanel
        DataView panel = new DataView(filePath);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);


    }


}