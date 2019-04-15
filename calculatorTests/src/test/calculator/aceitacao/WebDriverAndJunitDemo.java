package test.calculator.aceitacao;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Fellipe
 * 
 * automatizando teste de aceitacao usando selenium webdriver
 * 
 *  Calculadora online:
 *  testando opera��es de soma e divisao
 *  
 *  <(value1, value2, operacao), resposta>
 *  CT01: <(1,1, +), 2>
 *  CT02: <(13,7.5, +), 20.5>
 *  CT03: <(-3,1, +), -2>
 *  CT04: <(1,2, /), 0.5>
 *  CT04: <(10,0, /), Infinity>
 * 
 */

public class WebDriverAndJunitDemo {

	private WebDriverDemo cd;
	private WebDriver driver;

	@BeforeClass
	public static void setUp() {
//		System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", "./lib/geckodriver-v0.24.0-linux64/geckodriver");
	}
	
	
	@Before
	public void beforeEachTestCase() {
		cd = new WebDriverDemo();
		driver = new FirefoxDriver();
	}
	
	@After
	public void afterEachTestCase() {
		cd = null;
		driver.close();
	}
	
	@Test
	public void testeCase01() throws Exception {
		double result = cd.sum(driver, 1.0, 1.0);
		assertEquals(2, result, 0);
	};

	@Test
	public void testeCase02() throws Exception {
		double result = cd.sum(driver, 13, 7.5);
		assertEquals(20.5, result, 0);
	};
	
	@Test
	public void testeCase03() throws Exception {
		double result = cd.sum(driver, -3.0, 1.0);
		assertEquals(-2.0, result, 0);
	};

	@Test
	public void testeCase04() throws Exception {
		double result = cd.division(driver, 1.0, 2.0);
		assertEquals(0.5, result, Double.POSITIVE_INFINITY);
	};
	
	@Test
	public void testeCase05() throws Exception {
		double result = cd.division(driver, 10.0, 0);
		assertEquals(Double.POSITIVE_INFINITY, result, 0);
	};
}