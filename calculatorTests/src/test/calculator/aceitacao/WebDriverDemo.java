package test.calculator.aceitacao;
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

public class WebDriverDemo {

	public double sum(WebDriver driver, double value1, double value2) throws Exception{
		driver.get("http://www.calculadoraonline.com.br/basica");

		//Maximize the browser
		driver.manage().window().maximize();

		driver.findElement(By.id("TIExp")).sendKeys(""+value1);

		// Get the Result Text based on its xpath
		String result = driver.findElement(By.id("TIExp")).getAttribute("value");
		System.out.println("antes do + "+result);

		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div/section/div[2]/article/div/div[6]/div[1]/div[2]/table/tbody/tr[1]/td[4]")).click();
		result = driver.findElement(By.id("TIExp")).getAttribute("value");
		System.out.println("depois do + "+result);
		      
		driver.findElement(By.id("TIExp")).sendKeys(""+value2);
		      
		// Click Calculate Button
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div/section/div[2]/article/div/div[6]/div[1]/div[2]/table/tbody/tr[4]/td[3]")).click();
		
		      
		// Get the Result Text based on its xpath
		result = driver.findElement(By.id("TIExp")).getAttribute("value");
		      
		// Print a Log In message to the screen
		System.out.println(" The Result is " + result);
		return Double.parseDouble(result);
	}
	
	public double division(WebDriver driver, double value1, double value2) throws Exception{
		driver.get("http://www.calculadoraonline.com.br/basica");

		//Maximize the browser
		driver.manage().window().maximize();

		driver.findElement(By.id("TIExp")).sendKeys(""+value1);

		// Get the Result Text based on its xpath
		String result = driver.findElement(By.id("TIExp")).getAttribute("value");
		System.out.println("antes do / "+result);

		driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/section/div[2]/article/div/div[6]/div[1]/div[2]/table/tbody/tr[4]/td[4]")).click();
		result = driver.findElement(By.id("TIExp")).getAttribute("value");
		System.out.println("depois do / "+result);
		      
		driver.findElement(By.id("TIExp")).sendKeys(""+value2);
		      
		// Click Calculate Button
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div/section/div[2]/article/div/div[6]/div[1]/div[2]/table/tbody/tr[4]/td[3]")).click();
		
		      
		// Get the Result Text based on its xpath
		result = driver.findElement(By.id("TIExp")).getAttribute("value");
		      
		// Print a Log In message to the screen
		System.out.println(" The Result is " + result);
		return Double.parseDouble(result);
	}
	
	private String executaCasoTesteSoma(Double value1, double d, Double f) {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver();
		//Puts an Implicit wait, Will wait for 10 seconds before throwing exception
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		/**Launch website
		 * driver.navigate().to("http://www.calculadoraonline.com.br/basica");
		 * or 
		 * driver.get("http://www.calculadoraonline.com.br/basica");
		 */
		String msg = "";
		try {
			double sumResult = sum(driver,value1, d);
			
			if (sumResult == f) {
				msg = "("+value1+","+d+") = "+ sumResult+ " --> OK!";
			}else {
				msg = "("+value1+","+d+") = "+ sumResult+"!="+f+ " --> FAIL!";
			}
			 

		} catch (Exception e) {
			msg = "excecao em ("+value1+","+d+"):"+e;
		}finally {
			//Close the Browser.
			driver.close();
		}
		
		return msg;

	}
	
	private String executaCasoTesteDivisao(Double value1, double d, Double f) {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver();
		//Puts an Implicit wait, Will wait for 10 seconds before throwing exception
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		/**Launch website
		 * driver.navigate().to("http://www.calculadoraonline.com.br/basica");
		 * or 
		 * driver.get("http://www.calculadoraonline.com.br/basica");
		 */
		String msg = "";
		try {
			double sumResult = division(driver,value1, d);
			
			if (sumResult == f) {
				msg = "("+value1+","+d+") = "+ sumResult+ " --> OK!";
			}else {
				msg = "("+value1+","+d+") = "+ sumResult+"!="+f+ " --> FAIL!";
			}
			 

		} catch (Exception e) {
			msg = "excecao em ("+value1+","+d+"):"+e;
		}finally {
			//Close the Browser.
			driver.close();
		}
		
		return msg;

	}	
	
	
	
	public static void main(String[] args) {
		
//		System.setProperty("webdriver.gecko.driver", "./lib/geckodriver-v0.24.0-win64/geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", "./lib/geckodriver-v0.24.0-linux64/geckodriver");
		
		WebDriverDemo cd = new WebDriverDemo();
		System.out.println(cd.executaCasoTesteSoma(1.0,1.0,2.0));
		System.out.println(cd.executaCasoTesteSoma(13.0,7.5,20.5));
		System.out.println(cd.executaCasoTesteSoma(-3.0,1.0,-2.0));
		System.out.println(cd.executaCasoTesteDivisao(2.0,2.0,1.0));
		System.out.println(cd.executaCasoTesteDivisao(1.0,2.0,0.5));
	}
}