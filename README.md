[![NeuralOR](https://github.com/Killian-LeClainche/HackIllinois2017/blob/master/stock.jpg?raw=true)](https://github.com/Killian-LeClainche/HackIllinois2017)

# Introduction

NeuralOR is focused on innovative features and performance regarding the image detection field using :

 - Genetic Algorithms

 - Neural Networks

 - And Other Tricks

to provide a faster implementation of specific tasks that while other libraries adequately solve, they do
not solve them in a faster manner.

#Current Implementations

In it's current state, NeuralOR provides the fundamental functionality needed to generate a
Neural Network for Image Recognition. 

There are a handful of features that we would like to implement before moving onwards towards more use cases of
this application. They are :

 - Implement a NEAT genetic algorithm that evolves user parameters in a way that is proven to accelerate learning

 - Optimize code for fundamental operations to reduce learning time.

 - Expand the interfacing system with a better defined outline of how you can use the Neural Network
for any application.

#How to Use

In the current state of the project, we would like to highlight that the current implementation for using the product
is rather rigid. As stated above, we will be simplifying and expanding the capabilities of this project in the future.

To use this program, provide a folder in the project directory named "images". Inside this folder include
any number of other folders that are known as classifications. Inside these folders should contain every image you'd like
the genetic algorithm to train with for object recognition. These classification folders shoud contain two folders to split the positive and negative training examples.
