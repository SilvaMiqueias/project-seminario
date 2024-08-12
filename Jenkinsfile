pipeline {
    agent any
    environment {
        HEROKU_API_KEY = credentials('heroku-api-key-id') // ID da credencial que você configurou
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_TOKEN = credentials('sonar-cloud')
        SONARQUBE_SERVER = 'SonarCloud'  // Nome do SonarQube Server configurado no Jenkins
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
                            jacoco()
                    }
        }

          stage('SonarCloud Analysis') {
                    steps {
                        script {
                            // A configuração do scanner para Gradle
                           withSonarQubeEnv(SONARQUBE_SERVER) {
                               sh 'gradle sonarqube -Dsonar.projectKey=SilvaMiqueias_project-seminario -Dsonar.organization=silvamiqueias'
                           }
                        }
                    }
           }

           stage('Quality Gate') {
                    steps {
                        script {
                            // Espera o SonarQube analisar e verificar o Quality Gate
                            timeout(time: 1, unit: 'HOURS') {
                                waitForQualityGate abortPipeline: true
                            }
                        }
                    }
           }

        stage('Deploy') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'heroku-api-key-id', variable: 'HEROKU_API_KEY')]) {
                        // Login no Heroku
                        sh 'echo $HEROKU_API_KEY | heroku auth:token'

                           // Obtemos o nome da branch atual
                              def branchName = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()

                               // Verificamos se a branch é 'main'
                                  if (branchName == 'main') {
                                    sh 'git remote add heroku https://git.heroku.com/project-seminario-prod.git || true'
                                   } else {
                                     sh 'git remote add heroku https://git.heroku.com/project-seminario.git|| true'
                                   }

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
                jacoco()
            }
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Falha no deploy.'
        }
    }
}