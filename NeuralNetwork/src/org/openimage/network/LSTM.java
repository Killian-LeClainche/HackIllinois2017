package org.openimage.network;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static final int KEEP = 0;
	public static final int WRITE = 1;
	public static final int READ = 2;
	public static final int INPUT = 3;
	public static final int MEMORY = 4;
	public static final int OUTPUT = 5;

    /*public Node keep;
    public Node write;
    public Node read;
    public Node memory;
    public Node input;
    public Node output;*/

    public Node[] gates;

    public double value;
    public double previous;

    public LSTM ()
    {
    	gates = new Node[6];
    	gates[KEEP] = new Neuron(SquashFunction.STEP);
    	gates[WRITE] = new Neuron(SquashFunction.STEP);
    	gates[READ] = new Neuron(SquashFunction.STEP);
    	gates[MEMORY] = new Neuron();
    	gates[INPUT] = new Sensor();
    	gates[OUTPUT] = new Neuron();
    	
    	gates[MEMORY].addIncomingNode(gates[MEMORY]);
    	gates[MEMORY].addIncomingNode(gates[INPUT]);
    	gates[OUTPUT].addIncomingNode(gates[MEMORY]);
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
    
    public List<List<Double>> getListOfIncomingWeights()
    {
        List<List<Double>> listList = new ArrayList<>();

        listList.add(getKeep().getIncomingWeights());
        listList.add(getWrite().getIncomingWeights());
        listList.add(getRead().getIncomingWeights());
        listList.add(getInput().getIncomingWeights());

        return listList;
    }

    /**
     * Add node input to a gate with a missing input, otherwise a random gate.
     * @param node The node to be connected.
     * @param weight The weight to be multiplied by the node input.
     */
    public void addIncomingNode(Node node, double weight)
    {
        int minIndex = 0;
        for (int i = 0; i < INPUT + 1; i++)
        {
            Node gate = gates[i];

            if (gate.getIncomingNodes().size() < gates[minIndex].getIncomingNodes().size())
            {
                minIndex = i;
            }

            if (gate.getIncomingNodes().size() <= 0)
            {
                gate.getIncomingNodes().add(node);
                gate.getIncomingWeights().add(weight);
                return;
            }
        }

        gates[minIndex].getIncomingNodes().add(node);
        gates[minIndex].getIncomingWeights().add(weight);

        /*int seed = org.openimage.Param.rng.nextInt(3 + 1);
        gates[seed].getIncomingNodes().add(node);
        gates[seed].getIncomingWeights().add(weight);*/
    }

    /**
     * Add node input to a gate with a missing input, otherwise a random gate. A normalized weight is generated and set.
     * @param node The node to be connected.
     */
    public void addIncomingNode(Node node)
    {
        int minIndex = 0;
        for (int i = 0; i < INPUT + 1; i++)
        {
            Node gate = gates[i];
            if (gate.getIncomingNodes().size() < gates[minIndex].getIncomingNodes().size())
            {
                minIndex = i;
            }

            if (gate.getIncomingNodes().size() <= 0)
            {
                gate.getIncomingNodes().add(node);
                gate.getIncomingWeights().add((1 / Math.sqrt(gate.getIncomingNodes().size())) * Math.random());
                return;
            }
        }

        gates[minIndex].getIncomingNodes().add(node);
        gates[minIndex].getIncomingWeights().add((1 / Math.sqrt(gates[minIndex].getIncomingNodes().size())) * Math.random());

        /*int seed = org.openimage.Param.rng.nextInt(3 + 1);
        gates[seed].getIncomingNodes().add(node);
        gates[seed].getIncomingWeights().add((1 / Math.sqrt(gates[seed].getIncomingNodes().size())) * Math.random());*/
    }

    public void normalizeWeights()
    {
        for (int i = 0; i < INPUT + 1; i++) {
            Node gate = gates[i];
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

    /**
     * Sets the value of the memory Node.
     * @param value the value to be remembered.
     */
    public void setValue(double value)
    {
        getMemory().setValue(value);
    }

    public double getPrevious()
    {
        return this.previous;
    }

    /**
     * Computes the current output value of the LSTM using the write gate and memory node. Then, the memory is squashed,
     * and the rest of the gets are activated to prepare for the next cycle.
     * @return The value squashed and gated by the LSTM.
     */
    public double activate()
    {
        // It is possible that a gate recieved no nodes.
        assert(getKeep().getIncomingNodes().size() > 0 &&
            getRead().getIncomingNodes().size() > 0 &&
            getWrite().getIncomingNodes().size() > 0 &&
            getInput().getIncomingNodes().size() > 0 &&
            getMemory().getIncomingNodes().size() > 0 &&
            getOutput().getIncomingNodes().size() > 0);

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

    /**
     * Activate the LSTM as above but using the provided input.
     * @param input The input to be gated and squashed.
     * @return The squashed and gated input.
     */
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
        return this.gates[KEEP];
    }

    public Node getRead()
    {
        return this.gates[READ];
    }

    public Node getWrite()
    {
        return this.gates[WRITE];
    }

    public Node getInput()
    {
        return this.gates[INPUT];
    }

    public Node getMemory()
    {
        return this.gates[MEMORY];
    }

    public Node getOutput()
    {
        return this.gates[OUTPUT];
    }

    public String toString()
    {
        return "LSTM { gates: " + Arrays.toString(this.gates) + ", previous: " + this.previous +
                ", value: " + this.value + " }";
    }
}