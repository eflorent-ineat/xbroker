pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {

                currentBuild.displayName = "${env.BRANCH_NAME}-${v}-${env.BUILD_NUMBER}"
                sh 'echo M2_HOME'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.skip=true install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // sh 'ssh user@server rm -rf /var/www/temp_deploy/dist/'
                // sh 'ssh user@server mkdir -p /var/www/temp_deploy'
                // sh 'scp -r dist user@server:/var/www/temp_deploy/dist/'
                // sh 'ssh user@server "rm -rf /var/www/example.com/dist/ && mv /var/www/temp_deploy/dist/ /var/www/example.com/"'
            }
        }
    }
}


def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    return matcher ? matcher[0][1] : null
}
