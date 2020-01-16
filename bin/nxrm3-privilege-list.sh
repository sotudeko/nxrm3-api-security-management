#!/bin/bash

playbook=nxrm3-privilege-list

script_name=privilege_list

ansible-playbook ../playbooks/${playbook}.yml --extra-vars "script_name=${script_name}"

