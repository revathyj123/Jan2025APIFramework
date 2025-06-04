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
		stage('Print Docker Image') {
    		steps {
       			echo "DOCKER_IMAGE: ${env.DOCKER_IMAGE}"
    		}
		}
        stage('Checkout Code') {
            steps {
                git 'https://github.com/revathyj123/Jan2025APIFramework.git'
            }
        }

        stage('Build Docker Image') {
    		steps {
       	 		powershell '''
		            echo "DOCKER_IMAGE is: $env:DOCKER_IMAGE"
		            docker version
		            docker build -t "$env:DOCKER_IMAGE" .
        			'''
		    }
		}

        stage('Push Docker Image to Docker Hub') {
		    steps {
		        echo "DOCKER_IMAGE: ${env.DOCKER_IMAGE}"
		        
		        withCredentials([usernamePassword(
		            credentialsId: 'dockerhub_credentials',
		            usernameVariable: 'DOCKER_USER',
		            passwordVariable: 'DOCKER_PASS'
		        )]) {
		            powershell '''
		                echo "DOCKER_USER: $env:DOCKER_USER"
		                echo "DOCKER_PASS: [hidden for security]"
		                
		                # Securely passing credentials to Docker
		                $env:DOCKER_PASS | Out-File -Encoding ascii -FilePath "password.txt"
		                docker login -u $env:DOCKER_USER --password-stdin < password.txt
		                Remove-Item "password.txt"
		
		                # Pushing the image
		                echo "DOCKER_IMAGE: $env:DOCKER_IMAGE"
		                docker push $env:DOCKER_IMAGE
		            '''
		        }
		    }
		}
    }
}