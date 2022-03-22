# Request-Reply Messaging App

<div id="top"></div>

<br />
<div align="center">
  <h2 align="center">Communication Networks</h2>
  <p align="center"> Mandana Eleni</p>
  <p align="center"> fifth semester 2021/2022 </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Contents</summary>
  <ol>
    <li> <a href="#about">About</a></li>
    <li>
      <a href="#classes">Classes</a>
      <ul>
        <li><a href="#server">Server</a></li>
        <li><a href="#client">Client</a></li>
        <li><a href="#funcs">Funcs</a></li>
        <li><a href="#functions">Functions Server</a></li>
          <ul>
            <li><a href="#create-account">Create Account</a></li>
            <li><a href="#show-accounts">Show Accounts</a></li>
            <li><a href="#send-message">Send Message</a></li>
            <li><a href="#show-inbox">Show Inbox</a></li>
            <li><a href="#read-message">Read Message</a></li>
            <li><a href="#delete-message">Delete Message</a></li>
          </ul>
        <li><a href="#account">Account</a></li>
        <li><a href="#message">Message</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contant</a></li>
  </ol>
</details>


<div id="about"></div>
<font size="6">About</font>

It is a simple program that allows **multiple users** to connect to a **server** and communicate with each other.

Each user can perform six *functions* :

* <a href="#create-account">create an account </a>
* <a href="#show-accounts">view existing accounts</a>
* <a href="#send-message">send a message</a>
* <a href="#read-message">read a message</a>
* <a href="#delete-message">delete a message</a>
* <a href="#show-inbox">view their inbox</a>

<div id="classes"></div>
<font size="6"> Classes </font>

<div id="server"></div>
<h2> <u> Server </u> </h2>

The *Server.java* file contains the server code. Τhe server is implemented using **RMI**. 

In order to run the Server, you need to provide a single argument which is the **port number**. If there is more or less than one argument the program exits.

<div id="client"></div>
<h2> <u> Client </u> </h2>

The *Client.java* is the client file.

When running the client the arguments vary, depending on the function you are performing.

---
>*Function ID* 1 (*create account*) : \<**ip**> \<**port number**> 1 \<**username**>

>*Function ID* 2 (*show accounts*) : \<**ip**> \<**port number**> 2 \<**auth token**>

>*Function ID* 3 (*send message*) : \<**ip**> \<**port number**> 3 \<**auth token**> \<**recipient**> \<**messsage body**>

>*Function ID* 4 (*show inbox*) : \<**ip**> \<**port number**> 4 \<**auth token**>

>*Function ID* 5 (*read message*) : \<**ip**> \<**port number**> 5 \<**auth token**> \<**message id**>

>*Function ID* 6 (*delete message*) : \<**ip**> \<**port number**> 6 \<**auth token**> \<**message id**>
---
Αrguments are considered invalid only if their number is less than four. The trust is placed on the user which is the reason why the arguments' order is not examined any further. Necessary checks are performed only on the auth token, and on the username (FN ID 3), **provided** that they are in the right place. There is one more check for the function ID, given that it is in the correct position, to have a value between 1 and 6.

Once all these arguments have passed the check and depending on the function ID, the client requests from the server the propper function. The response is returned and printed on the client's side.

<div id="funcs"></div>
<h2> <u> Funcs </u> </h2>

Funs is the *common interface* of both the Client and the Server. 

<div id="functions"></div>
<h2> <u> Functions Server </u> </h2>
In Functions_Server.java the functions declared in the interface are implemented. 
Users are stored in a Hash Map to reduce the complexity of some searches that need to be done.

To give each message a unique id (message id), there is a counter counting the total number of sent messages. The id each messages gets is the value of the counter at the moment the message is sent.

<div id="create-account"></div>
<h3 align="center"><b> Create Account </b></h3>
<p>A user requests to create an account with a desired username. This function checks if the desired username is available and valid. A username is available if there is no other user named after it. A valid <a href="#username-info">username</a> is one consisting only of alphanumeric characters but can also contain underscores

Due to the limited auth tokens <a href="#tokens-info">available</a>, there is an additional check that determines if the number of users has reached the maximum number of auth tokens, which needs to be done to avoid the infinite loop at the generation of the auth token.

If the username is valid and available it returns the auth token that refers to the user, if the username is taken it returns -1 as auth token, and the message *Sorry, the user already exists* is printed, if the username is invalid it returns -2, and the message *Invalid Username* is printed and if there can be no more unique users because all the 4-digits auth tokens have been generated it returns -3 and the message *Sorry, unable to create new accounts* is printed.</p>

<div id="show-accounts"></div>
<h3 align="center"><b> Show Accounts </b></h3>
<p> Simply returns an array containing every user's username. These usernames are printed in the Client.
</p>


<div id="send-message"></div>
<h3 align="center"><b> Send Message </b></h3><p> 

Allows a user to send a message to another user. Both users must be registered. A user is allowed to send a message to themselves.

Also, the message body can be empty but the message body argument should not be omitted, <!-- ues ontos na to pis afto? -->there is no check for that.

To send a message, a Message object must be created first. By default every message is considered not read. Then, in order to add this message to the user's inbox, we create a copy of the user, add the message to the copied user's inbox, and replace the user with the updated copy.

When a message is successfuly sent, the message *OK* is printed, any other message indicates the problem that occured while trying to send the message</p>

<div id="show-inbox"></div>
<h3 align="center"><b> Show Inbox </b></h3>

Prints the user's inbox. A user is authorised to view only **their** inbox.

The function returns an arraylist of strings, of every message in a specific user's inbox. The messages are in the following format: 

><font size="3"><p align="center"><b><i>message_id</i>. from: <i>sender</i></b></p></font>

followed by '*' in case the message is not read yet.
><font size="3"><p align="center"><b><i>message_id</i>. from: <i>sender</i>*</b></p></font>

In case of an empty inbox, it returns an empty arraylist.
</p>

<div id="read-message"></div>
<h3 align="center"><b> Read Message </b></h3><p> 

With the read message function you can mark a message as read. Basically it allows a user to change the *isRead* value of a specific message from false to true. Although if a message is already read, the user can "re-read" it and there will be no change. Returns a string of the message, in the following format:

><font size="3"><p align="center"> <b>(<i>sender</i>)<i>message body</i></b></p></font>

If a user tries to read a message that does not exist or exists in another user's inbox then it returns *Message ID does not exist*</p>

<div id="delete-message"></div>
<h3 align="center"><b> Delete Message </b></h3><p>

Allows the user to remove a message from their inbox, by providing the message's id. A user is not allowed to remove messages from another user's inbox. If the message is successfuly deleted it returns the string *OK*. If the message is not in their inbox, then it returns *Message does not exist*. The returned string is printed in the Client. </p>

Apart from the above six user functions there are also some assistive functions. These are:

<div id="username-info">

1. The function that checks if a provided username is valid. This function is used localy, only when a user tries to create an account to check if the username he provided is valid. A valid username, as stated earlier, consists of alphanumeric characters and can contain underscores. Empty strings are not accepted, **the username must at least contain an alphanumeric character or an underscore**. </div>
```
  private static boolean is_valid_username(String s)
```
<div id="tokens-info"> 

2. The function that creates the auth token. This function generates a random number between 1000 and 9999. For this project, it is accepted that the auth token is a 4-digit number. This number must be unique, and to be so, there is a while loop that generates a new random 4-digit number for as long as there are collisions with existing auth tokens. Then, it returns the token. </div>
```
  private int generate_unique_auth_token()
```
3. The function that finds a message with a specific message id in a given inbox. It is used localy when a user tries to read/ delete a message, and it returns the index of the message in the array list that represents the inbox or -1 if the message does not exist in the specific inbox.
```
  private int find_message(int message_id, ArrayList<Message> inbox)
```
4. The function that checks if a username belongs to an existing user. This function is used localy when an account is created and in the Client when a user tries to send a message to another user by providing their username, where it checks the provided username.
```
  public int check_username(String username)
```
5. The function that checks if an auth token belongs to an existing user. This function is used in the Client for every function apart from the FN ID 1. 
```
  public int check_auth_token(int auth_token) 
```

<div id="account"></div>
<h2> <u> Account </u> </h2> <p>

Every user registered in the system is represented by an account. It stores the username, the auth token and the inbox that corresponds to every user in the system. Apart from the getters and setters for these properties, there is a function to **add** a new message in the user's inbox and one to **remove** a message from a user's inbox.  
</p>

<div id="message"></div>
<h2> <u> Message </u><p> </h2>

The message class, represents the messages which users interact with. For every message in the system we must know the sender, the receiver, the content of the message (message body), the id of the message and whether the receiver has read it.

In order to create the printing template requested by the task instructions, when the user wants to read his inbox or when the user wants to read a message, there are **two** functions.

The function,
```
public String show_message_inbox()
```
returns this template which is used when a user wants to see his inbox
> <font size="3"> message_id. from user </font>

and the function,
```
public String show_message_read()
```
is used when a user wants to read a message in his inbox.
> <font size="3"> (sender)message_body </font>

<br><hr></br>

<div id="contact"></div>
<h2> Contact </h2>

Eleni Mandana - emandana@csd.auth.gr 

<p align="right">(<a href="#top">^</a>)</p>
