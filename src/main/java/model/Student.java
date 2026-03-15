package model;

public class Student {

    private int id;
    private String name;
    private String email;
    private String password;
    private double cgpa;
    private String branch;

    // Constructor
    public Student(int id, String name, String email, String password, double cgpa, String branch) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cgpa = cgpa;
        this.branch = branch;
    }

    public Student() {}

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}