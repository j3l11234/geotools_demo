package com.j3l11234.tutorial.math;

public class Main {
	

	public static void main(String[] args) {
		
		// 构造高斯模型
		GaussianModel gm = new GaussianModel(
				200*1000,	// 点污染源强度  			      单位ug/s
				2,			// 风速 					      单位m/s
				1.1,		// 横向风向扩散参数的回归系数   查表获得
				0.9,		// 横向风向扩散参数的回归指数   查表获得
				0.7,		// 垂直风向扩散参数的回归系数   查表获得
				0.8,		// 垂直风向扩散参数的回归指数   查表获得
				20			// 点污染源高度，如烟囱高度       单位m
				);
		
		// 构造搜寻器
		Searcher ser = new Searcher(
				2,		// 在空间2米高度的等浓度线
				0.01,	// 浓度误差小于0.01被认为可接受
				gm		// 搜索基于此高斯模型
				);
		
		// 获取等浓度线椭圆
		Oval oval = ser.SameDensityOval(
				11.1, // 搜索的目标等浓度线椭圆的浓度是11.1
				0.001 // 以步长0.001米进行搜索
				);
		
		System.out.println(oval);
		
		Oval oval1 = ser.SameDensityOval(
				20, // 搜索的目标等浓度线椭圆的浓度是11.1
				0.001 // 以步长0.001米进行搜索
				);
		
		System.out.println(oval1);
		
		Oval oval2 = ser.SameDensityOval(
				10, // 搜索的目标等浓度线椭圆的浓度是11.1
				0.001 // 以步长0.001米进行搜索
				);
		
		System.out.println(oval2);
		
		
		
		// 将一个二维点，逆时针旋转若干弧度
		RotatePoint pt = new RotatePoint(1,0);
		pt.rotate_anticlockwise(Math.PI / 2); 
		System.out.println(pt);
		pt.rotate_anticlockwise(Math.PI / 2);
		System.out.println(pt);
	}
	
	

}
