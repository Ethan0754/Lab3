import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSet {
    private String filePath;
    List<List<String>> data = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(DataSet.class.getName());


    public DataSet(String filePath) {
        this.filePath = filePath;
        readData();
    }

    public String getFilePath() {
        return filePath;
    }

    public List<List<String>> getData() {
        return data;
    }

    private void readData() {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                data.add(List.of(nextLine)); // Convert array to list
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV file", e);
        }
    }

}
