<h1> Chat Group Server </h1>
<h3>This is a chat group application.</h3>

Language: Java 8<br>
IDE: Intellij IDEA

We have a pool of threads that receives clients connection to send message to the server, and the server sends the message to all connected clientes.

* Thread safe
* Admin user can 
  * stop the server (and all clients connection).
  * list all connected clients
* Handles connection failure when client connection is lost

<h2>Class Diagram</h2>

![alt tag](https://github.com/claudiomarpda/ChatGroupServer/blob/master/uml/class_diagram.png)
