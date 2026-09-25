package adapter;

/**
 * TARGET interface of the Adapter Pattern.
 *
 * This is the common, unified interface that the rest of the system
 * (RentalService, controller, etc.) uses to work with ANY vehicle,
 * no matter which provider it originally came from.
 *
 * The client never talks to CarRentalProvider, MotorcycleRentalProvider
 * or SUVRentalProvider directly. It only talks to this interface.
 */
public interface Vehicle {

    String getVehicleName();

    String getBrand();

    String getCategory();
}
