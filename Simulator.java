import java.util.LinkedList;
import java.util.Queue;
import java.util.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simulator {
  private int time; // counts the total time to finish all processes
  private int contextSwitchTime; // how long it takes a context switch to finish
  private int contextSwitches; // how many context switches happen
  private int timeQuantum; // how long a process can spend in execution
  private int numberOfProcesses; // how many processes will be processed
  private int totalBurstTime; // total burst time of all processses
  private String gantt = ""; // sequence of processes being executed

  private ArrayList<Process> processes; // list of processes
  private Queue<Process> ready; // ready queue of processes

  public Simulator(String filePath, int timeQuantum, int contextSwitchTime) {
    this.timeQuantum = timeQuantum;
    this.contextSwitchTime = contextSwitchTime;
    this.time = 0;
    this.contextSwitches = 0;
    this.totalBurstTime = 0;
    this.processes = new ArrayList<>();
    this.ready = new LinkedList<>();

    //read lines from the csv
    String text = null;
    try {
      text = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (Exception e) {
      System.out.println(e);
    }
    String[] processesCSV = text.split("\\r?\\n");

    this.numberOfProcesses = processesCSV.length;
    // create and add the processes to the list
    for (String process : processesCSV) {
      String[] x = process.split(",");
      this.processes.add(new Process(Integer.parseInt(x[0]), // pid
          Integer.parseInt(x[1]), // arrival time
          Integer.parseInt(x[2]) // burst time
      ));
      this.totalBurstTime += Integer.parseInt(x[2]);
    }
    // initial method call to add processes to ready queue
    addToReadyQueue();
  }

  public void addToReadyQueue() {
    for (Process process : this.processes) {
      if (process.getBurst() != 0) {
        ready.add(process);
        process.setReady(true);
        this.addGantt(process.getPID());
      }
    }
  }

  // acts as the cpu, does all the processing for the round robin
  public void processQueue() {
    if (ready.size() != 0) { // if there is stuff in ready queue
      Process process = ready.remove();
      for (int i = 0; i < this.timeQuantum; i++) {
        if (process.getBurst() != 0) {
          this.time++;
          process.setBurst(process.getBurst() - 1); 
        } // run for the time quantum, if there is burst time left in the process, it will keep running
        else break;
      }
      if (process.getBurst() != 0) { // if burst is not 0, put back into ready queue
        process.setArrival(this.getTime());
        ready.add(process);
        this.addGantt(process.getPID());
      } else {
        // set processes completed and set waiting time
        process.setCompleted(true);
        process.setCompletedTime(this.getTime() - process.getInitialArrival()); // turnaround time
        process.setWaitingTime(
          (process.getCompletedTime() - process.getInitialArrival()) - process.getInitialBurst()
        ); // turnaround time - initial burst time
      }
      if (ready.size() != 0) { contextSwitch(); }
    }
  }

  public void contextSwitch() {
    // add context switch time and increase context switches
    this.time += contextSwitchTime;
    this.contextSwitches++;
    processQueue();
  }

  // basic get and set methods
  public String getProcesses() {
    String s = "\n";
    for (Process process : this.processes) {
      s += String.format(
        "ID: %s | Turnaround Time: %s | Waiting Time: %s | Completion Time: %s \n", String.format("%02d", process.getPID()),
        String.format("%02d", process.getCompletedTime() - process.getInitialArrival()),
        String.format("%02d", process.getWaitingTime()),
        String.format("%02d", process.getCompletedTime())
      );
    }
    return s;
  }

  public int getContextSwitchTime() {
    return this.contextSwitchTime;
  }
  public String getGantt() {
    return this.gantt;
  }

  public void addGantt(int pid) {
    this.gantt += " -> " + "P" + pid;
  }

  public double getAverageTurnaround() {
    // calculate the avg turnaround
    int totalTime = 0;
    for (Process process : this.processes) {
      totalTime += process.getCompletedTime() - process.getInitialArrival();
    }
    return (double) totalTime / this.getTotalProcesses();
  }
  
  public double getAverageWaitingTime() {
    // calculate the avg waiting time
    int totalTime = 0;
    for (Process process : this.processes) {
      totalTime += process.getWaitingTime();
    }
    return (double) totalTime / (double) this.getTotalProcesses();
  }

  public int getQuantum() {
    return this.timeQuantum;
  }

  public int getContextSwitches() {
    return this.contextSwitches;
  }

  public int getTime() {
    return this.time;
  }

  public int getTotalBurstTime() {
    return this.totalBurstTime;
  }

  public int getTotalProcesses() {
    return this.numberOfProcesses;
  }

  public double getCPU() {
    return (double) this.getTotalBurstTime() / this.getTime();
  }

  public double getThroughput() {
    return (double) this.getTotalProcesses() / this.getTime();
  }

  public void setQuantum(int newQuantum) {
    this.timeQuantum = newQuantum;
  }

}