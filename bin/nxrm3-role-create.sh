#!/bin/bash

playbook=nxrm3-role-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/role.json



