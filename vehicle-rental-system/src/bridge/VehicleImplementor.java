package bridge;

/**
 * IMPLEMENTATION interface of the Bridge Pattern.
 *
 * Represents the "vehicle type" side of the bridge (Car, Motorcycle, SUV).
 * It only knows how to give its own daily rate and its own name.
 *
 * It knows NOTHING about rental plans (Daily/Weekly/Monthly). That is the
 * whole point of the Bridge Pattern: the vehicle type (implementation) and
 * the rental plan (abstraction) are completely independent of each other.
 */
public interface VehicleImplementor {

    double getDailyRate();

    String getVehicleTypeName();
}
