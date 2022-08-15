package br.com.challenge.apirest.alura.unitests.mockito.asserts;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionAssert {
	
	public static void assertExceptionIsNull(Exception exception) {
		String exceptedMessage = "Não é permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(exceptedMessage));
	}

}
