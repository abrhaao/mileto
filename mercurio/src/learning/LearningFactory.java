package learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonObject;


public class LearningFactory {


	/** 
	 * Método de acesso à base que retorna o caboclo autenticado
	 * @return
	 */
	public String jsonAutentica( String pLogin, String pSenha ) { 
		JsonObject value = Json.createObjectBuilder()
				.add("nomeCaboclo", "Steven BlodBound")
				.add("loginCaboclo", "116900")			     			     
				.add("cargo", "Supervisor da Tancagem")
				.add("valido", false)
				.build();
		System.out.println("I~m in server side");
		return value.toString();
	}

	public String jsonListaFrota() { 
		JsonObject value = Json.createObjectBuilder()
				.add("veiculo", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
								.add("tipo", "CARRETA CONJUGADA")
								.add("uf", "AL")
								.add("placa", "KAD7234"))
						.add(Json.createObjectBuilder()
								.add("tipo", "CARRETA CONJUGADA")
								.add("uf", "SE")
								.add("placa", "KYB9076"))
						.add(Json.createObjectBuilder()
								.add("tipo", "CAVALO MECANICO")
								.add("uf", "SE")
								.add("placa", "BDA9030"))
						.add(Json.createObjectBuilder()
								.add("tipo", "BITREM")
								.add("uf", "AL")
								.add("placa", "KER5530"))
						).build();
		return value.toString();
	}

	/*************

	public static void main(String[] args) {
		LearningFactory y = new LearningFactory();
		System.out.println(y.myJson());
	}




	public String myJson() { 
	 JsonObject value = Json.createObjectBuilder()
		     .add("firstName", "John")
		     .add("lastName", "Smith")
		     .add("age", 25)
		     .add("address", Json.createObjectBuilder()
		         .add("streetAddress", "21 2nd Street")
		         .add("city", "New York")
		         .add("state", "NY")
		         .add("postalCode", "10021"))
		     .add("phoneNumber", Json.createArrayBuilder()
		         .add(Json.createObjectBuilder()
		             .add("type", "home")
		             .add("number", "212 555-1234"))
		         .add(Json.createObjectBuilder()
		             .add("type", "fax")
		             .add("number", "646 555-4567")))
		     .build();
	 return value.toString();

}

	 **/

	
}