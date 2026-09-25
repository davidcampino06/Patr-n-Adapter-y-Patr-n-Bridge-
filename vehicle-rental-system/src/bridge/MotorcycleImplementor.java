package bridge;

/**
 * CONCRETE IMPLEMENTATION of the Bridge Pattern.
 * Provides the base daily rate for a Motorcycle.
 */
public class MotorcycleImplementor implements VehicleImplementor {

    private static final double BASE_DAILY_RATE = 40000;

    @Override
    public double getDailyRate() {
        return BASE_DAILY_RATE;
    }

    @Override
    public String getVehicleTypeName() {
        return "Motorcycle";
    }
}
