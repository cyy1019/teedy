pipeline {
    agent any
    environment {
        DEPLOYMENT_NAME = "hello-node"
        CONTAINER_NAME = "hello-node-6f98659985-5d6n8"
        IMAGE_NAME = "cyy1019/sismics/docs:v1.11"
        MINIKUBE = "/d/course/SE/minikube/minikube.exe"
    }
    stages {
        stage('Start Minikube') {
            steps {
                sh '''
                if ! "$MINIKUBE" status | grep -q "Running"; then
                  echo "Starting Minikube..."
                  "$MINIKUBE" start
                else
                  echo "Minikube already running."
                fi
                '''
            }
        }
        stage('Set Image') {
            steps {
                sh '''
                echo "Setting image for deployment..."
                kubectl set image deployment/${DEPLOYMENT_NAME} ${CONTAINER_NAME}=${IMAGE_NAME}
                '''
            }
        }
        stage('Verify') {
            steps {
                sh '''
                kubectl rollout status deployment/${DEPLOYMENT_NAME}
                kubectl get pods
                '''
            }
        }
    }
}
