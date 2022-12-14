package xxx;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

import java.util.Scanner;

import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
public class testGUI extends JFrame {

	private JPanel contentPane;

	public DefaultMutableTreeNode rootNode;
	public DefaultTreeModel fileTreeModel;
	public JTree fileTree;

	JScrollPane fileWindow;
	public ImageIcon icon;
	public ArrayList<Layer> layers;
	public ArrayList<MapPanel> mapPanel;
	public int mapPanelX;
	public int mapPanelY;
	public int scale;	
	
	public Integer pixel;
	public double values[][];
	public int mousePrevX;
	public int mousePrevY;

	public int layerCount;
	public int currentLayer; //index

	public HashMap<String,Integer> hm; //layername,index

	final JLabel lblNewLabel;
	
	private JPanel statusbar;
	
	//Design Colors
	Color pierogi = new Color(254, 234, 132);
	Color darkblue = new Color(	23, 34, 67);
	Color lightblue  = new Color(76, 127, 200);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final testGUI frame = new testGUI();
					frame.setVisible(true);	

					frame.addComponentListener(new ComponentListener() {

						@Override
						public void componentResized(ComponentEvent e) {
							// TODO Auto-generated method stub
							System.out.println(e.getComponent().getHeight());
							frame.fileWindow.setBounds(0,0,200,e.getComponent().getHeight()-100);
						//	frame.lblNewLabel.setBounds(10, e.getComponent().getHeight()-100, 100, 32);
							frame.statusbar.setBounds(0, e.getComponent().getHeight()-100, 1000, 32);

						}

						@Override
						public void componentMoved(ComponentEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void componentShown(ComponentEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void componentHidden(ComponentEvent e) {
							// TODO Auto-generated method stub

						}

					});


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public testGUI() {
		
		//SET ICON
		icon = new ImageIcon("src//xxx//pierogis.png");
		setBackground(new Color(255, 255, 255));
		setIconImage(icon.getImage());
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Gebruiker\\OneDrive\\Documenten\\GitHub\\project\\src\\xxx\\perogi.jfif"));
		
		//SET TITLE+FRAME
		setTitle("PieroGIS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);


		rootNode=new DefaultMutableTreeNode(null);
		fileTreeModel=new DefaultTreeModel(rootNode);
		fileTree = new JTree(fileTreeModel);		
		fileTree.setBackground(new Color(255, 255, 255));

		fileTree.setScrollsOnExpand(true);

		fileWindow=new JScrollPane(fileTree);	
		fileWindow.setBounds(0,0,200,this.getHeight()-100);

		layers = new ArrayList();

		mapPanel=new ArrayList();
		mapPanelX=220;
		mapPanelY=0;
		scale = 1;

		mousePrevX=0;
		mousePrevY=0;

		layerCount=0;
		currentLayer=-1; 

		hm=new HashMap(); 


		//----------------menubar
		javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenu mnActions = new JMenu("Actions");
		mnFile.add(mnActions);	


		JMenu mnMapAlgebra = new JMenu("MapAlgebra");
		menuBar.add(mnMapAlgebra);
		JMenuItem mntmLocal = new JMenuItem("LocalStatistic");		
		mnMapAlgebra.add(mntmLocal);
		JMenuItem mntmFocal = new JMenuItem("FocalStatistic");		
		mnMapAlgebra.add(mntmFocal);
		JMenuItem mntmZonal = new JMenuItem("ZonalStatistic");		
		mnMapAlgebra.add(mntmZonal);

		JMenu mnZoomIn = new JMenu("Zoom in +");
		mnZoomIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Zoom in
				scale+=1;

				for(MapPanel mp:mapPanel) {
					contentPane.remove(mp);
					contentPane.repaint();
					mp.setSize(scale);
					contentPane.add(mp,BorderLayout.EAST);
					mp.setBounds(mapPanelX, mapPanelY, mp.width, mp.height);
				};		    	

			}});
		mnZoomIn.setIcon(null);
		menuBar.add(mnZoomIn);

		JMenu mnZoomOut = new JMenu("Zoom out -");
		mnZoomOut.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseClicked(MouseEvent e) {

				if(scale==1) {
					return;
				}
				//Zoom out
				scale-=1;

				for(MapPanel mp:mapPanel) {
					contentPane.remove(mp);
					contentPane.repaint();
					mp.setSize(scale);
					contentPane.add(mp,BorderLayout.EAST);
					mp.setBounds(mapPanelX, mapPanelY, mp.width, mp.height);
				};
			}
		});
		menuBar.add(mnZoomOut);

		JMenu mnRefresh = new JMenu("Refresh");
		menuBar.add(mnRefresh);

		mnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				contentPane.revalidate();
				contentPane.repaint();
				//refresh page Button

			}
		});

		mntmLocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LocalWindow frame = new LocalWindow(testGUI.this);
				frame.setVisible(true);	
			}
		});


		mntmFocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FocalWindow frame = new FocalWindow(testGUI.this);


				frame.setVisible(true);
			}

		});

		mntmZonal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZonalWindow frame = new ZonalWindow(testGUI.this);
				frame.setVisible(true);
			}

		});	


		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
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

		fileChooser.setCurrentDirectory(new
				File("user.home"));
		fileChooser.setMultiSelectionEnabled(true);

		//---------------open file	
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				int result = fileChooser.showOpenDialog(testGUI.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File[] selectedFiles = 
							fileChooser.getSelectedFiles();
					for (int i = 0; i < selectedFiles.length; i++) {						

						String filePath=selectedFiles[i].getAbsolutePath();
						String layerName=selectedFiles[i].getName();

						newFile(filePath,layerName);						
					}
				}
			}
		});	

		//---------------save file		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//				JFileChooser fileChooser=new JFileChooser(".");				
				int result = fileChooser.showSaveDialog(testGUI.this);	
				if (result == JFileChooser.APPROVE_OPTION) {					
					String outputFileName=fileChooser.getSelectedFile().getPath();					
					String fileName=fileChooser.getSelectedFile().getName();					
					if(fileName.indexOf(".txt")==-1) {
						outputFileName=outputFileName+".txt";
						fileName=fileName+".txt";
					}
					if(layers!=null) {					
						layers.get(currentLayer).save(outputFileName);
					}
					fileChooser.setVisible(true);					
				}
			}		
		});		

		mnActions.add(mntmOpen);
		mnActions.add(mntmSave);
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		mnFile.add(mntmExit);


		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(fileWindow,BorderLayout.WEST);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//---------------
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
				//		        System.out.println("Selected node: " + selectedNode.getUserObject());		        
				//								System.out.println(hm.get(selectedNode.getUserObject()));

				int selectNodeIndex=hm.get(selectedNode.getUserObject());
				if(currentLayer>-1) {
					mapPanel.get(currentLayer).setVisible(false);
				}				
				currentLayer=selectNodeIndex;
				mapPanel.get(currentLayer).setVisible(true);
			}
		});

		JPopupMenu popMenu=new JPopupMenu();
		JMenuItem mntmDelete=new JMenuItem("Delete");

		popMenu.add(mntmDelete);

		fileTree.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				TreePath treePath=fileTree.getPathForLocation(e.getX(), e.getY());
				if(treePath==null) {
					return;
				}
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
				int selectNodeIndex=-9999;
				selectNodeIndex=hm.get(selectedNode.getUserObject());

				if(selectNodeIndex>-9999) {
					fileTree.setSelectionPath(treePath);
					if(e.getButton()==3) {
						popMenu.show(fileTree,e.getX(),e.getY());
					}
				}
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

		mntmDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//				System.out.println("delete");
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();				
				int selectNodeIndex=hm.get(selectedNode.getUserObject());			
				if(selectNodeIndex==currentLayer) {
					System.out.println("current");
					mapPanel.get(currentLayer).setVisible(false);
					currentLayer=-1;					
					//					contentPane.repaint();
				}
				layers.remove(selectNodeIndex);
				layerCount--;
				mapPanel.remove(selectNodeIndex);
				hm.remove(selectedNode.getUserObject());
				hmIndexRefresh(hm);				
				System.out.println(layers.size()+" "+hm.size());

				fileTreeModel.removeNodeFromParent((MutableTreeNode) selectedNode.getParent());
			}

		});

		//---------------zoom in and zoom out 
		contentPane.addMouseWheelListener(new MouseWheelListener() {
			// Override the mouseWheelMoved method to handle mouse wheel move events
			public void mouseWheelMoved(MouseWheelEvent e) {
				// Get the mouse wheel's scroll amount
				int scrollAmount = e.getWheelRotation();

				//			    System.out.println(scrollAmount);
				//			    System.out.println(mapPanelWidth);

				// Check if the mouse wheel is being scrolled up or down
				if (scrollAmount > 0) {
					// If the mouse wheel is being scrolled down, decrease the panel's size
					if(scale==1) {
						return;							    		
					}
					scale-=1;							    	
				} else {
					// If the mouse wheel is being scrolled up, increase the panel's size						    	
					scale+=1;
				}		    

				for(MapPanel mp:mapPanel) {
					contentPane.remove(mp);
					contentPane.repaint();
					mp.setSize(scale);
					contentPane.add(mp,BorderLayout.EAST);
					mp.setBounds(mapPanelX, mapPanelY, mp.width, mp.height);
				};		    	

				//		    	System.out.println(mapPanel.width);
			}
		});

		lblNewLabel = new JLabel("");
	//	lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
	//	lblNewLabel.setBounds(10, this.getHeight()-100, 200, 32);
	//	contentPane.add(lblNewLabel); 
	//	lblNewLabel.setPreferredSize(new Dimension(contentPane.getWidth(), 16));



		statusbar = new JPanel();
		statusbar.setPreferredSize(new Dimension(contentPane.getWidth(), 32));
		statusbar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		statusbar.setBounds(0, 1000, 1000, 41);
		statusbar.setBackground(darkblue);
		contentPane.add(statusbar);
		
		JLabel StatusBar = new JLabel("Status Bar                ");
		StatusBar.setForeground(pierogi);
		StatusBar.setBackground(darkblue);
		statusbar.add(StatusBar);
		

		JLabel lblNewLabel1 = new JLabel("");
		lblNewLabel1.setForeground(pierogi);
		statusbar.add(lblNewLabel1);
		
		
		//TO DO, couldnt fix the Jspinner
		//JSpinner scalebar = new JSpinner();
		//statusbar.add(scalebar);
		
		//JLabel cellval = new JLabel("Cell Value ="); // couldnt manage to show the values
		//cellval.setForeground(pierogi);
		//statusbar.add(cellval);
		
		//JLabel cellid = new JLabel("Cell ID");
		//cellid.setForeground(pierogi);
		//statusbar.add(cellid);

		//---------------drag the map
		contentPane.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// Get the current mouse position
				int x = e.getX();
				int y = e.getY(); 

				// Calculate the distance moved
				int dx = x - mousePrevX;
				int dy = y - mousePrevY;            

				mousePrevX=x;
				mousePrevY=y;

				mapPanelX+=dx;
				mapPanelY+=dy;
				

				// Set the position of the map
				if(mapPanel!=null) {
					for(MapPanel mp:mapPanel) {
						mp.setBounds(mapPanelX, mapPanelY, mp.width, mp.height);
					};	
				}                

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Handle the mouse move event
				// Get the current mouse position
				int x = e.getX();
				int y = e.getY();
				
				mousePrevX=x;
				mousePrevY=y;
				
			//	pixel = hm.get(values);
			

				String displayText = (mousePrevX+" "+mousePrevY);
				lblNewLabel.setText("X = "+e.getX()+" ; Y = "+e.getY());
				lblNewLabel1.setText("X = "+e.getX()+" ; Y = "+e.getY());
				//cellval.setText("Cell Value ="+pixel);//value
				
				// System.out.println(mousePrevX+" "+mousePrevY); 
			}

		});
	}

	public void newFile(String filePath, String layerName) {		
		//-------------------------add file content to fileWindow
		System.out.println("fileName: " + layerName);
		MutableTreeNode parentNode=new DefaultMutableTreeNode(filePath);
		MutableTreeNode childNode=new DefaultMutableTreeNode(layerName);

		fileTreeModel.insertNodeInto(parentNode, rootNode, 0);
		fileTreeModel.insertNodeInto(childNode, parentNode, 0);

		fileTree.expandRow(0);
		fileTree.expandRow(1);	

		//		System.out.println(fileTree.getRowCount());

		System.out.println("Selected file: " + 
				filePath);

		//-----------------------------display map	
		Layer newLayer=new Layer("layer", filePath);
		layers.add(newLayer);
		BufferedImage layerImage = newLayer.toImage();						

		MapPanel mp=new MapPanel(layerImage,scale);
		mapPanel.add(mp);
		contentPane.add(mp, BorderLayout.CENTER);
		mp.setBounds(mapPanelX, mapPanelY, mp.width, mp.height);					

		if(currentLayer>-1) {
			mapPanel.get(currentLayer).setVisible(false);							
		}

		layerCount+=1;
		currentLayer=layerCount-1;	
		//		System.out.println(currentLayer);

		hm.put(layerName, currentLayer);
		//		System.out.println(layerName+" "+currentLayer);


	}

	public void hmIndexRefresh(HashMap<String,Integer> hm) {
		int index=0;
		for(String s:hm.keySet()) {
			hm.put(s,index);
			index++;		
		}
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
