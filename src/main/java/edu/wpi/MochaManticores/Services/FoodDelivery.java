package edu.wpi.MochaManticores.Services;

public class FoodDelivery extends ServiceRequest {
    private String dietaryPreference;
    private String allergies;
    private String menu;

    public FoodDelivery(boolean employee, boolean completed, String dietaryPreference, String allergies, String menu) {
        super(employee, completed);
        this.dietaryPreference = dietaryPreference;
        this.allergies = allergies;
        this.menu = menu;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getMenu() {
        return menu;
    }
}
