# Merlion Bank: Retail Core Banking System (RCBS)

Merlion Bank, an upcoming retail bank in Singapore, requires a **Retail Core Banking System (RCBS)** to be developed over 9 weeks. The RCBS will comprise:
1. A core backend with a component-based architecture.
2. Multiple retail banking applications for the bank's operations.

## Initial Project Structure:
The basic project structure to be set up in NetBeans includes:

## Functionalities for the Current Phase:

### 1. **Create Customer**:
- New customers need to have a record created by the teller before any transactions.
- Each customer is unique and identified by a distinct customer record.

### 2. **Open Deposit Account**:
- Teller can open a new deposit account for an already registered customer.
- The customer provides an initial deposit.
- A customer can own multiple deposit accounts.
- At this stage, only savings accounts are necessary, and joint accounts are not to be considered.

### 3. **Issue ATM Card**:
- Teller gives a new ATM card to customers.
- One ATM card can be linked with multiple deposit accounts.
- Each deposit account can be associated with a maximum of one ATM card.

### 3.1. **Issue Replacement ATM Card**:
- Teller issues a replacement card, deleting the record of the previous one and creating a new card record.

### 4. **Insert ATM Card**:
- Customers can insert their ATM card using card number and PIN.

### 5. **Change PIN**:
- Customers can modify their ATM card's PIN.

### 6. **Enquire Available Balance**:
- Customers can check the balance for an account linked to their ATM card.
- The available balance is the amount ready for spending, transfers, or withdrawal.
- Ledger balance equals the sum of the available balance and any holding balance. If no holding balance exists, the ledger balance matches the available balance.

## Implementation:
For this phase, design an efficient component-based architecture for RCBS and implement it using suitable EJB session beans. Data persistence can be achieved through either file object serialization or a locally hosted MySQL instance for a relational database.

> **Note**:
> While file object serialization offers a straightforward way to store objects, using a locally hosted MySQL instance provides scalability, security, and reliability, making it more suitable for a banking system like RCBS. Ensure your database design reflects the entities and relationships outlined in the provided UML class and use case diagrams.
