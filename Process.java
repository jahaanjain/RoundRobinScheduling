
public class Process {

  private int pid; // id of process loaded from csv
  private int burst; // burst time of process loaded from csv
  private int initialBurst; // initial burst time of process
  private int arrival; // arrival time of process while going through simulation
  private int initialArrival; // initial arrival time of process
  private int waitingTime; // how long the process is kept waiting
  private int completedTime; // total time to for the process to complete execution
  private boolean completed; // checks if process is completed
  private boolean ready; // check if the process is in the ready queue

  public Process(int pid, int arrival, int burst) {
    this.pid = pid;
    this.arrival = arrival;
    this.initialArrival = arrival;
    this.burst = burst;
    this.initialBurst = burst;
    this.waitingTime = 0;
    this.completedTime = 0;
    this.completed = false;
    this.ready = false;
  }

  // Basic get and set methods
  public int getPID() {
    return this.pid;
  }

  public int getArrival() {
    return this.arrival;
  }

  public int getInitialArrival() {
    return this.initialArrival;
  }

  public int getInitialBurst() {
    return this.initialBurst;
  }

  public int getBurst() {
    return this.burst;
  }

  public int getWaitingTime() {
    return this.waitingTime;
  }

  public int getCompletedTime() {
    return this.completedTime;
  }

  public boolean getCompleted() {
    return this.completed;
  }

  public boolean getReady() {
    return this.ready;
  }

  public void setPID(int newPid) {
    this.pid = newPid;
  }

  public void setArrival(int newArrival) {
    this.arrival = newArrival;
  }

  public void setInitialArrival(int newArrival) {
    this.initialArrival = newArrival;
  }

  public void setBurst(int newBurst) {
    this.burst = newBurst;
  }

  public void setWaitingTime(int newTime) {
    this.waitingTime = newTime;
  }

  public void setCompletedTime(int newTime) {
    this.completedTime = newTime;
  }

  public void setCompleted(boolean newCompleted) {
    this.completed = newCompleted;
  }

  public void setReady(boolean newReady) {
    this.ready = newReady;
  }

  public String toString() {
    String s = "";
    s += "PID: " + this.getPID() + " | ";
    s += "Arrival Time: " + this.getArrival() + " | ";
    s += "Burst Time: " + this.getBurst() + " | ";
    s += "Turnaround Time: " + this.getCompletedTime() + " | ";
    s += "Waiting Time: " + this.getWaitingTime() + " | ";
    // s += "Completed? " + this.getCompleted() + "\n";
    return s;
  }
}
