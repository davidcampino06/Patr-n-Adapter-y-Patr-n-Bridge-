package adapter;

/**
 * ADAPTER of the Adapter Pattern.
 *
 * Wraps a MotorcycleRentalProvider (the adaptee) and translates its
 * incompatible getMotorcycle() method into the common Vehicle interface.
 */
public class MotorcycleAdapter implements Vehicle {

    private MotorcycleRentalProvider motorcycleRentalProvider;

    public MotorcycleAdapter(MotorcycleRentalProvider motorcycleRentalProvider) {
        this.motorcycleRentalProvider = motorcycleRentalProvider;
    }

    @Override
    public String getVehicleName() {
        MotorcycleData motorcycleData = motorcycleRentalProvider.getMotorcycle();
        return motorcycleData.modelName;
    }

    @Override
    public String getBrand() {
        MotorcycleData motorcycleData = motorcycleRentalProvider.getMotorcycle();
        return motorcycleData.manufacturer;
    }

    @Override
    public String getCategory() {
        MotorcycleData motorcycleData = motorcycleRentalProvider.getMotorcycle();
        return motorcycleData.motorcycleType;
    }
}
