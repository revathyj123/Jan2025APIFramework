pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        DOCKER_IMAGE = "jrevathy82/jan2025apiframework:${BUILD_NUMBER}"
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
                powershell 'docker build -t $env:DOCKER_IMAGE .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    powershell '''
                        echo "DOCKER_USER: $env:DOCKER_USER"
                        echo "DOCKER_PASS: [hidden for security]"

                        # Logging into Docker Hub
                        docker login -u $env:DOCKER_USER -p $env:DOCKER_PASS

                        # Pushing the Docker image
                        echo "DOCKER_IMAGE: $env:DOCKER_IMAGE"
                        docker push $env:DOCKER_IMAGE
                    '''
                }
            }
        }

        stage('Run Sanity Tests on Dev') {
            steps {
                script {
                    def status = powershell(
                        script: '''
                            echo "DOCKER_IMAGE: $env:DOCKER_IMAGE"
                            docker run --rm -v "$env:WORKSPACE:/app" -w "/app" $env:DOCKER_IMAGE mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod
                        ''',
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
    }
}