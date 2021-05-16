package servija.controller.reqBodies;

import servija.controller.reqBodies.base.Categoria;
import servija.controller.reqBodies.base.iRequest;

public class CategoriaRequest extends iRequest<Categoria>{

	public CategoriaRequest(String token, Categoria obj) {
		super(token, obj);
	}
	
}
