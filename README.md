# ENSF409 Final Project: Re:Claim

## Introduction

### Problem Statement

Re:Claim was produced for ENSF409 Winter 2021 and is an application which optimizes supply-chain management (SCM) by managing the flow of furniture components throughout the University of Calgary campus. With this technology, the University of Calgary can prevent reusable materials from being discarded into landfills and continue to be an industry example for responsible supply-chain management.

### Project Objectives

1. Become familiar with the process and tools used to collaborate with others when developing software.
2. Practice manipulating and accessing databases to store, manage, and track data.
3. Solidify Java programming skills by building a Java application that solved a real-life problem.
4. Design and implement software architecture for a fairly large software system in accordance with OOP practices.

### Team

The following people were part of this project:

1. Joshua Duha
2. Ruha Javed
3. Khaled Elmalawany
4. Nicolo Rivera

# Technologies

The technologies used in this project include:

1. Java - version 13.0.2
2. mySQL - server version 8.0.23

# Launch

First ensure that the "inventory.sql" database (file included) has been setup and that a user account with username "scm" and password "ensf409" can access this database.

Then ensure that your working directory contains the edu.ucalgary.ensf409 package.

Now you can run the program. The executable class is SupplyChainManagement; therefore use the following command-line commands to execute the program:

 1. javac edu/ucalgary/ensf409/SupplyChainManagement.java
 2. java edu.ucalgary.ensf409.SupplyChainManagement

Upon running the code, the user will be prompted with a few questions for the Faculty Name, Contact Name and material and amount requested. Upon selection of the material, a connection to the database will be established to determine the appropriate combination.

If successful, a text file will be generated with the ordered material. If unsuccessful, the user will be prompted with the appropriate manufacturers to contact through the terminal.

# Possible Improvements

1. Check integer input for large input numbers - the code currently throws a NumberFormatException with no feedback.
2. Give the user more guidance on acceptable inputs for a better user experience.

# Notes

1. This program includes the following classes as well as a Unit test class for each (except for SupplyChainManagement):
- DBLinker
- OptimumCostCalculator
- OutputGen
- SupplyChainManagement
- UserInterpreter
2. "orderForm000.txt" and "orderForm001.txt," alongside other order forms, are available in the Project Files folder as examples of a succesful completion of the program.
3. Each order form produced is unique. That is, pre-existing order forms will not be overwritten.
4. The devpost for this project can be found here: https://devpost.com/software/re-claim. This includes a video demonstration. However, a more thorough video demonstration can be found here: https://youtu.be/DbXZPtnCgH4.
