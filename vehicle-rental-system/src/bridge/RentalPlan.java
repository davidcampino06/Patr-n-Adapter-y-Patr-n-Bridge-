package bridge;

/**
 * ABSTRACTION of the Bridge Pattern.
 *
 * Represents the "rental modality" side of the bridge (Daily/Weekly/Monthly).
 * It does NOT implement vehicle pricing itself. Instead, it holds a
 * reference to a VehicleImplementor and delegates to it.
 *
 * This composition relationship (RentalPlan HAS-A VehicleImplementor) is
 * the core of the Bridge Pattern: any RentalPlan can be combined with any
 * VehicleImplementor, and both can change independently.
 */
public abstract class RentalPlan {

    protected VehicleImplementor vehicleImplementor;

    public RentalPlan(VehicleImplementor vehicleImplementor) {
        this.vehicleImplementor = vehicleImplementor;
    }

    public abstract double getPricePerPeriod();

    public abstract double calculateTotalPrice(int duration);

    public abstract String getPlanName();

    public String getVehicleTypeName() {
        return vehicleImplementor.getVehicleTypeName();
    }
}
