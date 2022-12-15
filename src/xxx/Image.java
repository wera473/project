package xxx;

import javax.swing.*;


public class Image extends JFrame {
	JFrame frame;
	JLabel displayfield;
	ImageIcon image;
	
	public Image() {
		frame = new JFrame("Pierogi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		try {
			image = new ImageIcon(getClass().getResource("perogi.png"));
			displayfield = new JLabel(image);
			frame.add(displayfield);
		} catch(Exception e) {
			System.out.println("Image cannot be found");
		}
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		Image i = new Image();
	}
}
