pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                script {
                    v = version()
                    currentBuild.displayName = "${env.BRANCH_NAME}-${v}-${env.BUILD_NUMBER}"
                }
            }
        }
        stage('Build') {
            steps {
                sh '''mvn -Dmaven.test.skip=true install
                mvn clean install
                mvn --projects backend compile'''
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
            when { tag "release-*" }
            steps {
                echo 'Deploying only because this commit is tagged...'
                // sh 'make deploy'
            }
        }
    }
}


def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    return matcher ? matcher[0][1] : null
}
