/**
 * IDSA Long Project 3
 * Group members:
 * Adarsh Raghupati   axh190002
 * Akash Akki         apa190001
 * Keerti Keerti      kxk190012
 * Stewart cannon     sjc160330
 */

####Implementation of Red Black Tree, Skip List and their comparison with Java's TreeSet

###### Steps to run the code in IntelliJ IDE
* Create an empty java project
* Unzip the source code files and paste it under the location "Java Project Name"/src folder


###### Steps to run the tests

Run RedBlackTree.java to run sample test
Run RedBlackTreeDriver.java with the input test file for other test runs

Run SkipList.java to run sample test
Run SkipListDriver.java with the input test file for other test runs

###### Sample test runs:
Run RedBlackTree.java
Output:

Insert 50
Insert 60
Insert 40
Insert 35
Insert 65
Is RBT true
Delete 65
Is RBT true
 50:B
 40:B  60:B
 35:R  N:B  N:B  N:B
 N:B  N:B

Run SkipList.java
Output:
(Skip list is printed horizontally. First coloumns is level 0 and last column is level 32)
Inserted elements:[49, 33, 22, 39, 40, 9, 12, 13, 29]
Remove :33
----------START----------
9(1)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
12(1)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
13(1)	13(3)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
22(1)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
29(1)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
39(1)	39(3)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
40(1)	40(1)	40(7)	40(7)	40(7)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
49(1)	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|
----------END----------
Get(0) :9