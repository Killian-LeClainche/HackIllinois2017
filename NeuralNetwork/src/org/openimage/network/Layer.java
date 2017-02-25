package org.openimage.network;

import java.util.ArrayList;

public class Layer
{
    public Layer (int size, int lstmCount)
    {
        // TODO Generate a layer with Size Nodes, with a set number of LSTM Nodes.
    }

    public Layer (ArrayList<Node> nodes)
    {
        // TODO Generate a layer with given Nodes.
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
