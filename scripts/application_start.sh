echo "Application Start"
echo "Run as `whoami` at `pwd`"

echo "Starting bootJar"
nohup java -jar app.jar > app.log 2>&1 &