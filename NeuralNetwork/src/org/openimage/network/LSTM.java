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
    private enum Gate {
        KEEP (0),
        WRITE (1),
        READ (2),
        INPUT (3),
        MEMORY (4),
        OUTPUT (5);


        private int value;
        Gate (int value)
        {
            this.value = value;
        }

        int getValue()
        {
            return this.value;
        }
    }

    /*public Node keep;
    public Node write;
    public Node read;
    public Node memory;
    public Node input;
    public Node output;*/

    public ArrayList<Node> gates;

    public double value;
    public double previous;

    public LSTM ()
    {
        gates.set(Gate.KEEP.getValue(), new Neuron(SquashFunction.STEP));
        gates.set(Gate.WRITE.getValue(), new Neuron(SquashFunction.STEP));
        gates.set(Gate.READ.getValue(), new Neuron(SquashFunction.STEP));

        gates.set(Gate.MEMORY.getValue(), new Neuron());
        gates.set(Gate.INPUT.getValue(), new Sensor());
        gates.set(Gate.OUTPUT.getValue(), new Neuron());

        this.gates.get(Gate.MEMORY.getValue()).addIncomingNode(this.gates.get(Gate.MEMORY.getValue()));
        this.gates.get(Gate.MEMORY.getValue()).addIncomingNode(this.gates.get(Gate.INPUT.getValue()));
        this.gates.get(Gate.OUTPUT.getValue()).addIncomingNode(this.gates.get(Gate.MEMORY.getValue()));

        /*
        keep = new Neuron(SquashFunction.STEP);
        write = new Neuron(SquashFunction.STEP);
        read = new Neuron(SquashFunction.STEP);
        memory = new Neuron();
        input = new Sensor();
        output = new Neuron();

        this.memory.addIncomingNode(memory); // The memory has a loopback for "remembering".
        this.memory.addIncomingNode(input); // Feed gated input to memory.
        this.output.addIncomingNode(memory); // Feed memory to gated output.
        */
    }

    /**
     * @return All external nodes that input to the LSTM.
     */
    public List<Node> getIncomingNodes()
    {
        List<Node> nodes = new ArrayList<>();

        nodes.addAll(getKeep().getIncomingNodes());
        nodes.addAll(getWrite().getIncomingNodes());
        nodes.addAll(getRead().getIncomingNodes());
        nodes.addAll(getInput().getIncomingNodes());

        return nodes;
    }

    /**
     * @return All external weights corresponding to all external inputs of the LSTM.
     */
    public List<Double> getIncomingWeights()
    {
        List<Double> weights = new ArrayList<>();

        weights.addAll(getKeep().getIncomingWeights());
        weights.addAll(getWrite().getIncomingWeights());
        weights.addAll(getRead().getIncomingWeights());
        weights.addAll(getInput().getIncomingWeights());

        return weights;
    }

    /**
     * Add node input to a gate with a missing input, otherwise a random gate.
     * @param node The node to be connected.
     * @param weight The weight to be multiplied by the node input.
     */
    public void addIncomingNode(Node node, double weight)
    {
        for (int i = 0; i < Gate.INPUT.getValue() + 1; i++)
        {
            Node gate = gates.get(i);
            if (gate.getIncomingNodes().size() <= 0)
            {
                gate.getIncomingNodes().add(node);
                gate.getIncomingWeights().add(weight);
                return;
            }
        }

        int seed = org.openimage.Param.rng.nextInt(3 + 1);
        gates.get(seed).getIncomingNodes().add(node);
        gates.get(seed).getIncomingWeights().add(weight);
    }

    /**
     * Add node input to a gate with a missing input, otherwise a random gate. A normalized weight is generated and set.
     * @param node The node to be connected.
     */
    public void addIncomingNode(Node node)
    {
        for (int i = 0; i < Gate.INPUT.getValue() + 1; i++)
        {
            Node gate = gates.get(i);
            if (gate.getIncomingNodes().size() <= 0)
            {
                gate.getIncomingNodes().add(node);
                gate.getIncomingWeights().add((1 / Math.sqrt(gate.getIncomingNodes().size())) * Math.random());
                return;
            }
        }

        int seed = org.openimage.Param.rng.nextInt(3 + 1);
        gates.get(seed).getIncomingNodes().add(node);
        gates.get(seed).getIncomingWeights().add((1 / Math.sqrt(gates.get(seed).getIncomingNodes().size())) * Math.random());
    }

    public void normalizeWeights()
    {
        for (int i = 0; i < Gate.INPUT.getValue() + 1; i++) {
            Node gate = gates.get(i);
            for (int j = 0; j < gate.getIncomingNodes().size(); j++)
            {
                gate.normalizeWeights();
            }
        }
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(double value)
    {
        getMemory().setValue(value);
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
        this.value = getMemory().getValue() * getWrite().getValue();

        // Squash the memory with the recurrent loop and the read input.
        getMemory().activate(getInput().getValue() * getRead().getPrevious() + getMemory().getValue() * getKeep().getValue());

        // Squash the gates for next cycle.
        getRead().activate();
        getWrite().activate();
        getKeep().activate();

        return this.value;
    }

    public double activate(double input) {
        getInput().setValue(input);
        return this.activate();
    }

    public void reset()
    {
        for (Node node : gates)
        {
            node.setValue(0);
        }
    }

    public Node getKeep()
    {
        return this.gates.get(Gate.KEEP.getValue());
    }

    public Node getRead()
    {
        return this.gates.get(Gate.READ.getValue());
    }

    public Node getWrite()
    {
        return this.gates.get(Gate.WRITE.getValue());
    }

    public Node getInput()
    {
        return this.gates.get(Gate.INPUT.getValue());
    }

    public Node getMemory()
    {
        return this.gates.get(Gate.MEMORY.getValue());
    }

    public Node getOutput()
    {
        return this.gates.get(Gate.OUTPUT.getValue());
    }
}