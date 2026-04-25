pipeline {
    agent any

    environment {
        DOCKER_HUB_ID  = "kjk1526"
        IMAGE_NAME     = "colonydrop"
        BLUE_SERVER    = "ubuntu@3.38.96.248"
        GREEN_SERVER   = "ubuntu@13.125.49.98"
    }

    stages {
        stage('Pull') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/gyu0918/ColonyDrop-backend.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build -x test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        docker build -t $DOCKER_HUB_ID/$IMAGE_NAME:latest .
                        docker login -u $DOCKER_USER -p $DOCKER_PASS
                        docker push $DOCKER_HUB_ID/$IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('Deploy Green') {
            steps {
                withCredentials([
                    string(credentialsId: 'db-host',        variable: 'DB_HOST'),
                    string(credentialsId: 'db-name',        variable: 'DB_NAME'),
                    string(credentialsId: 'db-username',    variable: 'DB_USERNAME'),
                    string(credentialsId: 'db-password',    variable: 'DB_PASSWORD'),
                    string(credentialsId: 'redis-host',     variable: 'REDIS_HOST'),
                    string(credentialsId: 'jwt-secret',     variable: 'JWT_SECRET'),
                    string(credentialsId: 'jwt-expiration', variable: 'JWT_EXPIRATION')
                ]) {
                    sshagent(['ec2-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no $GREEN_SERVER "
                                docker pull $DOCKER_HUB_ID/$IMAGE_NAME:latest &&
                                docker rm -f app || true &&
                                docker run -d --name app -p 8080:8080 \
                                    -e DB_HOST=$DB_HOST \
                                    -e DB_NAME=$DB_NAME \
                                    -e DB_USERNAME=$DB_USERNAME \
                                    -e DB_PASSWORD=$DB_PASSWORD \
                                    -e REDIS_HOST=$REDIS_HOST \
                                    -e JWT_SECRET=$JWT_SECRET \
                                    -e JWT_EXPIRATION=$JWT_EXPIRATION \
                                    $DOCKER_HUB_ID/$IMAGE_NAME:latest
                            "
                        '''
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                    echo "앱 시작 대기 중..."
                    sleep 40
                    for i in {1..10}; do
                        STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://13.125.49.98:8080/)
                        if [ $STATUS -eq 200 ] || [ $STATUS -eq 401 ]; then
                            echo "헬스 체크 통과! STATUS=$STATUS"
                            exit 0
                        fi
                        echo "대기 중... $i/10"
                        sleep 10
                    done
                    echo "헬스 체크 실패!"
                    exit 1
                '''
            }
        }

        stage('Deploy Blue') {
            steps {
                withCredentials([
                    string(credentialsId: 'db-host',        variable: 'DB_HOST'),
                    string(credentialsId: 'db-name',        variable: 'DB_NAME'),
                    string(credentialsId: 'db-username',    variable: 'DB_USERNAME'),
                    string(credentialsId: 'db-password',    variable: 'DB_PASSWORD'),
                    string(credentialsId: 'redis-host',     variable: 'REDIS_HOST'),
                    string(credentialsId: 'jwt-secret',     variable: 'JWT_SECRET'),
                    string(credentialsId: 'jwt-expiration', variable: 'JWT_EXPIRATION')
                ]) {
                    sshagent(['ec2-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no $BLUE_SERVER "
                                docker pull $DOCKER_HUB_ID/$IMAGE_NAME:latest &&
                                docker stop app || true &&
                                docker rm app || true &&
                                docker run -d --name app -p 8080:8080 \
                                    -e DB_HOST=$DB_HOST \
                                    -e DB_NAME=$DB_NAME \
                                    -e DB_USERNAME=$DB_USERNAME \
                                    -e DB_PASSWORD=$DB_PASSWORD \
                                    -e REDIS_HOST=$REDIS_HOST \
                                    -e JWT_SECRET=$JWT_SECRET \
                                    -e JWT_EXPIRATION=$JWT_EXPIRATION \
                                    $DOCKER_HUB_ID/$IMAGE_NAME:latest
                            "
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Blue/Green 배포 성공!'
        }
        failure {
            echo '❌ 배포 실패!'
        }
    }
}