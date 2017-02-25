package org.openimage.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * LSTM stands for Long-Short Term Memory. An LSTM node is centered around a recurrent neuron (points to itself).
 * It receives an input which is gated by an input neuron, and outputs a signal which is gated by an output neuron.
 * Additionally, the value of the memory neuron is gated by a keep neuron. Each of the gate neuron uses a step function
 * for squashing instead of a sigmoid function. Thus, the I/O of the memory node is opened or closed by multiplying
 * weights of 1 or 0 to the synapses that connect them.
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

    /**
     * @return All external nodes that input to the LSTM.
     */
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

    /**
     * @return All external weights corresponding to all external inputs of the LSTM.
     */
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

    /**
     * Add node input to a gate with a missing input, otherwise a random gate.
     * @param node The node to be connected.
     * @param weight The weight to be multiplied by the node input.
     */
    public void addIncomingNode(Node node, double weight)
    {
        if (this.keep.getIncomingNodes().size() <= 0)
        {
            this.keep.getIncomingNodes().add(node);
            this.keep.getIncomingWeights().add(weight);
        }
        else if (this.write.getIncomingNodes().size() <= 0)
        {
            this.write.getIncomingNodes().add(node);
            this.write.getIncomingWeights().add(weight);
        }
        else if (this.read.getIncomingNodes().size() <= 0)
        {
            this.read.getIncomingNodes().add(node);
            this.read.getIncomingWeights().add(weight);
        }
        else if (this.memory.getIncomingNodes().size() <= 0)
        {
            this.memory.getIncomingNodes().add(node);
            this.memory.getIncomingWeights().add(weight);
        }
        else if (this.input.getIncomingNodes().size() <= 0)
        {
            this.input.getIncomingNodes().add(node);
            this.input.getIncomingWeights().add(weight);
        }
        else
        {
            int seed = org.openimage.Param.rng.nextInt(4 + 1);
            switch (seed)
            {
                case 0:
                    this.keep.getIncomingNodes().add(node);
                    this.keep.getIncomingWeights().add(weight);
                    break;
                case 1:
                    this.write.getIncomingNodes().add(node);
                    this.write.getIncomingWeights().add(weight);
                    break;
                case 2:
                    this.read.getIncomingNodes().add(node);
                    this.read.getIncomingWeights().add(weight);
                    break;
                case 3:
                    this.memory.getIncomingNodes().add(node);
                    this.memory.getIncomingWeights().add(weight);
                    break;
                case 4:
                    this.input.getIncomingNodes().add(node);
                    this.input.getIncomingWeights().add(weight);
                    break;
                default:
                    System.err.println("ERROR: Invalid RNG case " + seed);
            }
        }
    }

    /**
     * Add node input to a gate with a missing input, otherwise a random gate. A normalized weight is generated and set.
     * @param node The node to be connected.
     */
    public void addIncomingNode(Node node)
    {
        if (this.keep.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.keep.getIncomingNodes().size())) * Math.random());
        }
        else if (this.write.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.write.getIncomingNodes().size())) * Math.random());
        }
        else if (this.read.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.read.getIncomingNodes().size())) * Math.random());
        }
        else if (this.memory.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.memory.getIncomingNodes().size())) * Math.random());
        }
        else if (this.input.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.input.getIncomingNodes().size())) * Math.random());
        }
        else
        {
            int seed = org.openimage.Param.rng.nextInt(4 + 1);
            switch (seed)
            {
                case 0:
                    addIncomingNode(node, (1 / Math.sqrt(this.keep.getIncomingNodes().size())) * Math.random());
                    break;
                case 1:
                    addIncomingNode(node, (1 / Math.sqrt(this.write.getIncomingNodes().size())) * Math.random());
                    break;
                case 2:
                    addIncomingNode(node, (1 / Math.sqrt(this.read.getIncomingNodes().size())) * Math.random());
                    break;
                case 3:
                    addIncomingNode(node, (1 / Math.sqrt(this.memory.getIncomingNodes().size())) * Math.random());
                    break;
                case 4:
                    addIncomingNode(node, (1 / Math.sqrt(this.input.getIncomingNodes().size())) * Math.random());
                    break;
                default:
                    System.err.println("ERROR: Invalid RNG case " + seed);
            }
        }
    }

    public void normalizeWeights()
    {
        for (int i = 0; i < this.keep.getIncomingNodes().size(); i++)
        {
            this.keep.getIncomingWeights().set(i, (1 / Math.sqrt(this.keep.getIncomingNodes().size())) * Math.random());
        }

        for (int i = 0; i < this.write.getIncomingNodes().size(); i++)
        {
            this.write.getIncomingWeights().set(i, (1 / Math.sqrt(this.write.getIncomingNodes().size())) * Math.random());
        }

        for (int i = 0; i < this.read.getIncomingNodes().size(); i++)
        {
            this.read.getIncomingWeights().set(i, (1 / Math.sqrt(this.read.getIncomingNodes().size())) * Math.random());
        }

        for (int i = 0; i < this.memory.getIncomingNodes().size(); i++)
        {
            this.memory.getIncomingWeights().set(i, (1 / Math.sqrt(this.memory.getIncomingNodes().size())) * Math.random());
        }

        for (int i = 0; i < this.input.getIncomingNodes().size(); i++)
        {
            this.input.getIncomingWeights().set(i, (1 / Math.sqrt(this.input.getIncomingNodes().size())) * Math.random());
        }
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

    /**
     *
     * @return
     */
    public double activate()
    {
        // TODO ... Implement this mess.

        this.previous = this.getValue();

        this.keep.activate();
        //this.memory.getValue() * this.keep.getValue();

        this.write.activate();
        this.read.activate();

        this.memory.getIncomingWeights().set(0, keep.getValue());
        this.memory.activate();

        return 0;
    }

    public void reset()
    {
        // TODO
    }
}
