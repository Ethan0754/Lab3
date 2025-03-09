import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) { //try to create csv reader from a filereader from a given filepath
            data = reader.readAll().stream() // Create a stream from the CSVReader
                    .map(List::of) // Convert each line (array) to a List
                    .collect(Collectors.toList());
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV file", e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public void printData() {
        data.forEach(System.out::println);
    }
}
