package test;

import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

import main.EchoGetHandler;

public class TestMain {
	
	@Test
	  public void test_URL() throws Exception {
		  HttpServer server =
				  HttpServer.create(new InetSocketAddress("127.0.0.1", 5001), 0);
				 server.createContext("/", new EchoGetHandler()); ThreadPoolExecutor
				 threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
				 server.setExecutor(threadPoolExecutor);
				 System.out.println("SERVER RUNNING");
				 server.start();
	 URL u = new URL("http://127.0.0.1:5001/?x=10&y=5");
	 try {
		 BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
		 String input = u.getQuery();
		 ArrayList<String> out = new ArrayList<String>();
		 while ((input = in.readLine()) != null)
		 {	
			 in.toString();
			 out.add(input);
			 System.out.println(input);
		 }
		Assert.assertEquals("correct x param", out.get(0), "x = 10");
		Assert.assertEquals("correct y param", out.get(1), "y = 5");
		Assert.assertEquals("correct answer", out.get(2), "answer: 100000");
	 } catch(Exception e)
	 {
		 e.printStackTrace();
		 throw e;
	 }
	 server.stop(0);
	  }

	 @Test
	 public void test_no_params() throws Exception {	
		 HttpServer server =
				  HttpServer.create(new InetSocketAddress("127.0.0.1", 80), 0);
				 server.createContext("/", new EchoGetHandler()); ThreadPoolExecutor
				 threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
				 server.setExecutor(threadPoolExecutor);
				 System.out.println("SERVER RUNNING");
				 server.start();
				 int code =0;

				 URL u = new URL("http://127.0.0.1:80/");
				 try {
				 HttpURLConnection connection = (HttpURLConnection)u.openConnection();
				 connection.setRequestMethod("GET");
				 
					 connection.connect();
				 
				 code = connection.getResponseCode();
				 
				 connection.disconnect();
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
					 throw e;
				 }

				 Assert.assertEquals(400, code);
				 System.out.println(code);
				 server.stop(0);
				 }
	 @Test
	 public void test_x_incorrect_params() throws Exception {	
		 HttpServer server =
				  HttpServer.create(new InetSocketAddress("127.0.0.1", 80), 0);
				 server.createContext("/", new EchoGetHandler()); ThreadPoolExecutor
				 threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
				 server.setExecutor(threadPoolExecutor);
				 System.out.println("SERVER RUNNING");
				 server.start();
				 int code =0;

				 URL u = new URL("http://127.0.0.1:80/?x=h");
				 try {
				 HttpURLConnection connection = (HttpURLConnection)u.openConnection();
				 connection.setRequestMethod("GET");
				 
					 connection.connect();
				 
				 code = connection.getResponseCode();
				 
				 connection.disconnect();
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
					 throw e;
				 }

				 Assert.assertEquals(400, code);
				 System.out.println(code);
				 server.stop(0);
				 }
	 
	 @Test
	 public void test_y_incorrect_params() throws Exception {	
		 HttpServer server =
				  HttpServer.create(new InetSocketAddress("127.0.0.1", 80), 0);
				 server.createContext("/", new EchoGetHandler()); ThreadPoolExecutor
				 threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
				 server.setExecutor(threadPoolExecutor);
				 System.out.println("SERVER RUNNING");
				 server.start();
				 int code =0;

				 URL u = new URL("http://127.0.0.1:80/?x=5&y=j");
				 try {
				 HttpURLConnection connection = (HttpURLConnection)u.openConnection();
				 connection.setRequestMethod("GET");
				 
					 connection.connect();
				 
				 code = connection.getResponseCode();
				 
				 connection.disconnect();
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
					 throw e;
				 }

				 Assert.assertEquals(400, code);
				 System.out.println(code);
				 server.stop(0);
				 }
	 
	 

	@Test
	public void testexpCorrect() throws Exception {
		double answer = EchoGetHandler.exponentCalc(2, 2);
		Assert.assertEquals("correct answer 4", answer, 4.0, 0.0);
	}
	
	


}
