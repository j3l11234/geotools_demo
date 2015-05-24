package com.j3l11234.airpollution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import org.geotools.data.CachingFeatureSource;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;

import com.j3l11234.tutorial.math.GaussianModel;
import com.j3l11234.tutorial.math.Oval;
import com.j3l11234.tutorial.math.Searcher;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;


public class Main extends JMapFrame{
	static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
	static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
	 
	private SimpleFeatureType TYPE;
	private MapContent map;
	private JMenuItem mntmFileOpen;
	private JMenuItem mntmDataInput;

	public Main() {
		try {
			TYPE = DataUtilities.createType("Location",
					"the_geom:Polygon," + // <- the geometry attribute: Point type
					"name:String," +   // <- a String attribute
					"number:Integer"   // a number attribute
			);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
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

		mntmFileOpen = new JMenuItem("Open");
		mnFile.add(mntmFileOpen);


		JMenu mnData = new JMenu("Data");
		menuBar.add(mnData);

		mntmDataInput = new JMenuItem("Input");
		mnData.add(mntmDataInput);	


		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelpAbout = new JMenuItem("About");
		mnHelp.add(mntmHelpAbout);
	}

	private void initHandle(){
		mntmFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});

		mntmDataInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				test2();
			}
		});
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
					Main main = new Main();
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 打开新文件
	 */
	private void openFile(){
		File path = new File(System.getProperty("user.dir")+"/data");
		File file = JFileDataStoreChooser.showOpenFile("shp", path, null);

		if(file == null){
			return;
		}

		try {
			FileDataStore store = FileDataStoreFinder.getDataStore(file);         
			SimpleFeatureSource featureSource = store.getFeatureSource();
			CachingFeatureSource cache = new CachingFeatureSource(featureSource);
			
			Style style2 = createStyle2(featureSource);
			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			Layer layer = new FeatureLayer(cache, style2);
			map.layers().add(layer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("文件:"+file.getAbsolutePath());  
	}

	
	private void test2(){
		DataDialog dataDialog = new DataDialog();
		dataDialog.setModal(true);
		dataDialog.setVisible(true);
		DataDialog.MyData data =  dataDialog.getData();
		if(data == null){
			return;
		}
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
	
		double longitude = 116.41;
		double latitude = 39.97;
		Coordinate center = new Coordinate(longitude,latitude);
		String name = "testname";
		int number = 10;

		// 构造高斯模型
		GaussianModel gm = new GaussianModel(	
				data.density,		// 点污染源强度 单位ug/s
				data.speed,			// 风速 单位m/s
				data.sigma_y_gama,	// 横向风向扩散参数的回归系数   查表获得
				data.sigma_y_a,		// 横向风向扩散参数的回归指数   查表获得
				data.sigma_z_gama,	// 垂直风向扩散参数的回归系数   查表获得
				data.sigma_z_a,		// 垂直风向扩散参数的回归指数   查表获得
				data.height			// 点污染源高度，如烟囱高度 单位m
				);
		
		double latitudeRatio = 30.87 * 36*10;
		double longitudeRatio = 30.87 * Math.cos(2*Math.PI/360*39.9)* 36*10;
		for (double i = 5; i < 20; i+=3){
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
			
			double a = oval.a/longitudeRatio;
			double b = oval.b/latitudeRatio;
			double left = oval.x/longitudeRatio;
			double top = oval.y/latitudeRatio;
			
			List<Coordinate> ovalList = Service.creatOval(a, b, left, top, 100,center);
			Coordinate[] ovalArray = ovalList.toArray(new Coordinate[1]);
			Polygon polygon = geometryFactory.createPolygon(ovalArray);							
			featureBuilder.add(polygon);
			featureBuilder.add(name);
			featureBuilder.add(number);
			SimpleFeature feature = featureBuilder.buildFeature(null);
			features.add(feature);
		}
		
		addFeatures(features);
	}
	
	private void addFeatures(List<SimpleFeature> features){
		try {

			System.out.println("TYPE:"+TYPE);	
			System.out.println(TYPE.getCoordinateReferenceSystem());
			//TYPE.set
			MemoryDataStore newDataStore = new MemoryDataStore();
			newDataStore.createSchema(TYPE);

			String typeName = newDataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
			SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
			System.out.println("SHAPE:"+SHAPE_TYPE);

			if (featureSource instanceof SimpleFeatureStore) {
				SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
				SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
				featureStore.addFeatures(collection);		
			} else {
				System.out.println(typeName + " does not support read/write access");
			}
			
			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			Layer layer = new FeatureLayer(featureSource, style);
			map.layers().add(layer);
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	
	/**
     * Here is how you can use a SimpleFeatureType builder to create the schema for your shapefile
     * dynamically.
     * <p>
     * This method is an improvement on the code used in the main method above (where we used
     * DataUtilities.createFeatureType) because we can set a Coordinate Reference System for the
     * FeatureType and a a maximum field length for the 'name' field dddd
     */
    private static SimpleFeatureType createFeatureType() {

    	
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system

        // add attributes in order
        builder.add("the_geom", Point.class);
        builder.length(15).add("Name", String.class); // <- 15 chars width for name field
        builder.add("number",Integer.class);
        
        // build the type
        final SimpleFeatureType LOCATION = builder.buildFeatureType();

        return LOCATION;
    }
    
    
	/**
     * Here is a programmatic alternative to using JSimpleStyleDialog to
     * get a Style. This methods works out what sort of feature geometry
     * we have in the shapefile and then delegates to an appropriate style
     * creating method.
     */
    private Style createStyle2(FeatureSource featureSource) {
        SimpleFeatureType schema = (SimpleFeatureType)featureSource.getSchema();
        Class geomType = schema.getGeometryDescriptor().getType().getBinding();

        if (Polygon.class.isAssignableFrom(geomType)
                || MultiPolygon.class.isAssignableFrom(geomType)) {
            return createPolygonStyle();

        } else if (LineString.class.isAssignableFrom(geomType)
                || MultiLineString.class.isAssignableFrom(geomType)) {
            return createLineStyle();

        } else {
            return createPointStyle();
        }
    }

    /**
     * Create a Style to draw polygon features with a thin blue outline and
     * a cyan fill
     */
    private Style createPolygonStyle() {

        // create a partially opaque outline stroke
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.BLACK),
                filterFactory.literal(1),
                filterFactory.literal(0.5));

        // create a partial opaque fill
//        Fill fill = styleFactory.createFill(
//                filterFactory.literal(Color.CYAN),
//                filterFactory.literal(0.5));
        Fill fill = styleFactory.createFill(
                filterFactory.literal(Color.WHITE),
                filterFactory.literal(0.5));
        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        PolygonSymbolizer sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }
    
    /**
     * Create a Style to draw line features as thin blue lines
     */
    private Style createLineStyle() {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.RED),
                filterFactory.literal(1));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    /**
     * Create a Style to draw point features as circles with blue outlines
     * and cyan fill
     */
    private Style createPointStyle() {
        Graphic gr = styleFactory.createDefaultGraphic();

        Mark mark = styleFactory.getCircleMark();

        mark.setStroke(styleFactory.createStroke(
                filterFactory.literal(Color.BLUE), filterFactory.literal(1)));

        mark.setFill(styleFactory.createFill(filterFactory.literal(Color.CYAN)));

        gr.graphicalSymbols().clear();
        gr.graphicalSymbols().add(mark);
        gr.setSize(filterFactory.literal(5));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to
         * draw the default geomettry of features
         */
        PointSymbolizer sym = styleFactory.createPointSymbolizer(gr, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }
}
