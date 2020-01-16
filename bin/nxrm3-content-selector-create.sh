#!/bin/bash

playbook=nxrm3-content-selector-create

ansible-playbook ../playbooks/${playbook}.yml --extra-vars @data/content-selector.json



