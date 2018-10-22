public class Weightage{
    private String name;
    private double value;
    private Weightage parent;
    public Weightage(String name, double value){
        name = name;
        value = value;
    }
    public Weightage(String name, double value, Weightage parent){
        name = name;
        value = value;
        parent = parent;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        name = name;
    }
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        value = value;
    }
    public Weightage getParent(){
        return parent;
    }
    public Weightage setParent(){
        parent = parent;
    }
}