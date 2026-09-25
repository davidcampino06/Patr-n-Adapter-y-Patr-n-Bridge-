package bridge;

/**
 * REFINED ABSTRACTION of the Bridge Pattern.
 *
 * Charges the vehicle's daily rate directly, once per day of the
 * rental duration. "duration" here means number of days.
 */
public class DailyRental extends RentalPlan {

    public DailyRental(VehicleImplementor vehicleImplementor) {
        super(vehicleImplementor);
    }

    @Override
    public double getPricePerPeriod() {
        return vehicleImplementor.getDailyRate();
    }

    @Override
    public double calculateTotalPrice(int duration) {
        return getPricePerPeriod() * duration;
    }

    @Override
    public String getPlanName() {
        return "Daily";
    }
}
