package main;
//https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EchoGetHandler implements HttpHandler {
	@Override
        public void handle(HttpExchange he) throws IOException {
                // parse request
                Map<String, Object> parameters = new HashMap<String, Object>();
                URI requestedUri = he.getRequestURI();
                OutputStream os = he.getResponseBody();
                int status = 200;
                int x = 0;
                int y = 0;
                String error = "";
                if (requestedUri.getRawQuery() == null) {
                	error ="No arguments recieved";
                	status = 400;
                	he.sendResponseHeaders(status, error.length());
                	os.write(error.toString().getBytes());
                	os.close();
                }
                String query = requestedUri.getRawQuery();
                parseQuery(query, parameters);
                
                // send response
                String response = "";
                for (String key : parameters.keySet()) {
                         response += key + " = " + parameters.get(key) + "\n";
                
                if (parameters.get("x")==null || parameters.get("y")==null) {
                	error ="Parameters are not valid";
            		status = 400;
            		he.sendResponseHeaders(status, error.length());
            		os.write(error.toString().getBytes());
            		os.close();
                }
     
                try{
                	x = Integer.valueOf((String) parameters.get("x"));
                	y = Integer.valueOf((String) parameters.get("y"));
                }
                catch (IllegalArgumentException e) {
                	error =  "Invalid parameters for calculation: params are not valid integers";
                	status = 400;
            		he.sendResponseHeaders(status, error.length());
            		os.write(error.toString().getBytes());
            		os.close();
            		throw new IllegalArgumentException("params not integers");
                }
                }
            
                int answer = exponentCalc(x, y);
                response += "answer: " + answer;
                //org.json.JSONObject obj = new org.json.JSONObject();
                //obj.put("x ", x);
                //obj.put("y ", y);
                //obj.put("calculation: ", "exponent");
                //obj.put("answer ", x);
                he.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                he.getResponseHeaders().add("Content-Type", "application/json");
                he.sendResponseHeaders(status, response.length());
                os.write(response.toString().getBytes());
                os.close();
        }
		
	public static int exponentCalc(int x, int y) {
		return (int) Math.pow(x, y);
	}
	//got from stackoverflow or similar can't find exact link - similar https://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection
	public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

	         if (query != null) {
	                 String pairs[] = query.split("[&]");
	                 for (String pair : pairs) {
	                          String param[] = pair.split("[=]");
	                          String key = null;
	                          String value = null;
	                          if (param.length > 0) {
	                          key = URLDecoder.decode(param[0], 
	                          	System.getProperty("file.encoding"));
	                          }

	                          if (param.length > 1) {
	                                   value = URLDecoder.decode(param[1], 
	                                   System.getProperty("file.encoding"));
	                          }

	                          if (parameters.containsKey(key)) {
	                                   Object obj = parameters.get(key);
	                                   if (obj instanceof List<?>) {
												@SuppressWarnings("unchecked")
												List<String> values = (List<String>) obj;
	                                            values.add(value);

	                                   } else if (obj instanceof String) {
	                                            List<String> values = new ArrayList<String>();
	                                            values.add((String) obj);
	                                            values.add(value);
	                                            parameters.put(key, values);
	                                   }
	                          } else {
	                                   parameters.put(key, value);
	                          }
	                 }
	         }
	}

		}