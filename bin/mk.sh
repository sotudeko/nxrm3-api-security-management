#!/bin/bash

#curl -u admin:admin123 -X POST "http://localhost:8081/service/rest/beta/security/roles" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"id\": \"my-role-id\", \"name\": \"my-role-name\", \"description\": \"the description\", \"privileges\": [ \"nx-repository-admin-*-*-*\" ], \"roles\": [ \"nx-admin\" ]}"

curl -u admin:admin123 -X POST "http://localhost:8081/service/rest/beta/security/roles" -H "accept: application/json" -H "Content-Type: application/json" -d@/tmp/data.json 

