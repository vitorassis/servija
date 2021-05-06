package servija.controller.reqBodies.base;

public abstract class iRequest<T> {
	public String token;
	public T obj;
	
	public iRequest(String token, T obj) {
		super();
		this.token = token;
		this.obj = obj;
	}

}
