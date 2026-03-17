package model;

public class Job {
    private int id;
    private String title;
    private String company;
    private String location;
    private String salary;

    public Job(int id, String title, String company, String location, String salary){
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
    }

    public int getId(){ return id; }
    public String getTitle(){ return title; }
    public String getCompany(){ return company; }
    public String getLocation(){ return location; }
    public String getSalary(){ return salary; }
}
