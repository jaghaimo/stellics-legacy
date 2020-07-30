#!/usr/bin/env bash

set -eu
cd ..

if [ ! -d "Console Commands" ]; then
    wget https://github.com/LazyWizard/console-commands/releases/download/dev-2020-07-12/Console_Commands_3.0_dev_2020-07-12.zip
    unzip Console_Commands*
    rm -f Console_Commands*.zip
fi
