#!/bin/bash

playbook=privilege-repository-content-selector-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/privilege-repository-content-selector.json



