package fr.gallonemilien.speed;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public enum SpeedEnum {
    KMH(0,"km/h"), BPS(1,"b/s"), MPH(2,"mph");

    private int choice;
    private String name;

    SpeedEnum(int choice, String name) {
        this.choice = choice;
        this.name = name;
    }

    private static double parseDouble(double d) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.0", symbols);
        return Double.parseDouble(df.format(d));
    }

    public static String getDisplaySpeed(int type, double speed) {
        SpeedEnum typeE = getFromChoice(type);
        if(typeE == BPS) {
            return parseDouble(speed)+" "+typeE.name;
        }
        if(typeE == MPH) {
            return parseDouble(speed * 2.23694)+" "+typeE.name;
        }
        if(typeE == KMH) {
            return parseDouble(speed * 3.6)+" "+typeE.name;
        }
        return "";
    }

    static SpeedEnum getFromChoice(int choice) {
        return switch (choice) {
            case 1 -> BPS;
            case 2 -> MPH;
            default -> KMH;
        };
    }
}