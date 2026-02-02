FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy everything needed to build
COPY . .

# Make gradlew executable
RUN chmod +x gradlew

# BUILD the jar file first
RUN ./gradlew clean build -x test

# Now copy the jar (it exists now!)
RUN cp build/libs/*.jar app.jar

EXPOSE 7070

ENTRYPOINT ["java", "-jar", "app.jar"]