import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import com.opencsv.CSVWriter;

public class Results {

    public static void WriteToCSV(List<String[]> dataSet)
    {
        File file = new File(getTimeStamp());
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeAll(dataSet);

            writer.close();



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private static String getTimeStamp()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);

    }

}