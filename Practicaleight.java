package Practicals;
import java.io.Closeable;
import java.util.Scanner;
import java.util.Arrays;

class ProcessEight {
    int pid;
    int waitingTime;
    int arrivalTime;
    int burstTime;
    int turnAroundTime;
    int timeToComplete;
    int completionTime;
    int priority;

    ProcessEight (int pid, int sub, int bur, int priority) {
        this.pid = pid;
        this.arrivalTime = sub;
        this.burstTime = bur;
        this.priority = priority;
        this.timeToComplete = burstTime;
    }
}

public class Practicaleight {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the number of Processes: ");
        int n = s.nextInt();
        ProcessEight[] myprocess = new ProcessEight[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter ArrivalTime, BurstTime, and Priority: ");
            int sub = s.nextInt();
            int bur = s.nextInt();
            int priority = s.nextInt();
            myprocess[i] = new ProcessEight(i + 1, sub, bur, priority);
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
                FCFS(myprocess);
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

    static void FCFS(ProcessEight[] myprocess) {
        int x = 0;

        //arrange process according to their arrival time in asc order
        ProcessEight temp;
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

    static void RoundRobbin(ProcessEight[] myprocess) {
        int n = myprocess.length;
        int quantum = 2; // Change the quantum as needed
        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            for (int i = 0; i < n; i++) {
                ProcessEight process = myprocess[i];
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
        for (ProcessEight process : myprocess) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t"
                    + process.completionTime + "\t\t" + process.turnAroundTime + "\t\t" + process.waitingTime);
        }
    }
}