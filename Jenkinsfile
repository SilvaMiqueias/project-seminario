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
        }

        stage('Build') {
            steps {
               script{
                sh 'gradle build'
               }
            }
        }


        stage('JaCoCo Report') {
             steps {
                    script {
                       // Rodar testes e gerar relatórios JaCoCo
                       sh 'gradle test jacocoTestReport'
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

            always {
                jacoco(
                    classPattern: '**/classes',
                    execPattern: '**/jacoco.exec',
                    sourcePattern: '**/src/main/java'
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