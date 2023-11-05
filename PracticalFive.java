package Practicals;

import java.io.Closeable;
import java.util.Scanner;
import java.util.Arrays;

class Process {
    int pid;
    int waitingTime;
    int arrivalTime;
    int burstTime;
    int turnAroundTime;
    int timeToComplete;
    int completionTime;
    int priority;

    Process (int pid, int sub, int bur, int priority) {
        this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.priority = priority;
        this.timeToComplete = burstTime;
    }
}

public class PracticalFive {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the number of Processes: ");
        int n = s.nextInt();
        Processes[] myprocess = new Processes[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter ArrivalTime, BurstTime, and Priority: ");
            int sub = s.nextInt();
            int bur = s.nextInt();
            int priority = s.nextInt();
            myprocess[i] = new Processes(i + 1, sub, bur, priority);
        }

        System.out.println("Select the type of Scheduler to be used: ");
        System.out.println("1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Exit");
        System.out.println("Enter your Choice: ");
        int choice = s.nextInt();

        switch (choice) {
            case 1:
                FCFS(myprocess);
                break;
            case 2:
                SJF(myprocess);
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

    static void FCFS(Processes[] myprocess) {
        int x = 0;

        //arrange process according to their arrival time in asc order
        Processes temp;
        for (int i = 0; i < myprocess.length; i++) {
            for (int j = i; j < myprocess.length; j++) {
                if (myprocess[i].arrivalTime > myprocess[j].arrivalTime) {
                    temp = myprocess[j];
                    myprocess[j] = myprocess[i];
                    myprocess[i] = temp;
                }
            }
        }

        for (int i = 0; i < myprocess.length; i++) {
            x = Math.max(x, myprocess[i].arrivalTime);  //update the curent time;
            myprocess[i].completionTime = x + myprocess[i].burstTime;
            myprocess[i].turnAroundTime = myprocess[i].completionTime - myprocess[i].arrivalTime;
            myprocess[i].waitingTime = myprocess[i].turnAroundTime - myprocess[i].burstTime;
            x = myprocess[i].completionTime; // updating the current time
            System.out.println("Process" + myprocess[i].pid + ":");
            System.out.println("TurnAroundTime\tCompletionTime\tWaitingTime");
            System.out.println(myprocess[i].turnAroundTime + "\t\t\t" + myprocess[i].completionTime + "\t\t\t" + myprocess[i].waitingTime);
        }
    }

    static void SJF(Processes[] myprocess) {
        int currentTime = 0;
        int completedProcesses = 0;

        // Sort processes based on arrival time initially
        Arrays.sort(myprocess, (a, b) -> a.arrivalTime - b.arrivalTime);

        while (completedProcesses < myprocess.length) {
            int shortestJobIndex = -1;
            int shortestJobTime = Integer.MAX_VALUE;

            for (int i = 0; i < myprocess.length; i++) {
                if (myprocess[i].arrivalTime <= currentTime && myprocess[i].timeToComplete < shortestJobTime && myprocess[i].timeToComplete > 0) {
                    shortestJobIndex = i;
                    shortestJobTime = myprocess[i].timeToComplete;
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
            } else {
                Processes shortestJob = myprocess[shortestJobIndex];
                shortestJob.timeToComplete--;
                currentTime++;

                if (shortestJob.timeToComplete == 0) {
                    shortestJob.completionTime = currentTime;
                    shortestJob.turnAroundTime = shortestJob.completionTime - shortestJob.arrivalTime;
                    shortestJob.waitingTime = shortestJob.turnAroundTime - shortestJob.burstTime;
                    completedProcesses++;
                }
            }
        }

        // Sort processes based on process ID to print in the order they were entered
        Arrays.sort(myprocess, (a, b) -> a.pid - b.pid);

        // Print results
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Processes process : myprocess) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t"
                    + process.completionTime + "\t\t" + process.turnAroundTime + "\t\t" + process.waitingTime);
        }
    }
}
