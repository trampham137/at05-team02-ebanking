package models;

import models.enums.City;
import models.enums.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// TODO: gop voi user
public class RegisterData {
    private final String username;
    private final String password;
    private final String confirmPassword;
    private final String fullName;
    private final String phone;
    private final String dob;
    private final Gender gender;
    private final City city;
    private final String idNumber;
    private final String email;

    private static final DateTimeFormatter DOB_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RegisterData(String username, String password, String confirmPassword, String fullName, String phone, LocalDate dob, Gender gender, City city, String idNumber, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.phone = phone;
        this.dob = dob.format(DOB_FORMAT);
        this.gender = gender;
        this.city = city;
        this.idNumber = idNumber;
        this.email = email;
    }

    public RegisterData(String username, String password, String confirmPassword, String fullName, String phone, String dob, Gender gender, City city, String idNumber, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.city = city;
        this.idNumber = idNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    public City getCity() {
        return city;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDobAsDate() {
        return LocalDate.parse(dob, DOB_FORMAT);
    }
}
