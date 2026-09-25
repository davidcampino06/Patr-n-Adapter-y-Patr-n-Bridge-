package bridge;

/**
 * REFINED ABSTRACTION of the Bridge Pattern.
 *
 * Charges a monthly price with a bigger discount compared to 30 daily rates.
 * "duration" here means number of months.
 */
public class MonthlyRental extends RentalPlan {

    private static final double MONTHLY_DISCOUNT = 0.8; // 20% off

    public MonthlyRental(VehicleImplementor vehicleImplementor) {
        super(vehicleImplementor);
    }

    @Override
    public double getPricePerPeriod() {
        return vehicleImplementor.getDailyRate() * 30 * MONTHLY_DISCOUNT;
    }

    @Override
    public double calculateTotalPrice(int duration) {
        return getPricePerPeriod() * duration;
    }

    @Override
    public String getPlanName() {
        return "Monthly";
    }
}
