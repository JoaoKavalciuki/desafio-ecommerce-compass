package com.compass.ecommnerce.entities.enums;

public enum Role {
    ADMIN("adminstrator"),
    USER("user");

    public String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }


}
