# IoT Energy Meter	
The idea of this exercise is to build up a solution to meassure real-time energy consumption at home:

Hardware Needed:

- Sonoff Pow R2
- Raspberry pi 
- FT232RL

Software used:

- Node-red 
- Mosquitto MQTT
- Kafka
- Flink





# Follow the Steps:

## 1) Flash tasmota into sonoff pow 2

https://www.youtube.com/watch?v=H6YUWkeSZNU&list=PLwmIacBWziUvBl1aUX6UIhp1174DhipGu&index=2&t=19s

#### 1.1) Soldar pines

#### 1.2) Download Tasmota

http://ota.tasmota.com/tasmota/release/

#### 1.3) Controller

https://github.com/letscontrolit/espeasy/releases

#### 2) Flash Raspberry with Raspbian

https://www.raspberrypi.org/software/

#### 3) Init Setup

##### 3.1) Connect to Wifi

##### 3.2) Enable SSH

##### 3.3) Update

`sudo apt update`

`sudo apt upgrade`

`sudo apt install default-jdk`

##### 3.4) Install software

###### 3.4.1) Mosquitto

`apt-get install mosquitto`

`apt-get install mosquitoo-clients`

`mosquitto_sub -d -t testTopic`

`mosquitto_pub -d -t testTopic -m "Hello world!"`

###### 3.4.2) Node-Red or Nifi

`bash <(curl -sL https://raw.githubusercontent.com/node-red/linux-installers/master/deb/update-nodejs-and-nodered)`

`sudo systemctl enable nodered.service`

`sudo systemctl restart nodered.service`

`node-red-pi --max-old-space-size=256`

###### 3.4.3) Kafka

`wget https://downloads.apache.org/kafka/2.7.0/kafka_2.13-2.7.0.tgz`

`tar -xvf kafka_2.12-2.3.0.tgz`

`mv kafka_2.12-2.3.0 kafka`

`cd kafka`

`mkdir data`

`cd data`

`mkdir zookeeper`

`mkdir kafka`

Config:

zookeeper.properties 
replace:
	dataDir=/kafka/data/zookeeper

server.properties
replace:
	log.dirs=/kafka/data/kafka
	listeners=PLAINTEXT://192.168.1.61:9092

**Start Zookeeper**
`bin/zookeeper-server-start.sh config/zookeeper.properties`

**Start Kafka**
`bin/kafka-server-start.sh config/server.properties`

Create a topic:
`bin/kafka-topics.sh --create --bootstrap-server 192.168.1.61:9092 --replication-factor 1 --partitions 1 --topic iotdata`

`bin/kafka-topics.sh --list  --bootstrap-server 192.168.1.61:9092`

###### 3.4.3) Flink
`wget https://downloads.apache.org/flink/flink-1.12.0/flink-1.12.0-bin-scala_2.12.tgz`

`/home/pi/Data_Station/installation/binaries/flink/flink-1.12.0#./bin/start-cluster.sh`
