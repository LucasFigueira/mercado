package mercado;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hepta.mercado.entity.Fabricante;
import com.hepta.mercado.entity.Produto;

public class MercadoServiceTest {

	private static WebTarget service;
	private static final String URL_LOCAL = "http://localhost:8080/mercado/rs/produtos";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		service = client.target( UriBuilder.fromUri(URL_LOCAL).build() );
	}

	@Test
	void testListaTodosProdutos() {
		Response response = service.request(MediaType.APPLICATION_JSON).get();
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
	}
	
	@Test
	void testDeletarProduto() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		service = client.target( UriBuilder.fromUri(URL_LOCAL+"/100").build() ); 
		Response response = service.request(MediaType.APPLICATION_JSON).header("Content-type", "application/json").delete();
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	 @Test
	 public void testUpdate() {
	    ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		service = client.target( UriBuilder.fromUri(URL_LOCAL+"/100").build() ); 
		 
		Produto produto = createProduto();
		Response response = service.request().put(Entity.entity(produto, MediaType.APPLICATION_JSON));
		assertTrue(Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatusInfo().getStatusCode());
		System.out.println(response.getStatus());
	 }
	 
	 @Test
	 public void testCreate() { 	 
	  Produto produto = createProduto();
	  Response response = service.request().post(Entity.entity(produto, MediaType.APPLICATION_JSON));
	  System.out.println(response.getStatus());
	  assertEquals("OK", 201, response.getStatus());
	 }

	 private Produto createProduto() {
		Produto produto = new Produto();
		Fabricante fabricante = new Fabricante();
		
		fabricante.setNome("Chavex");
		
		produto.setVolume(5.2);
		produto.setNome("Chave");
		produto.setEstoque(1);
		produto.setUnidade("1");
		produto.setFabricante(fabricante);
		
		return produto;
	 } 
}