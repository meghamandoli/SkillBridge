package model;

public class Job {

    private int id;
    private String title;
    private String company;
    private String location;
    private String salary;

    private double minCgpa;
    private String branch;
    private boolean noBacklogs;
    private String skills;

    public Job(int id, String title, String company, String location, String salary){
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;

        this.minCgpa = 0.0;
        this.branch = "Any";
        this.noBacklogs = false;
        this.skills = "";
    }

    public Job(int id, String title, String company, String location, String salary, double minCgpa, String branch, boolean noBacklogs, String skills) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.minCgpa = minCgpa;
        this.branch = branch;
        this.noBacklogs = noBacklogs;
        this.skills = skills;
    }

    public Job(){}

    public int getId(){ return id; }
    public String getTitle(){ return title; }
    public String getCompany(){ return company; }
    public String getLocation(){ return location; }
    public String getSalary(){ return salary; }
    public double getMinCgpa() { return minCgpa; }
    public String getBranch() { return branch; }
    public boolean isNoBacklogs() { return noBacklogs; }
    public String getSkills() { return skills; }

    public void setId(int id){ this.id = id; }
    public void setTitle(String title){ this.title = title; }
    public void setCompany(String company){ this.company = company; }
    public void setLocation(String location){ this.location = location; }
    public void setSalary(String salary){ this.salary = salary; }
    public void setMinCgpa(double minCgpa) { this.minCgpa = minCgpa; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setNoBacklogs(boolean noBacklogs) { this.noBacklogs = noBacklogs; }
    public void setSkills(String skills) { this.skills = skills; }
}
