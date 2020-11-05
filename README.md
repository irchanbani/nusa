# nusa
A Dataflow Template that hope to complement the [DataflowExamples](https://github.com/GoogleCloudPlatform/DataflowTemplates/tree/master/src/main/java/com/google/cloud/teleport/templates) from Google. The idea of creating this repository because I want to learn more about Java, Apache Beam, and Dataflow templates. I will try to always update the [Apache Beam Version](beam.apache.org/get-started/downloads) in the `pom.xml` as soon as possible

**Disclaimer:**
*A lot of code in this repo is copied from the [DataflowExamples](https://github.com/GoogleCloudPlatform/DataflowTemplates/tree/master/src/main/java/com/google/cloud/teleport/templates), I just modified some configuration like using Pub/Sub Subscriptions instead of using Pub/Sub Topic.* :bow:

## Preparation

This repository use:
- [Java 8](https://github.com/AdoptOpenJDK/openjdk8-binaries/releases)
- [Maven](https://maven.apache.org)

If both installed you will see something like this:

```bash
▶ java -version
openjdk version "1.8.0_272"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_272-b10)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.272-b10, mixed mode)
```

```bash
▶ mvn -version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /usr/local/Cellar/maven/3.6.3_1/libexec
Java version: 1.8.0_272, vendor: AdoptOpenJDK, runtime: /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre
Default locale: en_ID, platform encoding: UTF-8
OS name: "mac os x", version: "10.15.1", arch: "x86_64", family: "mac"
```

## Creating Template

Based on this [reference](https://cloud.google.com/dataflow/docs/guides/templates/creating-templates#creating-and-staging-templates):

```bash
mvn compile exec:java \
     -Dexec.mainClass=com.irchanbani.beam.PubsubSubscriptionToAvro \
     -Dexec.cleanupDaemonThreads=false \
     -Dexec.args=" \
     --runner=DataflowRunner \
     --enableStreamingEngine \
     --diskSizeGb=30 \
     --project=[YOUR_PROJECT_ID] \
     --region=[YOUR_BUCKET_REGION] \
     --tempLocation=gs://[YOUR_BUCKET_NAME]/temp \
     --stagingLocation=gs://[YOUR_BUCKET_NAME]/staging \
     --templateLocation=gs://[YOUR_BUCKET_NAME]/templates/[BEAM_VERSION]/<template-name>"
```

Also you need to copy the metadata file in the same folder as the template.

```bash
gsutil cp metadata/Cloud_PubSub_Subscription_to_Avro_metadata gs://[YOUR_BUCKET_NAME]/templates/[BEAM_VERSION]/<template-name>
```


## Run Dataflow

Basen on this [reference](https://cloud.google.com/sdk/gcloud/reference/dataflow/jobs/run):

```bash
gcloud dataflow jobs run [JOB_NAME] \
    --gcs-location gs://[YOUR_BUCKET_NAME]/templates/[BEAM_VERSION]/<template-name> \
    --region [REGION_ID] \
    --network [NETWORK] \
    --subnetwork [SUBNETWORK] \
    --max-workers [MAX_WORKERS] \
    --worker-machine-type [WORKER_MACHINE_TYPE] \
    --disable-public-ips \
    --parameters \
inputSubscription=projects/[PROJECT_ID]/subscriptions/[SUBSCRIPTIONS_ID],\
outputDirectory=gs://[BUCKET_NAME],\
outputFilenamePrefix=[PREFIX],\
outputFilenameSuffix=[SUFFIX],\
inputAttributeTimestamp=[PUBSUB_TIMESTAMP_ATTRIBUTE],\
inputAttributeId=[PUBSUB_ID_ATTRIBUTE],\
numShards=[NUM_SHARDS],\
avroTempDirectory=gs://[BUCKET_NAME]/tmp/
```

## Contribution

- Commit with proper, descriptive message (see: https://karma-runner.github.io/2.0/dev/git-commit-msg.html)
- Create Pull Requests, describing the changes
