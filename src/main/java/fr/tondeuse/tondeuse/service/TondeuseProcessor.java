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
            // Ligne des dimensions de la grille
            String[] coordinates = line.split(" ");
            maxX = Integer.parseInt(coordinates[0]);
            maxY = Integer.parseInt(coordinates[1]);
            tondeuseService = new TondeuseService(maxX, maxY);
            return null;
        } else if (line.matches("\\d+ \\d+ [NESW]")) {
            // Ligne de position initiale de la tondeuse
            String[] parts = line.split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            char orientation = parts[2].charAt(0);
            lastTondeuse = new Tondeuse(x, y, orientation);
            return null;
        } else if (line.matches("[GAD]+")) {
            // Ligne des instructions de la tondeuse
            Tondeuse tondeuse = tondeuseService.executeInstructions(lastTondeuse, line);
            lastTondeuse = tondeuse;
            return tondeuse;
        } else {
            // Ligne non reconnue
            throw new IllegalArgumentException("Ligne non reconnue : " + line);
        }
    }

    private Tondeuse lastTondeuse;

    @BeforeStep
    public void saveLastTondeuse(StepExecution stepExecution) {
        this.lastTondeuse = null;
    }
}
