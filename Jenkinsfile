pipeline {
    agent any

    tools {
        // Ensure this name matches exactly what you have in 
        // Manage Jenkins -> Global Tool Configuration
        maven 'Maven' 
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls your code from GitHub
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                // Deletes the target folder for a fresh build
                bat 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                // Checks for syntax errors in App.java
                bat 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                // Runs AppTest.java and checks your add/subtract logic
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Creates the final .jar file in the target folder
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            // Displays your test results in the Jenkins UI
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo 'Build Successful! Artifact is ready.'
            // Saves the .jar file so you can download it from Jenkins
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo 'The build failed. Check the Console Output or Test Reports.'
        }
    }
}