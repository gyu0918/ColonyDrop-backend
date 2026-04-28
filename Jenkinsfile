
//pipeline {
//    agent any
//
//    environment {
//        DOCKER_HUB_ID  = "kjk1526"
//        IMAGE_NAME     = "colonydrop"
//        BLUE_SERVER    = "ubuntu@3.38.96.248"
//        GREEN_SERVER   = "ubuntu@13.125.49.98"
//    }
//
//    stages {
//        stage('Pull') {
//            steps {
//                git branch: 'main',
//                    credentialsId: 'github-token',
//                    url: 'https://github.com/gyu0918/ColonyDrop-backend.git'
//            }
//        }
//
//        stage('Build') {
//            steps {
//                sh './gradlew clean build -x test'
//            }
//        }
//
//        stage('Docker Build & Push') {
//            steps {
//                withCredentials([usernamePassword(
//                    credentialsId: 'docker-hub',
//                    usernameVariable: 'DOCKER_USER',
//                    passwordVariable: 'DOCKER_PASS'
//                )]) {
//                    sh '''
//                        docker build -t $DOCKER_HUB_ID/$IMAGE_NAME:latest .
//                        docker login -u $DOCKER_USER -p $DOCKER_PASS
//                        docker push $DOCKER_HUB_ID/$IMAGE_NAME:latest
//                    '''
//                }
//            }
//        }
//
//        stage('Deploy Green') {
//            steps {
//                withCredentials([
//                    string(credentialsId: 'db-host',              variable: 'DB_HOST'),
//                    string(credentialsId: 'db-name',              variable: 'DB_NAME'),
//                    string(credentialsId: 'db-username',          variable: 'DB_USERNAME'),
//                    string(credentialsId: 'db-password',          variable: 'DB_PASSWORD'),
//                    string(credentialsId: 'redis-host',           variable: 'REDIS_HOST'),
//                    string(credentialsId: 'jwt-secret',           variable: 'JWT_SECRET'),
//                    string(credentialsId: 'jwt-expiration',       variable: 'JWT_EXPIRATION'),
//                    string(credentialsId: 'kakao-client-id',      variable: 'KAKAO_CLIENT_ID'),
//                    string(credentialsId: 'kakao-client-secret',  variable: 'KAKAO_CLIENT_SECRET'),
//                    string(credentialsId: 'google-client-id',     variable: 'GOOGLE_CLIENT_ID'),
//                    string(credentialsId: 'google-client-secret', variable: 'GOOGLE_CLIENT_SECRET'),
//                    string(credentialsId: 'naver-client-id',      variable: 'NAVER_CLIENT_ID'),
//                    string(credentialsId: 'naver-client-secret',  variable: 'NAVER_CLIENT_SECRET')
//                ]) {
//                    sshagent(['ec2-ssh-key']) {
//                        sh '''
//                            ssh -o StrictHostKeyChecking=no $GREEN_SERVER "
//                                docker pull $DOCKER_HUB_ID/$IMAGE_NAME:latest &&
//                                docker rm -f app || true &&
//                                docker run -d --name app -p 8080:8080 \
//                                    -e DB_HOST=$DB_HOST \
//                                    -e DB_NAME=$DB_NAME \
//                                    -e DB_USERNAME=$DB_USERNAME \
//                                    -e DB_PASSWORD=$DB_PASSWORD \
//                                    -e REDIS_HOST=$REDIS_HOST \
//                                    -e JWT_SECRET=$JWT_SECRET \
//                                    -e JWT_EXPIRATION=$JWT_EXPIRATION \
//                                    -e KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
//                                    -e KAKAO_CLIENT_SECRET=$KAKAO_CLIENT_SECRET \
//                                    -e GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID \
//                                    -e GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET \
//                                    -e NAVER_CLIENT_ID=$NAVER_CLIENT_ID \
//                                    -e NAVER_CLIENT_SECRET=$NAVER_CLIENT_SECRET \
//                                    $DOCKER_HUB_ID/$IMAGE_NAME:latest
//                            "
//                        '''
//                    }
//                }
//            }
//        }
//
//        stage('Health Check') {
//            steps {
//                sh '''
//                    echo "앱 시작 대기 중..."
//                    sleep 40
//                    for i in {1..10}; do
//                        STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://13.125.49.98:8080/)
//                        if [ $STATUS -eq 200 ] || [ $STATUS -eq 401 ]; then
//                            echo "헬스 체크 통과! STATUS=$STATUS"
//                            exit 0
//                        fi
//                        echo "대기 중... $i/10"
//                        sleep 10
//                    done
//                    echo "헬스 체크 실패!"
//                    exit 1
//                '''
//            }
//        }
//
//        stage('Deploy Blue') {
//            steps {
//                withCredentials([
//                    string(credentialsId: 'db-host',              variable: 'DB_HOST'),
//                    string(credentialsId: 'db-name',              variable: 'DB_NAME'),
//                    string(credentialsId: 'db-username',          variable: 'DB_USERNAME'),
//                    string(credentialsId: 'db-password',          variable: 'DB_PASSWORD'),
//                    string(credentialsId: 'redis-host',           variable: 'REDIS_HOST'),
//                    string(credentialsId: 'jwt-secret',           variable: 'JWT_SECRET'),
//                    string(credentialsId: 'jwt-expiration',       variable: 'JWT_EXPIRATION'),
//                    string(credentialsId: 'kakao-client-id',      variable: 'KAKAO_CLIENT_ID'),
//                    string(credentialsId: 'kakao-client-secret',  variable: 'KAKAO_CLIENT_SECRET'),
//                    string(credentialsId: 'google-client-id',     variable: 'GOOGLE_CLIENT_ID'),
//                    string(credentialsId: 'google-client-secret', variable: 'GOOGLE_CLIENT_SECRET'),
//                    string(credentialsId: 'naver-client-id',      variable: 'NAVER_CLIENT_ID'),
//                    string(credentialsId: 'naver-client-secret',  variable: 'NAVER_CLIENT_SECRET')
//                ]) {
//                    sshagent(['ec2-ssh-key']) {
//                        sh '''
//                            ssh -o StrictHostKeyChecking=no $BLUE_SERVER "
//                                docker pull $DOCKER_HUB_ID/$IMAGE_NAME:latest &&
//                                docker stop app || true &&
//                                docker rm app || true &&
//                                docker run -d --name app -p 8080:8080 \
//                                    -e DB_HOST=$DB_HOST \
//                                    -e DB_NAME=$DB_NAME \
//                                    -e DB_USERNAME=$DB_USERNAME \
//                                    -e DB_PASSWORD=$DB_PASSWORD \
//                                    -e REDIS_HOST=$REDIS_HOST \
//                                    -e JWT_SECRET=$JWT_SECRET \
//                                    -e JWT_EXPIRATION=$JWT_EXPIRATION \
//                                    -e KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
//                                    -e KAKAO_CLIENT_SECRET=$KAKAO_CLIENT_SECRET \
//                                    -e GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID \
//                                    -e GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET \
//                                    -e NAVER_CLIENT_ID=$NAVER_CLIENT_ID \
//                                    -e NAVER_CLIENT_SECRET=$NAVER_CLIENT_SECRET \
//                                    $DOCKER_HUB_ID/$IMAGE_NAME:latest
//                            "
//                        '''
//                    }
//                }
//            }
//        }
//    }
//
//    post {
//        success {
//            echo '✅ Blue/Green 배포 성공!'
//        }
//        failure {
//            echo '❌ 배포 실패!'
//        }
//    }
//}

pipeline {
    agent any

    environment {
        DOCKER_HUB_ID    = "kjk1526"
        IMAGE_NAME       = "colonydrop"
        REGION           = "ap-northeast-2"
        BLUE_TG_ARN      = "arn:aws:elasticloadbalancing:ap-northeast-2:027551668655:targetgroup/colonydrop-tg-blue/2c3b563463b43962"
        GREEN_TG_ARN     = "arn:aws:elasticloadbalancing:ap-northeast-2:027551668655:targetgroup/colonydrop-tg-green/0da47489e5afb350"
        ALB_LISTENER_ARN = "arn:aws:elasticloadbalancing:ap-northeast-2:027551668655:listener/app/colonydrop-alb/8fc40534a09d62e7/d204bec7e8c8e032"
        BLUE_ASG         = "colonydrop-autoscaling-blue"
        GREEN_ASG        = "colonydrop-autoscaling-green"
        LAUNCH_TEMPLATE  = "colonydrop-launch-template"
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

        stage('Check Active') {
            steps {
                script {
                    // 현재 ALB가 Blue로 향하는지 확인
                    def currentTg = sh(
                        script: """
                            aws elbv2 describe-rules \
                                --listener-arn $ALB_LISTENER_ARN \
                                --region $REGION \
                                --query 'Rules[?IsDefault==`true`].Actions[0].TargetGroupArn' \
                                --output text
                        """,
                        returnStdout: true
                    ).trim()

                    if (currentTg.contains("tg-blue")) {
                        env.ACTIVE = "blue"
                        env.NEXT = "green"
                        env.NEXT_ASG = GREEN_ASG
                        env.NEXT_TG_ARN = GREEN_TG_ARN
                        env.CURRENT_ASG = BLUE_ASG
                    } else {
                        env.ACTIVE = "green"
                        env.NEXT = "blue"
                        env.NEXT_ASG = BLUE_ASG
                        env.NEXT_TG_ARN = BLUE_TG_ARN
                        env.CURRENT_ASG = GREEN_ASG
                    }
                    echo "현재 운영: ${env.ACTIVE} → 배포 대상: ${env.NEXT}"
                }
            }
        }

        stage('Create Next ASG') {
            steps {
                script {
                    // 현재 운영 중인 ASG 서버 수 확인
                    def currentCount = sh(
                        script: """
                            aws autoscaling describe-auto-scaling-groups \
                                --auto-scaling-group-names ${env.CURRENT_ASG} \
                                --region $REGION \
                                --query 'AutoScalingGroups[0].DesiredCapacity' \
                                --output text
                        """,
                        returnStdout: true
                    ).trim().toInteger()

                    echo "현재 서버 수: ${currentCount}대 → ${env.NEXT} ASG ${currentCount}대 생성"

                    // 다음 ASG가 이미 존재하면 삭제
                    sh """
                        aws autoscaling delete-auto-scaling-group \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --force-delete \
                            --region $REGION || true
                        sleep 30
                    """

                    // 새 ASG 생성
                    sh """
                        aws autoscaling create-auto-scaling-group \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --launch-template LaunchTemplateName=$LAUNCH_TEMPLATE,Version='\$Latest' \
                            --min-size 1 \
                            --max-size 6 \
                            --desired-capacity ${currentCount} \
                            --target-group-arns ${env.NEXT_TG_ARN} \
                            --vpc-zone-identifier 'subnet-0b844475ca17a4a16,subnet-0ed4476c534e85a13' \
                            --health-check-type ELB \
                            --health-check-grace-period 300 \
                            --region $REGION
                    """
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    echo "${env.NEXT} 서버 헬스 체크 중..."
                    def healthy = false
                    for (int i = 0; i < 20; i++) {
                        sleep 30
                        def healthyCount = sh(
                            script: """
                                aws elbv2 describe-target-health \
                                    --target-group-arn ${env.NEXT_TG_ARN} \
                                    --region $REGION \
                                    --query 'length(TargetHealthDescriptions[?TargetHealth.State==`healthy`])' \
                                    --output text
                            """,
                            returnStdout: true
                        ).trim().toInteger()

                        echo "${i+1}/20 시도 - 헬시 서버: ${healthyCount}대"

                        if (healthyCount > 0) {
                            healthy = true
                            break
                        }
                    }

                    if (!healthy) {
                        error "헬스 체크 실패! 배포 중단."
                    }
                    echo "헬스 체크 통과!"
                }
            }
        }

        stage('Switch Traffic') {
            steps {
                script {
                    echo "트래픽 전환: ${env.ACTIVE} → ${env.NEXT}"
                    sh """
                        aws elbv2 modify-listener \
                            --listener-arn $ALB_LISTENER_ARN \
                            --default-actions Type=forward,TargetGroupArn=${env.NEXT_TG_ARN} \
                            --region $REGION
                    """
                    echo "트래픽 전환 완료!"
                }
            }
        }

        stage('Delete Current ASG') {
            steps {
                script {
                    echo "${env.ACTIVE} ASG 삭제 중..."
                    sh """
                        aws autoscaling delete-auto-scaling-group \
                            --auto-scaling-group-name ${env.CURRENT_ASG} \
                            --force-delete \
                            --region $REGION
                    """
                    echo "${env.ACTIVE} ASG 삭제 완료!"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Blue/Green 배포 성공! 현재 운영: ${env.NEXT}"
        }
        failure {
            echo "❌ 배포 실패! 트래픽은 ${env.ACTIVE}에 유지됩니다."
        }
    }
}