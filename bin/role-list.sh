#!/bin/bash

playbook=role-list

script_name=role_list

ansible-playbook -v ../playbooks/${playbook}.yml --extra-vars "script_name=${script_name}"

