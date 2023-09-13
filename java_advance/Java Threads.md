# Exo1: Basic Multi-Threads

Define a Threadsubclass called MyThread -> MyThread.java

## • Each instance receives a String name upon creation  
&nbsp;• The run() method  
&nbsp;• Prints a message iteratively , for 100 times  
&nbsp;• Each message: <the thread’s name> and <iteration index: 1..100>  
&nbsp;• Sleeps for 50ms between iterations  
&nbsp;• Prints an END message before exiting   

## • In a mainmethod, create 2 instances of MyThreadand start them   
### &nbsp;•What is the order of the message printouts?  

The exact order of the printouts is not deterministic. It depends on how the Java Vitual Machine (JVM) and the underlying system schedules the threads. Two threads are running concurrently, and the order of their execution is not guaranteed. That's why we see the output from Thread 1 and Thread 2 interleaved in a seemingly random manner.

### &nbsp;• Execute several times: is the order the same each time?  

No, the order is not the same each time. As mentioned above, thread scheduling is non-deterministic and depends on many factors, such as the current workload of the system, the JVM's thread scheduling policy, and more.

### &nbsp;• Same questions when: adding more threads; changing the sleep delays. 

Adding more threads or changing the sleep delays would still results in non-deterministic behaviour. With more threads, you'd have more interleaving and potentially more randomness in the printout order. If you change the sleep delays, it maight affect the relative execution order of the threads, but it would still remain non-deterministic.

# Exo2: Command Buffer

• Define a CommandsBufferclass as shown in the slides • Stores commands in an array: String[ ] commands • Provides pushand popmethods to add and remove commands to/from the buffer, respectively