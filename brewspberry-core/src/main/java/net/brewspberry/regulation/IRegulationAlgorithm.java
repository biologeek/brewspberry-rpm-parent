package net.brewspberry.regulation;

import java.util.List;

import net.brewspberry.business.beans.AbstractAlgorithmData;

public interface IRegulationAlgorithm<K extends AbstractAlgorithmData> {

	
	
	List<K> calculateVariationSpeed (List<K> data);
	
	boolean returnBinaryAnswerFromDataset (List<K> dataset);
	
	int returnIntegerAnswerFromDataset (List<K> dataset);
	
	
}
