package com.amazon;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CSVWriterTest {
    Random rand = new Random();

    public record Application(UUID id, String name, Integer age, String created_at) {};

    public static void main(String[] args) {
        CSVWriterTest writer = new CSVWriterTest();
        writer.testWritingInLoop();
    }

    private void testWritingInLoop() {
        final File outpuFile = new File("/Users/abhijeetsrivastava/Downloads/testDIR/testFile.csv");
        try(final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outpuFile),Charset.defaultCharset())) {
            var builder = new StatefulBeanToCsvBuilder<Application>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();
            for(int i = 1, j = 5; i <= 50; i += j ) {
                List<Application> applications = fetchNextApplications(i, i+j);
                builder.write(applications);
            }

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Application> fetchNextApplications(int i, int j) {
        List<Application> applications = new ArrayList<>();
        while (i++ <= j) {
            Application app = new Application(
                    UUID.randomUUID(),
                    String.format("Employee %d", i),
                    rand.nextInt(25, 42),
                    "2023-08-11"
                    );
            applications.add(app);
        }
        return applications;
    }
}
