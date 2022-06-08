package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.EmpresaCustom;
import br.com.live.custom.SuprimentoCustom;
import br.com.live.model.CentroCusto;
import br.com.live.model.DivisaoProducao;
import br.com.live.model.Empresa;

@RestController
@CrossOrigin
@RequestMapping("/suprimentos")
public class SuprimentoController {

	private SuprimentoCustom suprimentoCustom;	
	private EmpresaCustom empresaCustom;
	
	@Autowired
    public SuprimentoController (SuprimentoCustom suprimentoCustom, EmpresaCustom empresaCustom) {
		this.suprimentoCustom = suprimentoCustom;		
		this.empresaCustom = empresaCustom;
	}

	@RequestMapping(value = "empresas", method = RequestMethod.GET)	
	public List<Empresa> findEmpresas() {
		return empresaCustom.findEmpresasAtivas();
	}
	
	@RequestMapping(value = "centros-custos", method = RequestMethod.GET)	
	public List<CentroCusto> findCentroCusto() {
		return suprimentoCustom.findCentroCusto();
	}
	
	@RequestMapping(value = "divisao-producao", method = RequestMethod.GET)	
	public List<DivisaoProducao> findDivisaoProducao() {
		return suprimentoCustom.findDivisaoProducao();
	}	
}
