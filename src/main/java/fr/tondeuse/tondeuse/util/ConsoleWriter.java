package fr.tondeuse.tondeuse.util;

import fr.tondeuse.tondeuse.model.Tondeuse;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter implements ItemWriter<Tondeuse> {

    @Override
    public void write(Chunk<? extends Tondeuse> items) {
        for (Tondeuse tondeuse : items) {
            System.out.println(tondeuse.getX() + " " + tondeuse.getY() + " " + tondeuse.getOrientation());
        }
    }
}
