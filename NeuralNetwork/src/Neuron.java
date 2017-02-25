import java.util.ArrayList;
import java.util.List;


/**
 * A Neuron is the most basic unit of a neural network. It computes the sum of the incoming values from other
 */
public class Neuron implements Node
{
    private List<Node> incoming;
    private List<Double> weights;
    private double value;


    /**
     * Initializes empty values for the new Neuron.
     */
    public Neuron()
    {
        this.incoming = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.value = 0.0;
    }

    /**
     * @return Nodes that point to this Neuron.
     */
    @Override
    public List<Node> getIncoming()
    {
        return this.incoming;
    }

    /**
     * @return Weights that correspond to each of the incoming Neurons.
     */
    @Override
    public List<Double> getIncomingWeights()
    {
        return this.weights;
    }

    /**
     * @return this Neuron's value.
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

        return 0;
    }
}
