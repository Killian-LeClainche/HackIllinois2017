package network;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A Neuron is the most basic unit of a neural network. It computes the sum of the incoming values from other
 * TODO
 * 
 * @author Max O'Cull
 */
public class Neuron implements Node
{
    private List<Node> incomingNodes;
    private List<Double> incomingWeights;
    private double value;

    /**
     * @return The logistic (sigmoid) squash function of the input.
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
     * The activation function computes an output for this Neuron based on inputs and weights.
     * @return The sum of the incoming node values multiplied by their respective weights, then squashed.
     */
    public double activate()
    {
        double weightedSum = 0;
        for (int i = 0; i < this.incomingNodes.size(); i++)
        {
            weightedSum += this.incomingWeights.get(i) * this.incomingNodes.get(i).getValue();
        }

        // Use the logistic sigmoid curve for squashing.
        this.value = Neuron.LOGISTIC(weightedSum);
        return value;
    }

    /**
     * @return A String representation of the object and its data.
     */
    @Override
    public String toString()
    {
        return "Neuron { id: " + this.hashCode() +
                ", incomingNodes: " + Arrays.toString(this.incomingNodes.toArray()) + " " +
                ", incomingWeights: " + Arrays.toString(this.incomingNodes.toArray()) + " " +
                ", value: " + this.value + " }";
    }
}
