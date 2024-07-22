package fr.tondeuse.tondeuse.service;

import fr.tondeuse.tondeuse.model.Tondeuse;

public class TondeuseService {
    private int maxX;
    private int maxY;

    public TondeuseService(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Tondeuse executeInstructions(Tondeuse tondeuse, String instructions) {
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'G' -> tournerGauche(tondeuse);
                case 'D' -> tournerDroite(tondeuse);
                case 'A' -> avancer(tondeuse);
            }
        }
        return tondeuse;
    }

    private void tournerGauche(Tondeuse tondeuse) {
        switch (tondeuse.getOrientation()) {
            case 'N' -> tondeuse.setOrientation('W');
            case 'W' -> tondeuse.setOrientation('S');
            case 'S' -> tondeuse.setOrientation('E');
            case 'E' -> tondeuse.setOrientation('N');
        }
    }

    private void tournerDroite(Tondeuse tondeuse) {
        switch (tondeuse.getOrientation()) {
            case 'N' -> tondeuse.setOrientation('E');
            case 'E' -> tondeuse.setOrientation('S');
            case 'S' -> tondeuse.setOrientation('W');
            case 'W' -> tondeuse.setOrientation('N');
        }
    }

    private void avancer(Tondeuse tondeuse) {
        int x = tondeuse.getX();
        int y = tondeuse.getY();
        switch (tondeuse.getOrientation()) {
            case 'N' -> {
                if (y < maxY) tondeuse.setY(y + 1);
            }
            case 'E' -> {
                if (x < maxX) tondeuse.setX(x + 1);
            }
            case 'S' -> {
                if (y > 0) tondeuse.setY(y - 1);
            }
            case 'W' -> {
                if (x > 0) tondeuse.setX(x - 1);
            }
        }
    }
}
