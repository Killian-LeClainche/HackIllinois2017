package org.openimage.network;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Layer
{
    List<Node> nodes;
    Layer beforeLayer;
    Layer afterLayer;

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
    	beforeLayer = before;
    	before.afterLayer = this;
        // TODO Connect synapses from each Node in Before pointing to each Node in After.
    	nodes.forEach(n -> before.nodes.forEach(n1 -> n.addIncomingNode(n1)));
    }
    
    public void connectAfter(Layer before, double[][] weights)
    {
    	beforeLayer = before;
    	before.afterLayer = this;
    	for(int i = 0; i < before.nodes.size(); i++)
    	{
    		for(int j = 0; j < nodes.size(); j++)
    		{
    			nodes.get(j).addIncomingNode(before.nodes.get(i), weights[j][i]);
    		}
    	}
    }

    public List<Double> getValue()
    {
    	List<Double> list = new ArrayList<>();
    	nodes.forEach(node -> list.add(node.getValue()));
        return list;
    }

    public List<Double> activate()
    {
    	List<Double> list = new ArrayList<>();
    	nodes.forEach(node -> list.add(node.activate()));
        return list;
    }

    public void addNode(Node node)
    {
        // TODO Add node to the layer and connect it to adjacent Layers (if possible).
    	nodes.add(node);
    	if(afterLayer != null)
    	{
    		afterLayer.nodes.forEach(n -> n.addIncomingNode(node));
    	}
    	if(beforeLayer != null)
    	{
    		beforeLayer.nodes.forEach(n -> node.addIncomingNode(n));
    	}
    }

    public void normalizeWeights()
    {
        // TODO Normalize the weights of each Node in the layer.
    	nodes.forEach(node -> node.normalizeWeights());
    }

    public void reset()
    {
        // TODO Set all Node values in the Layer to 0.
    	nodes.forEach(node -> node.reset());
    }

    public String toString()
    {
        // TODO Return a String representation of the Layer.
        return null;
    }
}
