import java.util.Scanner;
import java.io.*;

public class Engine {
	public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException {

		Game game = new Game();
 
		int newGame = 1;
		Scanner input = new Scanner(System.in);
		PrintWriter wr = new PrintWriter(new File("out"));

		BufferedOutputStream bout = new BufferedOutputStream(System.out);

		while(true){
			// daca s-a dat comanda 'quit', newGame = 0 si se iese din program
			// daca s-a dat comanda 'new', newGame = 1 si se intra inapoi in while
			if(newGame == 0){
				break;
			}
			
			while(true){
				String command = input.nextLine();

				if(command != null){
					wr.println(command);

					if(command.equals("xboard")){
						// prima comanda din input
					} 

					if(command.equals("protover 2")){
						bout.write("feature sigint=0\n".getBytes());
						bout.flush();
						//System.out.print("feature sigint=0\n");
					}

					if (command.equals("new")){
						game = new Game();
						break;
					}

					if(command.equals("quit")){
						newGame = 0;
						break;
					}

					// muta un pion pe aceeasi coloana pe care a mutat oponentul (merge doar pentru 2 mutari - dupa da crash (?) )
					if(command.length() == 4){
						if(Character.isLetter(command.charAt(0)) && Character.isLetter(command.charAt(2)) &&
							Character.isDigit(command.charAt(1)) && Character.isDigit(command.charAt(3))){

							//bout.write(String.format("move %c7%c6\n", command.charAt(0), command.charAt(0)).getBytes());
							//bout.flush();
							game.makeMove(bout);
						}
					}

			    }

			}
		}
	
	}
}