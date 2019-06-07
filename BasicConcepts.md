# Basic Concepts in ORCC-IoT

## ORCC-IoT Project

Start the Eclipse that already has the ORCC-IoT plugin installed and follow these steps:
1. File -> New -> Other 

2. From the dialog box, select Orcc Project and hit Next.   
![New Project][newPrj1]

3. Provide the name of your project, e.g. orccProjectName     
![New Project][newPrj2]

Project Explorer should show the *orccProjectName* project and its content.     
![New Project][newPrj3]
 
 

## Network
IoT applications are represented as a dataflow networks in ORCC-IoT. These networks consist of one or more 
interconnected actors that represent distinct parts of the IoT application. Once finalized, these networks are compiled by ORCC-IoT 
 resulting with concrete software code. For example, during the compilation several actors from the network 
can be compiled to .java code, wile the remaining are compiled to C code. The java and c files are then compiled for their corresponding
target platforms and deployed.  During the execution, java and c parts are fully aware of each other and able to communicate seamlesly. 
Example network is presented i Figure 1. 

![Example_network][ExampleNetworkHVAC]


## Actor 
Actor encapsulates a distinct part of the application's functionality. It can have input ports, through which it
can consume data from other actors, and output ports, through which it can provide data to other actors. The
Actor's functionality, or behaviour, is defined with the [CAL Actor language](https://en.wikipedia.org/wiki/CAL_Actor_Language). The .cal units are used during compilation to produce the softare code for each actor and the network as a whole. There are two types of actors:

1. regular actor (or just actor)
2. native actor   

### 1. Regular Actor
As its name suggests, is an actor that is built from scratch by ORCC-IoT user to represent one of the application's functionality. 
It is written in cal language, and compiled using the selected backends. The compilation network (of actors) results with software code
(e.g. .java or .c units). This code is then compiled for, and deploy to target hardware platform.

Here is a basic [tutorial][NativeActTutorial] on how to create and compile the network.  


### 2. Native Actors
Native actor is a ready made actor that can be used in ORCC IoT networks. Compared to regular, native actors come with executables
(e.g .jar packages) that are ready for deployment to target platform. The cal definition of these actors allow user to connect it 
with other actors in the network via its input and output ports and to pass the necessary parameters for configuration. During the 
compilation of the ORCC-IoT network, native actors are not compiled as regular actors since they are accompanied wiht its executable
version that is built it the IoT application that is being made. 



[ExampleNetworkHVAC]: imgs/hvacapp.jpg
[NativeActTutorial]: NativeActors.md
[newPrj1]: imgs/orccnewprj1.jpg
[newPrj2]: imgs/orccnewprj2.jpg
[newPrj3]: imgs/orccnewprj3.jpg