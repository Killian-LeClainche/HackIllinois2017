import java.util.ArrayList;
import java.util.List;

public interface Node
{
    public List<Node> getIncomingNodes();
    public List<Double> getIncomingWeights();
    public double getValue();
}
