package org.openimage.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 2/25/17.
 */
public class SensorLayer extends Layer
{

    public SensorLayer(int size, int lstmCount)
    {
        super(size, lstmCount);
        System.err.println("Invalid constructor");
    }

    public SensorLayer(int size)
    {
        nodes = new ArrayList<Node>(size);

        for (int i = 0; i < size; i++)
        {
            Sensor sensor = new Sensor();
            nodes.add(sensor);
        }

    }

    public SensorLayer(List<Node> inputs)
    {
        for (Node node : inputs) {
            if ())
        }
    }

    public
}
