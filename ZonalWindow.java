package se.kth.ag2411.mapalgebra;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ZonalWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfOutputFile;

	public String inputZonalFile;
	public String inputValueFile;
	public String outputFileName;
	public String statisticType;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					ZonalWindow frame = new ZonalWindow();
	//					frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the frame.
	 */
	public ZonalWindow(final HashMap<String,Integer> hm,final ArrayList<Layer> layers) {
		setTitle("ZonalStatistic");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 588, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{230, 73, 78, 78, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("Input file (zonal data)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		final JComboBox cbInputZoneFile = new JComboBox();
		GridBagConstraints gbc_cbInputZoneFile = new GridBagConstraints();
		gbc_cbInputZoneFile.gridwidth = 2;
		gbc_cbInputZoneFile.insets = new Insets(0, 0, 5, 5);
		gbc_cbInputZoneFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInputZoneFile.gridx = 0;
		gbc_cbInputZoneFile.gridy = 1;
		contentPane.add(cbInputZoneFile, gbc_cbInputZoneFile);

		JLabel lblNewLabel_1 = new JLabel("Input value file");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		final JComboBox cbInputValueFile = new JComboBox();
		GridBagConstraints gbc_cbInputValueFile = new GridBagConstraints();
		gbc_cbInputValueFile.gridwidth = 2;
		gbc_cbInputValueFile.insets = new Insets(0, 0, 5, 5);
		gbc_cbInputValueFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInputValueFile.gridx = 0;
		gbc_cbInputValueFile.gridy = 3;
		contentPane.add(cbInputValueFile, gbc_cbInputValueFile);

		JLabel lblNewLabel_2 = new JLabel("Output file");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		tfOutputFile = new JTextField();
		GridBagConstraints gbc_tfOutputFile = new GridBagConstraints();
		gbc_tfOutputFile.insets = new Insets(0, 0, 5, 5);
		gbc_tfOutputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOutputFile.gridx = 0;
		gbc_tfOutputFile.gridy = 5;
		contentPane.add(tfOutputFile, gbc_tfOutputFile);
		tfOutputFile.setColumns(10);

		final JButton btnOutputFile = new JButton("choose");
		GridBagConstraints gbc_btnOutputFile = new GridBagConstraints();
		gbc_btnOutputFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutputFile.gridx = 1;
		gbc_btnOutputFile.gridy = 5;
		contentPane.add(btnOutputFile, gbc_btnOutputFile);

		JLabel lblNewLabel_3 = new JLabel("Statistic type");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);

		final JComboBox cbStatisticType = new JComboBox();
		GridBagConstraints gbc_cbStatisticType = new GridBagConstraints();
		gbc_cbStatisticType.gridwidth = 2;
		gbc_cbStatisticType.insets = new Insets(0, 0, 5, 5);
		gbc_cbStatisticType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbStatisticType.gridx = 0;
		gbc_cbStatisticType.gridy = 7;
		contentPane.add(cbStatisticType, gbc_cbStatisticType);

		JButton btnOk = new JButton("ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 12;
		contentPane.add(btnOk, gbc_btnOk);

		JButton btnCancle = new JButton("cancle");
		GridBagConstraints gbc_btnCancle = new GridBagConstraints();
		gbc_btnCancle.gridx = 3;
		gbc_btnCancle.gridy = 12;
		contentPane.add(btnCancle, gbc_btnCancle);


		//-------------------------input zonal file
		for(String k:hm.keySet()) {
			cbInputZoneFile.addItem(k);
		}

		inputZonalFile=(String) cbInputZoneFile.getItemAt(0); //default

		cbInputZoneFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inputZonalFile = (String) cbInputZoneFile.getSelectedItem();
				//				System.out.println("选择的选项是: " + selectedItem);								
			}			
		});

		//-------------------------input value file		
		for(String k:hm.keySet()) {
			cbInputValueFile.addItem(k);
		}

		inputValueFile=(String) cbInputValueFile.getItemAt(0); //default		
		cbInputValueFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inputValueFile = (String) cbInputValueFile.getSelectedItem();
				//				System.out.println("选择的选项是: " + selectedItem);								
			}			
		});

		//-------------------------output file
		//		tfOutputFile
		//		btnOutputFile
		btnOutputFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser=new JFileChooser(".");				
				int result = fileChooser.showSaveDialog(ZonalWindow.this);
				if (result == JFileChooser.APPROVE_OPTION) {					
					outputFileName=fileChooser.getSelectedFile().getPath();
					//					String fileName=fileChooser.getSelectedFile().getName();				
					fileChooser.setVisible(true);
					tfOutputFile.setText(outputFileName);
				}				
			}
		});		

		//-------------------------statistic type
		//		cbStatisticType		 
		cbStatisticType.addItem("MINIMUM");		
		cbStatisticType.addItem("MAXIMUM");		
		cbStatisticType.addItem("SUM");
		cbStatisticType.addItem("MEAN");
		cbStatisticType.addItem("VARITY");
		
		//the first item added is default
		statisticType=(String) cbStatisticType.getItemAt(0); 

		cbStatisticType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				statisticType = (String) cbStatisticType.getSelectedItem();				
			}			
		});


		//		inputZonalFile;
		//		inputValueFile;
		//		outputFileName;
		//	    statisticType;
		//-------------------------click ok
		//		btnOk
		btnOk.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(outputFileName==null) {
					JOptionPane.showMessageDialog(new JFrame(), "fail");
					return;
				}

				int zonalLayerIndex=hm.get(inputZonalFile);
				int valueLayerIndex=hm.get(inputValueFile);				

				Layer zonalLayer=layers.get(zonalLayerIndex);
				Layer valueLayer=layers.get(valueLayerIndex);

				Layer newlayer;

				switch(statisticType) {
				case "VARITY":						
					break;
				case "MAXIMUM":
					break;
				case "MINIMUM":						
					newlayer=valueLayer.zonalMinimum(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				case "SUM":
					break;
				case "MEAN":
					break;
				default:
					break;
				}

				dispose();

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		//-------------------------click cancle
		//		btnCancle
		btnCancle.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}

}
