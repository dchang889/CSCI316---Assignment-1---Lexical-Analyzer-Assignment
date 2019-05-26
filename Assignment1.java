import java.io.BufferedWriter;
import java.io.FileWriter;

public class Assignment1 {

	public static void main(String[] args) {
		//String filename = args[0];
		String filename = "input.txt";

		try {
			LexicalAnalyzer a = new LexicalAnalyzer(filename);
			BufferedWriter o = new BufferedWriter(new FileWriter("output1.txt"));
			Token t;
			
			do {
				String line;
				t = a.getToken();
				line = t.toString();
				o.write(line+"\n");
			}while (t.getSym()!=Symbol.EOI);

			o.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
