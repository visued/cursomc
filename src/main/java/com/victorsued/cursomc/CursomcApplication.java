package com.victorsued.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.victorsued.cursomc.domain.Categoria;
import com.victorsued.cursomc.domain.Cidade;
import com.victorsued.cursomc.domain.Cliente;
import com.victorsued.cursomc.domain.Endereco;
import com.victorsued.cursomc.domain.Estado;
import com.victorsued.cursomc.domain.Pagamento;
import com.victorsued.cursomc.domain.PagamentoComBoleto;
import com.victorsued.cursomc.domain.PagamentoComCartao;
import com.victorsued.cursomc.domain.Pedido;
import com.victorsued.cursomc.domain.Produto;
import com.victorsued.cursomc.domain.enums.EstadoPagamento;
import com.victorsued.cursomc.domain.enums.TipoCliente;
import com.victorsued.cursomc.repositories.CategoriaRepository;
import com.victorsued.cursomc.repositories.CidadeRepository;
import com.victorsued.cursomc.repositories.ClienteRepository;
import com.victorsued.cursomc.repositories.EnderecoRepository;
import com.victorsued.cursomc.repositories.EstadoRepository;
import com.victorsued.cursomc.repositories.PagamentoRepository;
import com.victorsued.cursomc.repositories.PedidoRepository;
import com.victorsued.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired 
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Categoria cat1 = new Categoria (null, "informática");
		Categoria cat2 = new Categoria (null, "escritório");
		
		Produto p1 = new Produto (null, "computador", 2000.00);
		Produto p2 = new Produto (null, "impressora", 800.00);
		Produto p3 = new Produto (null, "mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		categoriaRepository.save(Arrays.asList(cat1, cat2));
		produtoRepository.save(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Victor Sued", "visued@gmail.com", "1638101363", TipoCliente.PESSOFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1638101072", "1638102030"));
		
		Endereco e1 = new Endereco(null, "Rua Angelo Benedeti", "565", "Pedro Chediak", "Pedro Chediak", "14600000", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Angelo Benedeti", "565", "Pedro Chediak", "Pedro Chediak", "14600000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new  Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new  Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		
				
		
	}
	
	
}
