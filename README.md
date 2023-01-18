# SpringBoot 3 Observability using Grafana Stack

This is a demo of SpringBoot 3 Observability feature using Grafana Stack.

* Export application metrics using SpringBoot Actuator, Micrometer to Prometheus
* Stream logs from log files or from docker container logs into Loki using Promtail
* Export traceability metrics into Temp

## How to run?

```shell
$ ./run.sh build_apps
$ ./run.sh start_all
$ ./run.sh stop_all
```

* Application URL: http://localhost:8080
* Grafana URL: http://localhost:3000 , Login with admin/admin
