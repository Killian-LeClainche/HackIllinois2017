package network;
import java.util.List;

public interface Node
{
    /**
     * @return Nodes that point to this Neuron.
     */
    public List<Node> getIncomingNodes();

    /**
     * @return Weights that correspond to each of the incoming Neurons.
     */
    public List<Double> getIncomingWeights();

    /**
     * Add a new node and respective weight.
     */
    public void addIncomingNode(Node node, double weight);

    /**
     * Add a new node and generate its respective weight.
     */
    public void addIncomingNode(Node node);

    /**
     * Randomly generate fresh weights between -1/sqrt(#inputs) and 1/sqrt(#inputs).
     */
    public void normalizeWeights();

    /**
     * @return This Neuron's squashed value.
     */
    public double getValue();

    /**
     * @return The Neuron's value before being squashed.
     */
    public double getPrevious();

    /**
     * The activation function computes an output for this Neuron based on inputs and weights.
     * @return The sum of the incoming node values multiplied by their respective weights, then squashed.
     */
    public double activate();
}
