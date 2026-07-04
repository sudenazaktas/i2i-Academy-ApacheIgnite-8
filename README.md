# i2i Academy - Apache Ignite - 8

A Java application demonstrating basic CRUD operations with Apache Ignite 3,
using the modern Thin Client API to connect to a single-node Ignite cluster
running in Docker.

## Overview

This project sets up a `Subscriber` table in Apache Ignite and performs the
following operations:
- Creates the table if it does not exist
- Clears old data on every run
- Inserts 5 dummy subscriber records
- Simulates usage updates (data, SMS, call) with random values
- Reads and prints the final state of all subscribers

## Tech Stack

- Java 11+
- Apache Ignite 3.1.0 (Thin Client)
- Maven
- Docker

## Prerequisites

- Docker installed and running
- Java 11 or newer
- Maven

## Running the Ignite Cluster

Start a single-node Ignite cluster with Docker:

```bash
docker run -d --name ignite-node -p 10800:10800 -p 10300:10300 apacheignite/ignite:3.1.0
```

Initialize the cluster using the Ignite CLI:

```bash
docker run --rm -it --network=host apacheignite/ignite:3.1.0 cli
```

Inside the CLI:

```bash
cluster init --name=myCluster --metastorage-group=<node-name>
```

## Running the Application

Once the cluster is initialized, run `Main.java` from your IDE, or via Maven:

```bash
mvn compile exec:java -Dexec.mainClass="Main"
```

## Project Structure

- `Main.java` - Connects to the Ignite cluster and runs the CRUD operations
- `Subscriber.java` - Data model representing a subscriber record
