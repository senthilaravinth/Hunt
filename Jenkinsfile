pipeline {
    agent any

    tools {
        // Ensure this name matches Manage Jenkins -> Tools
        maven 'Maven' 
    }

    environment {
        // Replace with your Docker Hub username
        DOCKER_USER = "senthilaravinth"
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
                // Compile and run your AppTest.java logic
                bat 'mvn clean test'
            }
        }

        stage('Maven Package') {
            steps {
                // Create the .jar file
                bat 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                // Build the image using the Dockerfile in your root folder
                bat "docker build -t ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} ."
                bat "docker build -t ${DOCKER_USER}/${IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    // This requires a credential in Jenkins with ID 'docker-hub-creds'
                    // Type: Username with Password
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        bat "docker login"
                        bat "docker push ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
                        bat "docker push ${DOCKER_USER}/${IMAGE_NAME}:latest"
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
            echo "Build and Image Push Successful! Version: ${IMAGE_TAG}"
        }
        failure {
            echo "Build failed. Check the Jenkins console logs for errors."
        }
    }
}