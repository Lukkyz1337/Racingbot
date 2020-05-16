package de.lukkyz.srdc4j.users;

import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class NameStyle {

    //TODO // FIXME: 13.04.2020 

    private String style;
    private Map<String, String> color;
    @SerializedName("color-from")
    private Map<String, String> colorFrom;
    @SerializedName("color-to")
    private Map<String, String> colorTo;

    public Color getColor() {

        ArrayList<Color> c = new ArrayList<>();

        if (color != null) {
            c.addAll(color.values().stream().map(s -> new Color(Integer.parseInt(s.substring(1), 16))).collect(Collectors.toList()));
        }

        ArrayList<Color> cf = new ArrayList<>();

        if (colorFrom != null) {
            cf.addAll(colorFrom.values().stream().map(s -> new Color(Integer.parseInt(s.substring(1), 16))).collect(Collectors.toList()));
        }

        ArrayList<Color> ct = new ArrayList<>();

        if (colorTo != null) {
            ct.addAll(colorFrom.values().stream().map(s -> new Color(Integer.parseInt(s.substring(1), 16))).collect(Collectors.toList()));
        }

        ArrayList<Color> allColors = new ArrayList<>();

        allColors.addAll(c);
        allColors.addAll(cf);
        allColors.addAll(ct);

        int r = 0, g = 0, b = 0;

        for (Color col : allColors) {

            r += col.getRed();
            g += col.getGreen();
            b += col.getBlue();

        }

        return new Color(r / allColors.size(), g / allColors.size(), b / allColors.size());

    }

    public String getStyle() {
        return style;
    }
}
