## Box ELK Java Demo Setup

The demo will send logs directly to the `ElasticSearch` database, then provide a visualizer for the logs using `Kibana`.

1. Install [VirtualBox](http://virtualbox.org)
1. Install [Vagrant](https://www.vagrantup.com/downloads.html)
1. `git clone` https://github.com/comperiosearch/vagrant-elk-box
1. Get Box Java Sample code from RevDev

Logstash is present, but isn't used because the logs are sent directly to ES.

http://logstash.net/docs/1.4.2/tutorials/getting-started-with-logstash

### Setup server

    vagrant ssh
    sudo mv /etc/logstash/conf.d/logstash /etc/logstash/conf.d/logstash.conf
    sudo /etc/init.d/logstash restart

### Setup Elasticsearch

[Index](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-index_.html) need to be created.
    
[Create the index](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-create-index.html)

    PUT http://localhost:9200/logstash

Add a [mapping](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html) to the index:
    
    POST http://localhost:9200/logstash/_mapping/log/
    {
        "log": {
            "properties": {
                "@message": {"type": "string", "store": true},
                "@timestamp": {"type": "date", "store": true},
                "value": {"type": "integer", "store": true}
            }
        }
    }
    
To [delete](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-delete-index.html):

    DELETE http://localhost:9200/logstash

To send sample log:
        
    POST http://localhost:9200/logstash/log/
    {
        "@timestamp": "2015-03-02T03:13:24.76802+01:00",
        "@message": "This is a new sample log message here.",
        "value": 3
    }

See [Elastic HQ](http://localhost:9200/_plugin/HQ/#cluster).

### Setup Kibana

Update index to `logstash`. Set time field to `@timestamp`.

See [Kibana web app](http://localhost:5601/).

#### Notes

Alternate ELK virtual appliance: https://github.com/frntn/vagrant-elk-clientserver

- http://brewhouse.io/blog/2014/11/04/big-data-with-elk-stack.html
- http://blog.comperiosearch.com/blog/2014/08/14/elk-one-vagrant-box/
- http://christophe.vandeplas.com/2014/06/setting-up-single-node-elk-in-20-minutes.html
- http://www.anotherlan.com/make-sense-of-server-logs-with-logstash-elasticsearch-and-kibana