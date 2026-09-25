package service;

import adapter.CarAdapter;
import adapter.CarRentalProvider;
import adapter.MotorcycleAdapter;
import adapter.MotorcycleRentalProvider;
import adapter.SUVAdapter;
import adapter.SUVRentalProvider;
import adapter.Vehicle;
import bridge.CarImplementor;
import bridge.DailyRental;
import bridge.MonthlyRental;
import bridge.MotorcycleImplementor;
import bridge.RentalPlan;
import bridge.SUVImplementor;
import bridge.VehicleImplementor;
import bridge.WeeklyRental;
import model.RentalResult;

/**
 * Main business logic class of the application.
 *
 * This class is the CLIENT of the Adapter Pattern: it only talks to the
 * Vehicle interface (adapter.Vehicle) to get vehicle display information,
 * never to the concrete providers directly.
 *
 * It is also where the Bridge Pattern is put to work: it creates a
 * VehicleImplementor (implementation) and a RentalPlan (abstraction) and
 * combines them to calculate the price.
 */
public class RentalService {

    public RentalResult calculateRental(String vehicleType, String planType, int duration,
                                         String customerName, boolean confirm) {

        // ---- ADAPTER PATTERN: get vehicle info through the common Vehicle interface ----
        Vehicle vehicle = getVehicleFromAdapter(vehicleType);

        // ---- BRIDGE PATTERN: connect a rental plan (abstraction) with a vehicle type (implementation) ----
        VehicleImplementor vehicleImplementor = getVehicleImplementor(vehicleType);
        RentalPlan rentalPlan = getRentalPlan(planType, vehicleImplementor);

        double pricePerPeriod = rentalPlan.getPricePerPeriod();
        double totalPrice = rentalPlan.calculateTotalPrice(duration);

        RentalResult result = new RentalResult();
        result.setCustomerName(customerName);
        result.setVehicleName(vehicle.getVehicleName());
        result.setVehicleBrand(vehicle.getBrand());
        result.setVehicleCategory(vehicle.getCategory());
        result.setPlanName(rentalPlan.getPlanName());
        result.setDuration(duration);
        result.setPricePerPeriod(pricePerPeriod);
        result.setTotalPrice(totalPrice);
        result.setStatus(confirm ? "Rental confirmed successfully" : "Rental calculated successfully");

        return result;
    }

    // Uses the Adapter Pattern: each case wraps a different, incompatible
    // provider with its own adapter and returns it as a common Vehicle.
    private Vehicle getVehicleFromAdapter(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return new CarAdapter(new CarRentalProvider());
            case "motorcycle":
                return new MotorcycleAdapter(new MotorcycleRentalProvider());
            case "suv":
                return new SUVAdapter(new SUVRentalProvider());
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

    // Uses the Bridge Pattern: picks the concrete implementation (vehicle type).
    private VehicleImplementor getVehicleImplementor(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return new CarImplementor();
            case "motorcycle":
                return new MotorcycleImplementor();
            case "suv":
                return new SUVImplementor();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

    // Uses the Bridge Pattern: picks the refined abstraction (rental plan)
    // and connects it to the vehicle implementor received as a parameter.
    private RentalPlan getRentalPlan(String planType, VehicleImplementor vehicleImplementor) {
        switch (planType.toLowerCase()) {
            case "daily":
                return new DailyRental(vehicleImplementor);
            case "weekly":
                return new WeeklyRental(vehicleImplementor);
            case "monthly":
                return new MonthlyRental(vehicleImplementor);
            default:
                throw new IllegalArgumentException("Unknown rental plan: " + planType);
        }
    }
}
