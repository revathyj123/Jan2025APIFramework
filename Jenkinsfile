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
		            echo docker build -t "$env:DOCKER_IMAGE" .
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
		                 echo "$env:DOCKER_PASS" | docker login -u "$env:DOCKER_USER" --password-stdin
		
		                # Pushing the image
		                echo "DOCKER_IMAGE: $env:DOCKER_IMAGE"
		                docker push $env:DOCKER_IMAGE
		            '''
		        }
		    }
		}
		        stage('Deploy to Dev') {
            steps {
                echo 'Deploying to Dev environment...'
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
        

        stage('Deploy to QA') {
            steps {
                echo 'Deploying to QA environment...'
            }
        }

        stage('Run Regression Tests on QA') {
            steps {
                script {
                    def status = bat(
                        script: """
                                  docker run --rm -v \$WORKSPACE:/app -w /app ${DOCKER_IMAGE} \
                                  mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=prod
                                 """,
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to Stage') {
            steps {
                echo 'Deploying to Stage environment...'
            }
        }

        stage('Run Sanity Tests on Stage') {
            steps {
                script {
                    def status = bat(
                        script: """
                                docker run --rm -v \$WORKSPACE:/app -w /app ${DOCKER_IMAGE} \
                                mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod
                                """,
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Sanity ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to Prod') {
            steps {
                echo 'Deploying to Prod environment...'
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