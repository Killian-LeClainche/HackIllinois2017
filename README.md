.. class:: no-web

    .. image:: https://github.com/Killian-LeClainche/HackIllinois2017/blob/master/stock.jpg?raw=true
        :alt: HTTPie compared to cURL
        :width: 100%
        :align: center


.. class:: no-web no-pdf

# Introduction

NeuralOR is focused on innovative features and performance regarding the image detection field using :

 - Genetic Algorithms

 - Neural Networks

 - And Other Tricks

to provide a faster implementation of specific tasks that while other libraries adequately solve, they do
not solve them in a faster manner.

#Current Implementations

In it's current state, NeuralOR only provides the most fundamental functionality needed to create one's own
Neural Network for Image Recognition. 

There are a handful of features that we would like to implement before moving onwards towards more use cases of
this application. They are :

 - Provide a NEAT genetic algorithm that elimates user parameters and under some researched articles will provide 
a faster learning curve over any topic given.

 - Smooth out code to optimize certain fundamental operations to improve speed.

 - Expand the interfacing system with a much more rigid and defined outline of how you can use the Neural Network
for other topics of interest with little to no problem.

 - Finally, provide some more cohessive behavior to the project that is easily understandable and easiy editable for
any person not within the original development team.

#How to Use

In the current state of the project, we would like to highlight that the current implementation for using the product
is rather rigid. As stated above, we will be simplifying and expanding the capabilities of this project in the future.

To use please provide a folder in the directory the code is located named "images", inside said folder please provide
any number of other folders that are known as classifications. Inside these folders should contain every image you'd like
the genetic algorithm to train with for object recognition.