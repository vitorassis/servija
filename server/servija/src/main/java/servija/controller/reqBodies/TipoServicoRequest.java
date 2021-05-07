package servija.controller.reqBodies;

import servija.controller.reqBodies.base.iRequest;
import servija.controller.reqBodies.base.TipoServico;

public class TipoServicoRequest extends iRequest<TipoServico> {

	public TipoServicoRequest(String token, TipoServico obj) {
		super(token, obj);
	}

}
