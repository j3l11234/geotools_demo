package com.j3l11234.tutorial;

import java.io.File;

import org.geotools.data.CachingFeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 * <p>
 * This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class Quickstart {

  /**
   * GeoTools Quickstart demo application. Prompts the user for a shapefile and displays its
   * contents on the screen in a map frame
   */
  public static void main(String[] args) throws Exception {
    // display a data store file chooser dialog for shapefiles
    File path = new File("D:/gis/50m_cultural");
    File file = JFileDataStoreChooser.showOpenFile("shp", path, null);
    if (file == null) {
      return;
    }
    
    FileDataStore store = FileDataStoreFinder.getDataStore(file);
    SimpleFeatureSource featureSource = store.getFeatureSource();
    
    // CachingFeatureSource is deprecated as experimental (not yet production ready)
    CachingFeatureSource cache = new CachingFeatureSource(featureSource);

    
    // Create a map content and add our shapefile to it
    MapContent map = new MapContent();
    map.setTitle("Quickstart");

    Style style = SLD.createSimpleStyle(featureSource.getSchema());
    Layer layer = new FeatureLayer(cache, style);
    map.addLayer(layer);

    // Now display the map
    JMapFrame.showMap(map);
  }

}