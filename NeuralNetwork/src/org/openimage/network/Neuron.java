package org.openimage.network;
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
    private double previous;
    private SquashFunction squash;

    /**
     * A sigmoid curve that interpolates between 0 and 1.
     * @param input A double of the input value to be squashed.
     * @return The logistic (sigmoid) squash function of the input.
     */
    public static double LOGISTIC(double input)
    {
        return 1 / (1 + Math.pow(Math.E, -1 * input));
    }

    /**
     * A rising edge function centered at 0.5.
     * @param input A double of the input value to be squashed.
     * @return The step squash function of the input.
     */
    public static double STEP(double input)
    {
        if (input <= 0.5)
        {
            return 0;
        }
        return 1;
    }

    /**
     * Initializes empty values for the new Neuron.
     */
    public Neuron(SquashFunction squash)
    {
        this.incomingNodes = new ArrayList<>();
        this.incomingWeights = new ArrayList<>();
        this.value = 0.0;
        this.squash = squash;
    }

    /**
     * Initializes empty values and defaults to a logistic squash function for the new Neuron.
     */
    public Neuron () {
        this.incomingNodes = new ArrayList<>();
        this.incomingWeights = new ArrayList<>();
        this.value = 0.0;
        this.squash = SquashFunction.LOGISTIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Node> getIncomingNodes()
    {
        return this.incomingNodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getIncomingWeights()
    {
        return this.incomingWeights;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIncomingNode(Node node)
    {
        this.incomingNodes.add(node);
        this.incomingWeights.add((1 / Math.sqrt(this.incomingNodes.size())) * Math.random());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIncomingNode(Node node, double weight)
    {
        this.incomingNodes.add(node);
        this.incomingWeights.add(weight);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void normalizeWeights()
    {
        for (int i = 0; i < this.incomingWeights.size(); i++)
        {
            this.incomingWeights.set(i, (1 / Math.sqrt(this.incomingNodes.size())) * Math.random());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue()
    {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrevious()
    {
        return this.previous;
    }

    /**
     * {@inheritDoc}
     */
    public double activate()
    {
        this.previous = this.value;

        double weightedSum = 0;
        for (int i = 0; i < this.incomingNodes.size(); i++)
        {
            weightedSum += this.incomingWeights.get(i) * this.incomingNodes.get(i).getValue();
        }

        // Use the logistic sigmoid curve for squashing.
        if (this.squash == SquashFunction.LOGISTIC) {
            this.value = Neuron.LOGISTIC(weightedSum);
            return value;
        } else if (this.squash == SquashFunction.STEP) {
            this.value = Neuron.STEP(weightedSum);
            return value;
        }

        System.err.println("[ERROR] Unknown squash function: " + this.squash);
        return 0;
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
