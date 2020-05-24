# Eventuate CDC Service

## Overview
### Concepts
- Business logic services use Eventuate Tram libary to insert message/events into the transactional outbox table.
- The Eventuate CDC service reads those messages/events from the transactional outbox table and publish them to the message broker.

### Components
There are 3 components in the Eventuate CDC service:
- **Reader**: Reads the message/event records from the database by either tailing the transaction log or polling the transactional outbox table.
- **Pipeline**: Transforms the message/event records into the JSON messages which can be published to a message broker.
- **Publisher**: Publishes the JSON messages to the message brocker.

### Modes
The Eventuate CDC service can be run in one of those 2 modes:
- **Eventuate Local**
   - Reads the EVENTS table.
- **Eventuate Tram**
   - Reads the MESSAGES table.

## Configure Eventuate CDC Service
### Configure Reader
There are 2 ways to read the message/event records from the database:
- **Tailing the transaction log**
   - Only available for MySQL and Postgres database.
      - MySQL: By MySQL Binlog (Binary Log).
      - Postgres: By Postgres WAL (Write-Ahead Logging).
- **Polling the transactional outbox table**
   - Available for all the supported databases.
   - The mechanism is to poll the transactional outbox table by periodically executing a SQL SELECT statement to retrieve unpublished message/event records.
   - Different modes will read the transactional outbox table:
      - Eventuate Local: Reads the EVENTS table by default.
      - Eventuate Tram: Reads the MESSAGES table by default.

| Way | Available Database | Mechanism | Other Information |
|----|----|----|----|
| **Tailing the transaction log** | <li>MySQL<li>Postgres | <li>MySQL: MySQL Binlog (Binary Log).<li>Postgres: Postgres WAL (Write-Ahead Logging). | |
| **Polling the transactional outbox table** | All the supported databases | Poll the transactional outbox table by periodically executing a SQL SELECT statement to retrieve unpublished message/event records. | Different modes will read the transactional outbox table:<ul><li>Eventuate Local: Reads the EVENTS table by default.<li>Eventuate Tram: Reads the MESSAGES table by default</ul> |

## Run Eventuate CDC Service

## Specification
### Supported Databases
- MySQL
- Postgres
- Microsoft SQL server

### Supported Message Brokers
- Apache Kafka
- Apache ActiveMQ
- RabbitMQ
- Redis





## References
- https://eventuate.io/docs/manual/eventuate-tram/latest/getting-started-eventuate-tram.html#getting-started
- https://eventuate.io/docs/manual/eventuate-tram/latest/cdc-configuration.html
- https://hub.docker.com/r/eventuateio/eventuate-tram-cdc-mysql-service/tags
- https://github.com/eventuate-foundation/eventuate-cdc
- https://mvnrepository.com/artifact/io.eventuate.tram.core/eventuate-tram-cdc-mysql-service
