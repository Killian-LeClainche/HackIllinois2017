package org.openimage.network;
import java.util.List;

public interface Node
{
    public List<Node> getIncomingNodes();
    public List<Double> getIncomingWeights();
    public double getValue();

    public double activate();
}
