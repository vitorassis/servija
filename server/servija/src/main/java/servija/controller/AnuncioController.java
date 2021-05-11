package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import servija.controller.reqBodies.AnuncioRequest;
import servija.controller.respBodies.Response;
import servija.helper.User;
import servija.model.Anunciante;
import servija.model.Anuncio;
import servija.model.Disponibilidade;
import servija.model.Imagem;
import servija.model.Localidade;
import servija.model.TipoServico;
import servija.repository.AnuncioRepository;
import servija.repository.DisponibilidadeRepository;
import servija.repository.ImagemRespository;
import servija.repository.LocalidadeRepository;
import servija.repository.TipoServicoRepository;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {
	
	@Autowired
	AnuncioRepository anRepository;
	@Autowired
	LocalidadeRepository locRep;
	@Autowired
	TipoServicoRepository tsRep;
	@Autowired
	ImagemRespository imRep;
	@Autowired
	DisponibilidadeRepository dsRep;
	
	@Autowired
	User authenticator;
	
	@PostMapping
	public Response<Anuncio> create(@RequestBody AnuncioRequest request){
		Anuncio anuncio = new Anuncio();
		
		Anunciante anunciante = authenticator.authAnunciante(request.token);
		if(anunciante == null)
			return new Response<Anuncio>(false, "Usuário não autenticado!", null);
		
		Localidade local = locRep.findById(request.obj.localidade).orElse(null);
		if(local == null)
			return new Response<Anuncio>(false, "Localidade inexistente!", null);
		TipoServico tpServico = tsRep.findById(request.obj.tipoServico).orElse(null);
		if(tpServico == null)
			return new Response<Anuncio>(false, "Tipo de serviço inexistente!", null);
		if(request.obj.descricao == null)
			return new Response<Anuncio>(false, "Descrição Vazia!", null);
		if(request.obj.descLonga == null)
			return new Response<Anuncio>(false, "Descrição Longa Vazia!", null);
		
		anuncio.setDescricao(request.obj.descricao);
		anuncio.setDescrLonga(request.obj.descLonga);
		anuncio.setAnunciante(anunciante);
		anuncio.setLocalidade(local);
		anuncio.setTipoServico(tpServico);
		anuncio.setValor(request.obj.valor);
		anuncio.setTempo(request.obj.tempo);
		
		for(String imagem : request.obj.imagens)
			anuncio.addImagem(new Imagem(anuncio,imagem));
		
		
		for(servija.controller.reqBodies.base.Disponibilidade disp : request.obj.disponibilidades) {
			anuncio.addDisponibilidade(
				new Disponibilidade(
					anuncio,
					disp.diaSemana,
					disp.hrIni,
					disp.hrFim
				)
			);
		}
		
		
		try {
			anRepository.save(anuncio);
			imRep.saveAll(anuncio.getImagens());
		//	return new Response<Anuncio>(true, "Anúncio criado com sucesso!", anuncio); 
			dsRep.saveAll(anuncio.getDisponibilidades());
			
			return new Response<Anuncio>(true, "Anúncio criado com sucesso!", anuncio);
		}catch(Exception e) {	
			anRepository.delete(anuncio);
			
			return new Response<Anuncio>(false, "Falha na criação do anúncio", null);
		}
		
	}
	
	
	
}
