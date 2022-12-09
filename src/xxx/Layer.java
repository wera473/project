package xxx;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Layer {
	// Attributes
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[][] values; // data. Alternatively, public double[][] values;
	public double nullValue; // value designated as "No data"
	
	
	// Constructor
	public Layer(String layerName, String fileName) {
		// build a layer from ASCII raster file
		try {
			File rFile = new File(fileName); // fileName = "./data/raster3x4.txt"
			FileReader fReader = new FileReader(rFile);
			BufferedReader bReader = new BufferedReader(fReader);
			String text;

			text = bReader.readLine();
			int count = 1;
			// count == 1
			String nCols2 = text.substring(5).trim();
			nCols = Integer.parseInt(nCols2);
			text = bReader.readLine();
			count++;
			// count == 2
			String nRows2 = text.substring(5).trim();
			nRows = Integer.parseInt(nRows2);
			values = new double[nRows][nCols];
			text = bReader.readLine();
			count++;
			// count == 3
			String originx_str = text.substring(9).trim();
			origin[0] = Double.parseDouble(originx_str);
			text = bReader.readLine();
			count++;
			// count == 4
			String originy_str = text.substring(9).trim();
			origin[1] = Double.parseDouble(originy_str);
			text = bReader.readLine();
			count++;
			// count == 5
			String cellsize_str = text.substring(8).trim();
			resolution = Double.parseDouble(cellsize_str);
			text = bReader.readLine();
			count++;
			// count == 6
			String null_str = text.substring(12).trim();
			nullValue = Double.parseDouble(null_str);
			text = bReader.readLine();
			count++;
			// count >= 7
			while (text != null) {
				if (count >= 7) {
					String[] values_str = text.split(" ");
					for (int i = 0; i < nCols; i = i + 1) {
						int j = count - 7;
						values[j][i] = Double.parseDouble(values_str[i]);
					}
				}

				text = bReader.readLine();
				count = count + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Layer(String outLayerName, int nRows, int nCols, double[] origin, double resolution, double nullValue) {
		// if you want to create a new blank layer
		name = outLayerName;
		this.nRows = nRows;
		this.nCols = nCols;
		this.origin = origin;
		this.resolution = resolution;
		this.nullValue = nullValue;

		values = new double[nRows][nCols];

	}

	// Print
	public void print() {
		// Print this layer to console
		System.out.println("ncols " + nCols);
		System.out.println("nrows " + nRows);
		System.out.println("xllcorner " + origin[0]);
		System.out.println("yllcorner " + origin[1]);
		System.out.println("cellsize " + resolution);
		System.out.println("NODATA_value " + nullValue);
		for (int k = 0; k < nRows; k++) {
			for (int h = 0; h < nCols; h++) {
				System.out.print(values[k][h] + " ");
			}
			System.out.println();
		}
	}

	// Save
	public void save(String outputFileName) {
		// save this layer as an ASCII file that can be imported to ArcGIS
		File file = new File(outputFileName);
		FileWriter fWriter;
		try {
			fWriter = new FileWriter(file);
			fWriter.write("ncols	" + nCols + "\n");
			fWriter.write("nrows	" + nRows + "\n");
			fWriter.write("xllcorner	" + origin[0] + "\n");
			fWriter.write("yllcorner	" + origin[1] + "\n");
			fWriter.write("cellsize	" + resolution + "\n");
			fWriter.write("NODATA_value	" + nullValue + "\n");
			for (int k = 0; k < nRows; k++) {
				for (int h = 0; h < nCols; h++) {
					fWriter.write(values[k][h] + " ");
				}
				fWriter.write("\n");
			}
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	// Ex02

	private double getMax() {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] > max) {
					max = values[i][j];
				}
			}
		}
		return max;
	}

	private double getMin() {
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] < min) {
					min = values[i][j];
				}
			}
		}
		return min;
	}

	public double useGetMaxIndirectly() {
		double max = this.getMax();
		return max;
	}

	public double useGetMinIndirectly() {
		double min = this.getMin();
		return min;
	}

	public BufferedImage toImage() {
		// create a BufferedImage of the layer in grayscale
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();

		double value_max = getMax();
		double value_min = getMin();

		double rescaledvalue_double;
		int rescaledvalue;
		int[] color = new int[3];
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				rescaledvalue_double = (255 * (value_max - values[i][j]) / (value_max - value_min));
				rescaledvalue = (int) rescaledvalue_double;
				color[0] = rescaledvalue;
				color[1] = rescaledvalue;
				color[2] = rescaledvalue;
				raster.setPixel(j, i, color);
			}
		}

		return image;
	}

	public BufferedImage toImage(double[] voi) {
		// create a BufferedImage of the layer using value of interest
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();

		int voi_len = voi.length;
		int[][] color = new int[voi_len][3];
		int[] color_default = new int[3];
		color_default[0] = 255;
		color_default[1] = 255;
		color_default[2] = 255;

		for (int i = 0; i < voi_len; i++) {
			for (int j = 0; j < 3; j++) {
				color[i][j] = (int) (Math.random() * (255 - 0 + 1) + 0);
			}
		}
		// (10,20,30)
		// voi[0]=10

		// 20 1 color[1]

		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				raster.setPixel(j, i, color_default);
			}
		}

		for (int i = 0; i < voi_len; i++) {
			for (int j = 0; j < nRows; j++) {
				for (int k = 0; k < nCols; k++) {
					// System.out.println("i:"+i+" j:"+j+" k:"+k);
					if (values[j][k] == voi[i]) {
						raster.setPixel(k, j, color[i]);
					}
				}
			}
		}

		return image;

	}

	// algorithms
	// 01 local
	public Layer localSum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
			}
		}
		return outLayer;
	}
	public Layer localMaximum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] >= inLayer.values[i][j]) outLayer.values[i][j] = values[i][j];
				else outLayer.values[i][j] = inLayer.values[i][j];
			}
		}
		return outLayer;
	}
	public Layer localMinimum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] <= inLayer.values[i][j]) outLayer.values[i][j] = values[i][j];
				else outLayer.values[i][j] = inLayer.values[i][j];
			}
		}
		return outLayer;
	}
	
	public Layer localMean(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				outLayer.values[i][j] = (values[i][j] + inLayer.values[i][j])/2;
			}
		}
		return outLayer;
	}
	
	//02 focal
	
	public ArrayList<Double> getNeighborhood(int i, int j, int r, boolean IsSquare) {
		//if (IsSquare) {
			// IsSquare == true
			int i_left = Math.max(i - r,0); // left boundary of the cell
			int i_right = Math.min(i + r,nRows - 1); // right boundary of the cell
			int j_left = Math.max(j - r,0); // upper boundary
			int j_right = Math.min(j + r,nCols - 1); // lower boundary
			
			

			// double[] neighborhood = new double[(i_right - i_left + 1)*(j_right - j_left +
			// 1)];
			ArrayList<Double> neighborhood = new ArrayList<Double>();
			Double neighborObj;
			for (int cell_i = i_left; cell_i <= i_right; cell_i++) { // cell_i is the row index of the Layer
				for (int cell_j = j_left; cell_j <= j_right; cell_j++) { // cell_j is the col index of the Layer
					if (IsSquare==true) {
						System.out.println("aaaaaaaaaaaaaaaaaaaaa");
						neighborObj = values[cell_i][cell_j];
						neighborhood.add(neighborObj);
					}
					else { //
						double dist = Math.sqrt(Math.pow((i-cell_i),2)+Math.pow((j-cell_j),2));
						System.out.println("dist: " + dist + "; r: "+r);
								if (dist <= r) {
									neighborObj = values[cell_i][cell_j];
									neighborhood.add(neighborObj);
								}
					}

				}
			}

			return neighborhood;
		//} else {
		//	// the code for "IsSquare == false" is not constructed
		//	System.out.println(
		//			"Warning(getNeighborhood): The code for \"IsSquare == false\" is not constructed. You get a empty array.");
		//	ArrayList<Double> empty_arraylist = new ArrayList<Double>();
		//	return empty_arraylist;
		//}

	}

	
	public Layer focalSum(int r, boolean isSquare, String outLayerName) {
		// r means radius
		// IsSquare means whether the neighborhood is a square or circle
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		ArrayList<Double> neighborhood;
		
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				neighborhood = getNeighborhood(i, j, r, isSquare);
				double neighborhoodSum = 0;
				for (double neighborhoodValue : neighborhood) {
					neighborhoodSum += neighborhoodValue;
				}

				outLayer.values[i][j] = neighborhoodSum;
				//System.out.println("outLayer.values" + outLayer.values[i][j]);
			}
		}
		return outLayer;
		
		
	}
	
	public Layer focalVariety(int r, boolean isSquare, String outLayerName) {
		// r means radius
		// IsSquare means whether it is a square or circle
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		ArrayList<Double> neighborhood;

		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				neighborhood = getNeighborhood(i, j, r, isSquare);
				Set<Double> uniqueValSet = new HashSet<Double>(neighborhood); // unique values of neighborhood cell
				int size_uniqueValSet = uniqueValSet.size(); // get variety of cell

				outLayer.values[i][j] = size_uniqueValSet;
				//System.out.println("outLayer.values" + outLayer.values[i][j]);
			}
		}
		return outLayer;
	}

	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		HashMap<Double, Double> hm = new HashMap<Double, Double>(); // Create a HashMap
		// key: zone value:minimum
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				// if (hm.get(zoneLayer.values[i][j])==null) {
				if (!hm.containsKey(zoneLayer.values[i][j])) {
					hm.put(zoneLayer.values[i][j], values[i][j]);
				} else {
					if (hm.get(zoneLayer.values[i][j]) > values[i][j]) {
						hm.put(zoneLayer.values[i][j],values[i][j]);
					}
				}

			}
		}
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				outLayer.values[i][j] = hm.get(zoneLayer.values[i][j]);
			}
		}
		// Collection<Integer> hm_values = hm.values();
		System.out.println(hm.values());
		return outLayer;
	}
	
	
}
