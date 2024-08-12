pipeline {
    agent any
    environment {
        HEROKU_API_KEY = credentials('heroku-api-key-id') // ID da credencial que você configurou
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_TOKEN = credentials('sonar-token')
        SONARQUBE_SERVER = 'SonarQube'  // Nome do SonarQube Server configurado no Jenkins
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
             steps {
                  check novamente
             }
        }

        stage('Build e Test') {
            steps {
               script{
                sh 'gradle clean build'
               }
            }
        }

        stage('JaCoCo Report') {
             steps {
                     jacoco(
                          classPattern: '**/classes',
                          execPattern: '**/build/jacoco.exec',
                          sourcePattern: '**/src/main/java',
                          inclusionPattern: '**/*.java'
                        )
                    }
        }

        stage('Deploy') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'heroku-api-key-id', variable: 'HEROKU_API_KEY')]) {
                        // Login no Heroku
                        sh 'echo $HEROKU_API_KEY | heroku auth:token'

           

                        // Push para o Heroku
                        sh 'git push heroku HEAD:main'
                    }
                }
            }
        }
    }
    post {

            always {
                junit '**/build/test-results/test/*.xml'
                jacoco(
                    classPattern: '**/classes',
                    execPattern: '**/build/jacoco.exec',
                    sourcePattern: '**/src/main/java',
                    inclusionPattern: '**/*.java'
                )
            }
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Falha no deploy.'
        }
    }
}