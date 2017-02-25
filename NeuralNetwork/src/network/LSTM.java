package network;

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

    public double previous;

    public LSTM ()
    {
        keep = new Neuron(SquashFunction.STEP);
        write = new Neuron(SquashFunction.STEP);
        read = new Neuron(SquashFunction.STEP);
        memory = new Neuron(SquashFunction.LOGISTIC);

        this.memory.addIncomingNode(memory); // The memory has a loopback for "remembering".
    }

    @Override
    public List<Node> getIncomingNodes()
    {
        List<Node> nodes = new ArrayList<>();

        nodes.addAll(keep.getIncomingNodes());
        nodes.addAll(write.getIncomingNodes());
        nodes.addAll(read.getIncomingNodes());
        nodes.addAll(memory.getIncomingNodes());

        return nodes;
    }


    @Override
    public List<Double> getIncomingWeights()
    {
        List<Double> weights = new ArrayList<>();

        weights.addAll(keep.getIncomingWeights());
        weights.addAll(write.getIncomingWeights());
        weights.addAll(read.getIncomingWeights());
        weights.addAll(memory.getIncomingWeights());

        return weights;
    }

    @Override
    public void addIncomingNode(Node node, double weight)
    {

    }

    @Override
    public void addIncomingNode(Node node)
    {

    }

    @Override
    public void normalizeWeights()
    {

    }

    @Override
    public double getValue()
    {
        // Read's value is a dynamic weight that controls the output.
        return this.memory.getValue() * this.read.getValue();
    }

    /**
     * {inheritDoc}
     */
    @Override
    public double getPrevious()
    {
        return this.previous;
    }

    /**
     * {inheritDoc}
     *
     */
    @Override
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
