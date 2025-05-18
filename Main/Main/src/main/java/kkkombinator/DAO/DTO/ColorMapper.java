package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Color;
import org.springframework.stereotype.Service;

@Service
public class ColorMapper {
    public Color getColorFromString(String color) throws IllegalArgumentException{
        return Color.valueOf(color);
    }

    public String getStringFromColor(Color color) {

        return switch (color) {
            case Color.BLACK -> "BLACK";
            case Color.WHITE -> "WHITE";
        };
    }
}
