package com.j3l11234.tutorial.math;

public class Searcher {
	private GaussianModel gm;
	private double z;
	private double diff_ok;
	
	public Searcher(double z,double diff_ok,GaussianModel gm){
		this.gm = gm;
		this.z = z;
		this.diff_ok = diff_ok;
	}
	
	public Oval SameDensityOval(double target_density,double step){
		
		
		final double left  =  search_a(target_density,0.01,step);
		final double max   =  max_at  (left,step);
		final double right =  search_a(target_density,max,step);
		final double a = (right - left)/2.0;
		

		
		final double x_centre = (left+right)/2.0;
		final double b = search_b(target_density,x_centre,step);
		
		//System.out.println("left"+left);
		//System.out.println("right"+right);
		//System.out.println("x_centre"+x_centre);
		//System.out.println("b"+b);
		
		//gm.print_density_xyz(left, 0, z);
		//gm.print_density_xyz(right, 0, z);
		//gm.print_density_xyz(x_centre, b, z);
		//gm.print_density_xyz(x_centre, -b, z);
		
		Oval oval = new Oval();
		oval.a = a;
		oval.b = b;
		oval.x = x_centre;
		oval.y = 0;
		return oval;
	}
	
	private double search_a(double target_density ,double from,double step){
		double x = from;
		
		while(true){
			double currrent_density = gm.densityOf(x, 0, z);
			
			double current_diff = Math.abs(currrent_density-target_density);
			
			if(current_diff<=diff_ok)
				return x;
			
			x+=step;
		}
	}
	
	private double search_b(double target_density ,double x_centre,double step){
		double y = 0;
		
		while(true){
			double currrent_density = gm.densityOf(x_centre, y, z);
			
			double current_diff = Math.abs(currrent_density-target_density);
			
			if(current_diff<=diff_ok)
				return y;
			
			y+=step;
		}
	}
	
	private double max_at(double from,double step){
		double x = from;
		
		double last_density = 0.0;
		
		while(true){
			
			double currrent_density = gm.densityOf(x, 0, z);
			
			if(currrent_density<last_density)
				return x;
			
			last_density = currrent_density;
			x+=step;
		}
	}

}
