package se.kth.ag2411.mapalgebra;

//import java.util.Locale;
//import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class Layer {
	// Attributes
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[] values; // raster data
	public double nullValue; // value designated as "No data"
	
	
	//Constructor
	public Layer(String layerName, String fileName) {
			
		File rFile=new File(fileName);		
		// checks if the file exists
		if(rFile.exists()){ 
			
				name=layerName;
		
				try { // Exception may be thrown while reading (and writing) a file.
				// Get access to the lines of Strings stored in the file
				FileReader fReader=new FileReader(rFile);
				BufferedReader bReader = new BufferedReader(fReader);
								
				// Read first line, which starts with "ncols"
				nCols=Integer.parseInt(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));
				
				// Read second line, which starts with "nrows"
				nRows=Integer.parseInt(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));
				
				// Read third line, which starts with "xllcorner"				
				origin[0]=Double.parseDouble(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));			
				
				// Read forth line, which starts with "yllcorner"
				origin[1]=Double.parseDouble(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));		
				
				// Read fifth line, which starts with "cellsize"
				resolution=Double.parseDouble(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));
				
				// Read sixth line, which starts with "NODATA_value"
				nullValue=Double.parseDouble(bReader.readLine().split("\\s+")[1].trim().replaceAll(",", "."));							
				
				
				values=new double[nRows*nCols];				
				// Read each of the remaining lines, which represents a row of raster values
				for (int i = 0; i < nRows; i++) {
					
					String arow=bReader.readLine();					
					String[] arow_value=arow.split("\\s+");
					
					for (int j = 0; j < nCols; j++) {
						values[j+i*nCols]=Double.parseDouble(arow_value[j].trim().replaceAll(",", "."));
					}
					
				}
				
				bReader.close();
				
				
				} catch (Exception e) {
					e.printStackTrace();
					}
		}
		else {
			System.out.println("the file does not exist.");
		}
	}	
	
	public Layer(String layerName, int nRows, int nCols, double[] origin,
			double resolution, double nullValue) {
			// construct a new layer by assigning a value to each of its attributes
			this.name = layerName; // on the left hand side are the attributes of
			this.nRows = nRows; // the new layer;
			this.nCols = nCols; // on the right hand side are the parameters.
			this.origin=origin;
			this.resolution=resolution;
			this.nullValue=nullValue;
			this.values=new double[nRows*nCols];
	}
	
	// Print 
	// print this layer on the display
	public void print(){
	//Print this layer to console
		System.out.println("ncols "+nCols);
		System.out.println("nrows "+nRows);
		System.out.println("xllcorner "+origin[0]);
		System.out.println("yllcorner "+origin[1]);
		System.out.println("cellsize "+resolution);
		System.out.println("NODATA_value " + nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
			System.out.print(values[j+i*nCols]+" ");
			}
			System.out.println();
		}
	}
	
	// Save
	public void save(String outputFileName) {
	// save this layer as an ASCII file that can be imported to ArcGIS		
		
		File file = new File(outputFileName);		
		
		try {
			
			FileWriter fWriter = new FileWriter(file);
			
			// Write to the file
			fWriter.write("ncols         "+nCols+"\n"); // "\n" represents a new line
			fWriter.write("nrows         "+nRows+"\n"); 
			fWriter.write("xllcorner     "+origin[0]+"\n"); 
			fWriter.write("yllcorner     "+origin[1]+"\n"); 
			fWriter.write("cellsize      "+resolution+"\n"); 
			fWriter.write("NODATA_value  "+nullValue+"\n"); 
			
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					fWriter.write(values[j+i*nCols]+" "); 
				}
				fWriter.write("\n");
			}
			
			fWriter.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public double getMax(double[] values) {
		double maxValue=values[0];
		for(double v:values) {
			if(v>maxValue) {
				maxValue=v;
			}
		}
		return maxValue;
	}
	
	public double getMin(double[] values) {
		double minValue=values[0];
		for(double v:values) {
			if(v<minValue) {
				minValue=v;
			}
		}
		return minValue;
	}
	
	
	// create a BufferedImage of the layer in grayscale
	public BufferedImage toImage(){
		
//		creates a 24-bit RGB image
		BufferedImage grayImage=new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_BGR);
		
		//get access to its raster
		WritableRaster raster=grayImage.getRaster();
		
		//get the largest value in the layer
		double maxValueInLayer=getMax(values);
		double minValueInLayer=getMin(values);
		
		//assign a gray value to every cell
		for(int i=0;i<nRows;i++) {
			for(int j=0;j<nCols;j++) {
								
				//the largest value of a cell
//				double[] cellValues=new double[]{values[j+i*nCols],values[j+i*nCols+1],values[j+i*nCols+2]};
//				double maxValueInCell=Arrays.stream(cellValues).max().getAsDouble();
				
				//convert a cell value to a grayscale value
//				assigning each cell a 0-255 grayscale
//				(or lightness) value proportional to the difference of 
//				the largest value in the layer and that cell’s value
				int gray=(int) (255*(values[j+i*nCols]-minValueInLayer)/(maxValueInLayer-minValueInLayer));				
				
				//System.out.println(gray);
				
				raster.setPixel(j, i, new int[]{gray,gray,gray});		
			}
		}		
		return grayImage;
	}
	
	// visualize a BufferedImage of the layer in color
	public BufferedImage toImage(double[] interest){
		
//		creates a 24-bit RGB image
		BufferedImage colorImage=new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_BGR);
		
		//get access to its raster
		WritableRaster raster=colorImage.getRaster();	
		
		//creating a random RGB color for each of the input values
		int[][] color=new int[interest.length][3];
		for(int i=0;i<interest.length;i++) {			
			color[i][0]=(int)(Math.random()*255);
			color[i][1]=(int)(Math.random()*255);
			color[i][2]=(int)(Math.random()*255);			
		}
		
//		assigning each cell 
//		the color corresponding to that cell’s value		
		for(int i=0;i<nRows;i++) {
			for(int j=0;j<nCols;j++) {				
				double v=values[j+i*nCols];
				for(int k=0;k<interest.length;k++) {
					if(interest[k]==v) {
						raster.setPixel(j, i, color[k]);			
					}
				}
				
			}
		}	
		
		return colorImage;
	}
	
	public Layer localSum(Layer inLayer, String outLayerName){
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, 
		resolution, nullValue);
		for(int i=0; i<(nRows*nCols); i++){
			outLayer.values[i] = values[i] + inLayer.values[i];
		}
		return outLayer;
	}
	
	public Layer focalVariety(int r, boolean isSquare, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, 
				resolution, nullValue);
		
		ArrayList<Double> variety=new ArrayList<>();
		for(int i=0;i<nCols*nRows;i++) {
			variety.clear();
			
			//get neighbor indices
			int[] neighbor=getNeighborhood(i,r,isSquare);
			for(int index:neighbor) {
				if(!variety.contains(values[index])) {
					variety.add(values[index]);
				}
			}
			outLayer.values[i]=variety.size();		
		}	
		return outLayer;
	}
	
	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, 
				resolution, nullValue);
		HashMap<Double,Double> zonalMin=new HashMap<>();
		
		for(int i=0;i<nCols*nRows;i++) {
			
			double key=zoneLayer.values[i];
			double value=values[i];
			
			if(zoneLayer.values[i]!=zoneLayer.nullValue) {
				if(!zonalMin.containsKey(key)) {
					zonalMin.put(key,value);
					continue;
				}
				
				if(value<zonalMin.get(key) || zonalMin.get(key)==nullValue) {
					zonalMin.put(key,value);
				}
			}else {
				zonalMin.put(nullValue,nullValue); 
			}
					
		}
		
		for(int i=0;i<nCols*nRows;i++) {
			outLayer.values[i]=zonalMin.get(zoneLayer.values[i]);
		}		
		
		return outLayer;
	}
	
	private int[] getNeighborhood(int index, int radius, boolean isSquare) {
		ArrayList<Integer> indices=new ArrayList<>();
		
		int row=index/nCols;
		int column=index%nCols;
		
		int rowStart=(row-radius)<0?0:row-radius;
		int rowEnd=(row+radius)>(nRows-1)?nRows-1:row+radius;
		int columnStart=(column-radius)<0?0:column-radius;
		int columnEnd=(column+radius)>(nCols-1)?nCols-1:column+radius;
		
		if(isSquare) {
			for(int i=rowStart;i<=rowEnd;i++){
				for(int j=columnStart;j<=columnEnd;j++) {
					indices.add(j+i*nCols);
				}
			}					
		}else{
			for(int i=rowStart;i<=rowEnd;i++){
				for(int j=columnStart;j<=columnEnd;j++) {
					double distance=Math.sqrt((i-row)*(i-row)+(j-column)*(j-column));
					if(distance<=radius) {
						indices.add(j+i*nCols);
					}
				}
			}	
		}		
		
		// Convert the list to an array of integer values
		int counter=0;
		int[] intArray=new int[indices.size()];
		for(Integer i:indices) {
			intArray[counter]=i;
			counter++;
		}
		
		return intArray;		
	}
	
}
