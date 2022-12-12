package xxx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {
	// here you can find algorithms that can take multiple layers as input
	public Layer localMean_ml(Layer[] inLayers, String outLayerName) {
		// This method applies when taking multiple layers as input
		
		// we assume that all layers in inLayers have the same number of rows and columns. *Develop a new method to check if necessary*.
		int nRows = inLayers[0].nRows;
		int nCols = inLayers[0].nCols;
		double[] origin = inLayers[0].origin;
		double resolution = inLayers[0].resolution;
		double nullValue = inLayers[0].nullValue;
		Layer sumLayer = inLayers[0];
		Layer[] remainingInLayers = Arrays.copyOfRange(inLayers, 1, inLayers.length);
		
		//
		for (Layer inLayer : remainingInLayers) {
			sumLayer = sumLayer.localSum(inLayer,sumLayer.name);
		}
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				outLayer.values[i][j] = sumLayer.values[i][j]/inLayers.length;
			}
		}
		return outLayer;
	}
	
	public static Layer localVariety(Layer[] inLayers, String outLayerName) {
		int nRows = inLayers[0].nRows;
		int nCols = inLayers[0].nCols;
		double[] origin = inLayers[0].origin;
		double resolution = inLayers[0].resolution;
		double nullValue = inLayers[0].nullValue;
		
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				//for every cell of outLayer:
				ArrayList<Double> outCell = new ArrayList<Double>(); //store all values in the same cell of inLayers
				for (Layer inLayer : inLayers) {
					outCell.add(inLayer.values[i][j]); //add the current cell of inLayer
				}
				Set<Double> uniqueValSet_outCell = new HashSet<Double>(outCell); // unique values of current cell
				outLayer.values[i][j] = uniqueValSet_outCell.size(); // get variety of cell
			}
		}
		return outLayer;
	}
	
}
