package org.openimage.network;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 *
 * @Author Max O'Cull
 */
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
        for (Node node : inputs)
        {
            if (node != null)
            {
                this.nodes = inputs;
            }
        }
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
            if (node instanceof LSTM)
            {
            	LSTM lstm = (LSTM) node;
            	List<List<Double>> oldWeights = lstm.getListOfIncomingWeights();
            	for (List<Double> list : oldWeights)
            	{
            		for (int i = 0; i < list.size(); i++)
            		{
            			if (weightIterator.hasNext())
            			{
            				list.set(i, weightIterator.next());
            			}
            		}
            	}
            }
            else
	        {
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

    /**
     * @return A list of all weights from each node top to bottom.
     */
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
        String beforeLayerStr = "null", afterLayerStr = "null";

        if (beforeLayer != null)
            beforeLayerStr = "" + beforeLayer.hashCode();

        if (afterLayer != null)
            afterLayerStr = "" + afterLayer.hashCode();

        return "Layer { beforeLayer: " + beforeLayerStr + ", afterLayer: " + afterLayerStr + ", nodes: ["
                + nodes.toString() + "] }";
    }

    public static void main(String[] args) {
    	List<Double> list = new ArrayList<Double>();
    	for (int i = 0; i < 64; i++)
    	{
    		list.add(new Double(5));
    	}
    	
        Layer a = new Layer(8, 0);
        Layer b = new Layer(8, 0);
        Layer c = new Layer(8, 1);
        
        b.connectAfter(a, list);
        c.connectAfter(a, list);
        
        System.out.println(b.getWeights());
        System.out.println(c.getWeights());
        
        List<Double> list2 = new ArrayList<Double>();
        list2.addAll(list);
        list2.set(1, new Double(2));
        System.out.println();
        System.out.println(list);
        System.out.println(list2);
    }
}
