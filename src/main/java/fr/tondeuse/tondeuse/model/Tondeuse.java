package fr.tondeuse.tondeuse.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tondeuse {
    private int x;
    private int y;
    private char orientation;

}
