package dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Calculator{
	public double add(double number1, double number2){
		return number1 + number2;
	}

	public double addAndStore(double number1, double number2, String resultsPath) throws IOException {
		
		double resultsToStore = number1 + number2;
		File output = new File(resultsPath);
		
		if (output.exists()) {
			FileWriter out = new FileWriter(output);
			out.write(number1+"+"+number2+"="+resultsToStore);
			out.close();
			
			return resultsToStore;
		}else{
			throw new FileNotFoundException(resultsPath);
		}
	}
}
