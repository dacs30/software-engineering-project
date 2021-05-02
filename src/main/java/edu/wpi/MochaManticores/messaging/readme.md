#Messaging Package
***
The messaging package is composed of five classes. The observer model is utilized to update all client instances as the
server is updated. The server contains the global state, clients read and write this state over a socket connection to facilitate 
messaging. The server is also responseable for updating the message database, this is used only to store past messages,
not to send messages. 
1. connectionUtil
   + This class holds information that defines the connection network and port
   + Used when creating new sockets 
2. messageClient
   + This is the messaging client, the user inteacts with this element
   + GUI is defined here
   + Server startup is called here if there is no current server
   + Spawns the client reader thread to continuously read and update messageClient GUI
3. clientReader
   + Spawned by the message client to read the socket connection and write messages to the client
   + TODO, have the clientReader update a new client on missed messages
4. messageServer
   + This is the message server, spawned by a new client if no other servers exist
   + Holds global state of the observer model
   + Responcable for broadcasting changes across the network
5. serverConnections
   + gjjg