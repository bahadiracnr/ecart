package com.ecart.auth_service.dto;



public class RegisterEvent {

    private String email;
    private String firstName;
    private String lastName;

    // Boş constructor (JSON deserialize için gerekli)
    public RegisterEvent() {
    }

    // Full constructor
    public RegisterEvent(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter - Setter'lar
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
