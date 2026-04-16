pipeline {
    agent any

    tools {
        // This must match the name you gave Maven in 'Global Tool Configuration'
        maven 'Maven 3.x' 
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls your code from the Git repository
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                // Removes old build artifacts to ensure a fresh start
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                // Checks for syntax errors in your App.java
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                // Runs AppTest.java and generates a report
                // If a test fails, the pipeline stops here (turns Red)
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Creates the final .jar file in the target/ folder
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            // This archives the test results in Jenkins so you can view them in the UI
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo 'The build and tests passed successfully!'
            // This saves the .jar file so you can download it from the Jenkins dashboard
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo 'Build failed. Please check the unit tests or syntax errors.'
        }
    }
}