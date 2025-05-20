pipeline {
    agent any
    environment {
        DEPLOYMENT_NAME = "hello-node"
        CONTAINER_NAME = "hello-node-6f98659985-5d6n8"
        IMAGE_NAME = "cyy1019/sismics/docs:v1.11"
        MINIKUBE_PATH = "D:\\course\\SE\\minikube\\minikube.exe"
    }
    stages {
        stage('Start Minikube') {
            steps {
                bat '''
                if not exist "%MINIKUBE_PATH%" (
                    echo Minikube not found at %MINIKUBE_PATH%
                    exit /b 1
                )

                echo Checking Minikube status...
                "%MINIKUBE_PATH%" status

                echo Starting Minikube...
                "%MINIKUBE_PATH%" start
                '''
            }
        }
        stage('Set Image') {
            steps {
                bat '''
                echo Setting image for deployment...
                kubectl set image deployment/%DEPLOYMENT_NAME% %CONTAINER_NAME%=%IMAGE_NAME%
                '''
            }
        }
        stage('Verify') {
            steps {
                bat '''
                kubectl rollout status deployment/%DEPLOYMENT_NAME%
                kubectl get pods
                '''
            }
        }
    }
}
