package com.example.stunningcode;

public class Employee {
    private String firstName;
    private String lastName;
    private String dob; // Format: yyyy-MM-dd
    private String email;
    private String phoneNumber;

    public Employee(String firstName, String lastName, String dob, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDob() { return dob; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}
