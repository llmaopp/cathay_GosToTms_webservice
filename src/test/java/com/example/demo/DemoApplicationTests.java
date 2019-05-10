package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		//		protoc.exe -I=proto的输入目录 --java_out=java类输出目录 proto的输入目录包括包括proto文件
		String strCmd = "C:\\Users\\cathay\\Downloads\\bin\\protoc.exe " +
				" -I=E:\\workspace\\cathay-webService\\src\\main\\java\\com\\example\\demo\\proto" +
				" --java_out=E:\\workspace\\cathay-webService\\src\\main\\java\\com\\example\\demo\\protoModel " +
				"E:\\workspace\\cathay-webService\\src\\main\\java\\com\\example\\demo\\proto\\*.proto";
		Runtime.getRuntime().exec(strCmd);
	}

}
