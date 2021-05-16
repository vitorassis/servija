package servija.controller.reqBodies;

import servija.controller.reqBodies.base.Anuncio;
import servija.controller.reqBodies.base.iRequest;

public class AnuncioRequest extends iRequest<Anuncio> {

	public AnuncioRequest(String token, Anuncio obj) {
		super(token, obj);
	}

}
