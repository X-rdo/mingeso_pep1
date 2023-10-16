pipeline{
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR"){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/X-rdo/mingeso_pep1']])
                
                    bat "ls"
                    bat "mvn clean install"
                
            }
        }
        stage("Build Docker Image"){
            steps {
                
                    bat "docker build -t xrdo51/proyecto_mingeso_pep1 ."
                
            }
        }
        stage("Push Docker Image"){
            steps {
                
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                        sh "docker login -u xrdo51 -p ${dckpass}"
                    }
                    bat "docker push xrdo51/proyecto_mingeso_pep1"
                
            }
        }
        stage("Test"){
            steps {
                bat "mvn test"
            }
        }
    }
    post {
        always {
            bat "docker logout"
        }
    }
}