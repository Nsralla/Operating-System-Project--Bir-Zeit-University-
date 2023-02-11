# Operating-System-Project--Bir-Zeit-University-
Project aim: You need to simulate a multilevel feedback queue scheduling algorithm with 4 queues: - Queue#1: Round Robin (RR) with time quantum q1. - Queue#2: Round Robin (RR) with time quantum q2. - Queue#3: Shortest-remaining-time first. - Queue#4: FCFS
The user should be able to specify the value of q1, q2 and 𝛼 that is used to predict the
duration of next CPU burst for the shortest-remaining-time first algorithm.
A new job enters Queue#1, if it does not finish its CPU burst within 10 time-quanta, it is
moved to Queue#2. Any job in Queue#2 should receive at max 10 time-quanta to finish its
current CPU burst, if it does not finish, it is moved to Queue#3.
Jobs in Queue#3 are scheduled using the shortest-remaining-time first algorithm. If a process
is preempted 3 times by other process entering Queue#3, it is moved to Queue#4 where it
is scheduled using FCFS algorithm.
Queue#1 is considered the highest priority and Queue#4 is the lowest. The scheduler first
executes all processes in Queue#1. Only when Queue#1 is empty will it execute processes in
Queue#2. A process that arrives for Queue#2 will preempt a process in Queue#3. A process
in Queue#2 will in turn be preempted by a process arriving for Queue#1.
Assume that the context switch time is very small and can be ignored in the simulation.
When a process finishes its current CPU burst, it moves to IO and starts consuming its IO
burst. Assume that processes do not wait for each other and they can perform IO
simultaneously. When the IO burst is finished, the process goes back to the queue from
which it requested the last IO burst (the last queue that the process was assigned to).
