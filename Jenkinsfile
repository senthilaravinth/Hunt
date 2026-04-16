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
                    withCredentials([usernamePassword(credentialsId: 'dockerhubid', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
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
                    // This uses the 'kubeconfig' file credential you created in Jenkins
                    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_PATH')]) {
                        // 1. Apply the deployment configuration
                        bat "kubectl --kubeconfig=%KUBECONFIG_PATH% apply -f deployment.yaml"
                        
                        // 2. Force the deployment to use the new image we just pushed
                        bat "kubectl --kubeconfig=%KUBECONFIG_PATH% set image deployment/calculator-deployment calculator=%DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%"
                        
                        // 3. Verify the rollout status
                        bat "kubectl --kubeconfig=%KUBECONFIG_PATH% rollout status deployment/calculator-deployment"
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