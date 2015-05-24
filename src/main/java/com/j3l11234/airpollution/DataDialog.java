package com.j3l11234.airpollution;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;
import java.awt.FlowLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataDialog extends JDialog {

	private JPanel contentPane;
	private JTextField textField_density;
	private JTextField textField_density_delt;
	private JTextField textField_density_rt;
	
	private JTextField textField_density_long;
	private JTextField textField_density_lati;
	private JTextField textField_density_awr;
	
	
	private JTextField textField_speed;
	private JTextField textField_sigma_y_gama;
	private JTextField textField_sigma_y_a;
	private JTextField textField_sigma_z_gama;
	private JTextField textField_sigma_z_a;
	private JTextField textField_height;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JButton btn_ok;
	private JButton btn_cancel;

	private MyData data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataDialog dialog = new DataDialog();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 420);
		setMinimumSize(new Dimension(480, 420));
		
		initDialog();
		initHandle();
	}
	
	private void addItem(JTextField jtf,String title,String defaultvalsue,int index){
		JLabel lbl_1_rt = new JLabel(title);
		GridBagConstraints gbc_lbl_1_rt = new GridBagConstraints();
		gbc_lbl_1_rt.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_1_rt.gridx = 0;
		gbc_lbl_1_rt.gridy = index;
		panel.add(lbl_1_rt, gbc_lbl_1_rt);
		
		//jtf = new JTextField();
		jtf.setText(defaultvalsue);
		GridBagConstraints gbc_textField_density_rt = new GridBagConstraints();
		gbc_textField_density_rt.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_density_rt.anchor = GridBagConstraints.NORTH;
		gbc_textField_density_rt.insets = new Insets(0, 0, 5, 0);
		gbc_textField_density_rt.gridx = 1;
		gbc_textField_density_rt.gridy = index;
		panel.add(jtf, gbc_textField_density_rt);
		jtf.setColumns(10);
		
		
	}
	
	private void initDialog(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 10, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "基本参数", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {250, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lbl_1 = new JLabel("点污染源强度  (ug/s)");
		GridBagConstraints gbc_lbl_1 = new GridBagConstraints();
		gbc_lbl_1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_1.gridx = 0;
		gbc_lbl_1.gridy = 0;
		panel.add(lbl_1, gbc_lbl_1);
		
		textField_density = new JTextField();
		textField_density.setText("200000");
		GridBagConstraints gbc_textField_density = new GridBagConstraints();
		gbc_textField_density.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_density.anchor = GridBagConstraints.NORTH;
		gbc_textField_density.insets = new Insets(0, 0, 5, 0);
		gbc_textField_density.gridx = 1;
		gbc_textField_density.gridy = 0;
		panel.add(textField_density, gbc_textField_density);
		textField_density.setColumns(10);
		
		
		
		
		
		JLabel lbl_2 = new JLabel("风速  (m/s)");
		GridBagConstraints gbc_lbl_2 = new GridBagConstraints();
		gbc_lbl_2.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_2.gridx = 0;
		gbc_lbl_2.gridy = 1;
		panel.add(lbl_2, gbc_lbl_2);
		
		textField_speed = new JTextField();
		textField_speed.setText("2");
		GridBagConstraints gbc_textField_speed = new GridBagConstraints();
		gbc_textField_speed.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_speed.anchor = GridBagConstraints.NORTH;
		gbc_textField_speed.insets = new Insets(0, 0, 5, 0);
		gbc_textField_speed.gridx = 1;
		gbc_textField_speed.gridy = 1;
		panel.add(textField_speed, gbc_textField_speed);
		textField_speed.setColumns(10);
		
		JLabel label_1 = new JLabel("点污染源高度");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel.add(label_1, gbc_label_1);
		
		textField_height = new JTextField();
		textField_height.setText("20");
		GridBagConstraints gbc_textField_height = new GridBagConstraints();
		gbc_textField_height.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_height.insets = new Insets(0, 0, 5, 0);
		gbc_textField_height.anchor = GridBagConstraints.NORTH;
		gbc_textField_height.gridx = 1;
		gbc_textField_height.gridy = 2;
		panel.add(textField_height, gbc_textField_height);
		textField_height.setColumns(10);
		
		
		JLabel lbl_1_delt = new JLabel("源强变化率  (ug/s)");
		GridBagConstraints gbc_lbl_1_delt = new GridBagConstraints();
		gbc_lbl_1_delt.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_1_delt.gridx = 0;
		gbc_lbl_1_delt.gridy = 3;
		panel.add(lbl_1_delt, gbc_lbl_1_delt);
		
		textField_density_delt = new JTextField();
		textField_density_delt.setText("20000");
		GridBagConstraints gbc_textField_density_delt = new GridBagConstraints();
		gbc_textField_density_delt.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_density_delt.anchor = GridBagConstraints.NORTH;
		gbc_textField_density_delt.insets = new Insets(0, 0, 5, 0);
		gbc_textField_density_delt.gridx = 1;
		gbc_textField_density_delt.gridy = 3;
		panel.add(textField_density_delt, gbc_textField_density_delt);
		textField_density_delt.setColumns(10);
		
		JLabel lbl_1_rt = new JLabel("刷新次数");
		GridBagConstraints gbc_lbl_1_rt = new GridBagConstraints();
		gbc_lbl_1_rt.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_1_rt.gridx = 0;
		gbc_lbl_1_rt.gridy = 4;
		panel.add(lbl_1_rt, gbc_lbl_1_rt);
		
		textField_density_rt = new JTextField();
		textField_density_rt.setText("5");
		GridBagConstraints gbc_textField_density_rt = new GridBagConstraints();
		gbc_textField_density_rt.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_density_rt.anchor = GridBagConstraints.NORTH;
		gbc_textField_density_rt.insets = new Insets(0, 0, 5, 0);
		gbc_textField_density_rt.gridx = 1;
		gbc_textField_density_rt.gridy = 4;
		panel.add(textField_density_rt, gbc_textField_density_rt);
		textField_density_rt.setColumns(10);
		

		textField_density_long = new JTextField();
		textField_density_lati = new JTextField();
		textField_density_awr = new JTextField();
		addItem(textField_density_long,"污染源经度","111.57",5);
		addItem(textField_density_lati,"污染源维度","34.16",6);
		addItem(textField_density_awr,"东向逆时针方向","0.52333",7);
		
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "扩散参数", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {250, 0};
		gbl_panel_1.rowHeights = new int[] {0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lbl_3 = new JLabel("横向风向的回归系数");
		GridBagConstraints gbc_lbl_3 = new GridBagConstraints();
		gbc_lbl_3.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_3.gridx = 0;
		gbc_lbl_3.gridy = 0;
		panel_1.add(lbl_3, gbc_lbl_3);
		
		textField_sigma_y_gama = new JTextField();
		textField_sigma_y_gama.setText("1.1");
		GridBagConstraints gbc_textField_sigma_y_gama = new GridBagConstraints();
		gbc_textField_sigma_y_gama.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_sigma_y_gama.anchor = GridBagConstraints.NORTH;
		gbc_textField_sigma_y_gama.insets = new Insets(0, 0, 5, 0);
		gbc_textField_sigma_y_gama.gridx = 1;
		gbc_textField_sigma_y_gama.gridy = 0;
		panel_1.add(textField_sigma_y_gama, gbc_textField_sigma_y_gama);
		textField_sigma_y_gama.setColumns(10);
		
		JLabel lbl_4 = new JLabel("横向风向的回归指数");
		GridBagConstraints gbc_lbl_4 = new GridBagConstraints();
		gbc_lbl_4.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_4.gridx = 0;
		gbc_lbl_4.gridy = 1;
		panel_1.add(lbl_4, gbc_lbl_4);
		
		textField_sigma_y_a = new JTextField();
		textField_sigma_y_a.setText("0.9");
		GridBagConstraints gbc_textField_sigma_y_a = new GridBagConstraints();
		gbc_textField_sigma_y_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_sigma_y_a.anchor = GridBagConstraints.NORTH;
		gbc_textField_sigma_y_a.insets = new Insets(0, 0, 5, 0);
		gbc_textField_sigma_y_a.gridx = 1;
		gbc_textField_sigma_y_a.gridy = 1;
		panel_1.add(textField_sigma_y_a, gbc_textField_sigma_y_a);
		textField_sigma_y_a.setColumns(10);
		
		JLabel label = new JLabel("垂直风向的回归系数");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		panel_1.add(label, gbc_label);
		
		textField_sigma_z_gama = new JTextField();
		textField_sigma_z_gama.setText("0.7");
		GridBagConstraints gbc_textField_sigma_z_gama = new GridBagConstraints();
		gbc_textField_sigma_z_gama.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_sigma_z_gama.anchor = GridBagConstraints.NORTH;
		gbc_textField_sigma_z_gama.insets = new Insets(0, 0, 5, 0);
		gbc_textField_sigma_z_gama.gridx = 1;
		gbc_textField_sigma_z_gama.gridy = 2;
		panel_1.add(textField_sigma_z_gama, gbc_textField_sigma_z_gama);
		textField_sigma_z_gama.setColumns(10);
		
		lblNewLabel = new JLabel("垂直风向的回归指数");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_sigma_z_a = new JTextField();
		textField_sigma_z_a.setText("0.8");
		GridBagConstraints gbc_textField_sigma_z_a = new GridBagConstraints();
		gbc_textField_sigma_z_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_sigma_z_a.anchor = GridBagConstraints.NORTH;
		gbc_textField_sigma_z_a.gridx = 1;
		gbc_textField_sigma_z_a.gridy = 3;
		panel_1.add(textField_sigma_z_a, gbc_textField_sigma_z_a);
		textField_sigma_z_a.setColumns(10);
		
		btn_ok = new JButton("确定");
		GridBagConstraints gbc_btn_ok = new GridBagConstraints();
		gbc_btn_ok.insets = new Insets(0, 0, 0, 5);
		gbc_btn_ok.gridx = 0;
		gbc_btn_ok.gridy = 4;
		contentPane.add(btn_ok, gbc_btn_ok);
		
		btn_cancel = new JButton("取消");
		GridBagConstraints gbc_btn_cancel = new GridBagConstraints();
		gbc_btn_cancel.gridx = 1;
		gbc_btn_cancel.gridy = 4;
		contentPane.add(btn_cancel, gbc_btn_cancel);
	}
	
	private void initHandle(){
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					data = new MyData();
					data.density = Double.parseDouble(textField_density.getText());
					data.density_delt = Double.parseDouble(textField_density_delt.getText());
					data.density_rt = Double.parseDouble(textField_density_rt.getText());
					data.speed = Double.parseDouble(textField_speed.getText());
					data.height = Double.parseDouble(textField_height.getText());
					data.sigma_y_gama = Double.parseDouble(textField_sigma_y_gama.getText());
					data.sigma_y_a = Double.parseDouble(textField_sigma_y_a.getText());
					data.sigma_z_gama = Double.parseDouble(textField_sigma_z_gama.getText());
					data.sigma_z_a = Double.parseDouble(textField_sigma_z_a.getText());
					
					data.density_long = Double.parseDouble(textField_density_long.getText());
					data.density_lati = Double.parseDouble(textField_density_lati.getText());
					data.density_awr = Double.parseDouble(textField_density_awr.getText());
					
					DataDialog.this.dispose();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(DataDialog.this, "您输入的信息有误");
				}
			}
		});
		
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataDialog.this.dispose();
			}
		});
	}
	
	public MyData getData() {
		return data;
	}
	
	public class MyData{
		public double density;
		public double density_delt;
		public double density_rt;
		public double density_lati;
		public double density_long;
		public double density_awr;// antiwise radius
		public double speed;
		public double height;
		public double sigma_y_gama;
		public double sigma_y_a;
		public double sigma_z_gama;
		public double sigma_z_a;
	}
}
