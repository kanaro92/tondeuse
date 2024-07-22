package fr.tondeuse.tondeuse.util;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class TondeuseFileReader implements ItemReader<String> {

    private BufferedReader reader;

    @Override
    public String read() throws Exception {
        if (reader == null) {
            try {
                reader = new BufferedReader(new FileReader("input.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File not found: input.txt", e);
            }
        }

        String line = reader.readLine();
        if (line == null) {
            reader.close();
        }
        return line;
    }
}
