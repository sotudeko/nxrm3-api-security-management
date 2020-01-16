#!/bin/bash

playbook=nxrm3-privilege-repository-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/privilege-repository.json



