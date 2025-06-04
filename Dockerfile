# Use Maven with OpenJDK 11 as the base image to duild the docker image
FROM maven:3.6.3-openjdk-11

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml first to leverage dependency caching
COPY pom.xml .

# Download project dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Package the application skipping tests
RUN mvn clean package -DskipTests=true -B

# Set the default command to run tests
CMD ["mvn", "test"]