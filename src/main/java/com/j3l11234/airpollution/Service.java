package com.j3l11234.airpollution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.j3l11234.tutorial.math.RotatePoint;
import com.vividsolutions.jts.geom.Coordinate;

public class Service {
	public static List<Coordinate> creatOval(double a, double b, double left, double top, int accuracy,Coordinate centre,double anticlock_dadius) {
		left = left + centre.x;
		top = top + centre.y;
		double axa = a*a;
		double bxb = b*b;
		double height = 2;
		List<Coordinate> quarterOval = new ArrayList<Coordinate>();
		for (int i = 0; i < accuracy; i++) {
			double x1 = a*i/accuracy;
			double y1 = Math.sqrt((1-(x1*x1)/axa)*(bxb));
			y1 = Double.isNaN(y1) ? 0.0 : y1;
			double y2 = b*i/accuracy;
			double x2 = Math.sqrt((1-(y2*y2)/bxb)*(axa));
			x2 = Double.isNaN(x2) ? 0.0 : x2;
			quarterOval.add(new Coordinate(x1, y1));
			quarterOval.add(new Coordinate(x2, y2));
		}
		Collections.sort(quarterOval, new Comparator<Coordinate>() {
			public int compare(Coordinate a, Coordinate b) { 
				return Double.compare(a.x, b.x);
			}
		});
		int size = quarterOval.size();
		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		for (int i = 0; i < size; i++) {
			Coordinate point = quarterOval.get(i);
			coordinateList.add(new Coordinate(left + point.x, top + point.y, height));
		}
		for (int i = size - 1; i >= 0; i--) {
			Coordinate point = quarterOval.get(i);
			coordinateList.add(new Coordinate(left + point.x, top - point.y, height));
		}
		for (int i = 0; i < size; i++) {
			Coordinate point = quarterOval.get(i);
			coordinateList.add(new Coordinate(left - point.x, top - point.y, height));
		}
		for (int i = size - 1; i >= 0; i--) {
			Coordinate point = quarterOval.get(i);
			coordinateList.add(new Coordinate(left - point.x, top + point.y, height));
		}
		
//		for (int i = 0; i < coordinateList.size(); i++) {
//			Coordinate point = coordinateList.get(i);
//			System.out.println(point.x + "," + point.y);
//		}
		
		
		for(int i=0;i<coordinateList.size();++i){
			
			Coordinate ptc = coordinateList.get(i);
			RotatePoint ptr = new RotatePoint(ptc.x,ptc.y);
			ptr.rotate_anticlockwise(anticlock_dadius);
			ptc.x = ptr.x;
			ptc.y = ptr.y;
		}
		
		
		return coordinateList;
	}

}
