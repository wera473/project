package xxx;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
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
public class testGUI extends JFrame {

	private JPanel contentPane;
	
	public DefaultMutableTreeNode rootNode;
	public DefaultTreeModel fileTreeModel;
	public JTree fileTree;
	
	public ArrayList<Layer> layers;
	public ArrayList<MapPanel> mapPanel;
	public int mapPanelX;
	public int mapPanelY;
	public int scale;	
	
	public int mousePrevX;
	public int mousePrevY;
	
	public int layerCount;
	public int currentLayer; //index
	
	public HashMap<String,Integer> hm; //layername,index
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testGUI frame = new testGUI();
					frame.setVisible(true);					
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
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Wera\\Documents\\lab5\\3357797.png"));
		setTitle("GIteSt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		
		rootNode=new DefaultMutableTreeNode(null);
		fileTreeModel=new DefaultTreeModel(rootNode);
		fileTree = new JTree(fileTreeModel);		
		fileTree.setBackground(new Color(255, 255, 255));
		
		fileTree.setScrollsOnExpand(true);
		
		JScrollPane fileWindow=new JScrollPane(fileTree);	
		fileWindow.setBounds(0,0,200,750);
		
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
				LocalWindow frame = new LocalWindow(hm,layers);
				frame.setVisible(true);
			}
			
		});
		
		
		mntmFocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FocalWindow frame = new FocalWindow(hm,layers);
				frame.setVisible(true);
			}
			
		});
		
		mntmZonal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZonalWindow frame = new ZonalWindow(hm,layers);
				frame.setVisible(true);
			}
			
		});	
		

		final JFileChooser fileChooser = new JFileChooser();
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
				File("."));
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
				JFileChooser fileChooser=new JFileChooser(".");				
				int result = fileChooser.showSaveDialog(testGUI.this);	
				if (result == JFileChooser.APPROVE_OPTION) {					
					String fileName=fileChooser.getSelectedFile().getPath();
//					String fileName=fileChooser.getSelectedFile().getName();
					if(layers!=null) {					
						layers.get(currentLayer).save(fileName);
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
//				System.out.println(hm.get(selectedNode.getUserObject()));
		        
		        int selectNodeIndex=hm.get(selectedNode.getUserObject());
		        mapPanel.get(currentLayer).setVisible(false);
		        currentLayer=selectNodeIndex;
		        mapPanel.get(currentLayer).setVisible(true);		        
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
		
		 final JLabel lblNewLabel = new JLabel("");
		 lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
 		lblNewLabel.setBounds(10, 750, 200, 32);
 		contentPane.add(lblNewLabel); 
 		lblNewLabel.setPreferredSize(new Dimension(contentPane.getWidth(), 16));
 		
 		
 		//lblNewLabel.setLayout((LayoutManager) new BoxLayout(lblNewLabel, BoxLayout.Y_AXIS));

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
                
                String displayText = (mousePrevX+" "+mousePrevY);
                
                lblNewLabel.setText("X = "+e.getX()+" ; Y = "+e.getY());
                
               // System.out.println(mousePrevX+" "+mousePrevY); 
                
               
            }
		
		});
	}
	
 		
	
	public void newFile(String filePath, String layerName) {		
		//-------------------------add file content to fileWindow		
		MutableTreeNode parentNode=new DefaultMutableTreeNode(filePath);
		MutableTreeNode childNode=new DefaultMutableTreeNode(layerName);
		
		fileTreeModel.insertNodeInto(parentNode, rootNode, 0);
		fileTreeModel.insertNodeInto(childNode, parentNode, 0);
		
		fileTree.expandRow(0);
		fileTree.expandRow(1);	
		
		System.out.println(fileTree.getRowCount());
		
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
		System.out.println("hoi");
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
 