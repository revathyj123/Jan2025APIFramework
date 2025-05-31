pipeline{
    
    agent any
    
    stages{
        
        stage("build"){
            steps{
                echo("build the project")
            }
        }
        
        stage("Run Unit test"){
            steps{
                echo("run UTs")
            }
        }
        
        stage("Run Integration test"){
            steps{
                echo("run ITs")
            }
        }
        
        stage("Deploy to dev"){
            steps{
                echo("deploy to dev")
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to QA")
            }
        }
        
        stage("Run regression API test cases on QA"){
            steps{
                echo("Run API test cases on QA")
            }
        }
        
        stage("Deploy to stage"){
            steps{
                echo("deploy to stage")
            }
        }
        
        stage("Run sanity API test cases on Stage"){
            steps{
                echo("Run API sanity test cases on Stage")
            }
        }

        stage("Deploy to UAT"){
            steps{
                echo("deploy to UAT")
            }
        }

        stage("Run UAT API test cases on UAT"){
            steps{
                echo("Run API UAT test cases on UAT")
            }
        }
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }      
    }    
}