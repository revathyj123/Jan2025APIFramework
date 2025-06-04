pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        DOCKER_IMAGE = "jrevathy82/jan2025apiframework:${env.BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'dockerhub_credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/revathyj123/Jan2025APIFramework.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                powershell '''
                    docker build -t $env:DOCKER_IMAGE .
                '''
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub_credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    powershell '''
	                    echo "DOCKER_USER: $env:DOCKER_USER"
						echo "DOCKER_PASS: [hidden for security]"'''
                }
            }
        }
    }
}