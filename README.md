# Producers-Consumers
The app makes K consumers and P producers communicate with K + P controllers. The communication is done over TCP / IP. All these actors correspond to Threads. Producers and Consumers correspond to clients and controllers to the Server
Producers write messages to the buffer and consumers consume the messages in the same buffer, through the controller associated with each of them.
The problem is to allow parallel access (simultaneous read and write) from controllers to the buffer.

# Synchronization
At each moment the buffer is divided in three areas that are dynamically resized during execution :
 - An area of free box
 - An area containing the messages
 - An area booked by the producers( because producers ask for permission before sending their messages. This avoid messages lost)

These areas are controlled by three circulars variables representing each first position (index in the buffer) of  each area.  
The parallel access on these zones is synchronized by three locks.  
So three threads can work in parallel on the buffer.
