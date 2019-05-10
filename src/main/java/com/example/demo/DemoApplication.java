package com.example.demo;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) throws InvalidProtocolBufferException, UnsupportedEncodingException, JAXBException {
		SpringApplication.run(DemoApplication.class, args);
//		ZmqServer zmqServer =new ZmqServer();
//		zmqServer.start();
//		ZmqClient zmqClient =new ZmqClient();
//		zmqClient.start();
	}



}
