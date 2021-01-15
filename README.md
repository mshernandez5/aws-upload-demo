# File Uploading Demo

A demo Spring application that allows a client to upload files and puts them in a predefined Amazon S3 bucket. This project is adapted from [a similar demo](https://github.com/mshernandez5/FileUploadingDemo) that wrote files to a local directory.

Running The Demo:
1) Create a new user for this demo with S3 permissions under the Identity and Access Management (IAM) users section.
2) Set AWS credentials as defined by [this documentation](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html#get-started-setup-credentials) using the access key and secret given during the account setup. Keep this information private and secure.
3) Modify `application.properties` to define the destination AWS bucket name. It is best to make an external `application.properties` for this rather than modifying the embedded one in the static resources directory.
4) Start the Spring application, ex. `mvn spring-boot:run`
5) In your browser, open `http://localhost:8080` or whatever address the server is configured to run on.
6) Drag files into the upload area to automatically upload them to the server. The server will automatically send received files to the defined S3 bucket.

# Important Note
By default, Spring restricts the file upload size to about 1MB.
To change this, you can modify these settings in your `application.properties` and set a new maximum.
For example, to increase the max to 512MB you can add:
```properties
spring.servlet.multipart.max-file-size = 512MB
spring.servlet.multipart.max-request-size = 512MB
```
