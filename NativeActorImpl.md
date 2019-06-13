# How to Implement the Native Actors

The functionality of the native actors is not implemeted in cal language, thus the .cal units of these actors do not have the *body* - 
just a signature that indicates on the actor's name, parameters, inputs and outputs. Native actors, compared to regular actors, 
do come wiht their implemetation in target language, e.g. in java (.jar) package. This package, together with the native actor's .cal units,
form the native actor project. This tutorial explains how to implement the native actors and it consists of three main parts. First part is 
to define the native actor project in eclipse, second is to implement the native actor in target language. The third is to create the project
to where the native actor can be tested. Each of these part is accompanied with a small demonstration project that can be dowloaded and used. 

These projects implemented and test the native actor whose purpose is to receives integer values as its input and displays the message 
saying is the value odd or even.
 
## Native Actor Eclipse Project

Create a new ORCC project in Eclipse and name it *NativeActorOddEven*. In source folder create the necessary packages and the necessary .cal unit. For example, name the packages
*natives.valuetest*, and the unit OETest.cal . Paste the following code into the OETest.cal unit and save. 

```
package natives.valuetest;

@jar(artifactid = "OETest", jarversion = "1.0")
@backend(id = "java-spring") 
@native actor OETest () int IN ==>:

	//displays is the INput value odd or even...
	
end
```
The *native* annotation will signal the backend that this actor does not need the code generation. This is understandable, since it comes wiht 
its implementation (e.g. in .jar package). For this purpose, in the project root, create a new folder and name it *jars*. This is the folder 
where the actor implementation will be placed.

The project can be dowloaded from [here](/public_site/md/resources/NativeActorOddEven.zip). 

## The Implementation Project

Create a new Java project using your IDE or just common text editor. Create the OETest.java unit and paste the following code: 

```
package natives.valuetest;

import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.actors.IActor;

import java.net.UnknownHostException;

public class OETest<T> implements IActor {
	
	// Input FIFOs
	protected Fifo IN;  //The name of the fifo must correspond to the name of the input parameter in .cal

	
	// Actor's parameters
	// This example does not have any input params... 
	
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

	// The network class of the application will use this method to link this actor wiht others in the network.
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
Notice that this code can be seen as a *template* code for native actors. Also, notice that the OETest class implemetns IActor interface 
and imports several other classess. To facilitate the implementation of the native actor, accompaning files are prepared in a project 
which can be dowloaded from [here](/public_site/md/resources/NativeOETest.zip). To finalize the implementation and to create the package, 
do the following: 

1. Unpack and browse to root folder (NativeOETest). 
2. Execute the ```mvn package``` from command line.
3. The *OETest-1.0.jar* package is created in "bin" folder.
4. Copy the *OETest-1.0.jar* package to the *jars* folder of the *NativeActorOddEven* Eclipse project.

Once the step 4 is completed, the work on Native actor is done, thus it can be used in other project as a ready made actor. 

## Project for Testing

To test this actor, a simple project is prepared and made available for [download](/public_site/md/resources/OddEven.zip). Import the project and 
compile the network to see the results. If you are unfamiliar with how to include the native actors into your network, check [this](/public_site/md/NativeActors.md) 
tutorial before testing. 

