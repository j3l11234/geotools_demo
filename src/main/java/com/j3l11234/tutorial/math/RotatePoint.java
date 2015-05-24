package com.j3l11234.tutorial.math;

public class RotatePoint {
	
	public double x,y;
	
	public RotatePoint(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	// 逆时针旋转a度，单位弧度制
	public void rotate_anticlockwise(double a){
		double xnew = x * Math.cos(a) - y * Math.sin(a);
		double ynew = x * Math.sin(a) + y * Math.cos(a);
		x = xnew;
		y = ynew;
	}
	public String toString(){
		return "ponit:["+x+","+y+"]";
	}
}
