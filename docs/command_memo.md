# Command Memo

- **ZooKeeper**
   - Path: `/Users/wuyichen/zookeeper-3.4.14/bin`
   - Commands
      - Start: `sh zkServer.sh start`
      - Stop: `sh zkServer.sh stop`
   - Port: 2181
- **Kafka**
   - Path: `/Users/wuyichen/kafka_2.11-2.2.0/bin`
   - Commands
      - Start: `sh kafka-server-start.sh ../config/server.properties`
      - Press Crtl+C
      - List all topics: `bash kafka-topics.sh --list --zookeeper localhost:2181`
      - Comsume message of a topic: `bash kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic_name> --from-beginning`
   - Port: 9092
