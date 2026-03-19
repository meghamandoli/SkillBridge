package model;

public class Job {

    private int id;
    private String title;
    private String company;
    private String location;
    private String salary;

    // ✅ EXISTING CONSTRUCTOR (KEEP THIS)
    public Job(int id, String title, String company, String location, String salary){
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
    }

    // ✅ NEW EMPTY CONSTRUCTOR (IMPORTANT)
    public Job(){}

    // ---------- GETTERS ----------
    public int getId(){ return id; }
    public String getTitle(){ return title; }
    public String getCompany(){ return company; }
    public String getLocation(){ return location; }
    public String getSalary(){ return salary; }

    // ---------- SETTERS (NEW) ----------
    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setSalary(String salary){
        this.salary = salary;
    }
}