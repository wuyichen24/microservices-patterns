# Getting Started
- [**Install Apache ZooKeeper**](#install-apache-zookeeper)
- [**Install Apache Kafka**](#install-apache-kafka)
- [**Install MySQL**](#install-mysql)
- [**Create Schema And Tables**](#create-schema-and-tables)

---

## Install Apache ZooKeeper
### Step 1: Verify Java Installation
Use this command to verify the Java environment
```
java -version
```

### Step 2: Install ZooKeeper
#### Step 2.1: Download ZooKeeper
Use this [**link**](http://zookeeper.apache.org/releases.html) for downloading the latest version of ZooKeeper. 

#### Step 2.2: Extract the tar file
```
tar -zxf zookeeper-3.4.14.tar.gz
cd zookeeper-3.4.14
```

#### Step 2.3: Create a directory for data
Create a directory for storing the snapshot
```
mkdir data
```

#### Step 2.4: Create configuration file
Create **zoo.cfg** under the **conf** directory:
```
nano conf/zoo.cfg
```
Set the content of **zoo.cfg**:
```
tickTime = 2000
dataDir = /Users/wuyichen/zookeeper-3.4.14/data     (Change this path for your case, see Step 2.3)
clientPort = 2181
initLimit = 5
syncLimit = 2
```

#### Step 2.5: Start ZooKeeper server
```
sh bin/zkServer.sh start
```
After executing this command, you should see the following message:
```
ZooKeeper JMX enabled by default
Using config: /Users/wuyichen/zookeeper-3.4.14/bin/../conf/zoo.cfg
-n Starting zookeeper ... 
STARTED
```

#### Other useful command
- Start ZooKeeper client: `sh bin/zkCli.sh`
- Stop ZooKeeper server: `sh bin/zkServer.sh stop`
- Check ZooKeeper server status: `sh bin/zkServer.sh status`

---

## Install Apache Kafka
### Step 1: Verify Java Installation
Use this command to verify the Java environment
```
java -version
```

### Step 2: Install ZooKeeper
ZooKeeper is the prerequisite for Kafka, you need to install it before running Kafka.

### Step 3: Install Kafka
#### Step 3.1: Download Kafka
Use this [**link**](https://kafka.apache.org/downloads) for downloading the latest version of Kafka. Use the binary downloads like "kafka_a.b-x.y.z.tgz".

#### Step 3.2: Extract the tar file
```
tar -zxf kafka_2.11-2.2.0.tar.gz
cd kafka_2.11-2.2.0
```

#### Step 3.3: Start Kafka server
```
sh bin/kafka-server-start.sh config/server.properties
```
**NOTE: Please make sure the ZooKeeper server is running also.**

---

## Install MySQL

## Create Schema And Tables
### Step 1: Create `eventuate` schema and tables
Run the SQL script: [create_eventuate_schema.sql](../sql/create_eventuate_schema.sql)
Those table will be used by Eventuate CDC service for sending messages to Apache Kafka.

### Step 2: Create `ftgo` schema
Run the SQL script: [create_ftgo_schema.sql](../sql/create_ftgo_schema.sql)
This schema will be used by FTGO application.
