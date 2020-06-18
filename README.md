
# Helidon Quickstart MP Example - Product Catalog

This example implements a simple Hello World REST service using MicroProfile

## Prerequisites

1. Maven 3.5 or newer
2. Java SE 8 or newer
3. Docker 17 or newer (if you want to build and run docker images)
4. Kubernetes minikube v0.24 or newer (if you want to deploy to Kubernetes)
   or access to a Kubernetes 1.7.4 or newer cluster
5. Kubectl 1.7.4 or newer for deploying to Kubernetes

Verify prerequisites
```
java -version
mvn --version
docker --version
minikube version
kubectl version --short
```
## Building

```bash
mvn package
```

## Running

Create a debug configuration in IntelliJ that runs `mvn package` and then runs the generated JAR. Pass the following properties as 'VM Options':

```bash
-Ddatasource.username=[Username]
-Ddatasource.password=[Strong Password]
-Ddatasource.url=jdbc:oracle:thin:@product_LOW?TNS_ADMIN=/path/to/wallet
```

Create a self-sign certificate

```bash
Enter the directory where this `.md` file is located:
1. Execute: `openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 99999 -out certificate.pem`
    Provide: `US`, `California`, `Santa Clara`, `Oracle`, `Helidon`, `helidon-webserver-netty-test`, `info@helidon.io`
    Note that shorter RSA certificate may not work due to various restrictions on both client and server side
2. Convert the key from the traditional format to pkcs8: `openssl pkcs8 -topk8 -inform PEM -outform PEM -in key.pem -out key.pkcs8.pem -nocrypt`
3. Execute: `openssl pkcs12 -inkey key.pem -in certificate.pem -export -out certificate.p12`
    Provide password: `helidon`
```



**Note:** If you're not using IntelliJ, just make sure that you pass all properties:
 
 ```bash
java 
    -Ddatasource.username=[username] \
    -Ddatasource.password=[password] \
    -Ddatasource.url=jdbc:oracle:thin:@product_LOW?TNS_ADMIN=/path/to/wallet \
-jar target/product-svc.jar
```

## Test Endpoints

Get Product Service Endpoint (returns 200 OK):

```
curl -iX GET http://localhost:8080/product                                                                                                                                                    
HTTP/1.1 200 OK
Content-Type: application/json
Date: Thu, 20 Jun 2019 10:35:06 -0400
transfer-encoding: chunked
connection: keep-alive
{"OK":true}                                                          
```

List all product:

```bash
curl -iX GET http://localhost:8080/product/list                                                                                                                                               
HTTP/1.1 200 OK
Content-Type: application/json
Date: Thu, 20 Jun 2019 10:46:51 -0400
transfer-encoding: chunked
connection: keep-alive

```

Delete a product:

```bash
curl -iX DELETE  http://localhost:8080/product/1039                                                                                                                
HTTP/1.1 204 No Content
Date: Mon, 1 Jun 2020 07:12:22 -0500
connection: keep-alive
```

Confirm delete (same GET by ID will return 404):

```bash
curl -iX GET http://localhost:8080/product/1039
HTTP/1.1 404 Not Found
Date: Mon, 1 Jun 2020 07:13:33 -0500
transfer-encoding: chunked
connection: keep-alive
```

Save a new product (ID is returned in `Location` header):

```bash
curl -i"ATTRIBUTE2":null,"LAST_UPDATED_BY":"0","ATTRIBUTE1":null,"OBJECT_VERSION_ID":"1","MIN_PRICE":"2.99","PARENT_CATEGORY_ID":"1002","CREATION_DATE":"2014-12-03","PRODUCT_ID":"1039","PRODUCT_NAME":"Crayola Original Markers - Broad Line, Classic Colors","LAST_UPDATE_DATE":"2015-12-04","EXTERNAL_URL":"https://objectstorage.us-ashburn-1.oraclecloud.com/n/natdcshjumpstartprod/b/AlphaOffice-images/o/1039-Write-Crayola_Markers.jpg","ATTRIBUTE5":null,"ATTRIBUTE4":null,"ATTRIBUTE_CATEGORY":null,"PRODUCT_STATUS":"AVAILABLE","WARRANTY_PERIOD_MONTHS":"","COST_PRICE":"","TWITTER_TAG":"" }' http://localhost:8080/product/save

```

Save a new product with invalid data (will return 422 and validation errors):

```bash
curl -i"ATTRIBUTE2":null,"LAST_UPDATED_BY":"0","ATTRIBUTE1":null,"OBJECT_VERSION_ID":"1","MIN_PRICE":"2.99","PARENT_CATEGORY_ID":"1002","CREATION_DATE":"2014-12-03","PRODUCT_ID":"1039","PRODUCT_NAME":"Crayola Original Markers - Broad Line, Classic Colors","LAST_UPDATE_DATE":"2015-12-04","EXTERNAL_URL":"https://objectstorage.us-ashburn-1.oraclecloud.com/n/natdcshjumpstartprod/b/AlphaOffice-images/o/1039-Write-Crayola_Markers.jpg","ATTRIBUTE5":null,"ATTRIBUTE4":null,"ATTRIBUTE_CATEGORY":null,"PRODUCT_STATUS":"AVAILABLE","WARRANTY_PERIOD_MONTHS":"","COST_PRICE":"","TWITTER_TAG":"" }' http://localhost:8080/product/save
HTTP/1.1 422 Unprocessable Entity
Content-Type: application/json
Date: Sun, 31 May 2020 00:22:45 -0500
transfer-encoding: chunked
connection: keep-alive

validationErrors
```

Get the new product

```bash
curl -iX GET http://localhost:8080/product/1039                                                                                                                   
HTTP/1.1 200 OK
Content-Type: application/json
Date: Thu, 20 Jun 2019 10:46:17 -0400
transfer-encoding: chunked
connection: keep-alive

{"PRODUCT_ID":"1039","CATEGORY_ID":"1011","LIST_PRICE":"4.25","ATTRIBUTE3":null,"CREATED_BY":"0","ATTRIBUTE2":null,"LAST_UPDATED_BY":"0","ATTRIBUTE1":null,"OBJECT_VERSION_ID":"1","MIN_PRICE":"2.99","PARENT_CATEGORY_ID":"1002","CREATION_DATE":"2014-12-03","PRODUCT_NAME":"Crayola Original Markers - Broad Line, Classic Colors","LAST_UPDATE_DATE":"2015-12-04","EXTERNAL_URL":"https://objectstorage.us-ashburn-1.oraclecloud.com/n/natdcshjumpstartprod/b/AlphaOffice-images/o/1039-Write-Crayola_Markers.jpg","ATTRIBUTE5":null,"ATTRIBUTE4":null,"ATTRIBUTE_CATEGORY":null,"PRODUCT_STATUS":"AVAILABLE","WARRANTY_PERIOD_MONTHS":"","COST_PRICE":"","TWITTER_TAG":"","category_ID":"1011","list_PRICE":"4.25","attribute3":null,"created_BY":"0","attribute2":null,"last_UPDATED_BY":"0","attribute1":null,"object_VERSION_ID":"1","min_PRICE":"2.99","parent_CATEGORY_ID":"1002","creation_DATE":"Tue Dec 02 18:00:00 CST 2014","product_ID":"1039","product_NAME":"Crayola Original Markers - Broad Line, Classic Colors","last_UPDATE_DATE":"Thu Dec 03 18:00:00 CST 2015","external_URL":"https://objectstorage.us-ashburn-1.oraclecloud.com/n/natdcshjumpstartprod/b/AlphaOffice-images/o/1039-Write-Crayola_Markers.jpg","attribute5":null,"attribute4":null,"attribute_CATEGORY":null,"product_STATUS":"AVAILABLE","warranty_PERIOD_MONTHS":"","cost_PRICE":"","twitter_TAG":""}```



## View Health and Metrics

```bash
curl -s -X GET http://localhost:8080/health                                                                                                                                                
{"outcome":"UP","checks":[{"name":"deadlock","state":"UP"},{"name":"diskSpace","state":"UP","data":{"free":"254.50 GB","freeBytes":273264726016,"percentFree":"54.73%","total":"465.02 GB","totalBytes":499313172480}},{"name":"heapMemory","state":"UP","data":{"free":"254.45 MB","freeBytes":266813240,"max":"4.00 GB","maxBytes":4294967296,"percentFree":"98.69%","total":"308.00 MB","totalBytes":322961408}}]}
```

Prometheus Format

```bash
curl -s -X GET http://localhost:8080/metrics                                                                                                                                               
# TYPE base:classloader_current_loaded_class_count counter
# HELP base:classloader_current_loaded_class_count Displays the number of classes that are currently loaded in the Java virtual machine.
base:classloader_current_loaded_class_count 13218
# TYPE base:classloader_total_loaded_class_count counter
[Truncated]
```

Prometheus JSON Format

```bash
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics                                                                                                                    
{"base":{"classloader.currentLoadedClass.count":13229,"classloader.totalLoadedClass.count":13229,"classloader.totalUnloadedClass.count":0,"cpu.availableProcessors":4,"cpu.systemLoadAverage":3.65185546875,"gc.G1 Old Generation.count":0,"gc.G1 Old Generation.time":0,"gc.G1 Young Generation.count":9,"gc.G1 Young Generation.time":118,"jvm.uptime":556886,"memory.committedHeap":322961408,"memory.maxHeap":4294967296,"memory.usedHeap":58893312,"thread.count":59,"thread.daemon.count":45,"thread.max.count":59},"vendor":{"grpc.requests.count":0,"grpc.requests.meter":{"count":0,"meanRate":0.0,"oneMinRate":0.0,"fiveMinRate":0.0,"fifteenMinRate":0.0},"requests.count":8,"requests.meter":{"count":8,"meanRate":0.014449382373834188,"oneMinRate":0.022447789926396358,"fiveMinRate":0.009851690967428134,"fifteenMinRate":0.005533794777883567}}}
```

## Dockerfile

The generated `Dockerfile` requires some changes. See the `Dockerfile` for reference, particularly the need to install the `ojdbc` dependencies to the local Maven repo so they are included in the build since these are unavailable via public Maven repos. 

## Building the Docker Image

```
docker build -t product-svc .
```

## Running with Docker

```
docker run --rm --env DB_URL=jdbc:oracle:thin:@product_LOW\?TNS_ADMIN=build-resource/wallet  --env DB_PASSWORD=[Strong Password] --env DB_USER=[USER] -p 8080:8080 product-svc:latest
```

Test the endpoints as [described above](#test-endpoints)

## Deploying to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f secret.yaml               # Deploy Kubernetes Secrets
kubectl create -f app.yaml               # Deploy application
kubectl get service product-svc  # Verify deployed service
```

