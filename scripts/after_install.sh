echo "After Install"
echo "Run as `whoami` at `pwd`"

echo "Build bootJar"
./gradlew bootJar -x test

echo "Move bootJar to `pwd`"
cp build/libs/*.jar app.jar