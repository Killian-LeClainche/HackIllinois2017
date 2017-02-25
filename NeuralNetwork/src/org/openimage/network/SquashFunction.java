package org.openimage.network;

/**
 * Represents each of the different sigmoid functions for squashing in Neurons.
 * 
 * @author Max O'Cull
 */
public enum SquashFunction
{
	LOGISTIC, // 1 / (1 + Math.pow(Math.E, -1 * input))
	STEP // input <= 0.5 -> 0, input > 0.5 -> 1
}
