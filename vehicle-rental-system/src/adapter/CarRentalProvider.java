package adapter;

/**
 * ADAPTEE of the Adapter Pattern.
 *
 * This class simulates an already existing, external car provider system.
 * Notice that its method is called getCar(), not getVehicleName()/getBrand(),
 * and it returns a CarData object with its own field names.
 *
 * This is intentionally INCOMPATIBLE with the Vehicle interface, which is
 * exactly the kind of problem the Adapter Pattern is used to solve.
 */
public class CarRentalProvider {

    public CarData getCar() {
        return new CarData("Chevrolet", "Spark", "Compact Car");
    }
}

/**
 * Simple data holder used only by CarRentalProvider.
 * Its field names (carBrand, carModelName, carSegment) are specific to
 * this provider and are different from the Vehicle interface.
 */
class CarData {
    String carBrand;
    String carModelName;
    String carSegment;

    public CarData(String carBrand, String carModelName, String carSegment) {
        this.carBrand = carBrand;
        this.carModelName = carModelName;
        this.carSegment = carSegment;
    }
}
