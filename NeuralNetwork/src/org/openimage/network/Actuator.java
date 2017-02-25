package org.openimage.network;

import java.util.List;

/**
 * Created by max on 2/25/17.
 */
public class Actuator implements Node
{
    @Override
    public List<Node> getIncomingNodes()
    {
        return null;
    }

    @Override
    public List<Double> getIncomingWeights()
    {
        return null;
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
        return 0;
    }

    @Override
    public double getPrevious()
    {
        return 0;
    }

    @Override
    public double activate()
    {
        return 0;
    }
}
