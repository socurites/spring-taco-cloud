echo "Application Start"

cd /home/ec2-user/app

echo "Run as `whoami` at `pwd`"

echo "Starting bootJar"
nohup java -jar app.jar > app.log 2>&1 &