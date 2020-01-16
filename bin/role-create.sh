#!/bin/bash

playbook=role-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/role.json



