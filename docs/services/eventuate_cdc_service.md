# Eventuate CDC Service

- [**Overview**](#overview)
   - [Concepts](#concepts)
   - [Components](#components)
   - [Modes](#modes)
- [**Configure The CDC Service**](#configure-the-cdc-service)
   - [Configure Reader](#configure-reader)
   - [Common Configuration Properties](#common-configuration-properties)
      - [Core Service Properties](#core-service-properties)
      - [Tailing MySQL Binlog Properties](#tailing-mysql-binlog-properties)
      - [Polling from the Outbox Table Properties](#polling-from-the-outbox-table-properties)
      - [Publishing to Apache Kafka Properties](#publishing-to-apache-kafka-properties)
      - [Publishing to Apache ActiveMQ Properties](#publishing-to-apache-activemq-properties)
      - [Publishing to RabbitMQ Properties](#publishing-to-rabbitmq-properties)
      - [Publishing to Redis Properties](#publishing-to-redis-properties)
- [**Run The CDC Service**](#run-the-cdc-service)
- [**Specification**](#specification)
   - [Supported Databases](#supported-databases)
   - [Supported Message Brokers](#supported-message-brokers)
- [**References**](#references)

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
| **Eventuate Local** | <li>Reads the EVENT table.<li>The aggregate type of the event determines which channel that event will be published to. | Only Apache Kafka. |
| **Eventuate Tram** | <li>Reads the MESSAGE table.<li>The DESTINATION column in the MESSAGES table determines which channel each message will be published to. | All the supported message brokers. |

## Configure The CDC Service
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
#### Core Service Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `eventuate.cdc.type` | Specify which mode will be run for Eventuate CDC Service. | | <ul><li>`EventuateLocal`: Run Eventuate Local mode.<li>`EventuateTram`: Run Eventuate Tram mode.</ul> | |
| `spring.profiles.active` | <li>Specify which way to read the message/event records from the database.<li>Specify which message broker is using. | | For retrieving message/events:<ul><li>`(missing)`: Tailing the MySQL Binlog.<li>`PostgresWal`: Tailing the Postgres WAL.<li>`EventuatePolling`: Polling from the outbox table.</ul>For specifying message broker:<ul><li>`(missing)`: Use Apache Kafka.<li>`ActiveMQ`: Use Apache ActiveMQ.<li>`RabbitMQ`: Use RabbitMQ.<li>`Redis`: Use Redis.</ul>  | This property accepts multiple values which is separated by comma. |
| `spring.datasource.url` | JDBC connection URL. | | | |
| `spring.datasource.username` | Username to use for the connection. | | | |
| `spring.datasource.password` | Password to use for the connection. | | | |
| `spring.datasource.driver.class.name` | JDBC driver class name. | | | |
| `eventuate.database.schema` | Schema which is listened by the CDC service. | `eventuate` | | |
| `eventuatelocal.cdc.source.table.name` | Name of the table to read events/messages from. | <li>`EVENT` (for Eventuate Local)<li>`MESSAGE` (Eventuate Tram) | | |

#### Tailing MySQL Binlog Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `eventuatelocal.cdc.db.user.name` | Username to use for the MySQL connection. | | | For tailing MySQL Binlog tailing only. |
| `eventuatelocal.cdc.db.password` | Password to use for the MySQL connection. | | | For tailing MySQL Binlog tailing only. |
| `eventuatelocal.cdc.mysql.binlog.client.unique.id` | Unique client identifier for reading MySQL binlog. | | | For tailing MySQL Binlog tailing only. |
| `eventuatelocal.cdc.read.old.debezium.db.offset.storage.topic` | Specify the CDC service should start read records from the old debezium kafka topic or not. | | <ul><li>`true`: Start read records from the old debezium kafka topic.<li>`false`: Start read records from the new CDC kafka topic.</ul> | For tailing MySQL Binlog tailing only. |

#### Polling from the Outbox Table Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `eventuatelocal.cdc.polling.interval.in.milliseconds` | Sleep time between polling queries. | 500 | | |
| `eventuatelocal.cdc.max.events.per.polling` | Max number of events is allowed to be retrieved for each polling query. | 1000 | | |
| `eventuatelocal.cdc.max.attempts.for.polling` | Max number of attempts the reader will try again if polling fails.  | 100 | | |
| `eventuatelocal.cdc.polling.retry.interval.in.milleseconds` | Interval time of retries if polling fails. | 500 | | |

#### Publishing to Apache Kafka Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `eventuatelocal.kafka.bootstrap.servers` | URL (Host:Port) for connecting Apache Kafka. | | | |
| `eventuatelocal.zookeeper.connection.string` | URL (Host:Port) for connecting Zookeeper | | | |
| `eventuate.cdc.kafka.enable.batch.processing` | Enable sending multiple Eventuate Local events and Eventuate Tram messages inside singe Apache Kafka message. | `false` | | |
| `eventuate.cdc.kafka.batch.processing.max.batch.size` | 	
max size of multi-message Apache Kafka record in message. | `1000000` | | |

#### Publishing to Apache ActiveMQ Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `activemq.url` | URL (Protocol://IP:Port) for connecting Apache ActiveMQ. | | | |
| `activemq.user` | Username for connecting Apache ActiveMQ. | | | |
| `activemq.password` | Password for connecting Apache ActiveMQ. | | | |

#### Publishing to RabbitMQ Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `rabbitmq.host` | Hostname for connecting Apache ActiveMQ. | | | |
| `rabbitmq.port` | Port for connecting Apache ActiveMQ. | | | |
| `eventuate.rabbitmq.partition.count` | Number of partitions. Messages are split between partitions similar to Apache Kafka. Partition is selected depending on message key hash. Processing of messages with the same partitions are ordered. | | | |

#### Publishing to Redis Properties
| Property | Description | Default Value | Available Values | Notes |
|----|----|----|----|----|
| `eventuate.redis.servers` | URL (Host:Port) for connecting Redis. | | | |
| `eventuate.redis.partitions` | Number of partitions. Messages are split between partitions similar to Apache Kafka. Partition is selected depending on message key hash. Processing of messages with the same partitions are ordered. | | | |

## Run The CDC Service

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
