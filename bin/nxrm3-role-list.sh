#!/bin/bash

playbook=nxrm3-role-list

script_name=role_list

ansible-playbook ../playbooks/${playbook}.yml --extra-vars "script_name=${script_name}"

