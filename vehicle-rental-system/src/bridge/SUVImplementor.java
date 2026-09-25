package bridge;

/**
 * CONCRETE IMPLEMENTATION of the Bridge Pattern.
 * Provides the base daily rate for an SUV.
 */
public class SUVImplementor implements VehicleImplementor {

    private static final double BASE_DAILY_RATE = 120000;

    @Override
    public double getDailyRate() {
        return BASE_DAILY_RATE;
    }

    @Override
    public String getVehicleTypeName() {
        return "SUV";
    }
}
