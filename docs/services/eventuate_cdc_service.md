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
- **Eventuate Tram**

There are the differences between those 2 modes
| Mode | Polling The Transactional Outbox Table | Supported Message Brokers |
|----|----|----|
| **Eventuate Local** | <li>Reads the EVENTS table.<li>The aggregate type of the event determines which channel that event will be published to. | Only Apache Kafka. |
| **Eventuate Tram** | <li>Reads the MESSAGES table.<li>The DESTINATION column in the MESSAGES table determines which channel each message will be published to. | All the supported message brokers. |

## Configure Eventuate CDC Service
### Configure Reader
There are 2 ways to read the message/event records from the database:
- **Tailing the transaction log**
   - Available for: MySQL and Postgres databases.
   - Mechanism:
      - MySQL: By MySQL Binlog (Binary Log).
      - Postgres: By Postgres WAL (Write-Ahead Logging).
   - MySQL Binlog is disable by default (You have to enable it by changing the `my.cnf` file and restarting the MySQL server)
   - When using this way, the offset of the log file must be stored, there are 2 places to store the offset:
      - Apache Kafka topic: When using Apache Kafka as message broker.
      - Database table: When using a message broker other than Apache Kafka.
- **Polling the transactional outbox table**
   - Available for: All the supported databases.
   - Mechanism: 
      - Polls the transactional outbox table by periodically executing a SQL SELECT statement to retrieve unpublished message/event records.
   - Different modes will read the transactional outbox table:
      - Eventuate Local: Reads the EVENTS table by default.
      - Eventuate Tram: Reads the MESSAGES table by default.

### Common Configuration Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `spring.profiles.active` | <li>Specify which way to read the message/event records from the database.<li>Specify which message broker is using. | - | For reading message/events from the database:<ul><li>`(missing)`: Tailing the MySQL Binlog.<li>`PostgresWal`: Tailing the Postgres WAL.<li>`EventuatePolling`: Polling from the outbox table.<ul> | |

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
