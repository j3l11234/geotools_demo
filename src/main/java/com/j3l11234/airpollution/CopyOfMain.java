package com.j3l11234.airpollution;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import org.geotools.data.CachingFeatureSource;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;

import com.j3l11234.tutorial.math.GaussianModel;
import com.j3l11234.tutorial.math.Oval;
import com.j3l11234.tutorial.math.Searcher;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class CopyOfMain extends JMapFrame{
	private MapContent map;
	private JMenuItem mntmFileOpen;
	private float m_h;
	private float m_s;
	private float m_d;
	private float m_a;
	private float m_ts;

	public CopyOfMain() {
		/*
	  SimpleFeatureType TYPE = null;
	try {
		TYPE = DataUtilities.createType("Location",
		          "the_geom:Point:srid=4326," + // <- the geometry attribute: Point type
		          "name:String," +   // <- a String attribute
		          "number:Integer"   // a number attribute
		  );
	} catch (SchemaException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      System.out.println("TYPE:"+TYPE);*/
		Object obj;
		String str;
		initFrame();
		initHandle();
	}

	private void initFrame(){

		setSize(600, 600);

		map = new MapContent();
		map.setTitle("Feature selection tool example");
		getMapPane().setMapContent(map);

		enableToolBar(true);
		enableStatusBar(true);

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnData = new JMenu("Data");
		menuBar.add(mnData);

		JMenuItem mntmDataInput = new JMenuItem("Input");
		mnData.add(mntmDataInput);

		mntmDataInput.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JFrame jf=new JFrame("排污口数据"); 
						jf.setVisible(true); 
						jf.setBounds(200, 200, 250, 250);

						Container con = jf.getContentPane();
						JPanel pan = new JPanel();
						con.add(pan);

						JLabel H_st = new JLabel("烟筒高度H      ");
						pan.add(H_st);
						final JTextField H_tf = new JTextField(10);
						pan.add(H_tf);
						JLabel H_U_st = new JLabel(" M/n ");
						pan.add(H_U_st);

						JLabel Ts_st = new JLabel("烟气出口温度Ts ");
						pan.add(Ts_st);
						final JTextField Ts_tf = new JTextField(10);
						pan.add(Ts_tf);
						JLabel Ts_U_st = new JLabel(" K  ");
						pan.add(Ts_U_st);

						JLabel S_st = new JLabel("烟气流速speed  ");
						pan.add(S_st);
						final JTextField S_tf = new JTextField(10);
						pan.add(S_tf);
						JLabel S_U_st = new JLabel(" m/s ");
						pan.add(S_U_st);

						JLabel A_st = new JLabel("烟筒截面积Area ");
						pan.add(A_st);
						final JTextField A_tf = new JTextField(10);
						pan.add(A_tf);
						JLabel A_U_st = new JLabel(" m/s2");
						pan.add(A_U_st);

						JLabel D_st = new JLabel("排放浓度Dens   ");
						pan.add(D_st);
						final JTextField D_tf = new JTextField(10);
						pan.add(D_tf);
						JLabel D_U_st = new JLabel("mg/m3");
						pan.add(D_U_st);

						JButton B_ok = new JButton("Input");
						B_ok.addActionListener(
								new ActionListener()
								{
									public void actionPerformed(ActionEvent e)  {
										// TODO Auto-generated method stub
										m_h = Float.parseFloat(H_tf.getText());
										m_ts = Float.parseFloat(Ts_tf.getText());
										m_s = Float.parseFloat(S_tf.getText());
										m_a = Float.parseFloat(A_tf.getText());
										m_d = Float.parseFloat(D_tf.getText()); 
										File file = new File("new");

										SimpleFeatureType TYPE = null;
										try {
											TYPE = DataUtilities.createType("Location",
													"the_geom:Polygon:srid=4326," + // <- the geometry attribute: Point type
															"name:String," +   // <- a String attribute
															"number:Integer"   // a number attribute
													);
										} catch (SchemaException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

										List<SimpleFeature> features = new ArrayList<SimpleFeature>();
										File newFile = getNewShapeFile(file);

										ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

										Map<String, Serializable> params = new HashMap<String, Serializable>();
										try {
											params.put("url", newFile.toURI().toURL());
										} catch (MalformedURLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										params.put("create spatial index", Boolean.TRUE);

										ShapefileDataStore newDataStore = null;
										try {
											newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

										/*
										 * TYPE is used as a template to describe the file contents
										 */
										try {
											newDataStore.createSchema(TYPE);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}


										/*
										 * Write the features to the shapefile
										 */
										Transaction transaction = new DefaultTransaction("create");

										String typeName = null;
										try {
											typeName = newDataStore.getTypeNames()[0];
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										SimpleFeatureSource featureSource = null;
										try {
											featureSource = newDataStore.getFeatureSource(typeName);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
										/*
										 * The Shapefile format has a couple limitations:
										 * - "the_geom" is always first, and used for the geometry attribute name
										 * - "the_geom" must be of type Point, MultiPoint, MuiltiLineString, MultiPolygon
										 * - Attribute names are limited in length 
										 * - Not all data types are supported (example Timestamp represented as Date)
										 * 
										 * Each data store has different limitations so check the resulting SimpleFeatureType.
										 */
										System.out.println("SHAPE:"+SHAPE_TYPE);

										if (featureSource instanceof SimpleFeatureStore) {
											SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
											/*
											 * SimpleFeatureStore has a method to add features from a
											 * SimpleFeatureCollection object, so we use the ListFeatureCollection
											 * class to wrap our list of features.
											 */
											for (int i = 10; i < 20; i++){
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
														i, // 搜索的目标等浓度线椭圆的浓度是11.1
														0.001 // 以步长0.001米进行搜索
														);
												
												double a = oval.a;
												double b = oval.b;
												double left = oval.x;
												double top = oval.y;
												double step = 1;
												List<Coordinate> coordinateList = new ArrayList<Coordinate>();
												for (double j = a*-1; j <= a; j+= step) {
													double y= Math.sqrt((1-(j*j)/(a*a))*(b*b));
													coordinateList.add(new Coordinate(j+left,y+top));	
												}
												int size = coordinateList.size();
												for (int j = size-1; j >= 0; j--) {
													Coordinate point = coordinateList.get(j);
													coordinateList.add(new Coordinate(point.x, point.y*-1));
												}
												coordinateList.add(coordinateList.get(0));
												
												GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
												Coordinate[] coordinates = coordinateList.toArray(new Coordinate[1]);
												//geometryFactory.createLinearRing(coordinates)
												Polygon polygon = geometryFactory.createPolygon(
														coordinates);							
												String name = "abc";
												SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);								
												featureBuilder.add(polygon);
												featureBuilder.add(name);
												
												SimpleFeature feature = featureBuilder.buildFeature(null);
												features.add(feature);
												features.add(feature);	
											}
											
											
											SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
											featureStore.setTransaction(transaction);
											try {
												featureStore.addFeatures(collection);
												transaction.commit();
											} catch (Exception problem) {
												problem.printStackTrace();
												try {
													transaction.rollback();
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
											} finally {
												try {
													transaction.close();
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
											}

											Style style = SLD.createSimpleStyle(featureSource.getSchema());
											Layer layer = new FeatureLayer(collection, style);							           
											map.layers().add(layer);
										} else {
											System.out.println(typeName + " does not support read/write access");
										}
									}
								});
						pan.add(B_ok);
					}				
				});

		mntmFileOpen = new JMenuItem("Open");
		mnFile.add(mntmFileOpen);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelpAbout = new JMenuItem("About");
		mnHelp.add(mntmHelpAbout);
	}

	private void initHandle(){
		mntmFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File path = new File("D:/gis/50m_cultural");
				File file = JFileDataStoreChooser.showOpenFile("shp", path, null);

				if(file == null){
					return;
				}

				try {
					FileDataStore store = FileDataStoreFinder.getDataStore(file);         
					SimpleFeatureSource featureSource = store.getFeatureSource();
					CachingFeatureSource cache = new CachingFeatureSource(featureSource);
					Style style = SLD.createSimpleStyle(featureSource.getSchema());
					Layer layer = new FeatureLayer(cache, style);
					map.layers().add(layer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				System.out.println("文件:"+file.getAbsolutePath());  
			}
		});
	}



	private static File getNewShapeFile(File csvFile) {
		String path = csvFile.getAbsolutePath();
		String newPath = path.substring(0, path.length() - 4) + ".shp";

		JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
		chooser.setDialogTitle("Save shapefile");
		chooser.setSelectedFile(new File(newPath));

		int returnVal = chooser.showSaveDialog(null);

		if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
			// the user cancelled the dialog
			System.exit(0);
		}

		File newFile = chooser.getSelectedFile();
		if (newFile.equals(csvFile)) {
			System.out.println("Error: cannot replace " + csvFile);
			System.exit(0);
		}

		return newFile;
	}


	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopyOfMain main = new CopyOfMain();
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
