#!/bin/bash

curl -v -u admin:admin123 -X POST "http://localhost:8081/service/rest/beta/security/roles" -H "accept: application/json" -H "Content-Type: application/json" -d@../data/role.json
