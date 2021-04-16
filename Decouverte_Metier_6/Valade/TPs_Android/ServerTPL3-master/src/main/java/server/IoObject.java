package server;

public class IoObject {
	

	    private String userName;
	    private String message;

	    public IoObject() {
	    }
	    
	  

	    public IoObject(String userName, String message) {
	        super();
	        this.userName = userName;
	        this.message = message;
	    }

	    public String getUserName() {
	        return userName;
	    }
	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getMessage() {
	        return message;
	    }
	    public void setMessage(String message) {
	        this.message = message;
	    }

	

}
