// Round Robin Process Scheduling Simulation
// By: Jahaan Jain & Thomas Blandino
// Professor: Susan Gass
// Class: CSCI 330 M03 - Operating Systems

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.LinkedList;
import java.util.ArrayList;

public class Main {

  public static void print(String s) {
    System.out.println(s);
  } // so that we dont have to type out the full System.out.println()

  public static void main(String[] args) {
    if (args.length != 0) {
      print("Please include the file path and time quantum.");
    } else {
      Simulator rr = new Simulator(args[0], Integer.parseInt(args[1]), 1); // through cmd prompt
      // Simulator rr = new Simulator("processes.csv", 8, 1); // for manual simulation
      rr.processQueue();
      print("");
      print("+-+-+-+-+-+-+-+-+-+ Round Robin Simulator +-+-+-+-+-+-+-+-+-+");
      print("");
      print(String.format("    Processes: %d | Time Quantum: %d | Context Switch Time: %d", rr.getTotalProcesses(), rr.getQuantum(), rr.getContextSwitchTime()));
      print("");
      print("+-+-+-+-+-+ Authors: Jahaan Jain & Thomas Blandino +-+-+-+-+-+");
      print("");
      print("CPU Utilization (Expected / Actual): " + (double) Math.round(rr.getCPU() * 10000d) / 10000d);
      print("Throughput (Processes / Total Time): " + (double) Math.round(rr.getThroughput() * 10000d) / 10000d);
      print("Average Turnaround Time (Avg Completion Time / Processes): "
          + (double) Math.round(rr.getAverageTurnaround() * 10000d) / 10000d);
      print("Average Waiting Time ((Turnaround / Initial) / Processes): " + (double) Math.round(rr.getAverageWaitingTime() * 10000d) / 10000d);
      print("Gantt Chart: " + rr.getGantt());
      print("Total Context Switches: " + rr.getContextSwitches());
      print("Total Completion Time: " + rr.getTime());
      print(rr.getProcesses());
    }
  }
}