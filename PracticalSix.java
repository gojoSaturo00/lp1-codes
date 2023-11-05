package Practicals;

import java.io.Closeable;
import java.util.Scanner;
import java.util.Arrays;

class Processes {
    int pid;
    int waitingTime;
    int arrivalTime;
    int burstTime;
    int turnAroundTime;
    int timeToComplete;
    int completionTime;
    int priority;

    Processes (int pid, int sub, int bur, int priority) {
        this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.priority = priority;
        this.timeToComplete = burstTime;
    }
}

public class PracticalSix {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the number of Processes: ");
        int n = s.nextInt();
        ProcessSeven[] myprocess = new ProcessSeven[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter ArrivalTime, BurstTime, and Priority: ");
            int sub = s.nextInt();
            int bur = s.nextInt();
            int priority = s.nextInt();
            myprocess[i] = new ProcessSeven(i + 1, sub, bur, priority);
        }

        System.out.println("Select the type of Scheduler to be used: ");
        System.out.println("1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Priority");
        System.out.println("4. RoundRobbin");
        System.out.println("5. Exit");
        System.out.println("Enter your Choice: ");
        int choice = s.nextInt();

        switch (choice) {
            case 1:
                PriorityScheduling(myprocess);
                break;
            case 2:
                RoundRobbin(myprocess);
                break;
            case 3:
                System.exit(1);
                break;
            default:
                System.out.println("Incorrect Choice!!");
                break;
        }
        s.close();
    }

    static void PriorityScheduling(ProcessSeven[] myprocess) {
        int n = myprocess.length;
        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int highestPriorityIndex = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (myprocess[i].arrivalTime <= currentTime && myprocess[i].priority < highestPriority && myprocess[i].completionTime == 0) {
                    highestPriorityIndex = i;
                    highestPriority = myprocess[i].priority;
                }
            }

            if (highestPriorityIndex == -1) {
                currentTime++;
            } else {
                ProcessSeven process = myprocess[highestPriorityIndex];
                process.completionTime = currentTime + process.burstTime;
                process.turnAroundTime = process.completionTime - process.arrivalTime;
                process.waitingTime = process.turnAroundTime - process.burstTime;
                completedProcesses++;
                currentTime = process.completionTime;
            }
        }

        // Print results
        System.out.println("Process\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (ProcessSeven process : myprocess) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.priority
                    + "\t\t" + process.completionTime + "\t\t" + process.turnAroundTime + "\t\t" + process.waitingTime);
        }
    }
    static void RoundRobbin(ProcessSeven[] myprocess) {
        int n = myprocess.length;
        int quantum = 2; // Change the quantum as needed
        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            for (int i = 0; i < n; i++) {
                ProcessSeven process = myprocess[i];
                if (process.completionTime == 0 && process.arrivalTime <= currentTime) {
                    int remainingTime = process.timeToComplete;
                    if (remainingTime > quantum) {
                        currentTime += quantum;
                        process.timeToComplete -= quantum;
                    } else {
                        currentTime += remainingTime;
                        process.timeToComplete = 0;
                        process.completionTime = currentTime;
                        process.turnAroundTime = process.completionTime - process.arrivalTime;
                        process.waitingTime = process.turnAroundTime - process.burstTime;
                        completedProcesses++;
                    }
                }
            }
        }

        // Print results
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (ProcessSeven process : myprocess) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t"
                    + process.completionTime + "\t\t" + process.turnAroundTime + "\t\t" + process.waitingTime);
        }
    }
}


