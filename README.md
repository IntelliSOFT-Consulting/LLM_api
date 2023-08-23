# LLM_API

### Pre-requisites

- JDK 11 : **linux/amd64**
- MySQL

### How to run

### Step 1: Clone the Repository

- Clone this repository into to your local environment

### Step 2:Add your API Key to application.properties file
- Follow the instructions detailed at this link https://platform.openai.com/account/api-keys

All API requests are tokenised.

### Docker

### Step 1: Update the secrets in the maven-publish.yml
- These include DOCKER_USERNAME, SSH_PRIVATE_KEY (Server Private key), etc
### Step 2: Change the db urls
- The new urls should point to the new server url
