package projeto_final;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

import com.google.gson.*;




public class Jogo {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int menu = 0;
		
		
		
		do { // MENU PRINCIPAL
			
			System.out.println("### O �ltimo Teleportador! ###\n\n"
					+ "1 - Criar Novo Jogo\n"
					+ "2 - Carregar Jogo Com Personagens Salvos\n"
					+ "3 - Resumo da Hist�ria do Jogo\n"
					+ "4 - Tutorial\n"
					+ "5 - Cr�ditos\n"
					+ "0 - Sair\n");
			
			menu = Integer.parseInt(scan.nextLine());
			
			ArrayList<PersonagemJogavel> jogadores = new ArrayList<PersonagemJogavel>(); // Criando uma lista de Personagens
			ArrayList<Ambiente> ambientes = new ArrayList<Ambiente>(); // Criando uma lista com o 'Ambiente' para cada personagem. Aqui tamb�m acontecer� as informa��es de batalha.
			
			switch(menu) {
			case 1:
				//Cria��o de Personagens;
				System.out.println("Digite a quantidade de jogadores: ");
				int numJogadores = Integer.parseInt(scan.nextLine());
				
				for(int i = 0; i < numJogadores; i++) {
					System.out.println("Vamos criar o Personagem do Jogador "+(i+1)+ ": ");
					// Escolha de nome do Personagem
					System.out.println("Digite o nome do Personagem: ");
					String nome = scan.nextLine();
					// Escolha da classe
					System.out.println("Escolha uma Classe para seu Personagem: \n"
							+ "1 - Atirador de Elite (2 de atq; 1 de def; 3 de Muni��o M�xima; Especial: Causa 1 de Dano direto em outro Personagem)\n"
							+ "2 - Defensor (1 de atq; 2 de def; 3 de Muni��o M�xima; Especial: Recupera 2 de Vida)\n"
							+ "3 - Estrategista (1 de atq; 1 de def; 5 de Muni��o M�xima; Especial: Rouba at� 3 Muni��es de outro Personagem)\n");
					int classe = Integer.parseInt(scan.nextLine());
					
					//Instanciamento de classes
					if(classe == 1) {
						AtiradorDeElite e = new AtiradorDeElite(nome);
						jogadores.add(e);
					} else {
						if(classe == 2) {
							Defensor e = new Defensor(nome);
							jogadores.add(e);
						} else if(classe == 3) {
							Estrategista e = new Estrategista(nome);
							jogadores.add(e);
						}
					}
					
					//Cria��o do Mapa (Ambiente) do Personagem
					Ambiente a = new Ambiente(i);
					ambientes.add(a);
					ambientes.get(i).mostrarAmbiente(jogadores.get(i).getNome());
					
					
					menu = -1;
					
				}
				
				System.out.println("Voc� gostaria de Salvar as informa��es dos Personagens criados?");
				System.out.println("1 - Salvar (Se j� houver dados salvos, eles ser�o sobrescritos!!!); \n"
						+ "2 - N�o Salvar\n");
				
				int escolha = Integer.parseInt(scan.nextLine());
				
				switch(escolha) {
				case 1:
					// Salvando as informa��es dos personagens criados! (Caso j� exista um arquivo salvo ele ser� sobrescrito!)
					Gson gson = new Gson();
					
					ArrayList<String> jsonPersonagens = new ArrayList<String>();
					ArrayList<String> jsonAmbientes = new ArrayList<String>();
					
					for(int m = 0; m < jogadores.size(); m++) {
						jsonPersonagens.add(gson.toJson(jogadores.get(m)));
						jsonAmbientes.add(gson.toJson(ambientes.get(m)));
					}
					
					// Criando Arquivo e Salvando JSON ("Serializando")
					
					try {
						
						FileOutputStream arquivo = new FileOutputStream("save.json");
						PrintWriter pw = new PrintWriter(arquivo);
						pw.println(jogadores.size());
						for(int k = 0; k < jsonPersonagens.size(); k++) {
							if(k == 0) {
								pw.print(jsonPersonagens.get(k));
							} else {
								pw.print(", " + jsonPersonagens.get(k));
							}
						}
						pw.println();
						for(int j = 0; j < jsonAmbientes.size(); j++) {
							if(j == 0) {
								pw.print(jsonAmbientes.get(j));
							} else {
								pw.print(", " + jsonAmbientes.get(j));
							}
						}
						
						pw.close();
						arquivo.close();
						
					}
					catch(Exception e1) {
						System.out.println("Erro ao salvar o jogo!");
					}
					
					break;
				case 2:
					// Apenas segue para o pr�ximo passo
					break;
				default:
					System.out.println("Valor inv�lido digitado!");
				}
				
				break;
			case 2:
				//Leitura de arquivo salvo
				try {					
					
					String texto = "";
					
					Scanner in = new Scanner(new FileReader("save.json"));
					while (in.hasNextLine()) {
					    texto += in.nextLine();
					    texto += "\n";
					}
		
									
					//"Desserializando" o arquivo
					Gson gson = new Gson();
					String[] conteudoSalvo = texto.split("\n"); // Separando por cada quebra de linha
					String[] personagensSalvos = conteudoSalvo[1].split(", "); // Segunda linha representa o dado de todos os personagens criados
					String[] ambientesSalvos = conteudoSalvo[2].split(", "); // Terceira linha representa o ambiente de cada personagem criado
					
					for(int i = 0; i < Integer.parseInt(conteudoSalvo[0]); i++) {
						if(personagensSalvos[i].contains("Atirador")) {
							jogadores.add(gson.fromJson(personagensSalvos[i], AtiradorDeElite.class)); // Transformando os dados para suas respectivas classes
						} else {
							if(personagensSalvos[i].contains("Defensor")) {
								jogadores.add(gson.fromJson(personagensSalvos[i], Defensor.class));
							} else if(personagensSalvos[i].contains("Estrategista")) {
								jogadores.add(gson.fromJson(personagensSalvos[i], Estrategista.class));
							}
						}
						
						ambientes.add(gson.fromJson(ambientesSalvos[i], Ambiente.class));
					}
					
					System.out.println("\n\n##### PERSONAGENS CARREGADOS E PRONTOS PARA O JOGO! #####");
										
					menu = -1;
				}
				catch(Exception e) {
					System.out.println("N�o h� arquivo de jogo salvo!\n");
				}
				
				break;
			case 3:
				//Contar a hist�ria do jogo;
				System.out.println("\n\n########## RESUMO DA HIST�RIA ##########\n\n");
				System.out.println("No ano de 2222 foi criado um torneio chamado O �ltimo Teleportador. \nA ideia era usar a grande inven��o da humanidade, o teleportador de m�o. Essa"
						+ " arma incr�vel era capaz de teleportar objetos para outros locais instantaneamente. \n\nAssim como j� se esperava, o torneio foi um grande sucesso e continuou acontecendo anualmente."
						+ " \nA ideia consiste em ser o �ltimo sobrevivente de um jogo que mistura a��o e estrat�gia. \n\n\nAgora o torneio est� completando 10 anos e, nesta edi��o especial, voc� est� convidado a participar!\n\n"
						+ "##################################################\n\n");
				break;
			case 4:
				//Tutorial do jogo
				System.out.println("\n\n########## TUTORIAL ##########\n\n");
				System.out.println("Para um melhor entendimento do funcionamento e das regras do jogo, indicamos dar uma olhada na sess�o 3 do nosso relat�rio. L� h� uma explica��o detalhada do funcionamento do jogo!\n\n"
						+ "##################################################\n\n");
				break;
			case 5:
				//Mostrar os desenvolvedores;
				System.out.println("##### DESENVOLVEDORES ##### \n\n"
						+ "Wosley Mendes\n"
						+ "Antonio Emilio\n"
						+ "Francisco Wesley\n"
						+ "Pedro Henrique Lorenzom\n\n"
						+ "###########################\n\n");
				break;
			case 0:
				//Sair;
			default:
				System.out.println("Valor inv�lido digitado!");
			}
			
			
			if(menu == -1) { // In�cio de um novo jogo
				
				System.out.println("\n\n - NARRADOR: Ol���������, senhoras e senhores!!! \n"
						+ "Est� come�ando mais uma edi��o do �LTIMO TELEPORTADOOOOOOR!!!!!!\n"
						+ "O meu nome � Narrador e eu estarei narrando tudo pra voc�s hoje!!!!\n"
						+ "Eu sei que meu nome parece estranho e que a �ltima frase foi bem redundante, mas vamos l�!!!!\n"
						+ "Os nossos "+ jogadores.size() + " competidores j� receberam seus Teleportadores Port�teis e j� est�o em suas posi��es!!!!\n\n"
						+ "VAMOS COME�AR!!!!!!!!\n");
				
				while(jogadores.size() > 1) {
					ArrayList<Integer> ordem = new ArrayList<Integer>(); // Aqui criamos uma lista que mudar� a cada rodada. Ela representar� a ordem dos jogadores.
					for(int i = 0; i < jogadores.size(); i++) {
						ordem.add(i);
					}

					Collections.shuffle(ordem); // Altera a ordem da lista randomicamente;
					
					// Mostra ordem da rodada
					System.out.println("\n\n##### NOVA RODADA #####\n");
					System.out.print("Ordem da rodada: ");
					for(int i = 0; i < ordem.size(); i++) {
						System.out.print(jogadores.get(ordem.get(i)).getNome()+ " / ");
					}
					System.out.println();
					
					//Come�a a rodada
					for(int i = 0; i < ordem.size(); i++) {
						// aplicar efeito da rodada anterior
						// andar inimigos
						int dano = ambientes.get(ordem.get(i)).andarInimigos();
						
						if(dano > 0) {
							jogadores.get(ordem.get(i)).sofrerDano(dano);
						}
						
						if(jogadores.get(ordem.get(i)).getVidaAtual() == 0) {
							System.out.println("- NARRADOR: J� ERA!!! "+jogadores.get(ordem.get(i)).getNome() + " morreu!");
							int k = ordem.get(i);
							jogadores.remove(k);
							ordem.remove(i);
							break;
						}
						
						// mostrar mapa atual
						ambientes.get(ordem.get(i)).mostrarAmbiente(jogadores.get(ordem.get(i)).getNome());
						// mostrar op��es
						System.out.println("Turno de: " + jogadores.get(ordem.get(i)).getNome()+" (Muni��o: "+ jogadores.get(ordem.get(i)).getMunicaoAtual()+" - Vida: "+ jogadores.get(ordem.get(i)).getVidaAtual()+")");
						System.out.println("1 - Atacar (Custo: 1 Muni��o; Dano baseado no n�vel de ataque);\n"
										+  "2 - Defender (Recupera 1 de vida); \n"
										+  "3 - Recarregar Muni��o (Recupera 2 de Muni��o); \n"
										+  "4 - Especial ("+jogadores.get(ordem.get(i)).textoEspecial()+")");
						int escolha = Integer.parseInt(scan.nextLine());
						
						switch(escolha) {
						case 1: //Atacar
							System.out.println("Escolha um alvo (Digite na forma: linha coluna): ");
							String alvoCoord = scan.nextLine();
							String[] alvoCoordseparadas = alvoCoord.split(" ");
							//Atacando o alvo
							int inimigos = ambientes.get(ordem.get(i)).causarDano(Integer.parseInt(alvoCoordseparadas[0]), Integer.parseInt(alvoCoordseparadas[1]), jogadores.get(ordem.get(i)).getAtaque(), jogadores.get(ordem.get(i)).getMunicaoAtual());
							//Descontando muni��o
							jogadores.get(ordem.get(i)).atirar();
							
							//Mostrando Ambiente depois de atacar
							ambientes.get(ordem.get(i)).mostrarAmbiente(jogadores.get(ordem.get(i)).getNome());
							// Transferindo alvo para inimigo
							if(inimigos > 0) {
								if(i == ordem.size()-1) {
									//�ltimo para primeiro
									ambientes.get(ordem.get(0)).transferirInimigo(Integer.parseInt(alvoCoordseparadas[0])-1, Integer.parseInt(alvoCoordseparadas[1]), inimigos);
								} else {
									//para o pr�ximo
									ambientes.get(ordem.get(i+1)).transferirInimigo(Integer.parseInt(alvoCoordseparadas[0])-1, Integer.parseInt(alvoCoordseparadas[1]), inimigos);
								}
							}
							break;
						case 2: //Defender
							jogadores.get(ordem.get(i)).defender(1);
							break;
						case 3:
							jogadores.get(ordem.get(i)).recuperarMunicao(2);
							break;
						case 4:
							//Especial
							if(jogadores.get(ordem.get(i)).getUsouEspecial() == false) {
								if(i == ordem.size()-1) {
									//�ltimo para primeiro
									jogadores.get(ordem.get(i)).usarEspecial(jogadores.get(ordem.get(0)));
								} else {
									//para o pr�ximo
									jogadores.get(ordem.get(i)).usarEspecial(jogadores.get(ordem.get(i+1)));
								}
							} else {
								System.out.println("Nervoso, esqueceu que o Especial s� se usa uma vez!");
							}
							break;
						default:
							System.out.println("Valor inv�lido digitado!");
						}
					}
				}
				
				// Vencedor
				System.out.println("\n\n\n - NARRADOR: E o grande vencedor � ##### " + jogadores.get(0).getNome()+", o " + jogadores.get(0).getNomeClasse() + "!!! #####\n\n\n");
			}
			
			
		} while(menu != 0);
		scan.close();
	}
}
