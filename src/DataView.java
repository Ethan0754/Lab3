import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataView extends JPanel {

    // Title variables
    private JPanel titlePanel;
    private JLabel titleText;

    // holds table and details table
    private TablePanel tablePanel;

    // bottom two data representations
    private JPanel statsPanel;
    private JPanel chartPanel;

    // gui container panels
    private JPanel bottomHalf;
    private JPanel thirdRow;

    // filter variables
    private JCheckBox[] filterBoxes;
    private String[] filters = {"2025", "2024", "2023", "2022", "2021", "2020", "Days w/ Decreasing Debt", "Days w/ Increasing Debt"};
    private final int numDateFilters = 6;

    // sort variables
    private JComboBox<String> sortDropDown;
    private String[] sorts = {"Date", "Date Descending", "Debt Held by Public Increasing", "Debt Held by Public Decreasing", "Intragovernmental Holdings Increasing",
    "Intragovernmental Holdings Decreasing", "Total Public Debt Increasing", "Total Public Debt Decreasing"};


    private DataSet dataSet;
    private List<List<String>> filteredData;

    private Map<String, Dimension> dimensions = new HashMap<>() {{
        put("Main Panel", new Dimension(1200, 1000)); //main
        put("Title Panel", new Dimension(1200, 100)); //top

        put("Table Panel", new Dimension(599, 600)); //same panel, just encapsulated
        put("Scroll Panel", new Dimension(599, 550));

        put("Details Panel", new Dimension(599, 550)); //populates info when click
    }};

    public DataView(String filePath) {
        this.dataSet = new DataSet(filePath);
        this.filteredData = dataSet.getData();


        // DataView config
        setPreferredSize(dimensions.get("Main Panel"));
        setBackground(Color.WHITE);


        // Create Table Panel Class/JPanel
        tablePanel = new TablePanel(dataSet.getData());


        /*#################Title####################*/
        // Title Panel config
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setPreferredSize(dimensions.get("Title Panel"));

        // Title Text config
        titleText = new JLabel();
        titleText.setFont(new Font("Serif", Font.PLAIN, 30));
        titleText.setText("US Govt Debt");
        titlePanel.add(titleText);
        /*##########################################*/

        // Sort JTable
        sortDropDown = new JComboBox<>(sorts);
        sortDropDown.setSelectedIndex(1); // change filter to how data is initially sorted
        titlePanel.add(sortDropDown);
        sortDropDown.addActionListener(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            switch (sortDropDown.getSelectedIndex()) {
                case 0: // Date (ascending)
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        String dateString = row.get(0).replaceAll("[\\p{C}]", "");
                        return LocalDate.parse(dateString, formatter);
                    }));
                    break;

                case 1: // Date Descending
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        String dateString = row.get(0).replaceAll("[\\p{C}]", "");
                        return LocalDate.parse(dateString, formatter);
                    }).reversed());
                    break;

                case 2: // Debt Held by Public Increasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(1).replaceAll("[\\p{C}]", ""));
                    }));
                    break;

                case 3: // Debt Held by Public Decreasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(1).replaceAll("[\\p{C}]", ""));
                    }).reversed());
                    break;

                case 4: // Intragovernmental Holdings Increasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(2).replaceAll("[\\p{C}]", ""));
                    }));
                    break;

                case 5: // Intragovernmental Holdings Decreasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(2).replaceAll("[\\p{C}]", ""));
                    }).reversed());
                    break;

                case 6: // Total Public Debt Increasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(3).replaceAll("[\\p{C}]", ""));
                    }));
                    break;

                case 7: // Total Public Debt Decreasing
                    Collections.sort(filteredData, Comparator.comparing((List<String> row) -> {
                        return Double.parseDouble(row.get(3).replaceAll("[\\p{C}]", ""));
                    }).reversed());
                    break;

                default:
                    throw new IllegalArgumentException("Invalid sort option selected.");
            }
            updateData(filteredData);
        });

        /*#############Filter############*/
        // create a new jcheckbox for each filter with an actionlistener and update filterStates

        Map<String, Boolean> filterStates = new HashMap<>();//Create a Map to store filter states
        filterBoxes = new JCheckBox[filters.length];

        for (int i = 0; i < filters.length; i++) {
            filterBoxes[i] = new JCheckBox(filters[i]);
            titlePanel.add(filterBoxes[i]); // Add to GUI

            filterStates.put(filters[i], false); // Default to false (unselected)
            filterBoxes[i].addActionListener(e -> {
                JCheckBox box = (JCheckBox) e.getSource();
                filterStates.put(box.getText(), box.isSelected()); // Update the state in the map

                // Check each filter state to filter the data
                List<List<String>> data = filteredData;

                // Get the list of active year filters
                List<Integer> activeYears = new ArrayList<>();
                for (int j = 0; j < numDateFilters; j++) { // Only iterate through year filters
                    if (filterStates.get(filters[j])) {
                        activeYears.add(Integer.parseInt(filters[j])); // Add active year to the list
                    }
                }


                // Filter the data to include rows where the year matches any active year filter
                if (!activeYears.isEmpty()) {
                    data = data.stream()
                            .filter(row -> {
                                String dateString = row.get(0).replaceAll("[\\p{C}]", "");
                                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                return activeYears.contains(date.getYear()); // Check if the year is in the active years list
                            })
                            .collect(Collectors.toCollection(ArrayList::new)); // Collect the filtered rows into a mutable list
                }
                // Update the table with the filtered data
                updateData(data);
            });
        }







        // Stats Panel Config
        statsPanel = new JPanel();
        statsPanel.setBackground(Color.RED);

        // Chart Panel Config
        chartPanel = new JPanel();
        chartPanel.setBackground(Color.BLUE);


        /*###########GUI Organization##############*/
        setLayout(new BorderLayout());


        thirdRow = new JPanel(); //container panel for the bottom two dataviews
        thirdRow.setLayout(new GridLayout(1, 2));

        thirdRow.add(statsPanel);
        thirdRow.add(chartPanel);

        bottomHalf = new JPanel(); //container panel all four of the dataviews
        bottomHalf.setLayout(new GridLayout(2, 1));

        bottomHalf.add(tablePanel);
        bottomHalf.add(thirdRow);


        add(titlePanel, BorderLayout.PAGE_START); //add title panel to top
        add(bottomHalf); //add container panel
        /*#########################################*/
    }

    public void updateData(List<List<String>> data) {

        tablePanel.setModel(tablePanel.setTableData(data, List.of(0, 3)));
        tablePanel.setData(data);
        this.filteredData = dataSet.getData();
    }



}

