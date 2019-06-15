package it.lucius.customers.bean;

public class ResponseBean {

	public final static String ERROR_CODE = "900";
	public final static String ERROR_DASHBOARD_DUPLICATE_MSG = "Month in Dashboard already exists";
	public final static String ERROR_DASHBOARD_NOTEXISTS_MSG = "Month not exists";
	public final static String ERROR_CATEGORY_NOTEXISTS_MSG = "Category not exists";
	public final static String ERROR_CATEGORY_EXISTS_MSG = "Category exists";
	public final static String ERROR_CREATE_MSG = "Error during saving";
	public final static String ERROR_UPDATE_MSG = "Error during updating";
	public final static String ERROR_USER_EMAIL_EXISTS_MSG = "An user with same email already exists";
	public final static String ERROR_USER_USERNAME_EXISTS_MSG = "Username already exists";
	public final static String OK_CREATE_USER_MSG = "User created successfully.";
	public final static String OK_CREATE_MSG = "Create successfully";
	public final static String OK_UPDATE_MSG = "Update successfully";
	public final static String OK_DELETE_MSG = "Delete successfully";
	public final static String OK_CONFIRM_SIGNUP_MSG = "Account confirmed successfully";
	public final static String OK_CODE = "200";
	
	private String code;
	private String message;
	
	
	public ResponseBean() {
		super();
		this.code = OK_CODE;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
