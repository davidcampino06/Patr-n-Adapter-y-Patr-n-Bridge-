package adapter;

/**
 * ADAPTEE of the Adapter Pattern.
 *
 * A third existing provider system, this time for SUVs.
 * Its method is called getSUV() and it returns an SUVData object with
 * yet another different set of field names (make, modelLabel, bodyStyle).
 */
public class SUVRentalProvider {

    public SUVData getSUV() {
        return new SUVData("Toyota", "Fortuner", "Full-Size SUV");
    }
}

/**
 * Simple data holder used only by SUVRentalProvider.
 */
class SUVData {
    String make;
    String modelLabel;
    String bodyStyle;

    public SUVData(String make, String modelLabel, String bodyStyle) {
        this.make = make;
        this.modelLabel = modelLabel;
        this.bodyStyle = bodyStyle;
    }
}
