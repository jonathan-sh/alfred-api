#!/bin/bash
#internal
build_path=builds
#external vars
app_type=$1
app_name=$2
branch=$3
root_path=$4
service=$5

function service-restart(){
   sudo service ${service} restart
}

function extract-gz(){
   sudo gzip -d ${root_path}/${build_path}/*.gz
}

function extract-tar(){
   sudo tar -zxf ${root_path}/${build_path}/*.tar
}

case ${app_type} in
        jar)
            extract-gz;
            mkdir -p /solinftec/builded/
            sudo cp -Rf ${root_path}/${build_path}/${app_name}.jar /solinftec/builded/
            service-restart
            ;;
        war)
            extract-gz
            sudo cp -Rf ${root_path}/${build_path}/${app_name}.war /var/lib/tomcat8/webapps/
            service-restart
            ;;
        js)
            cd ${root_path}/${build_path}/
            extract-tar
            sudo rm -Rf /var/www/${branch}
            sudo rm ${root_path}/${build_path}/${branch}.tar
            sudo mv ${root_path}/${build_path}/${app_name} ${branch}
            sudo mkdir /var/www/${branch}
            sudo cp -Rf ${root_path}/${build_path}/${branch}/* /var/www/${branch}
            sudo rm -Rf ${root_path}/${build_path}/${branch}
            service-restart
            ;;
esac