package adapter;

/**
 * ADAPTER of the Adapter Pattern.
 *
 * Wraps a CarRentalProvider (the adaptee) and translates its incompatible
 * getCar() method into the common Vehicle interface, so the rest of the
 * system can use it exactly like any other vehicle.
 */
public class CarAdapter implements Vehicle {

    private CarRentalProvider carRentalProvider;

    public CarAdapter(CarRentalProvider carRentalProvider) {
        this.carRentalProvider = carRentalProvider;
    }

    @Override
    public String getVehicleName() {
        CarData carData = carRentalProvider.getCar();
        return carData.carModelName;
    }

    @Override
    public String getBrand() {
        CarData carData = carRentalProvider.getCar();
        return carData.carBrand;
    }

    @Override
    public String getCategory() {
        CarData carData = carRentalProvider.getCar();
        return carData.carSegment;
    }
}
