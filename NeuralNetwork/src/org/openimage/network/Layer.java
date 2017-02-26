package org.openimage.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Layer
{
    List<Node> nodes;
    Layer beforeLayer;
    Layer afterLayer;

    /**
     * Generate a layer with Size Nodes, with a set number of LSTM Nodes.
     * @param size The number of nodes to generate.
     * @param lstmCount The number of those nodes which should be LSTM's.
     */
    public Layer (int size, int lstmCount)
    {
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

    /**
     * Place nodes within a layer data structure.
     * @param inputs The inputs to be inserted.
     */
    public Layer(List<Node> inputs)
	{
        this.nodes = inputs;
	}

    /**
     * For each incoming synapse, apply the respective weight.
     * @param weights The weights of the synapses corresponding from top to bottom.
     */
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

    /**
     * Connect synapses from each Node in Before pointing to each Node in After.
     * @param before The layer that preceeds the current one.
     */
    public void connectAfter(Layer before)
    {
    	beforeLayer = before;
    	before.afterLayer = this;

    	nodes.forEach(n -> before.nodes.forEach(n1 -> n.addIncomingNode(n1)));
    }

    /**
     * Connect synapses from each Node in Before pointing to each Node in After. Also connect their respective weights.
     * @param before The layer the preceeds this one.
     * @param weights The weights that correspond to the Nodes in this layer from top to bottom.
     */
    public void connectAfter(Layer before, List<Double> weights)
    {
    	beforeLayer = before;
    	before.afterLayer = this;

        connectAfter(before);
        addWeights(weights);

        /*for(int i = 0; i < before.nodes.size(); i++)
    	{
    		for(int j = 0; j < nodes.size(); j++)
    		{
    			nodes.get(j).addIncomingNode(before.nodes.get(i), weights.get(i + j * before.nodes.size()));
    		}
    	}*/
    }

    /**
     * @return List of current node values.
     */
    public List<Double> getValue()
    {
    	List<Double> list = new ArrayList<>();
    	nodes.forEach(node -> list.add(node.getValue()));
        return list;
    }

    /**
     * Activates each node in the Layer.
     * @return A List of values returned by each Node.
     */
    public List<Double> activate()
    {
    	List<Double> list = new ArrayList<>();
    	nodes.forEach(node -> list.add(node.activate()));
        return list;
    }

    /**
     * Add node to the layer and connect it to adjacent Layers (if possible).
     * @param node The node to add to the layer.
     */
    public void addNode(Node node)
    {
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

    /**
     * Normalize the weights of each Node in the layer.
     */
    public void normalizeWeights()
    {
    	nodes.forEach(node -> node.normalizeWeights());
    }

    public List<Double> getWeights()
    {
        List<Double> result = new ArrayList<Double>();
        for (Node node : nodes)
        {
            for (Double weight : node.getIncomingWeights())
            {
                result.add(weight);
            }
        }

        return result;
    }

    /**
     * Clear the values of every Node in the layer.
     */
    public void reset()
    {
    	nodes.forEach(node -> node.reset());
    }

    @Override
    public String toString()
    {
        return "Layer { beforeLayer: " + beforeLayer + ", afterLayer: " + afterLayer + ", nodes: "
                + Arrays.toString(nodes.toArray()) + " }";
    }
    
    public static void main(String args[])
    {
    	
    }
}
