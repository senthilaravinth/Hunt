pipeline {
    agent any

    tools {
        maven 'Maven' 
    }

    environment {
        DOCKER_USER = "23mis0086"
        IMAGE_NAME = "calculator-app"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: '', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        // Build images
                        bat "docker build -t %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% ."
                        bat "docker build -t %DOCKER_USER%/%IMAGE_NAME%:latest ."
                        
                        // Login and Push
                        bat "docker login"
                        bat "docker push %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%"
                        bat "docker push %DOCKER_USER%/%IMAGE_NAME%:latest"
                    }
                }
            }
        }

        stage('Kubernetes Deploy') {
    steps {
        script {
            withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_PATH')]) {
                // Wrap the variable in double quotes "" to handle the space in "Mvn pipe"
                bat 'kubectl apply --kubeconfig="%KUBECONFIG_PATH%" -f deployment.yaml'
                
                bat 'kubectl set image deployment/calculator-deployment calculator=%DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% --kubeconfig="%KUBECONFIG_PATH%"'
                
                bat 'kubectl rollout status deployment/calculator-deployment --kubeconfig="%KUBECONFIG_PATH%"'
            }
        }
    }
}
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo "Successfully deployed version ${IMAGE_TAG} to Kubernetes!"
        }
        failure {
            echo "Pipeline failed. Check the logs for Maven, Docker, or Kubectl errors."
        }
    }
}