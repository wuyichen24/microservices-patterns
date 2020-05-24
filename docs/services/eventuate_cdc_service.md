# Eventuate CDC Service

## Overview
### Concepts
- Business logic services use Eventuate Tram libary to insert message/events into the transactional outbox.
- The Eventuate CDC service reads those messages/events from the transactional outbox table and publish them to the message broker.

### Components
There are 3 components in the Eventuate CDC service:
- **Reader**: Reads the message/event records from the database by either tailing the transaction log or polling using queries.
- **Pipeline**: Transforms the message/event records into the JSON messages which can be published to a message broker.
- **Publisher**: Publishes the JSON messages to the message brocker.

### Modes
The Eventuate CDC service can be run in one of those modes:
- **Eventuate Local**
- **Eventuate Tram**


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

## Run Eventuate CDC Service

## Configure Eventuate CDC Service

## References
