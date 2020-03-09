import java.util.Scanner;
import java.io.*;

public class Engine {
	public static void main(String args[]) throws InterruptedException, IOException {

		Game game = new Game();
 
		int newGame = 1;
		Scanner input = new Scanner(System.in);

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

					if(command.equals("xboard")){
						// prima comanda din input
					} 

					if(command.equals("protover 2")){
						bout.write("feature sigint=0\n".getBytes());
						bout.flush();
					}

					if (command.equals("new")){
						game = new Game();
						break;
					}

					if(command.equals("quit")){
						newGame = 0;
						break;
					}

					if(command.length() == 4){
						if(Character.isLetter(command.charAt(0))
							&& Character.isLetter(command.charAt(2))
							&& Character.isDigit(command.charAt(1))
							&& Character.isDigit(command.charAt(3))){
							game.makeMove(bout);
						}
					}

			    }

			}
		}
	
	}
}