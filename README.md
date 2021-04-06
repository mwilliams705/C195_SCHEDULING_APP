# C195_SCHEDULING_APP_MICHAEL_WILLIAMS
Author: Michael Williams - 001221520 <br>
Email: mwi1410@wgu.edu <br>
Version: 1.0 <br>
Date: 04/05/2021
<br>
<br>
Title: Scheduling App C195-WGU
<br>
<br>
Purpose: This application allows users to manage creating updating and deleting
customers and appointments from the company 
database and handling errors in the execution of these tasks. 
This application can be used in any timezone to manage appointments, and
the appointment will be stored to the database in UTC time while showing the
also to the user in their local timezone. The application also manages deleting customers appointments
if a customer is removed from the database.




###IDE
IntelliJ IDEA 2020.3.3 (Ultimate Edition)<br>
Build #IU-203.7717.56, built on March 15, 2021<br>

Java version: 11.0.10<br>
JavaFX version: 11.0.2<br>
MySQL Driver Version: mysql-connector-java:8.0.22<br>


##Getting started

###JavaFX
The JavaFX portion of this application is built using the same concepts used in C468 - Software I.
<br>
<br>
Start by downloading JavaFX 11.0.2. (No longer packaged with JDK11+) - https://gluonhq.com/products/javafx/
<br>
<br>
Once this has downloaded, add the lib folder to your project by going to<br>
File -> Project Structure -> libraries (Under Project Structure) <br>
and adding the lib folder of your downloaded JavaFX library.<br>

Next, a PATH variable for the lib folder needs to be added to IntelliJ. This will make the step of adding VM options easier. <br>Go to:<br>
File -> Settings -> Path Variables (Under Appearance & Behavior sub-menu)<br> 
Create a new variable named 'PATH_TO_FX' and point it to the same lib folder from the last step. <br>
<br>
Finally, We need to add VM options, so the application uses the JavaFX library.<br>
Go to: <br>
Run -> Edit Configurations and in the top-right 'Modify Options' dropdown, select 'Add VM Options'. <br>
This will add a new field in the Configuration Panel for VM Options.<br>
Paste this into the field:
<br>
<code>--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics</code>
<br>
###MySQL
This can be added using the Maven package manager:<br>
File -> Project Structure -> libraries (Under Project Structure) <br>
Select add(+) and choose Maven from the options and search for MySQL.

The MySQL Driver Version of this application is: <br>
<code>mysql-connector-java:8.0.22</code>

##Custom Report
For my custom report, I decided to count the Appointments and created a pie chart 
that shows the total count of appointments separated by Customer.

