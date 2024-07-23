package fr.tondeuse.tondeuse.util;

import fr.tondeuse.tondeuse.model.Tondeuse;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class TondeuseFileWriter implements ItemWriter<Tondeuse> {

    @Override
    public void write(Chunk<? extends Tondeuse> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output_test.txt", true))) {
            for (Tondeuse tondeuse : items) {
                writer.write(tondeuse.getX() + " " + tondeuse.getY() + " " + tondeuse.getOrientation());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
