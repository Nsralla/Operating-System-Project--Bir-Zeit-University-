import java.util.ArrayList;

public class Process {
    int pID;
    int arrivalTime;

    int numberOfCpuBursts;
    ArrayList<Integer> cpuBurst=new ArrayList<Integer>();
    ArrayList<Integer> IOBurst=new ArrayList<Integer>();

    int totalWorked=0;
    int finishTime=0;
    int firstArrivalTime=0;
    double waitingTime;

    int remainingTime;

    int numberOfPreemption;
    boolean isAtIO=false;

    public Process(){}


    public Process(int pID, int arrivalTime, int numberOfCpuBursts, ArrayList<Integer> cpuBurst, ArrayList<Integer> IOBurst, double waitingTime, int remainingTime, int numberOfPreemption,int totalWorked,int finishTime,int firstArrivalTime ) {
        this.pID = pID;
        this.arrivalTime = arrivalTime;
        this.numberOfCpuBursts = numberOfCpuBursts;
        this.cpuBurst = cpuBurst;
        this.IOBurst = IOBurst;
        this.waitingTime = waitingTime;
        this.remainingTime = remainingTime;
        this.numberOfPreemption = numberOfPreemption;
        this.finishTime=finishTime;
        this.firstArrivalTime=firstArrivalTime;
        this.totalWorked=totalWorked;
    }

    public int getpID() {
        return pID;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNumberOfCpuBursts() {
        return numberOfCpuBursts;
    }

    public void setNumberOfCpuBursts(int numberOfCpuBursts) {
        this.numberOfCpuBursts = numberOfCpuBursts;
    }

    public ArrayList<Integer> getCpuBurst() {
        return cpuBurst;
    }

    public void setCpuBurst(ArrayList<Integer> cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public ArrayList<Integer> getIOBurst() {
        return IOBurst;
    }

    public void setIOBurst(ArrayList<Integer>IOBurst) {
        this.IOBurst = IOBurst;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }


    public int getNumberOfPreemption() {
        return numberOfPreemption;
    }

    public void setNumberOfPreemption(int numberOfPreemption) {
        this.numberOfPreemption = numberOfPreemption;
    }

    @Override
    public String toString() {
        return "Process{" +
                "pID=" + pID +
                ", arrivalTime=" + arrivalTime +
                ", numberOfCpuBursts=" + numberOfCpuBursts +
                ", cpuBurst=" + cpuBurst +
                ", IOBurst=" + IOBurst +
                ", waitingTime=" + waitingTime +
                ", remainingTime=" + remainingTime +
                ", numberOfPreemption=" + numberOfPreemption +
                '}';
    }

    public int getTotalWorked() {
        return totalWorked;
    }

    public void setTotalWorked(int totalWorked) {
        this.totalWorked = totalWorked;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getFirstArrivalTime() {
        return firstArrivalTime;
    }

    public void setFirstArrivalTime(int firstArrivalTime) {
        this.firstArrivalTime = firstArrivalTime;
    }
}
