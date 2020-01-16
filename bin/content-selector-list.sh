#!/bin/bash

playbook=content-selector-list

script_name=content_selector_list

ansible-playbook ../playbooks/${playbook}.yml --extra-vars "script_name=${script_name}"

