package servija.controller.reqBodies;

import servija.controller.reqBodies.base.Anunciante;
import servija.controller.reqBodies.base.iRequest;

public class AnuncianteRequest extends iRequest<Anunciante> {

	public AnuncianteRequest(String token, Anunciante obj) {
		super(token, obj);
		// TODO Auto-generated constructor stub
	}
	
	public AnuncianteRequest(Anunciante obj) {
		super(obj);
	}

}
