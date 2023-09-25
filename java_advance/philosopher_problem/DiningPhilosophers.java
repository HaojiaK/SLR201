package philosopher_problem;

public class DiningPhilosophers {
  public static void main(String[] args) throws IOException {
    String filePath = "philosophers.txt";
    Fork fork1 = new Fork();
    Fork fork2 = new Fork();
    Fork fork3 = new Fork();
    Fork fork4 = new Fork();
    Fork fork5 = new Fork();

    Philosopher philosopher1 = new Philosopher("Philosopher-1", fork1, fork2, filePath);
    Philosopher philosopher2 = new Philosopher("Philosopher-2", fork2, fork3, filePath);
    Philosopher philosopher3 = new Philosopher("Philosopher-3", fork3, fork4, filePath);
    Philosopher philosopher4 = new Philosopher("Philosopher-4", fork4, fork5, filePath);
    Philosopher philosopher5 = new Philosopher("Philosopher-5", fork5, fork1, filePath);

    philosopher1.start();
    philosopher2.start();
    philosopher3.start();
    philosopher4.start();
    philosopher5.start();
  }
}
