package main;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.sun.net.httpserver.HttpServer;

//https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server

public class Main {
	
	public static void main(String[] args) throws Exception{
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 80), 0);
		server.createContext("/", new EchoGetHandler());
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
		server.setExecutor(threadPoolExecutor);
	    server.start();
	    }

}
