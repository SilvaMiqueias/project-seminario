pipeline {
    agent any
    environment {
        HEROKU_API_KEY = credentials('heroku-api-key-id') // ID da credencial que você configurou
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
                sh 'gradle clean build sonarqube'
               }
            }
        }
        stage('JaCoCo Report') {
                    steps {
                        // Publica o relatório JaCoCo
                        recordJacoco()
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