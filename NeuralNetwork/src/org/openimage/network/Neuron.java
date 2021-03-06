package org.openimage.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Neuron is the most basic unit of a neural network. It computes the sum of the incoming values multiplied by
 * their respective weights, then squashes the sum using a sigmoid function to produce an output signal.
 * 
 * @author Max O'Cull
 */
public class Neuron implements Node
{
	
	protected List<Node> incomingNodes;
	private List<Double> incomingWeights;
	protected double value;
	protected double previous;
	private SquashFunction squash;

	/**
	 * A sigmoid curve that interpolates between 0 and 1.
	 * 
	 * @param input A double of the input value to be squashed.
	 * @return The logistic (sigmoid) squash function of the input.
	 */
	public static double LOGISTIC(double input)
	{
		return 1 / (1 + Math.pow(Math.E, -1 * input));
	}

	/**
	 * A rising edge function centered at 0.5.
	 * 
	 * @param input
	 *            A double of the input value to be squashed.
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
		this.incomingNodes = new ArrayList<Node>();
		this.incomingWeights = new ArrayList<Double>();
		this.incomingNodes.add(null); // For Bias.
		this.incomingWeights.add(Math.random()/2); // For Bias.
		this.value = 0.0;
		this.previous = 0.0;
		this.squash = squash;
	}

	/**
	 * Initializes empty values and defaults to a logistic squash function for
	 * the new Neuron.
	 */
	public Neuron()
	{
		this(SquashFunction.LOGISTIC);
	}

	public List<Node> getIncomingNodes()
	{
		return this.incomingNodes;
	}

	public List<Double> getIncomingWeights()
	{
		return this.incomingWeights;
	}

	public void addIncomingNode(Node node)
	{
		this.incomingNodes.add(node);
		this.incomingWeights.add((1 / Math.sqrt(this.incomingNodes.size())) * Math.random());
	}

	public void addIncomingNode(Node node, double weight)
	{
		this.incomingNodes.add(node);
		this.incomingWeights.add(weight);
	}

	public void normalizeWeights()
	{
		for (int i = 0; i < this.incomingWeights.size(); i++)
		{
			this.incomingWeights.set(i, (1.0 / Math.sqrt(this.incomingNodes.size())) * Math.random());
		}
	}

	public double getValue()
	{
		return this.value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public double getPrevious()
	{
		return this.previous;
	}

	public double activate()
	{
		this.previous = this.value;

		double weightedSum = 0;
		for (int i = 0; i < this.incomingNodes.size(); i++)
		{
			if (this.incomingNodes.get(i) == null)
				weightedSum += this.incomingWeights.get(i) * -1;
			else
				weightedSum += this.incomingWeights.get(i) * this.incomingNodes.get(i).getValue();
		}

		// Use the logistic sigmoid curve for squashing.
		if (this.squash == SquashFunction.LOGISTIC)
		{
			this.value = Neuron.LOGISTIC(weightedSum);
			return value;
		} else if (this.squash == SquashFunction.STEP)
		{
			this.value = Neuron.STEP(weightedSum);
			return value;
		}

		System.err.println("[ERROR] Unknown squash function: " + this.squash);
		return 0;
	}

	/**
	 * Moves a specific input through the Neuron. Use sparingly.
	 * @param input The value to be squashed.
	 * @return The squashed value.
	 */
	public double activate(double input)
	{
		this.previous = this.value;

		// Use the logistic sigmoid curve for squashing.
		if (this.squash == SquashFunction.LOGISTIC)
		{
			this.value = Neuron.LOGISTIC(input);
			return value;
		} else if (this.squash == SquashFunction.STEP)
		{
			this.value = Neuron.STEP(input);
			return value;
		}

		System.err.println("[ERROR] Unknown squash function: " + this.squash);
		return 0;
	}

	public String toString()
	{
		return "Neuron { id: " + this.hashCode() + ", incomingNodes: [" + this.incomingNodes.size()
				+ " " + "], incomingWeights: [" + this.incomingNodes.size() + "] " + ", value: "
				+ this.value + " }";
	}

	public void reset()
	{
		this.value = 0.0;
	}

	public static void main(String[] args) {
		for (int x = -100; x < 100; x++) {
			System.out.println(x / 100.0 + "\t" + LOGISTIC(x / 100.0));
		}
	}
}
