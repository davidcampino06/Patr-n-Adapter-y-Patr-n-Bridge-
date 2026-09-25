package model;

/**
 * Simple data holder (POJO) used to carry the final rental summary
 * from the service layer to the controller, and from there to the
 * frontend as JSON. It does not contain any pattern logic itself.
 */
public class RentalResult {

    private String customerName;
    private String vehicleName;
    private String vehicleBrand;
    private String vehicleCategory;
    private String planName;
    private int duration;
    private double pricePerPeriod;
    private double totalPrice;
    private String status;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPricePerPeriod() {
        return pricePerPeriod;
    }

    public void setPricePerPeriod(double pricePerPeriod) {
        this.pricePerPeriod = pricePerPeriod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
