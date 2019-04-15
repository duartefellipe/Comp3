package test.calculator.unitario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.Calculator;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JunitDemo {
	
	private Calculator calc;

	@Before
	public void beforeEachTest() {
		calc = new Calculator();
	}
	
	@After
	public void afterEachTest() {
		calc = null;
	}
	
	@Test
	public void testAdd(){
		double result = calc.add(1,1);
		assertEquals(2, result, 0);
	}
	
	@Test
	public void testAddAndStore() throws IOException{
		double result = calc.addAndStore(1,1, "./src/test/calculator/unitario/results.txt");
		assertEquals(2, result, 0);
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testAddAndStoreFileNotFound() throws IOException{
		calc.addAndStore(1,1, "./src/test/calculator/unitario/results1.txt");
	}
}