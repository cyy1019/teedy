pipeline {
 agent any
 
 environment {
 DOCKER_HUB_CREDENTIALS = credentials('cyy-teedy') 
DOCKER_IMAGE = 'cyy1019/teedy'
 DOCKER_TAG = "${env.BUILD_NUMBER}"
 }
 
 stages {
 stage('Build') {
 steps {
 checkout scmGit(
 branches: [[name: '*/lab12']], 
 extensions: [], 
 userRemoteConfigs: [[url: 'https://github.com/cyy1019/teedy.git']]
 )
 sh 'mvn -B -DskipTests clean package'
 }
 }
 
 stage('Building image') {
 steps {
 script {
 docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
 }
 }
 }
 
 stage('Upload image') {
 steps {
 script {
 docker.withRegistry('https://registry.hub.docker.com',
'DOCKER_HUB_CREDENTIALS') {
 
docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
 
 
docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
 }
 }
 }
 }
 
 // Running Docker container
 stage('Run containers') {
 steps {
 script {
sh 'docker stop teedy-container-8081 || true'
sh 'docker rm teedy-container-8081 || true'
docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run(
'--name teedy-container-8081 -d -p 8081:8080'
)
sh 'docker ps --filter "name=teedy-container"'
 
}
}
 }
 }
}