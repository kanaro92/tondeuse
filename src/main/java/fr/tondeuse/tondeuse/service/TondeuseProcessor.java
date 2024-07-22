package fr.tondeuse.tondeuse.service;

import fr.tondeuse.tondeuse.model.Tondeuse;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TondeuseProcessor implements ItemProcessor<String, Tondeuse> {

    private int maxX;
    private int maxY;
    private TondeuseService tondeuseService;

    @Override
    public Tondeuse process(String line) throws Exception {
        if (line.matches("\\d+ \\d+")) {
            String[] coordinates = line.split(" ");
            maxX = Integer.parseInt(coordinates[0]);
            maxY = Integer.parseInt(coordinates[1]);
            tondeuseService = new TondeuseService(maxX, maxY);
            return null;
        } else if (line.matches("\\d+ \\d+ [NESW]")) {
            String[] parts = line.split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            char orientation = parts[2].charAt(0);
            return new Tondeuse(x, y, orientation);
        } else {
            Tondeuse tondeuse = tondeuseService.executeInstructions(lastTondeuse, line);
            lastTondeuse = tondeuse;  //save last tondeuse instructions
            return tondeuse;
        }
    }

    private Tondeuse lastTondeuse;

    @BeforeStep
    public void saveLastTondeuse(StepExecution stepExecution) {
        this.lastTondeuse = null;
    }
}
