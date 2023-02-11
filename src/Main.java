import java.io.*;
import java.util.*;
public class Main {
    static Queue<Process> Q1= new LinkedList<>();//FIRST QUEUE
    static Queue<Process> Q2= new LinkedList<>();//SECOND QUEUE
    static Queue<Process> Q3= new LinkedList<>();//THIRD QUEUE
    static Queue<Process> Q4= new LinkedList<>();//FOURTH QUEUE
    static Queue<Process> IOQueue= new LinkedList<>();//IO QUEUE
    static ArrayList<Process> processes=new ArrayList<>();//ALL PROCESSES
    static ArrayList<Process> averageWaiting=new ArrayList<>();//TO COMPUTE AVERAGE WAITING FOR ALL PROCESSES
    static Process pAtCpu=new Process();//PROCESS RUNNING AT CPU
    static Process pAtIO=new Process();//PROCESS RUNNING AT IO

    static int q1;//QUANTUM TIME
    static int q2;//SECOND QUANTUM TIME
    static int  cpuTimer=0;//TIMER FOR CPU
    static int IOTimer=0;//TIMER FOR I/O
    static int numberOfPreemption=0;//TO READ NUMBER OF PREEMPTION'S
    static int pause=0;//TO READ AT WHICH TIME TO PAUSE PROGRAM
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner reader=new Scanner(System.in);

        int numOfProcesses;//number of processes
        int maxArrivalTime;// maximum arrival time for process
        int maxNoOfCpuBurst;// maximum number of process want to visit cpu
        int maxNoOfIOBurst;// maximum number to visit IO
        int minIO;
        int maxIO;
        int minCpuBurst;
        int maxCpuBurst;
        int autoIncrement=0;//to increase process id
        int compare=0;


        //THERE ARE 2 OPTIONS. EITHER MAKE PROCESSES RANDOMLY, OR USER MAKE THEM
        int choice=0;
            System.out.println("IF YOU WANT PROCESSES TO GENERATE RANDOMLY ENTER: 1\nIF YOU WANT TO READ FROM FILE: 2\nIF YOU WANT ENTER THEM MANUALLY ENTER: 3");
            choice=reader.nextByte();

            System.out.println("ENTER AT WHICH SECOND YOU WANT TO PAUSE THE PROGRAM: ");
            pause=reader.nextByte();



            if(choice==1)//TO GENERATE RANDOM NUMBER AT FILE
                  {
                    System.out.println("Enter number of processes:");//reading data from user
                    numOfProcesses=reader.nextInt();
                    System.out.println("enter max arrival time for processes:");
                    maxArrivalTime=reader.nextInt();
                    System.out.println("enter maximum number of cpu burst:");
                    maxNoOfCpuBurst=reader.nextInt();
                    System.out.println("enter min IO:");
                    minIO=reader.nextInt();
                    System.out.println("enter max IO:");
                    maxIO=reader.nextInt();
                    System.out.println("Enter min cpu burst:");
                    minCpuBurst=reader.nextInt();
                    System.out.println("Enter max cpu burst:");
                    maxCpuBurst=reader.nextInt();
                    System.out.println("Enter number of preemption:");
                    numberOfPreemption=reader.nextByte();
                    System.out.println("Enter q1:");
                    q1 = reader.nextByte();
                    System.out.println("Enter q2: ");
                    q2 = reader.nextByte();



                File file=new File("Data.txt");//creating file to write in
                Writer writer=new FileWriter(file);
                writer.write("");

                    for(int i=0;i<numOfProcesses;i++){//Writing to file
                        writer.write(String.valueOf(autoIncrement)+" "+randomMaxArrivalTime(maxArrivalTime)+" ");

                        int tmp=randomMaxNoOfCpuBurst(maxNoOfCpuBurst);//because i don't want it to change with every loop
                        int tmpIO=tmp-1;
                                   /* System.out.println("tmp: "+tmp);
                                    System.out.println("tmp: "+tmpIO);*/
                        for(int k=0;k<tmp;k++){
                            writer.append(String.valueOf(randomCpu(minCpuBurst,maxCpuBurst))+" ");//to generate cpu burst time
                            if(tmpIO>0) {
                                writer.append(String.valueOf(randomCpu(minIO, maxIO))+" ");//to generate IO BURST TIME
                            } tmpIO--;
                        }
                        writer.write("\n");
                        autoIncrement++;
                    }//writing to file
                    writer.close();


                    int pID=0;
                    int arrivalTime=0;



                    BufferedReader fReader = new BufferedReader(new FileReader("Data.txt"));//file to read from
                    String line;//to read each line of the file
                while ((line = fReader.readLine()) != null)
                    {
                        ArrayList<Integer> tmpCpuBursts=new ArrayList<>();
                        ArrayList<Integer> tmpIOBursts=new ArrayList<>();

                            String[] stroke = line.split(" ");
                            pID = Integer.parseInt(stroke[0]);
                            arrivalTime = Integer.parseInt(stroke[1]);
                        for(int i=2;i<stroke.length;)
                        {

                                if(i%2==0)
                                    {// to seperate CPU FROM THE IO
                                        tmpCpuBursts.add(Integer.parseInt(stroke[i]));
                                    }
                                else
                                    {
                                        tmpIOBursts.add(Integer.parseInt(stroke[i]));
                                    }
                                i++;
                        }
                         processes.add(new Process(pID,arrivalTime,tmpCpuBursts.size(),tmpCpuBursts,tmpIOBursts,0.0,0,0,0,0,arrivalTime));
                    }
                 fReader.close();
            }

            else if(choice==2)//TO READ FROM FILE
                    {
                            System.out.println("Enter number of preemption:");
                            numberOfPreemption=reader.nextByte();
                            System.out.println("Enter q1:");
                            q1 = reader.nextByte();
                            System.out.println("Enter q2: ");
                            q2 = reader.nextByte();
                            int pID=0;
                            int arrivalTime=0;

                            BufferedReader fReader = new BufferedReader(new FileReader("user_data_in.txt"));//file to read from
                            String line;//to read each line of the file
                        while ((line = fReader.readLine()) != null)
                        {
                                ArrayList<Integer> tmpCpuBursts=new ArrayList<>();
                                ArrayList<Integer> tmpIOBursts=new ArrayList<>();

                                    String[] stroke = line.split(" ");
                                    pID = Integer.parseInt(stroke[0]);
                                    arrivalTime = Integer.parseInt(stroke[1]);
                                for(int i=2;i<stroke.length;)
                                {

                                        if(i%2==0)
                                            {// to seperate CPU FROM THE IO
                                                tmpCpuBursts.add(Integer.parseInt(stroke[i]));
                                            }
                                        else
                                            {
                                                tmpIOBursts.add(Integer.parseInt(stroke[i]));
                                            }
                                     i++;
                                }
                                     processes.add(new Process(pID,arrivalTime,tmpCpuBursts.size(),tmpCpuBursts,tmpIOBursts,0.0,0,0,0,0,arrivalTime));
                        }
                    fReader.close();
                  }

            else if(choice==3)//TO ENTER NUMBERS FROM THE PROGRAM DIRECTLY
                 {//reading data from user
                            System.out.println("Enter number of processes:");//reading data from user
                            numOfProcesses=reader.nextInt();
                            for(int i=0;i<numOfProcesses;i++)
                            {
                                    System.out.println("for PROCESS "+(i+1));
                                    System.out.println("ENTER PROCESS ARRIVAL TIME: ");
                                    maxArrivalTime=reader.nextInt();
                                    System.out.println("ENTER NUMBER OF CPU BURSTS(HOW MANY TIME PROCESS WILL ENTER CPU):");
                                    maxNoOfCpuBurst=reader.nextInt();//after this, i have to read (time duration) for each cpu Burst

                                    ArrayList<Integer> timeInCpu=new ArrayList<>();
                                    for(int j=0;j<maxNoOfCpuBurst;j++)
                                        {
                                                System.out.println("FOR PROCESS "+i+", Enter "+(j+1)+" CPU BURST");
                                                timeInCpu.add(reader.nextInt());
                                        }//then i have to read IO DURATION FOR THIS PROCESS

                                    ArrayList<Integer> timeInIO=new ArrayList<>();
                                    for(int k=0;k<maxNoOfCpuBurst-1;k++)
                                        {
                                                System.out.println("FOR PROCESS "+i+", Enter "+(k+1)+" I/O BURST");
                                                timeInIO.add(reader.nextInt());
                                        }
                              processes.add(new Process(i,maxArrivalTime,maxNoOfCpuBurst,timeInCpu,timeInIO,0,0,0,0,0,maxArrivalTime));
                            }

                       System.out.println("ENTER NUMBER OF PREEMPTION: ");
                       numberOfPreemption=reader.nextByte();
                       System.out.println("Enter q1:");
                       q1 = reader.nextByte();
                       System.out.println("Enter q2: ");
                       q2 = reader.nextByte();
                 }

////////////////////////////////////////////////start working






        // int arrivalTime=0;//counter to count time

            while (true)//START EXECUTING
            {
                        int f=0;
                            for(int k=0;k<processes.size();k++)//search between processes if any of them arrived, then add to the first queue
                            {
                                    if(cpuTimer==processes.get(k).getArrivalTime())
                                        {

                                            f=1;
                                            Q1.offer(processes.get(k));//if the process arrived, add it to the first queue
                                            //then remove the process from (processes)
                                            processes.remove(k);
                                           k=-1;
                                        }
                            }




                            while(!Q1.isEmpty())//keep looping until Q1 is empty, send process to execute at cpu
                                {
                                        executeAtCpu1(Q1.poll());//send the first process to execute at cpu
                                }


                            while(!Q2.isEmpty()&&Q1.isEmpty())//Q2 is added from Q1, when process doesn't finish its CPU BURST
                                {//now send process to execute
                                    executeAtCpu2(Q2.poll());
                                }


                        while(!Q3.isEmpty()&&Q1.isEmpty()&& Q2.isEmpty())
                            {//you have to send the process with SRT in Q3, but how to get it? re arrange processes in Q3 using another arraylist
                                    ArrayList<Process> tmp2=new ArrayList<>();
                                    for(int i=0;i< Q3.size();i++)
                                        {
                                            tmp2.add(Q3.poll());//processes right now are it tmp
                                        }
                                    //sort (tmp2) array list, on the shortest cpuBurst, then put them back at Q3
                                    for(int i=0;i<tmp2.size()-1;i++){
                                        for(int j=0;j<tmp2.size()-i-1;j++){
                                            if(tmp2.get(j).getCpuBurst().get(0)>tmp2.get(j+1).getCpuBurst().get(0)) {
                                                Process tmp=tmp2.get(j);
                                                tmp2.set(j,tmp2.get(j+1));
                                                tmp2.set(j+1,tmp);
                                            }
                                        }
                                    }
                                    //now put the processes in the Third QUEUE
                                    for(int i=tmp2.size()-1;i>=0;i--)
                                        {
                                            Q3.offer(tmp2.get(i));//adding processes to QUEUE3
                                        }

                              executeAtCpu3(Q3.poll());
                            }

                        while(!Q4.isEmpty()&&Q3.isEmpty()&&Q2.isEmpty()&&Q1.isEmpty())
                                {
                                        executeAtCpu4(Q4.poll());
                                }


                        if(Q1.isEmpty()&&Q2.isEmpty()&&Q3.isEmpty()&&Q4.isEmpty()&& processes.isEmpty()){//if all processes finished, then break
                                break;
                            }

                        if(f==0)
                            cpuTimer++;

                        if(cpuTimer==pause)
                            Thread.sleep(1500);
                }

         double sum=0.0;//TO COMPUTE SUM OF WAITING TIME OF EACH PROCESSES
         for(int i=0;i<averageWaiting.size();i++)//TO COMPUTE  WAITING TIME FOR EACH PROCESS
                {
                    System.out.println("PROCESS "+averageWaiting.get(i).getpID()+" WAITING TIME" +
                            " = "+(averageWaiting.get(i).getFinishTime() -averageWaiting.get(i).getTotalWorked()-
                            averageWaiting.get(i).getFirstArrivalTime()));

                    sum+=(averageWaiting.get(i).getFinishTime() -averageWaiting.get(i).getTotalWorked()-
                            averageWaiting.get(i).getFirstArrivalTime());
                }
         System.out.println("AVERAGE WAITING TIME FOR ALL PROCESSES: "+((double)sum/averageWaiting.size()));//PRINTING AVERAGE WAITING TIME
         System.out.println("PROGRAM HAS FINISHED :)");


    }

    public static void executeAtCpu1(Process p) throws InterruptedException//start executing (RR)(//TO EXECUTE PROCESSES FROM Q1)
        {
                int counter=0;
                    pAtCpu=p;
                    if(pAtCpu==pAtIO)
                        cpuTimer=IOTimer;
                    pAtIO=new Process();


                System.out.println("IN QUEUE1, PROCESS ("+p.getpID()+") IS AT CPU : ");
                System.out.print("[ ");


                        while(counter<=q1&&counter<=p.getCpuBurst().get(0))//keep processing at cpu until q1
                        {
                                for(int i=0;i<processes.size();i++)//check if process reached Q1
                                {
                                        if(cpuTimer==processes.get(i).getArrivalTime())
                                            {
                                                Q1.offer(processes.get(i));
                                                processes.remove(i);
                                             i=-1;
                                            }
                                }



                          if(cpuTimer==pause)
                                {
                                    printProcessesInQ1(p);
                                    printProcessesInQ4(p);
                                    printProcessesInQ3(p);
                                    printProcessesInQ2(p);
                                }


                           System.out.print(cpuTimer+" ");
                           cpuTimer++;
                           counter++;

                        }
        counter--;
                System.out.print("]");
                System.out.println();
                System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+counter+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(p.getCpuBurst().get(0)-counter));
                System.out.println("-----------------------------------------------------------------------------------------");

                cpuTimer--;
                p.setTotalWorked(p.getTotalWorked()+counter);

                if(cpuTimer==pause)
                    {
                        printProcessesInQ1(p);
                        printProcessesInQ4(p);
                        printProcessesInQ3(p);
                        printProcessesInQ2(p);
                    }


                    //check if process has finished at cpu
                if(q1>=p.getCpuBurst().get(0))//this means that the process has finished its cpu burst, then make it's cpu burst zero
                    {
                            p.setFinishTime(cpuTimer);
                            p.getCpuBurst().set(0, 0);
                            p.getCpuBurst().remove(0);//remove this cpu burst
                        //now check for I/O
                            if(p.getIOBurst().size()!=0)//multi threading
                                {
                                        //add this process IO to IO QUEUE
                                        IOQueue.offer(p);
                                            InputOutput inputOutput=new InputOutput(p);

                                         if(pause==cpuTimer)
                                            printProcessesInIO(p);

                                         while(!IOQueue.isEmpty())//burst all IO QUEUE
                                            {
                                                    inputOutput.start();
                                                    inputOutput.join();
                                            }
                                }

                            //we have to add this process to averageWaiting
                            //but first check if process exists in there
                            if(averageWaiting.size()==0)
                                averageWaiting.add(p);

                            else
                                {
                                        int exist=0;
                                        for(int i=0;i<averageWaiting.size();i++)
                                            {
                                              if(averageWaiting.get(i).getpID()==p.getpID())
                                                  {
                                                     averageWaiting.remove(i);
                                                     averageWaiting.add(p);
                                                     exist=1;
                                                  }
                                            }

                                        if(exist==0)
                                            averageWaiting.add(p);
                                }

                    }



               else//else the process hasn't finished it's cpu burst,  subtract the counter from cpu burst, then i must send this process to the second queue
                    {
                        p.getCpuBurst().set(0, p.getCpuBurst().get(0) - counter);//update cpu burst
                        //update arrival time for second cpu
                        p.setArrivalTime(cpuTimer);
                        Q2.offer(p);//add the process to the second queue to complete executin
                    }
    }

    public static void executeAtCpu2(Process p) throws InterruptedException//TO EXECUTE PROCESSES FROM Q2
        {//start executing
                int counter=0;
                    pAtCpu=p;

                if(pAtCpu==pAtIO)
                    cpuTimer=IOTimer;
                pAtIO=new Process();

                int flag=0;
                System.out.println("IN QUEUE2, PROCESS ("+p.getpID()+") EXECUTING AT CPU: ");
                System.out.print("[ ");

                    while(counter<=q2&&counter<=p.getCpuBurst().get(0))//keep going until reaching q2
                        {

                                if(cpuTimer==pause) {
                                    printProcessesInQ1(p);
                                    printProcessesInQ2(p);
                                    printProcessesInQ3(p);
                                    printProcessesInQ4(p);
                                      }

                                //i have to check if process has arrived the first queue, while iam at the second Q
                                ArrayList<Process> tmp=new ArrayList<>();
                                for(int i=0;i<processes.size();i++)
                                    {
                                        if(cpuTimer==processes.get(i).getArrivalTime())
                                            {
                                                Q1.offer(processes.get(i));
                                                processes.remove(i);
                                                i=-1;
                                            }
                                    }
                                for(int i=0;i<Q1.size();i++)
                                    {
                                        tmp.add(Q1.poll());

                                    }
                                for(int i=0;i< tmp.size();i++)
                                    {
                                        if(tmp.get(i).getArrivalTime()==cpuTimer)
                                            {
                                                flag=1;
                                            }
                                    }
                                for(int i= tmp.size()-1;i>=0;i--)
                                    {
                                        Q1.offer(tmp.get(i));
                                    }
                                if(flag==1)
                                    break;//stop executing


                                System.out.print(cpuTimer+" ");
                                counter++;
                                cpuTimer++;
                        }

                System.out.print("]");
                System.out.println();
                if(flag==1)
                    System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+counter+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(p.getCpuBurst().get(0)-counter));
                else
                    System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+(counter-1)+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(p.getCpuBurst().get(0)-(counter-1)));

            System.out.println("-----------------------------------------------------------------------------------------");

                if(cpuTimer==pause) {
                    printProcessesInQ1(p);
                    printProcessesInQ2(p);
                    printProcessesInQ3(p);
                    printProcessesInQ4(p);
                }

                if(flag==1) {
                    p.setTotalWorked(p.getTotalWorked() + counter);
                }
                else {
                    counter--;
                    p.setTotalWorked(p.getTotalWorked() + counter - 1);
                }


                //check if process has finished executing in cpu
                if(q2>=p.getCpuBurst().get(0))
                    {
                            if(flag!=1)
                                {
                                    cpuTimer--;
                                }

                            p.setFinishTime(cpuTimer);
                            p.getCpuBurst().set(0, 0);
                            p.getCpuBurst().remove(0);//remove this cpu burst

                            //now check for IO
                            if(!p.getIOBurst().isEmpty())//if it has IO
                                {
                                        IOQueue.offer(p);//SEND IT TO IOQUEUE
                                        InputOutput inputOutput=new InputOutput(p);//send process to the class
                                    if(pause==cpuTimer)
                                        printProcessesInIO(p);

                                    while(!IOQueue.isEmpty())//burst all IO QUEUE
                                        {
                                            inputOutput.start();
                                            inputOutput.join();
                                            if(Q2.isEmpty()&&Q1.isEmpty()&&Q3.isEmpty()&&processes.isEmpty())
                                                {
                                                    cpuTimer=IOTimer;
                                                }
                                        }
                                }
                            //we have to add this process to averageWaiting
                            //but first check if process exists in there
                            if(averageWaiting.size()==0)
                                averageWaiting.add(p);

                            else
                                {
                                    int exist=0;
                                    for(int i=0;i<averageWaiting.size();i++)
                                        {
                                            if(averageWaiting.get(i).getpID()==p.getpID())
                                                {
                                                    averageWaiting.remove(i);
                                                    averageWaiting.add(p);
                                                    //i=-1;
                                                    exist=1;
                                                }
                                        }
                                    if(exist==0)
                                        averageWaiting.add(p);
                                }


                    }

                else if(flag==1)
                {
                        p.getCpuBurst().set(0, p.getCpuBurst().get(0) - counter);//update cpu burst
                        p.setArrivalTime(cpuTimer);
                        Q2.offer(p);//put the process back in the QUEUE
                }

                else//now if the process hasn't finished executing in cpu send it to Q3, and don't forget to update cpu burst
                {
                        p.getCpuBurst().set(0, p.getCpuBurst().get(0) - counter);//update cpu burst
                        //update arrival time for second cpu
                        p.setArrivalTime(cpuTimer);
                        Q3.offer(p);//add the process to the second queue to complete executin
                }
        }

    public static void executeAtCpu3(Process p) throws InterruptedException//SRTF YOU HAVE TO CHECK 2 THINGS
        {//1-Q1,Q2 ARE EMPTY, AND IF Q3 HAS SRT LES THAN THE CURRENT PROCESS
                int counter=0;
                int flag=0;//to check if a process has arrived while a process is executing at cpu3
                int flag2=0;//to check for other processes cpuBurst TIME

                pAtCpu=p;//save the current running process at cpu

                if(pAtCpu==pAtIO)//if the the current process was at IO, THEN THE TIMER OF CPU, MUST BE THE SAME WHERE IO REACHED
                    cpuTimer=IOTimer;
                pAtIO=new Process();

                System.out.println("IN QUEUE3, process ("+p.getpID()+") is at cpu: ");
                System.out.print("[ ");
                //TAKE THE CpyBURST OF THIS PROCESS
                int tmpBurst=0;
                tmpBurst=p.getCpuBurst().get(0);//because process CBUBURST WILL BE CHANGED IN THE LOOP

                while(counter<=tmpBurst)//keep processing at cpu until tmpBurst
                    {
                            if(pause==cpuTimer) {
                                printProcessesInQ1(p);
                                printProcessesInQ2(p);
                                printProcessesInQ3(p);
                                printProcessesInQ4(p);
                                 }

                            ArrayList<Process> tmp=new ArrayList<>();
                            for(int i=0;i<processes.size();i++)//search in processes, and if the arrival time of each process=current time
                                {
                                    if(cpuTimer==processes.get(i).getArrivalTime())
                                        {
                                            Q1.offer(processes.get(i));//add the process to the Queue
                                            processes.remove(i);//remove it from the arrayList
                                            i=-1;
                                        }
                                }
                            for(int i=0;i<Q1.size();i++)//add the process to tmp
                                {
                                    tmp.add(Q1.poll());
                                }
                            for(int i=0;i< tmp.size();i++)//check if any process has arrived
                                {
                                    if(tmp.get(i).getArrivalTime()==cpuTimer)
                                        {
                                            flag=1;
                                        }
                                }
                            for(int i= tmp.size()-1;i>=0;i--)
                                {
                                    Q1.offer(tmp.get(i));//re send the process to the queue
                                }
                            if(flag==1)//if process has reached, break the loop
                                break;//stop executing

                            //also i have to check another processes in Q2, after you break the process after you find one with SRT, don't forget to put it pack in the   QUEUE
                            // I have to check another processes in the Q3
                            //but to check them you need an array list to check them
                            //but before reduce CPU BURST FOR THIS PROCESS BY ONE

                            //after this check for other processes, if process with less cpu burst came in, break
                            ArrayList<Process> t=new ArrayList<>();
                            for(int i=0;i< Q3.size();i++)//put procceses of Q3 AT t
                                {
                                    t.add(Q3.poll());
                                }//let's compare between processes and p
                            for(int i=0;i<t.size();i++)
                                {
                                    if(t.get(i).getCpuBurst().get(0)<p.getCpuBurst().get(0))
                                        {
                                            flag2 = 1;
                                        }
                                }
                            //re send the processes to the queue
                            for(int i=t.size()-1;i>=0;i--)
                                {
                                    Q3.offer(t.get(i));
                                }
                            if(flag2==1)//there is a process with less SRT
                                break;

                            System.out.print(cpuTimer+" ");
                            cpuTimer++;
                            counter++;
                            p.getCpuBurst().set(0,p.getCpuBurst().get(0)-1);
                    }

                System.out.print("]");
                System.out.println();
               if(flag==1||flag2==1)
                  System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+counter+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(tmpBurst-counter));
               else
                   System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+(counter-1)+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(tmpBurst-(counter-1)));
            System.out.println("-----------------------------------------------------------------------------------------");

                if(pause==cpuTimer) {
                    printProcessesInQ1(p);
                    printProcessesInQ2(p);
                    printProcessesInQ3(p);
                    printProcessesInQ4(p);          }

                if(flag==1||flag2==1) {
                    p.setTotalWorked(p.getTotalWorked() + counter);
                     }
                else {
                    counter--;
                    p.setTotalWorked(p.getTotalWorked() + counter - 1);
                 }

                //check if process has finished at cpu
                if(p.getCpuBurst().get(0)<=0)//this means that the process has finished its cpu burst, then make it's cpu burst zero
                {
                            p.getCpuBurst().remove(0);//remove this cpu burst
                            if(flag!=1&&flag2!=1)
                                cpuTimer--;
                            //now check for I/O
                            p.setFinishTime(cpuTimer);

                            if(p.getIOBurst().size()!=0)//multi threading
                                {
                                        //add this process IO to IO QUEUE
                                        IOQueue.offer(p);
                                        InputOutput inputOutput=new InputOutput(p);
                                        if(pause==cpuTimer)
                                            printProcessesInIO(p);

                                        while(!IOQueue.isEmpty())//burst all IO QUEUE
                                            {
                                                inputOutput.start();
                                                inputOutput.join();
                                            }
                                }

                            //we have to add this process to averageWaiting
                            //but first check if process exists in there
                            if(averageWaiting.size()==0)
                                averageWaiting.add(p);

                            else
                             {
                                    int exist=0;
                                    for(int i=0;i<averageWaiting.size();i++)
                                        {
                                            if(averageWaiting.get(i).getpID()==p.getpID())
                                                {
                                                    averageWaiting.remove(i);
                                                    averageWaiting.add(p);
                                               //     i=-1;
                                                    exist=1;
                                                }
                                        }

                                    if(exist==0)
                                        averageWaiting.add(p);
                             }
                }

                //now there is two types of interruption, the first one because of Q1,Q2, AND FOR THIS TYPE RE ADD THE PROCESS TO Q3
                else if(flag==1)
                    {
                        p.setArrivalTime(cpuTimer);
                        Q3.offer(p);//put the process back in the QUEUE
                    }
                //and the second type from the same QUEUE, then check for number of preemption
                //else meas that the process hasn't finished executing, then increase number of preemption,if it reached 3, then add it to the Q4
                else
                    {
                        p.setNumberOfPreemption(p.getNumberOfPreemption()+1);
                            if(p.getNumberOfPreemption()==numberOfPreemption) {//if the process has preempted 3 times add it to FOURTH QUEUE
                                p.setArrivalTime(cpuTimer);
                                Q4.add(p);
                            }
                            else
                                Q3.offer(p);

                    }
    }

    public static void executeAtCpu4(Process p) throws InterruptedException//TO EXECUTE PROCESSES FROM Q4
        {
                int counter=0;
                int flag=0;//to check if process has arrived in Q1

                pAtCpu=p;//save the current running process at cpu
                if(pAtCpu==pAtIO)//if the the current process was at IO, THEN THE TIMER OF CPU, MUST BE THE SAME WHERE IO REACHED
                    cpuTimer=IOTimer;
                pAtIO=new Process();

                System.out.println("IN QUEUE4, process ("+p.getpID()+") is at cpu: ");
                System.out.print("[ ");
                int tmpBurst=0;
                tmpBurst=p.getCpuBurst().get(0);//because process CBUBURST WILL BE CHANGED IN THE LOOP

                while (counter<tmpBurst)
                {
                        if(cpuTimer==pause) {
                            printProcessesInQ1(p);
                            printProcessesInQ2(p);
                            printProcessesInQ3(p);
                            printProcessesInQ4(p);
                                      }

                        ArrayList<Process> tmp=new ArrayList<>();
                        for(int i=0;i<processes.size();i++)//search in processes, and if the arrival time of each process=current time
                             {
                                if(cpuTimer==processes.get(i).getArrivalTime())
                                    {
                                        Q1.offer(processes.get(i));//add the process to the Queue
                                        processes.remove(i);//remove it from the arrayList
                                        i=-1;
                                    }
                              }
                        for(int i=0;i<Q1.size();i++)//add the process to tmp
                             {
                                 tmp.add(Q1.poll());

                             }

                        for(int i=0;i< tmp.size();i++)//check if any process has arrived
                             {
                                if(tmp.get(i).getArrivalTime()==cpuTimer)
                                {
                                    flag=1;
                                }
                             }

                        for(int i= tmp.size()-1;i>=0;i--)
                             {
                                Q1.offer(tmp.get(i));//re send the process to the queue
                             }
                        if(flag==1)//if process has reached, break the loop
                            break;//stop executing

                        System.out.print(cpuTimer+" ");
                        counter++;
                        cpuTimer++;
                        p.getCpuBurst().set(0,p.getCpuBurst().get(0)-1);
                }

                System.out.print("]");
                System.out.println();
                if(flag==1)
                     System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+counter+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(tmpBurst-counter));
                else
                    System.out.println("PROCESS ("+p.getpID()+") HAS BEEN EXECUTING FOR: "+(counter-1)+"\n"+"AND THE REMAINING TIME FOR THIS CPU BURST: "+(tmpBurst-(counter-1)));
                System.out.println("-----------------------------------------------------------------------------------------");

                if(pause==cpuTimer) {
                    printProcessesInQ1(p);
                    printProcessesInQ2(p);
                    printProcessesInQ3(p);
                    printProcessesInQ4(p);            }

                if(flag==1)
                    p.setTotalWorked(p.getTotalWorked()+counter);
                else {
                    counter--;
                    p.setTotalWorked(p.getTotalWorked() + counter - 1);
                }

                //check if process has finished executing in cpu
                if(p.getCpuBurst().get(0)<=0)
                {
                        p.getCpuBurst().remove(0);//remove this cpu burst
                        cpuTimer--;
                        p.setFinishTime(cpuTimer);
                        //now check for I/O
                        if(p.getIOBurst().size()!=0)//multi threading
                            {
                                //add this process IO to IO QUEUE
                                IOQueue.offer(p);
                                InputOutput inputOutput=new InputOutput(p);
                                if(pause==cpuTimer)
                                    printProcessesInIO(p);
                                while(!IOQueue.isEmpty())//burst all IO QUEUE
                                    {
                                        inputOutput.start();
                                        inputOutput.join();
                                    }
                            }
                        //we have to add this process to averageWaiting
                        //but first check if process exists in there
                        if(averageWaiting.size()==0)
                            averageWaiting.add(p);

                        else
                            {
                                int exist=0;
                                for(int i=0;i<averageWaiting.size();i++)
                                    {
                                        if(averageWaiting.get(i).getpID()==p.getpID())
                                        {
                                            averageWaiting.remove(i);
                                            averageWaiting.add(p);
                                         //   i=-1;
                                            exist=1;
                                        }
                                    }
                                if(exist==0)
                                    averageWaiting.add(p);
                            }
                }

                else if(flag==1)
                    {
                        p.setArrivalTime(cpuTimer);
                        Q4.offer(p);//put the process back in the QUEUE
                    }
    }

    public static int randomMaxArrivalTime(int max){
        int x;
        Random random=new Random();
        x=random.nextInt(max);
        return x;
    }//TO GENERATE RANDOM ARRIVAL TIME FOR PROCESSES
    public static int randomMaxNoOfCpuBurst(int max){
        int x;
        Random random=new Random();
        x=random.nextInt(max);
        while(x==0)
            x=random.nextInt(max);

        return x;
    }//TO GENERATE RANDOM NUMBER OF CPU BURSTS

    public static int randomCpu(int min,int max){
        int x;
        Random random=new Random();
        x=random.nextInt(30);
        while(x>max||x<min)
            x=random.nextInt(30);
        return x;
    }//TO GENERATE RANDOM CPU BURSTS

    public static void printProcessesInQ1(Process p) throws InterruptedException//TO PRINT PROCESSES AT CPU FROM Q1
        {
                System.out.println();
                System.out.println("CURRENT PROCESS RUNNING AT CPU:"+(p.getpID()));
                System.out.println();
                System.out.println("CURRENT PROCESSES AT Q1:");
                System.out.println();
                Thread.sleep(1000);
                ArrayList<Process> tmp=new ArrayList<>();//now i want to print processes in Q1
                    for(int i=0;i<Q1.size();i++)//take processes from Q 1
                        {
                            tmp.add(Q1.poll());
                        }
                    for(int i= tmp.size()-1;i>=0;i--)//send them back
                        {
                            Q1.offer(tmp.get(i));
                        }
                System.out.println(tmp.toString());
                Thread.sleep(3000);
                System.out.println();
        }
    public static void printProcessesInQ2(Process p) throws InterruptedException//TO PRINT PROCESSES AT CPU FROM Q2
         {
                System.out.println();
                System.out.println("CURRENT PROCESS RUNNING AT CPU:"+(p.getpID()));
                System.out.println();
                System.out.println("CURRENT PROCESSES AT Q2:");
                System.out.println();
                Thread.sleep(1000);
                ArrayList<Process> tmp=new ArrayList<>();//now i want to print processes in Q1
                    for(int i=0;i<Q2.size();i++)//take processes from Q 1
                        {
                            tmp.add(Q2.poll());
                        }
                    for(int i= tmp.size()-1;i>=0;i--)//send them back
                        {
                            Q2.offer(tmp.get(i));
                        }
                System.out.println(tmp.toString());
                Thread.sleep(2000);
                System.out.println();
      }
    public static void printProcessesInQ3(Process p) throws InterruptedException//TO PRINT PROCESSES AT CPU FROM Q3
        {
                System.out.println();
                System.out.println("CURRENT PROCESS RUNNING AT CPU:"+(p.getpID()));
                System.out.println();
                System.out.println("CURRENT PROCESSES AT Q3:");
                System.out.println();
                Thread.sleep(1000);
                ArrayList<Process> tmp=new ArrayList<>();//now i want to print processes in Q1
                    for(int i=0;i<Q3.size();i++)//take processes from Q 1
                        {
                            tmp.add(Q3.poll());
                        }
                    for(int i= tmp.size()-1;i>=0;i--)//send them back
                        {
                            Q3.offer(tmp.get(i));
                        }
                System.out.println(tmp.toString());
                Thread.sleep(2000);
                System.out.println();
        }
    public static void printProcessesInQ4(Process p) throws InterruptedException//TO PRINT PROCESSES AT CPU FROM Q4
        {
                System.out.println();
                System.out.println("CURRENT PROCESS RUNNING AT CPU:"+(p.getpID()));
                System.out.println();
                System.out.println("CURRENT PROCESSES AT Q4:");
                System.out.println();
                Thread.sleep(1000);
                ArrayList<Process> tmp=new ArrayList<>();//now i want to print processes in Q1
                    for(int i=0;i<Q4.size();i++)//take processes from Q 1
                        {
                            tmp.add(Q4.poll());

                        }
                    for(int i= tmp.size()-1;i>=0;i--)//send them back
                        {
                            Q4.offer(tmp.get(i));
                        }
                System.out.println(tmp.toString());
                Thread.sleep(2000);
                System.out.println();
        }
    public static void printProcessesInIO(Process p) throws InterruptedException//TO PRINT PROCESSES AT I/O
        {
                System.out.println();
                System.out.println("CURRENT PROCESS RUNNING AT IO:"+(p.getpID()));
                System.out.println();
                System.out.println("CURRENT PROCESSES AT I/O QUEUE:");
                System.out.println();
                Thread.sleep(1000);
                ArrayList<Process> tmp=new ArrayList<>();//now i want to print processes in Q1

                    for(int i=0;i<IOQueue.size();i++)//take processes from Q 1
                        {
                            tmp.add(IOQueue.poll());

                        }
                    for(int i= tmp.size()-1;i>=0;i--)//send them back
                        {
                            IOQueue.offer(tmp.get(i));
                        }
                System.out.println(tmp.toString());
                Thread.sleep(2000);
                System.out.println();
        }
}

