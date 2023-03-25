## Bank

- **_/banks:** GET - retrieve all banks_


- **_/banks/{id}:** GET - retrieve a specific bank by ID_


- **_/banks:** POST - create a new bank_


- **_/banks/{id}:** PUT - update an existing bank_


- **_/banks/{id}:** DELETE - delete a bank by ID_

## Client

- _**/banks/{bankId}/clients:** GET Returns a list of all clients for the specified bank._


- _**/banks/{bankId}/clients/new:** GET Renders a form for creating a new client for the specified bank._


- _**/banks/{bankId}/clients:** POST Creates a new client for the specified bank._


- _**/banks/{bankId}/clients/{clientId}**: GET Returns information for a specific client of the specified bank._


- _**/banks/{bankId}/clients/{clientId}/edit:** GET Renders a form for editing information for a specific client of the
  specified bank._


- _**/banks/{bankId}/clients/{clientId}:** PATCH Updates information for a specific client of the specified bank._


- _**/banks/{bankId}/clients/{clientId}:** DELETE Deletes a specific client of the specified bank._

## Account

- _**/banks/{bankId}/clients/{clientId}/accounts:** GET Returns a list of all accounts belonging to the specified
  client._


- _**/banks/{bankId}/clients/{clientId}/accounts/new**: GET Renders a form for creating a new account._


- _**/banks/{bankId}/clients/{clientId}/accounts:** POST Creates a new account for the specified client with the
  specified
  account type._


- _**/banks/{bankId}/clients/{clientId}/accounts/{accountId}:** GET Returns the details of the specified account._


- _**/banks/{bankId}/clients/{clientId}/accounts/{accountId}:** DELETE Deletes the specified account._


- _**/banks/{bankId}/clients/{clientId}/accounts/{accountId}/transactions/{transactionId}:** PATCH Undoes the specified
  transaction for the specified account._

## Notifications

- GET /banks/{bankId}/clients/{clientId}/notifications: retrieves all notifications for a specific client.

## Time

- _**GET /time**: retrieves the current date and time._


- _**POST /time/add**: adds a specified number of years, months, and days to the current date and time. If the request
  is
  successful, returns a success message, otherwise returns an error message._

## Transaction

- _**GET /transactions:** retrieves all transactions and displays the transaction page._


- _**POST /transactions/replenishment:** performs a replenishment transaction and redirects back to the transaction
  page._

  
- _**POST /transactions/withdraw:** performs a withdraw transaction and redirects back to the transaction page._


- _**POST /transactions/transfer:** performs a transfer transaction and redirects back to the transaction page._


- _**GET /transactions/replenishment**: displays a form for performing a replenishment transaction._


- _**GET /transactions/withdraw:** displays a form for performing a withdraw transaction._


- _**GET /transactions/transfer:** displays a form for performing a transfer transaction._


- _**PATCH /transactions/{transactionId}:** undoes a specific transaction and redirects back to the transaction page._