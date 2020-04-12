# Code Orgnization for Interservice Communication

![](diagrams/interservice_communication.png)

## Class

| Class | Description | Example |
|----|----|----|
| **Controller** | Defines RESTful APIs | |
| **Service** | Defines the operations for RESTful APIs | |
| **Saga** | Defines the Saga process for a specific operation. <ul><li>Each Saga process consists of multiples steps (step = state).<li>Each step consists of a command and the info of which service to execute that command.</ul> | |
| **Proxy** | Defines the command endpoints for connecting a service-specific channel and sending the commands to a specific service. | |
| **Commands Handler** | Defines how to handle each command. <ul><li>Defines mapping relationship between the commands and the command handlers.<li>Each command handler defines the operations need to be executed after receiving a specific command.</ul>
