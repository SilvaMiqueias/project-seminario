pipeline {
    agent any
    environment {
        HEROKU_API_KEY = credentials('heroku-api-key-id') // ID da credencial que você configurou
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_TOKEN = credentials('sonar-token')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
               script{
                sh 'gradle build'
               }
            }
        }

        stage('SonarQube Analysis') {
              steps {
                        script {
                            sh './gradle sonar -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.login=${SONARQUBE_TOKEN}'
                        }
                    }
              }

        stage('JaCoCo Report') {
             steps {
                            script {
                                publishJacocoReport(
                                    execPattern: '**/build/jacoco/test.exec',
                                    classPattern: '**/build/classes/java/main',
                                    sourcePattern: 'src/main/java'
                                )
                            }
                 }
        }

        stage('Deploy') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'heroku-api-key-id', variable: 'HEROKU_API_KEY')]) {
                        // Login no Heroku
                        sh 'echo $HEROKU_API_KEY | heroku auth:token'

                        // Adicionar o remote do Heroku se ainda não estiver configurado
                        sh 'git remote add heroku https://git.heroku.com/project-seminario.git|| true'

                        // Push para o Heroku
                        sh 'git push heroku HEAD:main'
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Falha no deploy.'
        }
    }
}