package adapter;

/**
 * ADAPTEE of the Adapter Pattern.
 *
 * Another existing provider system, this time for motorcycles.
 * Its method is called getMotorcycle() and it returns a MotorcycleData
 * object with yet another set of field names. It does not follow the
 * same shape as CarRentalProvider or the Vehicle interface.
 */
public class MotorcycleRentalProvider {

    public MotorcycleData getMotorcycle() {
        return new MotorcycleData("Yamaha", "FZ 2.0", "Sport Motorcycle");
    }
}

/**
 * Simple data holder used only by MotorcycleRentalProvider.
 */
class MotorcycleData {
    String manufacturer;
    String modelName;
    String motorcycleType;

    public MotorcycleData(String manufacturer, String modelName, String motorcycleType) {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.motorcycleType = motorcycleType;
    }
}
