package se.kth.ag2411.mapalgebra;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
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
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
public class TestGUI extends JFrame {
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
					TestGUI frame = new TestGUI();
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
	public TestGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		
		rootNode=new DefaultMutableTreeNode(null);
		fileTreeModel=new DefaultTreeModel(rootNode);
		fileTree = new JTree(fileTreeModel);		
		
		fileTree.setScrollsOnExpand(true);
		
		JScrollPane fileWindow=new JScrollPane(fileTree);	
		fileWindow.setBounds(0,0,200,600);
		
		layers=new ArrayList();
		
		mapPanel=new ArrayList();
		mapPanelX=220;
		mapPanelY=0;
		scale=1;
		
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
		JMenuItem mntmLocal = new JMenuItem("Local");		
		mnMapAlgebra.add(mntmLocal);
		JMenuItem mntmFocal = new JMenuItem("Focal");		
		mnMapAlgebra.add(mntmFocal);
		JMenuItem mntmZonal = new JMenuItem("Zonal");		
		mnMapAlgebra.add(mntmZonal);
		
		mntmFocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FocalWindow frame = new FocalWindow(hm,layers);
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
				int result = fileChooser.showOpenDialog(TestGUI.this);
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
				int result = fileChooser.showSaveDialog(TestGUI.this);	
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
		mnFile.add(mntmExit);
		contentPane = new JPanel();
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
                
//                System.out.println(mousePrevX+" "+mousePrevY);            	
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
		Layer newLayer=new Layer("layer", 
				filePath);
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
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
