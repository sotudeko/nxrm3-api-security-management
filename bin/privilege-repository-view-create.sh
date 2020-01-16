#!/bin/bash

playbook=privilege-repository-view-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/privilege-repository-view.json



