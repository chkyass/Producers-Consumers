# Producers-Consumers
The app allows K consumers and P producers to communicate with K + P controllers. The communication is done over TCP / IP. All these actors are Threads. Producers and Consumers correspond to clients and controllers to the Server.
Producers write messages to the buffer and consumers consume messages in the same buffer, via the controller associated with each one.
The problem is to allow parallel access (simultaneous read and write) in the buffer.

# Synchronization
At each moment the buffer is divided into three areas that are dynamically resized during execution :
 - A free zone
 - An area containing the messages
 - An area booked by the producers(because producers ask for permission before sending their messages to the buffer. Avoids the loss of messages)

These areas are controlled by three circular variables each representing the first position of a zone in the buffer.  
Parallel access on these areas is synchronized by three locks.  
Thus, three threads can work in parallel on the buffer.

# Test
First launch Generator.java in one shell.  
And launch Producers.java and Consumers.java in two other shells.
