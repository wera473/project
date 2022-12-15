package xxx;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class FocalWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfOutputFile;

	public String inputFile;
	public String outputFileName;
	public String fileName;
	public boolean isSquare; 
	public int radius;
	public String statisticType;

	public boolean isNew;

	//	/**
	//	 * Launch the application.
	//	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					FocalWindow frame = new FocalWindow();
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
	public FocalWindow(testGUI gui) {		
		setTitle("FocalStatistic");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 862, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{438, 78, 0, 0, 78, 0};
		gbl_contentPane.rowHeights = new int[]{35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("Input file");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JTextPane tpDescription = new JTextPane();
		GridBagConstraints gbc_tpDescription = new GridBagConstraints();
		gbc_tpDescription.gridheight = 12;
		gbc_tpDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tpDescription.fill = GridBagConstraints.BOTH;
		gbc_tpDescription.gridx = 4;
		gbc_tpDescription.gridy = 0;
		contentPane.add(tpDescription, gbc_tpDescription);

		final JComboBox cbInputFile = new JComboBox();
		GridBagConstraints gbc_cbInputFile = new GridBagConstraints();
		gbc_cbInputFile.gridwidth = 2;
		gbc_cbInputFile.insets = new Insets(0, 0, 5, 5);
		gbc_cbInputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInputFile.gridx = 0;
		gbc_cbInputFile.gridy = 1;
		contentPane.add(cbInputFile, gbc_cbInputFile);

		JLabel lblNewLabel_1 = new JLabel("Output file");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		tfOutputFile = new JTextField();
		GridBagConstraints gbc_tfOutputFile = new GridBagConstraints();
		gbc_tfOutputFile.insets = new Insets(0, 0, 5, 5);
		gbc_tfOutputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOutputFile.gridx = 0;
		gbc_tfOutputFile.gridy = 3;
		contentPane.add(tfOutputFile, gbc_tfOutputFile);
		tfOutputFile.setColumns(10);

		JButton btnOutputFile = new JButton("save");
		GridBagConstraints gbc_btnOutputFile = new GridBagConstraints();
		gbc_btnOutputFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutputFile.gridx = 1;
		gbc_btnOutputFile.gridy = 3;
		contentPane.add(btnOutputFile, gbc_btnOutputFile);

		JLabel lblNewLabel_2 = new JLabel("Neighborhood");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		final JComboBox cbNeighbor = new JComboBox();
		GridBagConstraints gbc_cbNeighbor = new GridBagConstraints();
		gbc_cbNeighbor.insets = new Insets(0, 0, 5, 5);
		gbc_cbNeighbor.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNeighbor.gridx = 0;
		gbc_cbNeighbor.gridy = 5;
		contentPane.add(cbNeighbor, gbc_cbNeighbor);

		JLabel lblNewLabel_3 = new JLabel("radius");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);

		final TextField tfRadius = new TextField();
		GridBagConstraints gbc_tfRadius = new GridBagConstraints();
		gbc_tfRadius.insets = new Insets(0, 0, 5, 5);
		gbc_tfRadius.gridx = 0;
		gbc_tfRadius.gridy = 7;
		contentPane.add(tfRadius, gbc_tfRadius);

		JLabel lblNewLabel_4 = new JLabel("Statistic type");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 8;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);

		final JComboBox cbStatisticType = new JComboBox();
		GridBagConstraints gbc_cbStatisticType = new GridBagConstraints();
		gbc_cbStatisticType.insets = new Insets(0, 0, 5, 0);
		gbc_cbStatisticType.gridwidth = 2;
		gbc_cbStatisticType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbStatisticType.gridx = 0;
		gbc_cbStatisticType.gridy = 9;
		contentPane.add(cbStatisticType, gbc_cbStatisticType);

		JButton btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 12;
		contentPane.add(btnOk, gbc_btnOk);

		//-------------------------description
		tpDescription.setText("FocalStatistic:\n"
				+ "Calculates for each input cell location a statistic of the values within a specified neigborhood around it.\r\n"
				+ "\r\n"
				+ "Input file: the file on which the statistic will be calculated.\n"
				+ "\r\n"
				+ "Output file: the calculated result. \r\n"
				+ "\r\n"
				+ "Neighborhood: Specified the shape of the area around each cell.\r\n"
				+ "\r\n"
				+ "Radius: number of cell around each cell. \r\n"
				+ "\r\n"
				+ "Statistic type: SUM, VARIETY, MAXIMUM, MINIMUM, MEAN");

		//-------------------------input file 
		for(String k:gui.hm.keySet()) {
			cbInputFile.addItem(k);
		}

		inputFile=(String) cbInputFile.getItemAt(0); //default

		cbInputFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inputFile = (String) cbInputFile.getSelectedItem();
				//				System.out.println("选择的选项是: " + selectedItem);								
			}			
		});

		//-------------------------output file
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
		outputFileName=(String) fileChooser.getCurrentDirectory().getPath() + "\\focaloperation.txt";
		tfOutputFile.setText(outputFileName);

		btnOutputFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//				JFileChooser fileChooser=new JFileChooser(".");				
				int result = fileChooser.showSaveDialog(FocalWindow.this);
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

		//-------------------------neighborhood
		cbNeighbor.addItem("Square");
		cbNeighbor.addItem("Circle");	

		isSquare=true; //default "Square"

		cbNeighbor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				String selectedItem = (String) cbNeighbor.getSelectedItem();
				if(selectedItem.equals("Square")) {
					isSquare=true;
				}else {
					isSquare=false;
				}
			}

		});

		//-------------------------radius
		radius=-9999; //default

		tfRadius.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String text=tfRadius.getText();
				//				System.out.println(text);
				if(!text.equals("")){
					radius=Integer.parseInt(text);
					//					System.out.println(radius);
				}
			}			
		});

		tfRadius.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				String text=tfRadius.getText();
				//				System.out.println(text);
				if(!text.equals("")) {
					radius=Integer.parseInt(text);
					//					System.out.println(radius);
				}				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				String text=tfRadius.getText();
				if(!text.equals("")) {
					radius=Integer.parseInt(text);
					//					System.out.println(radius);
				}
			}

		});

		//-------------------------statistic type
		cbStatisticType.addItem("VARIETY");
		cbStatisticType.addItem("MAXIMUM");
		cbStatisticType.addItem("MINIMUM");
		cbStatisticType.addItem("SUM");
		cbStatisticType.addItem("MEAN");
		
				JButton btnCancle = new JButton("Cancel");
				GridBagConstraints gbc_btnCancle = new GridBagConstraints();
				gbc_btnCancle.insets = new Insets(0, 0, 0, 5);
				gbc_btnCancle.gridx = 2;
				gbc_btnCancle.gridy = 12;
				contentPane.add(btnCancle, gbc_btnCancle);		
				
						//-------------------------click cancle
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

		//-------------------------click ok
		//		inputFile;
		//		outputFileName;
		//		isSquare; 
		//		radius;
		//		statisticType;

		btnOk.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				if(radius<0 || outputFileName==null) {
					JOptionPane.showMessageDialog(new JFrame(), "fail");
					return;
				}

				int layerIndex=gui.hm.get(inputFile);			

				Layer newlayer;

				switch(statisticType) {
				case "VARIETY":
					newlayer=gui.layers.get(layerIndex).focalVariety(radius, isSquare, "layer");
					newlayer.save(outputFileName);
					break;
				case "MAXIMUM":
					newlayer=gui.layers.get(layerIndex).focalMaximum(radius, isSquare, "layer");
					newlayer.save(outputFileName);
					break;
				case "MINIMUM":
					newlayer=gui.layers.get(layerIndex).focalMinimum(radius, isSquare, "layer");
					newlayer.save(outputFileName);
					break;
				case "SUM":
					newlayer=gui.layers.get(layerIndex).focalSum(radius, isSquare, "layer");
					newlayer.save(outputFileName);
					break;
				case "MEAN":
					newlayer=gui.layers.get(layerIndex).focalMean(radius, isSquare, "layer");
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

	}
}
