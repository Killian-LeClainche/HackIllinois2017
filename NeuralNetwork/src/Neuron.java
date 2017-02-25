import java.util.ArrayList;
import java.util.List;


/**
 * A Neuron is the most basic unit of a neural network. It computes the sum of the incoming values from other
 */
public class Neuron implements Node
{
    private List<Node> incomingNodes;
    private List<Double> incomingWeights;
    private double value;

    /**
     * @return The logistic (sigmoid) function of the input.
     */
    public static double LOGISTIC(double input)
    {
        return 1 / (1 + Math.pow(Math.E, -1 * input));
    }

    /**
     * Initializes empty values for the new Neuron.
     */
    public Neuron()
    {
        this.incomingNodes = new ArrayList<>();
        this.incomingWeights = new ArrayList<>();
        this.value = 0.0;
    }

    /**
     * @return Nodes that point to this Neuron.
     */
    @Override
    public List<Node> getIncomingNodes()
    {
        return this.incomingNodes;
    }

    /**
     * @return Weights that correspond to each of the incoming Neurons.
     */
    @Override
    public List<Double> getIncomingWeights()
    {
        return this.incomingWeights;
    }

    /**
     * @return This Neuron's squashed value.
     */
    @Override
    public double getValue()
    {
        return this.value;
    }

    /**
     * The squash function computes an output for this Neuron based on inputs and weights.
     * @return The sum of the incoming node values multiplied by their respective weights.
     */
    public double squash()
    {
        double weightedSum = 0;
        for (int i = 0; i < this.incomingNodes.size(); i++)
        {
            weightedSum += this.incomingWeights.get(i) * this.incomingNodes.get(i).getValue();
        }

        this.value = Neuron.LOGISTIC(weightedSum);
        return value;
    }
}
