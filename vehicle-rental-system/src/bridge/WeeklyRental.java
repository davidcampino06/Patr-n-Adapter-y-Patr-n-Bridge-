package bridge;

/**
 * REFINED ABSTRACTION of the Bridge Pattern.
 *
 * Charges a weekly price with a small discount compared to 7 daily rates.
 * "duration" here means number of weeks.
 */
public class WeeklyRental extends RentalPlan {

    private static final double WEEKLY_DISCOUNT = 0.9; // 10% off

    public WeeklyRental(VehicleImplementor vehicleImplementor) {
        super(vehicleImplementor);
    }

    @Override
    public double getPricePerPeriod() {
        return vehicleImplementor.getDailyRate() * 7 * WEEKLY_DISCOUNT;
    }

    @Override
    public double calculateTotalPrice(int duration) {
        return getPricePerPeriod() * duration;
    }

    @Override
    public String getPlanName() {
        return "Weekly";
    }
}
