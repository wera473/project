package xxx;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class ZonalWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfOutputFile;

	public String inputZonalFile;
	public String inputValueFile;
	public String outputFileName;
	public String fileName;
	public String statisticType;

	public boolean isNew;

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
	public ZonalWindow(testGUI gui) {
		setTitle("ZonalStatistic");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 766, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{230, 73, 43, 0, 78, 78, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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

		JTextPane tpDescription = new JTextPane();
		GridBagConstraints gbc_tpDescription = new GridBagConstraints();
		gbc_tpDescription.gridheight = 11;
		gbc_tpDescription.gridwidth = 2;
		gbc_tpDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tpDescription.fill = GridBagConstraints.BOTH;
		gbc_tpDescription.gridx = 4;
		gbc_tpDescription.gridy = 1;
		contentPane.add(tpDescription, gbc_tpDescription);

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

		JButton btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 12;
		contentPane.add(btnOk, gbc_btnOk);

		
		//-------------------------description
		tpDescription.setText("ZonalStatistic:\n"
				+ "Calculates statistics on values of a file within the zones of another file.\r\n"
				+ "\r\n"
				+ "Input file (zonal data): file that defines the zones.\n"
				+ "\r\n"
				+ "Input value file: file that contains the values on which to calculate a statistic.\r\n"
				+ "\r\n"
				+ "Output file: the calculated result. \r\n"
				+ "\r\n"
				+ "Statistic type: SUM, VARIETY, MAXIMUM, MINIMUM, MEAN");
		
		
		
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

				int zonalLayerIndex=gui.hm.get(inputZonalFile);
				int valueLayerIndex=gui.hm.get(inputValueFile);				

				Layer zonalLayer=gui.layers.get(zonalLayerIndex);
				Layer valueLayer=gui.layers.get(valueLayerIndex);

				Layer newlayer;

				switch(statisticType) {
				case "VARIETY":
					newlayer=valueLayer.zonalVariety(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				case "MAXIMUM":
					newlayer=valueLayer.zonalMaximum(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				case "MINIMUM":						
					newlayer=valueLayer.zonalMinimum(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				case "SUM":
					newlayer=valueLayer.zonalSum(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				case "MEAN":
					newlayer=valueLayer.zonalMean(zonalLayer,"layer");
					newlayer.save(outputFileName);
					break;
				default:
					break;
				}

				gui.newFile(outputFileName, fileName);
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


		//-------------------------input zonal file
		for(String k:gui.hm.keySet()) {
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
		for(String k:gui.hm.keySet()) {
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
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return "ASCII (*.txt)";
			}
			public boolean accept(File f) {
				if (f.isDirectory()) {

					return true;
				} else {
					return f.getName().toLowerCase().endsWith(".txt");
				}
			}
		});

		//default file path
		outputFileName=(String) fileChooser.getCurrentDirectory().getPath() + "\\zonaloperation.txt";
		tfOutputFile.setText(outputFileName);

		btnOutputFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//				JFileChooser fileChooser=new JFileChooser(".");				
				int result = fileChooser.showSaveDialog(ZonalWindow.this);
				if (result == JFileChooser.APPROVE_OPTION) {					
					outputFileName=fileChooser.getSelectedFile().getPath();

					fileName=fileChooser.getSelectedFile().getName();					
					if(fileName.indexOf(".txt")==-1) {
						outputFileName=outputFileName+".txt";
					}

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
		cbStatisticType.addItem("VARIETY");

		JButton btnCancle = new JButton("Cancel");
		GridBagConstraints gbc_btnCancle = new GridBagConstraints();
		gbc_btnCancle.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancle.gridx = 2;
		gbc_btnCancle.gridy = 12;
		contentPane.add(btnCancle, gbc_btnCancle);
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

		//the first item added is default
		statisticType=(String) cbStatisticType.getItemAt(0); 

		cbStatisticType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				statisticType = (String) cbStatisticType.getSelectedItem();				
			}			
		});

	}

}
