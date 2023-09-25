# Requirement

## DeadLock

1.During the execution of the program, the application should write the current state of each philosopher.


2.At the end of the program's execution, the application should write the number of times each philosopher was in the "eating" state.

1.2:For tracking the current state of each philosopher and the number of times they eat, you could add instance variables to the Philosopher class, such as currentState and eatingCount.

3.The application should be modified in such a way that it writes this information to a file, rather than printing it to the console.

For writing this information to a file instead of the console, you could use the java.io.FileWriter and java.io.BufferedWriter classes.

4.Additionally, the application should be modified in a way that allows the target of this information to be easily chosen. For example, there could be a configuration parameter that indicates whether the information should be sent to the console or to a file.

For choosing the target of the information (console or file), you could add a configuration setting that determines where the output goes.