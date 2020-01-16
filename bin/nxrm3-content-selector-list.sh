#!/bin/bash

playbook=nxrm3-content-selector-list

script_name=content_selector_list

ansible-playbook ../playbooks/${playbook}.yml --extra-vars "script_name=${script_name}"

