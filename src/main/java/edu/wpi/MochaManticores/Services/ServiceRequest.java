package edu.wpi.MochaManticores.Services;

public abstract class ServiceRequest {
    private boolean employee;
    private boolean completed;

    public ServiceRequest(boolean employee, boolean completed) {
        this.employee = employee;
        this.completed = completed;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
