package org.openimage.network;

import java.util.ArrayList;
import java.util.List;

/**
 * LSTM stands for Long-Short Term Memory
 *
 * @author Max O'Cull
 */
public class LSTM implements Node
{
    public Node keep;
    public Node write;
    public Node read;
    public Node memory;
    public Node input;
    public Node output;

    public double previous;

    public LSTM ()
    {
        keep = new Neuron(SquashFunction.STEP);
        write = new Neuron(SquashFunction.STEP);
        read = new Neuron(SquashFunction.STEP);
        memory = new Neuron();
        input = new Sensor();
        output = new Neuron();

        this.memory.addIncomingNode(memory); // The memory has a loopback for "remembering".
        this.memory.addIncomingNode(input); // Feed gated input to memory.
        this.output.addIncomingNode(memory); // Feed memory to gated output.
    }

    public List<Node> getIncomingNodes()
    {
        List<Node> nodes = new ArrayList<>();

        nodes.addAll(this.keep.getIncomingNodes());
        nodes.addAll(this.write.getIncomingNodes());
        nodes.addAll(this.read.getIncomingNodes());
        nodes.addAll(this.memory.getIncomingNodes());
        nodes.addAll(this.input.getIncomingNodes());

        return nodes;
    }


    public List<Double> getIncomingWeights()
    {
        List<Double> weights = new ArrayList<>();

        weights.addAll(this.keep.getIncomingWeights());
        weights.addAll(this.write.getIncomingWeights());
        weights.addAll(this.read.getIncomingWeights());
        weights.addAll(this.memory.getIncomingWeights());
        weights.addAll(this.input.getIncomingWeights());

        return weights;
    }

    public void addIncomingNode(Node node, double weight)
    {
        
    }

    public void addIncomingNode(Node node)
    {

    }

    public void normalizeWeights()
    {

    }

    public double getValue()
    {
        // Read's value is a dynamic weight that controls the output.
        return this.memory.getValue() * this.read.getValue();
    }


    public double getPrevious()
    {
        return this.previous;
    }


    public double activate()
    {
        this.previous = this.getValue();

        this.keep.activate();
        this.write.activate();
        this.read.activate();

        this.memory.getIncomingWeights().set(0, keep.getValue());
        this.memory.activate();

        return 0;
    }
}
