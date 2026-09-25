package adapter;

/**
 * ADAPTER of the Adapter Pattern.
 *
 * Wraps an SUVRentalProvider (the adaptee) and translates its incompatible
 * getSUV() method into the common Vehicle interface.
 */
public class SUVAdapter implements Vehicle {

    private SUVRentalProvider suvRentalProvider;

    public SUVAdapter(SUVRentalProvider suvRentalProvider) {
        this.suvRentalProvider = suvRentalProvider;
    }

    @Override
    public String getVehicleName() {
        SUVData suvData = suvRentalProvider.getSUV();
        return suvData.modelLabel;
    }

    @Override
    public String getBrand() {
        SUVData suvData = suvRentalProvider.getSUV();
        return suvData.make;
    }

    @Override
    public String getCategory() {
        SUVData suvData = suvRentalProvider.getSUV();
        return suvData.bodyStyle;
    }
}
