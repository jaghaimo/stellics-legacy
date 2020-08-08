#!/usr/bin/env bash

set -eu

# Console Commands
if [ ! -f "libs/lw_Console.jar" ]; then
    tmp_dir=$(mktemp -d -t libs-cc-XXXXXXXXXX)
    pushd $tmp_dir
    wget https://github.com/LazyWizard/console-commands/releases/download/dev-2020-07-12/Console_Commands_3.0_dev_2020-07-12.zip
    unzip *
    popd
    cp $tmp_dir/*/jars/lw_Console.jar libs/
fi

# LazyLib
if [ ! -f "libs/LazyLib.jar" ]; then
    tmp_dir=$(mktemp -d -t libs-ll-XXXXXXXXXX)
    pushd $tmp_dir
    wget https://github.com/LazyWizard/lazylib/releases/download/2.4f/LazyLib_2.4f.zip
    unzip *
    popd
    cp $tmp_dir/*/jars/LazyLib.jar libs/
fi

# Starsector
if [ ! -f "libs/starfarer.api.jar" ]; then
    tmp_dir=$(mktemp -d -t libs-ss-XXXXXXXXXX)
    pushd $tmp_dir
    wget https://s3.amazonaws.com/fractalsoftworks/starsector/starsector_linux-0.9.1a-RC8.zip
    unzip *
    popd
    cp $tmp_dir/*/starfarer.api.jar libs/
fi
