package philosopher_problem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Philosopher extends Thread {
  private final Fork leftFork;
  private final Fork rightFork;
  private int eatingCount = 0;
  private String currentState = "Thinking";
  private final BufferedWriter writer;
  private static final boolean PRINT_TO_CONSOLE = true; // Change this to false to print to file

  Philosopher(String name, Fork leftFork, Fork rightFork, String filePath) throws IOException {
    super(name);
    this.leftFork = leftFork;
    this.rightFork = rightFork;
    this.writer = new BufferedWriter(new FileWriter(filePath, true));
  }

  private void printState(String state) throws IOException {
    if (PRINT_TO_CONSOLE) {
      System.out.println(getName() + " " + state);
    } else {
      writer.write(getName() + " " + state);
      writer.newLine();
      writer.flush();
    }
  }

  public void run() {
    try {
      while (true) {
        printState("is thinking.");
        sleep((int)(Math.random()*2000));

        printState("is hungry and wants to eat.");
        if (getName().equals("Philosopher-1")) {
          rightFork.take();
          printState("picked up right fork.");
          leftFork.take();
          printState("picked up left fork.");
        } else {
          leftFork.take();
          printState("picked up left fork and starts eating.");
        }

        eatingCount++;
        currentState = "Eating";

        sleep((int)(Math.random()*2000));
        leftFork.putDown();
        printState("put down left fork.");
        rightFork.putDown();
        printState("put down right fork.");
        currentState = "Thinking";
      }
    } catch (InterruptedException | IOException e) {
      System.out.println(getName() + " is interrupted.");
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        // Handle exception
      }
    }
  }

  public String getCurrentState() {
    return currentState;
  }

  public int getEatingCount() {
    return eatingCount;
  }
}
