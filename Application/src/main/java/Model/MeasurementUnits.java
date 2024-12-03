package Model;

public enum MeasurementUnits {
    grams,
    cups,
    tablespoons,
    teaspoons,
    ounces,
    pounds,
    milliliters,
    liters,
    pieces,
    pinch;

    public static String[] getAll(){
        return (grams.toString() + "," + cups.toString() + "," + tablespoons.toString() + "," + teaspoons.toString()+ "," +
                ounces.toString() + "," + pounds.toString() + "," + milliliters.toString() + "," + liters.toString() + "," +
                pieces.toString() + "," + pinch.toString()).split(",");
    }
}


