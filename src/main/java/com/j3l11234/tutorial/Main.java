package com.j3l11234.tutorial;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

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

public class Main extends JMapFrame{
  private MapContent map;
  private JMenuItem mntmFileOpen;


  public Main() {
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
          map.addLayer(layer);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        
        System.out.println("文件:"+file.getAbsolutePath());  
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
}
