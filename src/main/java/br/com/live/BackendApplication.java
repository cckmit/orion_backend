package br.com.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = { "br.com.live.administrativo.entity", "br.com.live.comercial.entity",
		"br.com.live.producao.entity", "br.com.live.produto.entity", "br.com.live.sistema.entity",
		"br.com.live.util.entity" })
@EnableJpaRepositories(basePackages = { "br.com.live.administrativo.repository", "br.com.live.comercial.repository",
		"br.com.live.producao.repository", "br.com.live.produto.repository", "br.com.live.sistema.repository",
		"br.com.live.util.repository" })
@ComponentScan(basePackages = { "br.com.live.jobs", "br.com.live.administrativo.controller",
		"br.com.live.administrativo.service", "br.com.live.administrativo.custom",
		"br.com.live.comercial.controller", "br.com.live.comercial.service", "br.com.live.comercial.custom",
		"br.com.live.producao.controller", "br.com.live.producao.service", "br.com.live.producao.custom",
		"br.com.live.produto.controller", "br.com.live.produto.service", "br.com.live.produto.custom",
		"br.com.live.sistema.controller", "br.com.live.sistema.service", "br.com.live.sistema.custom",
		"br.com.live.util.controller", "br.com.live.util.service", "br.com.live.util.custom",
		"br.com.live.integracao.cigam.custom", "br.com.live.integracao.cigam.service"})
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}