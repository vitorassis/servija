package servija.controller.reqBodies;

import servija.controller.reqBodies.base.Local;
import servija.controller.reqBodies.base.iRequest;

public class LocalRequest extends iRequest<Local> {

	public LocalRequest(String token, Local obj) {
		super(token, obj);
	}
	
	
}
