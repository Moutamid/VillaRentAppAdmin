package com.moutimid.vellarentappadmin.model;

public class Villa {
    private String location;
    private String bedrooms;
    private String bathrooms;
    private String poolSize;
    private String poolType;
    private String villaName;
    private String city;
    private String neighborhood;
    private String country;
    private String description;
    private String rentalPrice;
    private String securityDeposit;
    private String checkInOutTimes;
    private String cancellationPolicy;

    // Empty constructor for Firebase
    public Villa() {
    }

    public Villa(String location, String bedrooms, String bathrooms, String poolSize, String poolType,
                 String villaName, String city, String neighborhood, String country, String description,
                 String rentalPrice, String securityDeposit, String checkInOutTimes, String cancellationPolicy) {
        this.location = location;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.poolSize = poolSize;
        this.poolType = poolType;
        this.villaName = villaName;
        this.city = city;
        this.neighborhood = neighborhood;
        this.country = country;
        this.description = description;
        this.rentalPrice = rentalPrice;
        this.securityDeposit = securityDeposit;
        this.checkInOutTimes = checkInOutTimes;
        this.cancellationPolicy = cancellationPolicy;
    }

    // Add getters and setters as needed

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }

    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public String getVillaName() {
        return villaName;
    }

    public void setVillaName(String villaName) {
        this.villaName = villaName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public String getCheckInOutTimes() {
        return checkInOutTimes;
    }

    public void setCheckInOutTimes(String checkInOutTimes) {
        this.checkInOutTimes = checkInOutTimes;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
}
