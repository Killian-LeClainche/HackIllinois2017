package org.openimage.network;

import java.util.ArrayList;
import java.util.List;

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
    /*private enum Gate {
        KEEP (0),
        WRITE (1),

    }*/

    public Node keep;
    public Node write;
    public Node read;
    public Node memory;
    public Node input;
    public Node output;

    public double value;
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
        else if (this.input.getIncomingNodes().size() <= 0)
        {
            this.input.getIncomingNodes().add(node);
            this.input.getIncomingWeights().add(weight);
        }
        else
        {
            int seed = org.openimage.Param.rng.nextInt(3 + 1);
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
        else if (this.input.getIncomingNodes().size() <= 0)
        {
            addIncomingNode(node, (1 / Math.sqrt(this.input.getIncomingNodes().size())) * Math.random());
        }
        else
        {
            int seed = org.openimage.Param.rng.nextInt(3 + 1);
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
            this.write.getIncomingWeights().set(i, (1 / Math.sqrt(this.write.getIncomingNodes().size())) * Math.random());
            this.read.getIncomingWeights().set(i, (1 / Math.sqrt(this.read.getIncomingNodes().size())) * Math.random());
            this.input.getIncomingWeights().set(i, (1 / Math.sqrt(this.input.getIncomingNodes().size())) * Math.random());
        }
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(double value)
    {
        this.memory.setValue(value);
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
        // Compute the current value.
        this.previous = this.value;
        this.value = this.memory.getValue() * this.write.getValue();

        // Squash the memory with the recurrent loop and the read input.
        this.memory.activate(this.input.getValue() * this.read.getPrevious() + this.memory.getValue() * this.keep.getValue());

        // Squash the gates for next cycle.
        this.read.activate();
        this.write.activate();
        this.keep.activate();

        return this.value;
    }

    public double activate(double input) {
        this.input.setValue(input);
        return this.activate();
    }

    public void reset()
    {
        this.keep.setValue(0);
        this.write.setValue(0);
        this.read.setValue(0);
        this.memory.setValue(0);
        this.input.setValue(0);
        this.output.setValue(0);
    }
}