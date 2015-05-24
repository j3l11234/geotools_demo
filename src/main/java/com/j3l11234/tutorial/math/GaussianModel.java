package com.j3l11234.tutorial.math;


public class GaussianModel {
	
	double q;				//点污染源强度  			      单位ug/s
	double u;				//风速 					      单位m/s
	double sigma_y_gama;	//横向风向扩散参数的回归系数   查表获得
	double sigma_y_a;		//横向风向扩散参数的回归指数   查表获得
	double sigma_z_gama;	//垂直风向扩散参数的回归系数   查表获得
	double sigma_z_a;		//垂直风向扩散参数的回归指数   查表获得
	double h;				//点污染源高度，如烟囱高度       单位m
	
	//	高斯模型需要若干参数，暂时不考虑数据合法性检验
	public GaussianModel(double q,double u,
			double sigma_y_gama,double sigma_y_a,
			double sigma_z_gama,double sigma_z_a,
			double h){
		this.q = q;
		this.u = u;
		this.sigma_y_gama = sigma_y_gama;
		this.sigma_y_a = sigma_y_a;
		this.sigma_z_gama = sigma_z_gama;
		this.sigma_z_a = sigma_z_a;
		this.h = h;
	}
	
	//	空间，点x,y,z的浓度  单位 mg/m^3
	double densityOf(double x,double y,double z){
		
		double pi = 3.1415926;
		double sigma_y = sigma_y_gama*Math.pow(x, sigma_y_a);
		double sigma_z = sigma_z_gama*Math.pow(x, sigma_z_a);
		double A = q/(2*pi*u*sigma_y*sigma_z);
		
		
		double yy = y*y;
		double sysy2 = sigma_y*sigma_y*2;	
		double B = Math.exp(-yy/sysy2);
		
		double z_m_he_fang = (z-h)*(z-h);
		double z_p_he_fang = (z+h)*(z+h);
		double szsz2 = sigma_z*sigma_z*2;
		double C = Math.exp(-z_m_he_fang/szsz2)+Math.exp(-z_p_he_fang/szsz2);
		
		double Final = A*B*C;
		
		return Final;
	}
	
	public void print_density_xyz(double x,double y,double z){
		System.out.println("["+x+","+y+","+z+"]"+"的浓度是："+this.densityOf(x, y, z)+" mg/m^3");
	}
}
