package com.dynamicform.app.config;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mohammad Lockman
 *
 */

@Getter
@Setter
public class CustomHttpServletRequest implements Serializable{

	private static final long serialVersionUID = 2410847241220452549L;

	private final String remoteAddress;
	private final String clientIpAddress;
	private final String macAddress;
    private final  String os;
	private  String browser;
	private  String hostName;
	
	
	private static final String[] HEADERS_LIST = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" 
		};

	private String findClientIpAddress(HttpServletRequest request) {
		
	    for (String header : HEADERS_LIST) {
	    	
	        String ip = request.getHeader(header);
	        
	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
	        	System.out.println("<<< ip >>> ");
	            return ip;
	        }
	        
	    }

	    return request.getRemoteAddr();
	}
	
	private String getClientIp(String ipAddress) {
		
		
	    if (ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
	        InetAddress inetAddress;
			try {
				inetAddress = InetAddress.getLocalHost();
				ipAddress = inetAddress.getHostAddress();
				
				this.hostName = inetAddress.getHostName();
				
				 System.out.println(" client ip"+inetAddress);  
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
	    return ipAddress;
	}
	
	
private InetAddress getLocalhost(String ipAddress) {
		
	   InetAddress inetAddress = null;
	    if (ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
	      
			try {
				inetAddress = InetAddress.getLocalHost();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
	    return inetAddress;
	}
	
	
	private String getClientMACaddresses(String ipAddress) {
		
		StringBuilder sb = new StringBuilder("");
		NetworkInterface network = null;
		try {
			
			InetAddress ip = getLocalhost(ipAddress);
			if( ip != null) {
				
				network = NetworkInterface.getByInetAddress(ip);
				
				if(network !=null) {
					byte[] hardwareAddress = network.getHardwareAddress();
					 
					if (hardwareAddress != null) {
							for (int i = 0; i < hardwareAddress.length; i++) {
								sb.append(String.format("%02X%s", hardwareAddress[i], (i < hardwareAddress.length - 1) ? "-" : ""));
							} 
					 }
				}
		
			}
		
		
			
			//System.out.println(sb.toString());
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	    return sb.toString();
	}
	
	
	public CustomHttpServletRequest(HttpServletRequest request) {

	    String ipAddress = getClientIp(findClientIpAddress(request));  
	    
	    this.macAddress = getClientMACaddresses(findClientIpAddress(request));
	  //  this.macAddress = "";//getClientMACaddresses(ipAddress);
	   
	    System.out.println("Client ip"+ipAddress);
	    System.out.println("Client Mac Address "+macAddress);
		
		this.clientIpAddress = ipAddress;
		this.remoteAddress      = request.getRemoteAddr();
		String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();


//        Client client = 	
   
        //================= OS =======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             this.os = "Windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
        	 this.os = "Mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
        	 this.os = "Unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
        	 this.os = "Android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
        	 this.os = "IPhone";
         }else{
        	 this.os = "UnKnown, More-Info: "+userAgent;
         }
         //===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            this.browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
            	this.browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        else if(user.contains("opr"))
            	this.browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
        	this.browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
        	this.browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else
        {
        	this.browser = "UnKnown, More-Info: "+userAgent;
        }
	}
	
	private String getDeviceDetails(String userAgent) {
	   
		String deviceDetails = "UNKNOWN";
	    
//	    Client client = parser.parse(userAgent);
//	    if (Objects.nonNull(client)) {
//	        deviceDetails = client.userAgent.family
//	          + " " + client.userAgent.major + "." 
//	          + client.userAgent.minor + " - "
//	          + client.os.family + " " + client.os.major
//	          + "." + client.os.minor; 
//	    }
	    return deviceDetails;
	}
}
