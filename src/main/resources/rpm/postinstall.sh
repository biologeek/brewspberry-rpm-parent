#!/bin/bash


############################################
####		POST INSTALL SCRIPT			####
#------------------------------------------#
# Author :								   #
# Version : 							   #
# Functions :							   #
# - Create directory /conf in $1 		   #
# - Copy config files to /conf 			   #
############################################



CURRENT_DIR=`${cd "$(dirname "$0")" && pwd}`


if [ ! -d "$1/conf" ]
then 
	mkdir -p "$1/conf"
fi








