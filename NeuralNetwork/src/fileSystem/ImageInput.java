package fileSystem;

public class ImageInput
{
	private double[] valueArray;
	private String classification;
	
	public ImageInput(String classification, double[] valueArray)
	{
		this.classification = classification;
		this.valueArray = valueArray;
	}

	public double[] getDoubleArr()
	{
		return valueArray;
	}

	public String getClassification()
	{
		return classification;
	}
}
