package edu.wpi.MochaManticores.Services;

public class FoodDelivery extends ServiceRequest {
    private String dietaryPreference;
    private String allergies;
    private String menu;

    public FoodDelivery(boolean employee, boolean completed, int row, String dietaryPreference, String allergies, String menu) {
        super(employee, completed, row);
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

    public String[] getFields() {
        String[] fields = {
                String.valueOf(ServiceMap.FoodDelivery),
                dietaryPreference,
                allergies,
                menu
        };

        return fields;
    }
}
