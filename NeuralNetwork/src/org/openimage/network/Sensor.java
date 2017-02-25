package org.openimage.network;

/**
 * A sensor is used to input data into a Neural Network. No weights or squash functions are needed.
 *
 * @author Max O'Cull
 */
public class Sensor extends Neuron
{
    /**
     * If a signal is not provided, sum all inputs.
     * @return The unweighted sum of all inputs.
     */
    @Override
    public double activate()
    {
        // Ignore weights in a sensor neuron.
        double sum = 0;
        for (int i = 0; i < this.incomingNodes.size(); i++)
        {
            sum += this.incomingNodes.get(i).getValue();
        }

        return sum;
    }

    /**
     * Propagate a signal through the network starting with this Neuron.
     * @param input The signal to be propagated.
     */
    public double activate(double input)
    {
        this.previous = this.value;
        this.value = input;

        return this.value;
    }

    @Override
    public String toString()
    {
        return "Sensor " + super.toString();
    }
}
