package scrame.helper;

public class Weightage {
  private String name;
  private double value;
  private Weightage parent;

  public Weightage(String name, double value) {
    this.name = name;
    this.value = value;
  }

  public Weightage(String name, double value, Weightage parent) {
    this.name = name;
    this.value = value;
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public Weightage getParent() {
    return parent;
  }

  public void setParent(Weightage parent) {
    this.parent = parent;
  }

}

