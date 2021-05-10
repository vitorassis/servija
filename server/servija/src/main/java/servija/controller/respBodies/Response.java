package servija.controller.respBodies;

import java.util.List;

import servija.model.Estado;

public class Response<T> {
	public Response(boolean success, String message, T obj) {
		this.success = success;
		this.message = message;
		this.obj = obj;
	}
	public Response() {	}
	public boolean success;
	public String message;
	public T obj;
}
