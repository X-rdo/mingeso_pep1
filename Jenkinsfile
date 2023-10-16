pipeline{
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR"){
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/X-rdo/mingeso_pep1']])
                {
                    sh "ls"
                    sh "mvn clean install"
                }
            }
        }
        stage("Build Docker Image"){
            steps {
                {
                    sh "docker build -t xrdo51/proyecto_mingeso_pep1 ."
                }
            }
        }
        stage("Push Docker Image"){
            steps {
                {
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                        sh "docker login -u xrdo51 -p ${dckpass}"
                    }
                    sh "docker push xrdo51/proyecto_mingeso_pep1"
                }
            }
        }
        stage("Test"){
            steps {
                {
                    sh "mvn test"
                }
            }
        }
    }
    post {
        always {
            {
                sh "docker logout"
            }
        }
    }
}