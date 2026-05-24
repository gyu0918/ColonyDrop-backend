pipeline {
    agent any

    environment {
        DOCKER_HUB_ID    = "kjk1526"
        IMAGE_NAME       = "colonydrop"
        REGION           = "ap-northeast-2"
        BLUE_TG_ARN      = "arn:aws:elasticloadbalancing:ap-northeast-2:706244675730:targetgroup/colonydrop-tg-blue/444aadce48a892d3"
        GREEN_TG_ARN     = "arn:aws:elasticloadbalancing:ap-northeast-2:706244675730:targetgroup/colonydrop-tg-green/dc87fbbded49933a"
        ALB_LISTENER_ARN = "arn:aws:elasticloadbalancing:ap-northeast-2:706244675730:listener/app/colonydrop-alb/62dd02ed5cd795fe/df6b062911d4ad2b"
        BLUE_ASG         = "colonydrop-autoscaling-blue"
        GREEN_ASG        = "colonydrop-autoscaling-green"
        LAUNCH_TEMPLATE  = "colonydrop-launch-template"
    }

    stages {
        stage('Pull') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-credentials',
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
                    credentialsId: 'dockerhub-credentials',
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

                    echo "현재 서버 수: ${currentCount}대 → ${env.NEXT} ASG 생성"

                    sh """
                        aws autoscaling delete-auto-scaling-group \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --force-delete \
                            --region $REGION || true
                        for i in \$(seq 1 40); do
                            EXISTS=\$(aws autoscaling describe-auto-scaling-groups \
                                --auto-scaling-group-names ${env.NEXT_ASG} \
                                --region $REGION \
                                --query 'length(AutoScalingGroups)' \
                                --output text)
                            if [ "\$EXISTS" = "0" ]; then
                                echo "ASG 삭제 완료!"
                                break
                            fi
                            echo "ASG 삭제 대기 중... \$i/40"
                            sleep 15
                        done
                    """

                    sh """
                        aws autoscaling create-auto-scaling-group \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --launch-template LaunchTemplateName=$LAUNCH_TEMPLATE,Version='\$Latest' \
                            --min-size 2 \
                            --max-size 3 \
                            --desired-capacity 2 \
                            --target-group-arns ${env.NEXT_TG_ARN} \
                            --vpc-zone-identifier 'subnet-0ed522794421a00dd,subnet-097daed735cb731f6' \
                            --health-check-type EC2 \
                            --health-check-grace-period 120 \
                            --region $REGION
                    """

                    sh """
                        aws autoscaling put-scaling-policy \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --policy-name TargetTrackingPolicy \
                            --policy-type TargetTrackingScaling \
                            --target-tracking-configuration '{"PredefinedMetricSpecification":{"PredefinedMetricType":"ASGAverageCPUUtilization"},"TargetValue":70.0}' \
                            --estimated-instance-warmup 120 \
                            --region $REGION
                    """
                    sh """
                        aws autoscaling put-scaling-policy \
                            --auto-scaling-group-name ${env.NEXT_ASG} \
                            --policy-name ScaleInPolicy \
                            --policy-type SimpleScaling \
                            --adjustment-type ChangeInCapacity \
                            --scaling-adjustment -1 \
                            --cooldown 600 \
                            --region $REGION
                    """
                    echo "Auto Scaling 정책 추가 완료!"
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    echo "${env.NEXT} 서버 헬스 체크 중..."
                    sleep 90

                    def allHealthy = false
                    for (int i = 0; i < 20; i++) {
                        try {
                            // ← 여기만 바뀜: 백틱 필터 제거, 첫 번째 인스턴스 ID 바로 조회
                            def instanceId = sh(
                                script: """
                                    aws autoscaling describe-auto-scaling-groups \
                                        --auto-scaling-group-names ${env.NEXT_ASG} \
                                        --region $REGION \
                                        --query 'AutoScalingGroups[0].Instances[0].InstanceId' \
                                        --output text
                                """,
                                returnStdout: true
                            ).trim()

                            if (!instanceId || instanceId == 'None') {
                                echo "${i+1}/20 시도 - 인스턴스 아직 없음"
                                sleep 30
                                continue
                            }

                            def privateIp = sh(
                                script: """
                                    aws ec2 describe-instances \
                                        --instance-ids ${instanceId} \
                                        --region $REGION \
                                        --query 'Reservations[0].Instances[0].PrivateIpAddress' \
                                        --output text
                                """,
                                returnStdout: true
                            ).trim()

                            def httpCode = sh(
                                script: "curl -s -o /dev/null -w '%{http_code}' http://${privateIp}:8080/actuator/health --max-time 5",
                                returnStdout: true
                            ).trim()

                            echo "${i+1}/20 시도 - 응답 코드: ${httpCode} (IP: ${privateIp})"

                            if (httpCode == '200') {
                                allHealthy = true
                                echo "헬스 체크 통과!"
                                break
                            }
                        } catch (Exception e) {
                            echo "${i+1}/20 시도 - 오류: ${e.message}"
                        }
                        sleep 30
                    }

                    if (!allHealthy) {
                        error "헬스 체크 실패! 배포 중단."
                    }
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
                    echo "트래픽 전환 완료! 드레이닝 대기 중... (30초)"
                    sleep 30
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