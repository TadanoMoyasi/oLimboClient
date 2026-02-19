package me.TadanoMoyasi.oLimboClient.core.api;

public class Coordinates implements Cloneable {
  public double x;
  
  public Coordinates(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public double y; public double z;
  
  public Coordinates clone() {
    return new Coordinates(this.x, this.y, this.z);
  }

  
  public String toString() {
    return " x:" + this.x + " y:" + this.y + " z:" + this.z;
  }
}


