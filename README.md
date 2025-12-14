🏦 Java Bank System

A simple Java-based banking application that simulates core banking functionality such as customer management, account handling, transactions, file persistence, and a graphical user interface.

The project is built using object-oriented principles and is intended as a learning project within Java programming, focusing on clean structure, separation of concerns, and basic persistence.

✨ Features

Create and manage customers

Support for multiple account types:

SavingsAccount

CreditAccount

Deposit and withdraw money

Transaction history per account

Persistent storage using files

Graphical User Interface (GUI)

Separation between logic, data handling, and UI

🗂️ Project Structure

├── Account.java

├── SavingsAccount.java

├── CreditAccount.java

├── Customer.java

├── BankLogic.java

├── AccountBook.java

├── GUI.java

├── bankData.dat

├── minaTransaktioner2.txt

Class Overview
Class	Description
Account	Abstract/base class for bank accounts
SavingsAccount	Represents a savings account with balance handling
CreditAccount	Represents a credit account with credit limits
Customer	Stores customer information and owned accounts
BankLogic	Core business logic for the bank system
AccountBook	Handles file I/O and persistence of data
GUI	Graphical user interface for interacting with the system
💾 Data Persistence

Account and customer data are stored locally using files.

Transaction history is written to text files (e.g. minaTransaktioner2.txt).

Serialization is used to persist objects between sessions.

🖥️ Graphical User Interface

The application includes a GUI that allows users to:

Select customers

View accounts

Perform deposits and withdrawals

View transaction history

The GUI communicates exclusively with BankLogic, ensuring a clear separation between UI and business logic.

🚀 How to Run

Clone the repository:

git clone <repository-url>


Open the project in an IDE (e.g. IntelliJ IDEA or Eclipse)

Compile and run:

GUI.java

🧠 Design Principles Used

Object-Oriented Programming (OOP)

Encapsulation

Inheritance

Separation of concerns

File-based persistence

Basic MVC-inspired structure

📚 Possible Improvements

Replace file storage with a database

Add authentication/login system

Improve error handling and validation

Add unit tests (JUnit)

Extend GUI with more advanced features

👤 Author

Oscar Ekberg
Java & Web Development Student
📍 Sweden
