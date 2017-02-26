package org.openimage.network;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Layer
{
    List<Node> nodes;

    public Layer (int size, int lstmCount)
    {
        // TODO Generate a layer with Size Nodes, with a set number of LSTM Nodes.
        nodes = new ArrayList<Node>(size);

        for (int i = 0; i < lstmCount; i++)
        {
            LSTM lstm = new LSTM();
            nodes.add(lstm);
        }

        for (int i = lstmCount; i < size; i++)
        {
            Neuron neuron = new Neuron();
            nodes.add(neuron);
        }
    }

    public Layer (ArrayList<Node> nodes, ArrayList<Double> weights)
    {
        // TODO Generate a layer with given Nodes and respective weights.

    }

    public Layer(List<Node> inputs)
	{
		// TODO Auto-generated constructor stub
	}

	public void addWeights(List<Double> weights)
    {
        ListIterator<Node> nodeIterator = nodes.listIterator();
        ListIterator<Double> weightIterator = weights.listIterator();

        while(nodeIterator.hasNext())
        {
            Node node = nodeIterator.next();
            List<Double> oldWeights = node.getIncomingWeights();
            for (int i = 0; i < oldWeights.size(); i++)
            {
                if (weightIterator.hasNext())
                {
                    oldWeights.set(i, weightIterator.next());
                }
            }
        }
    }

    public void connectAfter(Layer before)
    {
        // TODO Connect synapses from each Node in Before pointing to each Node in After.
    }

    public ArrayList<Double> getValue()
    {
        // TODO Return an ArrayList of the current outputs.
        return null;
    }

    public ArrayList<Double> activate()
    {
        // TODO Activate each Node in the Layer. Return an ArrayList of the outputs.
        return null;
    }

    public void addNode(Node node)
    {
        // TODO Add node to the layer and connect it to adjacent Layers (if possible).
    }

    public void normalizeWeights()
    {
        // TODO Normalize the weights of each Node in the layer.
    }

    public void reset()
    {
        // TODO Set all Node values in the Layer to 0.
    }

    public String toString()
    {
        // TODO Return a String representation of the Layer.
        return null;
    }
}
