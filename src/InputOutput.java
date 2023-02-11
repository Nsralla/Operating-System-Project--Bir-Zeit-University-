public class InputOutput extends Thread{
    //we need to take the process with us
    Process p=new Process();
    public InputOutput(Process p){//constructor
        this.p=p;
    }


    @Override
    public void run(){
        Main.IOQueue.poll();//remove the process from IO EXECUTING
            //process must start executing at IO:
        int counter=0;//TO PRINT THE TIME THAT PROCESS SPENT IN IO
            if(Main.cpuTimer>Main.IOTimer)
                 {Main.IOTimer=Main.cpuTimer;}
                      System.out.println("PROCESS ("+p.getpID()+") IS EXECUTING AT IO:");
        System.out.print("[ ");
                         Main.pAtIO=p;
                        while(counter<=p.getIOBurst().get(0))
                            {
                                System.out.print(Main.IOTimer+" ");
                               counter++;
                               Main.IOTimer++;
                            }
        System.out.print("]");
                        Main.IOTimer--;
                        counter--;
        System.out.println();
        System.out.println("PROCESS "+p.getpID()+" HAS FINISHED EXECUTING IO");
        System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+counter+"\n");
        System.out.println("-----------------------------------------------------------------------------------------");
        p.setArrivalTime(Main.IOTimer);//UPDATE CPU ARRIVAL TIME

            //ASLO WE HAVE TO REMOVE IO BURST FROM ARRAY LIST:
                p.getIOBurst().remove(0);
        Main.processes.add(p);//to go back to execute at cpu, since program must end with cpu and IO burst, note: it will be added automaticly to Q1



    }
}
