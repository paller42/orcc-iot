# Basic Concepts in ORCC-IoT

## ORCC-IoT Project

Start the Eclipse that already has the ORCC-IoT plugin installed and follow these steps:
1. File -> New -> Other 

2. From the dialog box, select Orcc Project and hit Next.   
![New Project](imgs/cworcc/orccnewprj1.jpg)

3. Provide the name of your project, e.g. orccProjectName     
![New Project](imgs/cworcc/orccnewprj2.jpg)

Project Explorer should show the *orccProjectName* project and its content.     
![New Project](imgs/cworcc/orccnewprj3.jpg)

Folder *src* is where actors, their packages and networks are stored. The azurecreds.properties file is
where the credentials for the cloud are stored. Current implemetation of Java cloud generates the code and config files for Microsof Azure. If cloud backends are not used, this file should
not be changed. 

## Network
IoT applications are represented as a dataflow networks in ORCC-IoT. These networks consist of one or more 
interconnected actors that represent distinct parts of the IoT application. Once finalized, these networks are compiled by ORCC-IoT 
 resulting with concrete software code. For example, during the compilation several actors from the network 
can be compiled to .java code, while the remaining are compiled to C code. The java and c files are then compiled for their corresponding
target platforms and deployed.  During the execution, java and c parts are fully aware of each other and able to communicate seamlesly. 
Example network is presented i Figure 1. 

![Example_network](imgs/cworcc/hvacapp.jpg)

## Actor 
Actor encapsulates a distinct part of the application's functionality. It can have input ports, through which it
can consume data from other actors, and output ports, through which it can provide data to other actors. The
Actor's functionality, or behaviour, is defined with the [CAL Actor language](https://en.wikipedia.org/wiki/CAL_Actor_Language).
The .cal units are used during compilation to produce the softare code for each actor and the network as a whole. 
In Figure 1, actors are represented wiht blue rectangles. There are two types of actors:

1. regular actor (or just actor)
2. native actor   

### 1. Regular Actor
As its name suggests, is an actor that is built from scratch by ORCC-IoT user to represent one of the application's functionality. 
It is written in cal language, and compiled using the selected backends. The compilation network (of actors) results with software code
(e.g. .java or .c units). This code is then compiled for, and deploy to target hardware platform.

Here is a basic [tutorial](CompileWithORCC.md) on how to create and compile the network with regural actors.  


### 2. Native Actors
Native actor is a ready made actor that can be used in ORCC IoT networks. Compared to regular, native actors come with executables
(e.g .jar packages) that are ready for deployment to target platform. The cal definition of these actors allow user to connect it 
with other actors in the network via its input and output ports and to pass the necessary parameters for configuration. During the 
compilation of the ORCC-IoT network, native actors are not compiled as regular actors since they are accompanied wiht its executable
version that is built it the IoT application that is being made.

To learn more about native actors, see this [tutorial](NativeActors.md). 