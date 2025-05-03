package kkkombinator.DAO.DTO;

import kkkombinator.DAO.Entities.Color;
import org.springframework.stereotype.Service;

@Service
public class ColorMapper {
    public Color getColorFromString(String color) {
        if (color.equals("BLACK")) {
            return Color.BLACK;
        } else if (color.equals("WHITE")) {
            return Color.WHITE;
        }
        return null;
    }
    public String getStringFromColor(Color color) {
        if (color.equals(Color.BLACK)) {
            return "BLACK";
        } else if (color.equals(Color.WHITE)) {
            return "WHITE";
        }
        return null;
    }
}
