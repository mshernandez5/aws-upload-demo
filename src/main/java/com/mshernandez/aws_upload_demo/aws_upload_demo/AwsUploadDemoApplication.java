package com.mshernandez.aws_upload_demo.aws_upload_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AwsUploadDemoApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(AwsUploadDemoApplication.class, args);
	}

}