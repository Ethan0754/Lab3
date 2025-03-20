import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DataChartPanel extends ChartPanel {

    private TimeSeries series;
    private TimeSeriesCollection dataset;

    public DataChartPanel() {
        super(null); // Call the parent constructor with null (no chart initially)

        // Initialize an empty dataset
        series = new TimeSeries("Total Public Debt Outstanding Over Time");
        dataset = new TimeSeriesCollection(series);

        // Create the chart with the empty dataset
        JFreeChart chart = createChart(dataset);

        // Set the chart in the ChartPanel
        this.setChart(chart);
    }

    public void updateChart(List<List<String>> data) {
        // Clear the existing data in the series
        series.clear();

        // Parse the new data and add it to the TimeSeries
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (List<String> row : data) {
            try {
                // Parse the date from the first column
                java.util.Date date = dateFormat.parse(row.get(0).replaceAll("[\\p{C}]", ""));
                // Parse the public debt oustanding value from the fourth column
                double pdebtout = Double.parseDouble(row.get(3));
                // Add the data point to the series
                series.add(new Day(date), pdebtout);
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + row.get(0));
            } catch (NumberFormatException e) {
                System.err.println("Error parsing public debt oustanding value: " + row.get(3));
            }
        }
        // Notify the series that the data has changed
        series.fireSeriesChanged();
    }

    private JFreeChart createChart(XYDataset dataset) {
        // Create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Total Public Debt Outstanding", // Chart title
                "Date", // X-axis label
                "Total Public Debt Outstanding", // Y-axis label
                dataset, // Dataset
                true, // Include legend
                true, // Include tooltips
                false // Include URLs
        );

        // Customize the chart (optional)
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Format the x-axis to show dates properly
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        return chart;
    }
}