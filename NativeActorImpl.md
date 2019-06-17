# How to Implement the Native Actors

The functionality of the native actors is not implemented in cal language, thus the .cal units of these actors do not have the *body* - 
just a signature that indicates on the actor's name, parameters, inputs and outputs. Native actors, compared to regular actors, 
do come with their implementation in target language, e.g. in java (.jar) package. This package, together with the native actor's .cal units,
form the native actor project. This tutorial explains how to implement the native actors and it consists of three main parts. First part is 
to define the native actor project in eclipse, second is to implement the native actor in target language. The third is to create the project
in which the native actor can be tested. Each of these part is accompanied with an exemplary demonstration project that can be downloaded and used. 

The native actor that that is used in the exemplary projects is very simple – it receives an integer and prints prints is the value odd or even to the screen. 

 
## Native Actor Eclipse Project

Create a new ORCC project in Eclipse and name it *NativeActorOddEven*. In source folder create the necessary package and the necessary .cal unit. It is a recommended practice to use the name *natives* for the top level package. For example, name the packages 
*natives.valuetest*, and the unit OETest.cal . Paste the following code into the OETest.cal unit and save. 

```
package natives.valuetest;

@jar(artifactid = "OETest", jarversion = "1.0")
@backend(id = "java-spring") 
@native actor OETest () int IN ==>:

	//displays is the INput value odd or even...
	
end
```
The *native* annotation will signal the backend that this actor does not need the code generation. This is understandable, since it comes with 
its implementation (e.g. in .jar package). For this purpose, in the project root, create a new folder and name it *jars*. This is the folder 
where the actor implementation will be placed.

This project can be downloaded from [here](resources/NativeActorOddEven.zip). 

## The Implementation Project

The purpose of this section is to generate the actual implementation of our native actor. The end result is therefore the .jar package that will be included (*jars* folder) in the project that is created in previous section of this tutorial. 
Create a new Java project using your IDE or just common text editor. Create the OETest.java unit and paste the following code: 

```
package natives.valuetest;

import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.actors.IActor;

import java.net.UnknownHostException;

public class OETest<T> implements IActor { 
//The name of the class **must correspond** to the name of the native actor
	
	// Input FIFOs
	protected Fifo IN;  //The name of the fifo **must correspond** to the name of the input port in .cal

	
	// Actor's parameters
	// This example does not have any input parameters...
	//Constructor
	//public OETest() {
	//}
	
	// Functions/procedures
	
	/***********
	 * Actions
	 **********/
	@Override
	public void initialize(){
		//In this example, there is nothing to initialize....
	}

	// The network class of the application will use this method to link this actor with others in the network.
	@Override
	@SuppressWarnings("unchecked")
	public <T> void setFifo(String portName, Fifo<T> fifo) {
		if ("IN".equals(portName)) {
			IN = (Fifo<T>) fifo;		
		} else  {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}   
	
	public int schedule() {
		boolean res;
		int i = 0;
		do {
			res = false;
			if (IN.hasTokens(1)) {
		// Your code goes here....
				int md = (int)IN.read() % 2;
				
				if( md == 0 )
					System.out.println("The value is even");
				else
					System.out.println("The value is odd");
					
		// Your code ends here....
			res = true;
			}
			i += res ? 1 : 0;
		} while (res);
		return i;
	} 
 	
}
```
Notice that this code can be seen as a *template* code for native actors, thus it can be used for the development of all other actors. Also, notice that the OETest class implements IActor interface 
and imports several other classes. To facilitate the implementation of the native actor, accompanying files are prepared in a project 
which can be downloaded from [here](resources/NativeOETest.zip). To finalize the implementation and to create the package, 
do the following: 

1. Unpack and browse to root folder (NativeOETest). 
2. Execute the ```mvn package``` from command line.
3. The *OETest-1.0.jar* package is created in "bin" folder. Make sure that the name of the .jar and its version corresponds to the *jar* annotation in .cal file. 
4. Copy the *OETest-1.0.jar* package to the *jars* folder of the *NativeActorOddEven* Eclipse project that is created in previous section of this tutorial.

Once the step 4 is completed, the work on Native actor is done, thus it can be used in other project as a ready made actor. 

## Project for Testing

To test this actor, a simple project is prepared and made available for [download](resources/OddEven.zip). Import the project, set the build path to include the *NativeActorOddEven*, and 
compile the network to see the results. If you are unfamiliar with how to include the native actors into your network, check [this](NativeActors.md) 
tutorial before testing. 