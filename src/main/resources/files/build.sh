#!/bin/bash
#internal vars
git_user="Solinftec-TI"
git_password="-28064212-Solinftec-Github"
organization="solinftec"
#alfred server url
url="http://localhost:4212/v1"
is_valid=0
path_for_build=""
artifact=""
m_log="mvn_b.log"
n_log="npm_b.log"
path_log=""
path_root=~/alfred
#external vars
app_name=$1
branch=$2
app_type=$3
build_id=$4

function build-src() {
    mkdir -p ${path_root}/builds/${build_id}
    mkdir -p ${path_root}/libs/js
    path_for_build=${path_root}/builds/${build_id}
}

function make-clone-app() {
    echo -e "▶ Making clone $app_name "
    cd ${path_for_build}
    git clone https://${git_user}:${git_password}@github.com/${organization}/${app_name}.git
    echo -e "▶ Trying build checkout to branch: $branch "
    cd ${app_name}
    git fetch
    git checkout ${branch}
    git pull
}

function make-clone-config() {
    echo -e "▶ Making clone of source-configuration "
    cd ../
    rm -R configuration || echo "▶ Remove source-configuration path"
    git clone https://${git_user}:${git_password}@github.com/${organization}/configuration.git
}

function replace-configurations(){
    echo -e "▶ Trying replace configure files"
    case ${app_type} in
    jar)
        cp configuration/sgpa/yml/${branch}/${app_name}/application.yml ${app_name}/src/main/resources
    ;;
    war)
        cp configuration/sgpa/yml/${branch}/${app_name}/config.properties ${app_name}/src/main/resources
    ;;
    js)
        cp configuration/sgpa/yml/${branch}/${app_name}/prod.config.js ${app_name}/
        rm -Rf ${app_name}/api/${organization}/*
        cp configuration/sgpa/yml/${branch}/${app_name}/maps/* ${app_name}/api/${organization}/
    ;;
    esac
}

function make-build-by-maven(){
    echo -e "▶ Making mvn build "
    cd ${app_name}
    touch ${m_log}
    mvn package -l ${m_log}
    path_log=${path_for_build}/${app_name}/${m_log}
}


function make-build-by-npm(){
  echo ""
  echo -e "Realizando build"
  echo ""
  echo -e "● Aguarde. Esse processo pode demorar um pouco..."
  echo ""
  sudo touch ${npm_log_file}
  sudo chmod 777 ${npm_log_file}
  sudo cp -Rf ${path_for_libs}/* .
  #sudo mkdir bower_components || echo "Diretoro do bower ja existe"
  sudo chmod 777 bower_components
  sudo npm install && sudo echo "npm install its ok" >> ${npm_log_file}
  bower install && sudo echo "bower install its ok" >> ${npm_log_file}
  sudo gulp prod  && sudo echo "gulp prod its ok" >> ${npm_log_file}
  sudo echo "Options -Indexes. " | sudo tee .htaccess
}

function make-build-by-npm(){
    echo -e "▶ Making npm build "
    cd ${app_name}
    touch ${n_log}
    cp -Rf ../../../libs/js/* .
    mkdir bower_components || echo "▶ Bower path already exists"
    chmod 777 bower_components
    npm install &&  echo "npm install its ok" >> ${n_log}
    bower install &&  echo "bower install its ok" >> ${n_log}
    gulp prod  &&  echo -e "gulp prod its ok\nBUILD SUCCESS" >> ${n_log}
    echo "Options -Indexes. " | tee .htaccess
    cp -Rf bower_components ../../../libs/js/
    cp -Rf node_modules ../../../libs/js/
    path_log=${path_for_build}/${app_name}/${n_log}
}

function check-build(){
    file=$1
    build_success=$(grep -R "BUILD SUCCESS" ${file} | wc -l)
    if [ ${build_success} -ge 1 ]
    then
        is_valid=1
    else
        is_valid=0
    fi
}

function make-zip(){
    case ${app_type} in
    jar)
       gzip target/${app_name}.jar
    ;;
    war)
       gzip target/${app_name}.war
    ;;
    js)
       cd ..
       tar -zcvf ${branch}.tar ${app_name}
    ;;
    esac
}

function get-artifact(){
    case ${app_type} in
    jar)
        artifact=${path_for_build}/${app_name}/target/${app_name}.jar.gz
    ;;
    war)
        artifact=${path_for_build}/${app_name}/target/${app_name}.war.gz
    ;;
    js)
        artifact=${path_for_build}/${branch}.tar
    ;;
    esac
}

function send-build-status() {
    curl -X POST \
    ${url}/build/feed-back \
    -H 'cache-control: no-cache' \
    -H 'content-type: application/json' \
    -d '{"build_id":"'${build_id}'", "status":'${is_valid}', "artifact":"'${artifact}'", "log":"'${path_log}'"}'
}

function start(){
    is_valid=0;
    build-src;
    make-clone-app;
    #make-clone-config;
    #replace-configurations;
}

function finish(){
    make-zip
    get-artifact;
    send-build-status;
}

clear;

case ${app_type} in
    jar)
        start;
        make-build-by-maven;
        check-build ${m_log};
        finish;
        ;;
    war)
        start;
        make-build-by-maven;
        check-build ${m_log};
        finish;
        ;;
    js)
        start;
        make-build-by-npm;
        check-build ${n_log};
        finish;
        ;;
esac