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

    public void activate()
    {
        // TODO Activate each Node in the Layer.
    }

    public String toString()
    {
        // TODO Return a String representation of the Layer.
        return null;
    }
}
