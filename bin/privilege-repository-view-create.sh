#!/bin/bash

playbook=privilege-repository-view-create
datafile=p3.json
#datafile=privilege-repository-view.json
ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/${datafile}



