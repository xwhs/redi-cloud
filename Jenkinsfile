pipeline {
    agent any

    tools {
        jdk 'jdk-8'
        gradle 'gradle-7.4.2'
    }

    stages {
        stage("Clean") {
            steps {
                sh "chmod +x ./gradlew";
                sh "./gradlew clean --stacktrace";
            }
        }
        stage("Build") {
            steps {
                sh "./gradlew projectBuild --stacktrace";
            }
        }
        stage("Create zip") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    sh "mkdir build/"
                    sh "cp -r test/node-1/storage/ build/storage/"
                    sh "cp node/node-base/build/libs/redicloud-node-base.jar build/"
                    sh "cp plugins/plugin-minecraft/build/libs/redicloud-plugin-minecraft.jar build/storage/"
                    sh "cp plugins/plugin-proxy/build/libs/redicloud-plugin-proxy.jar build/storage/"
                    sh "cd build/; zip -r redi-cloud.zip *";
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'build/redi-cloud.zip', fingerprint: true
                }
            }
        }
        stage("Publishing") {
            steps {
                sh "./gradlew publishToRepository --stacktrace";
            }
        }
        stage("Delete temp files") {
            steps {
                sh "rm -r build"
            }
        }
    }
}