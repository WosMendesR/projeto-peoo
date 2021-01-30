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
			
			System.out.println("### O Último Teleportador! ###\n\n"
					+ "1 - Criar Novo Jogo\n"
					+ "2 - Carregar Jogo Com Personagens Salvos\n"
					+ "3 - Resumo da História do Jogo\n"
					+ "4 - Tutorial\n"
					+ "5 - Créditos\n"
					+ "0 - Sair\n");
			
			menu = Integer.parseInt(scan.nextLine());
			
			ArrayList<PersonagemJogavel> jogadores = new ArrayList<PersonagemJogavel>(); // Criando uma lista de Personagens
			ArrayList<Ambiente> ambientes = new ArrayList<Ambiente>(); // Criando uma lista com o 'Ambiente' para cada personagem. Aqui também acontecerá as informações de batalha.
			
			switch(menu) {
			case 1:
				//Criação de Personagens;
				System.out.println("Digite a quantidade de jogadores: ");
				int numJogadores = Integer.parseInt(scan.nextLine());
				
				for(int i = 0; i < numJogadores; i++) {
					System.out.println("Vamos criar o Personagem do Jogador "+(i+1)+ ": ");
					// Escolha de nome do Personagem
					System.out.println("Digite o nome do Personagem: ");
					String nome = scan.nextLine();
					// Escolha da classe
					System.out.println("Escolha uma Classe para seu Personagem: \n"
							+ "1 - Atirador de Elite (2 de atq; 1 de def; 3 de Munição Máxima; Especial: Causa 1 de Dano direto em outro Personagem)\n"
							+ "2 - Defensor (1 de atq; 2 de def; 3 de Munição Máxima; Especial: Recupera 2 de Vida)\n"
							+ "3 - Estrategista (1 de atq; 1 de def; 5 de Munição Máxima; Especial: Rouba até 3 Munições de outro Personagem)\n");
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
					
					//Criação do Mapa (Ambiente) do Personagem
					Ambiente a = new Ambiente(i);
					ambientes.add(a);
					ambientes.get(i).mostrarAmbiente(jogadores.get(i).getNome());
					
					
					menu = -1;
					
				}
				
				System.out.println("Você gostaria de Salvar as informações dos Personagens criados?");
				System.out.println("1 - Salvar (Se já houver dados salvos, eles serão sobrescritos!!!); \n"
						+ "2 - Não Salvar\n");
				
				int escolha = Integer.parseInt(scan.nextLine());
				
				switch(escolha) {
				case 1:
					// Salvando as informações dos personagens criados! (Caso já exista um arquivo salvo ele será sobrescrito!)
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
					// Apenas segue para o próximo passo
					break;
				default:
					System.out.println("Valor inválido digitado!");
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
					System.out.println("Não há arquivo de jogo salvo!\n");
				}
				
				break;
			case 3:
				//Contar a história do jogo;
				System.out.println("\n\n########## RESUMO DA HISTÓRIA ##########\n\n");
				System.out.println("No ano de 2222 foi criado um torneio chamado O Último Teleportador. \nA ideia era usar a grande invenção da humanidade, o teleportador de mão. Essa"
						+ " arma incrível era capaz de teleportar objetos para outros locais instantaneamente. \n\nAssim como já se esperava, o torneio foi um grande sucesso e continuou acontecendo anualmente."
						+ " \nA ideia consiste em ser o último sobrevivente de um jogo que mistura ação e estratégia. \n\n\nAgora o torneio está completando 10 anos e, nesta edição especial, você está convidado a participar!\n\n"
						+ "##################################################\n\n");
				break;
			case 4:
				//Tutorial do jogo
				System.out.println("\n\n########## TUTORIAL ##########\n\n");
				System.out.println("Para um melhor entendimento do funcionamento e das regras do jogo, indicamos dar uma olhada na sessão 3 do nosso relatório. Lá há uma explicação detalhada do funcionamento do jogo!\n\n"
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
				System.out.println("Valor inválido digitado!");
			}
			
			
			if(menu == -1) { // Início de um novo jogo
				
				System.out.println("\n\n - NARRADOR: Olááááááááá, senhoras e senhores!!! \n"
						+ "Está começando mais uma edição do ÚLTIMO TELEPORTADOOOOOOR!!!!!!\n"
						+ "O meu nome é Narrador e eu estarei narrando tudo pra vocês hoje!!!!\n"
						+ "Eu sei que meu nome parece estranho e que a última frase foi bem redundante, mas vamos lá!!!!\n"
						+ "Os nossos "+ jogadores.size() + " competidores já receberam seus Teleportadores Portáteis e já estão em suas posições!!!!\n\n"
						+ "VAMOS COMEÇAR!!!!!!!!\n");
				
				while(jogadores.size() > 1) {
					ArrayList<Integer> ordem = new ArrayList<Integer>(); // Aqui criamos uma lista que mudará a cada rodada. Ela representará a ordem dos jogadores.
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
					
					//Começa a rodada
					for(int i = 0; i < ordem.size(); i++) {
						// aplicar efeito da rodada anterior
						// andar inimigos
						int dano = ambientes.get(ordem.get(i)).andarInimigos();
						
						if(dano > 0) {
							jogadores.get(ordem.get(i)).sofrerDano(dano);
						}
						
						if(jogadores.get(ordem.get(i)).getVidaAtual() == 0) {
							System.out.println("- NARRADOR: JÁ ERA!!! "+jogadores.get(ordem.get(i)).getNome() + " morreu!");
							int k = ordem.get(i);
							jogadores.remove(k);
							ordem.remove(i);
							break;
						}
						
						// mostrar mapa atual
						ambientes.get(ordem.get(i)).mostrarAmbiente(jogadores.get(ordem.get(i)).getNome());
						// mostrar opções
						System.out.println("Turno de: " + jogadores.get(ordem.get(i)).getNome()+" (Munição: "+ jogadores.get(ordem.get(i)).getMunicaoAtual()+" - Vida: "+ jogadores.get(ordem.get(i)).getVidaAtual()+")");
						System.out.println("1 - Atacar (Custo: 1 Munição; Dano baseado no nível de ataque);\n"
										+  "2 - Defender (Recupera 1 de vida); \n"
										+  "3 - Recarregar Munição (Recupera 2 de Munição); \n"
										+  "4 - Especial ("+jogadores.get(ordem.get(i)).textoEspecial()+")");
						int escolha = Integer.parseInt(scan.nextLine());
						
						switch(escolha) {
						case 1: //Atacar
							System.out.println("Escolha um alvo (Digite na forma: linha coluna): ");
							String alvoCoord = scan.nextLine();
							String[] alvoCoordseparadas = alvoCoord.split(" ");
							//Atacando o alvo
							int inimigos = ambientes.get(ordem.get(i)).causarDano(Integer.parseInt(alvoCoordseparadas[0]), Integer.parseInt(alvoCoordseparadas[1]), jogadores.get(ordem.get(i)).getAtaque(), jogadores.get(ordem.get(i)).getMunicaoAtual());
							//Descontando munição
							jogadores.get(ordem.get(i)).atirar();
							
							//Mostrando Ambiente depois de atacar
							ambientes.get(ordem.get(i)).mostrarAmbiente(jogadores.get(ordem.get(i)).getNome());
							// Transferindo alvo para inimigo
							if(inimigos > 0) {
								if(i == ordem.size()-1) {
									//último para primeiro
									ambientes.get(ordem.get(0)).transferirInimigo(Integer.parseInt(alvoCoordseparadas[0])-1, Integer.parseInt(alvoCoordseparadas[1]), inimigos);
								} else {
									//para o próximo
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
									//último para primeiro
									jogadores.get(ordem.get(i)).usarEspecial(jogadores.get(ordem.get(0)));
								} else {
									//para o próximo
									jogadores.get(ordem.get(i)).usarEspecial(jogadores.get(ordem.get(i+1)));
								}
							} else {
								System.out.println("Nervoso, esqueceu que o Especial só se usa uma vez!");
							}
							break;
						default:
							System.out.println("Valor inválido digitado!");
						}
					}
				}
				
				// Vencedor
				System.out.println("\n\n\n - NARRADOR: E o grande vencedor é ##### " + jogadores.get(0).getNome()+", o " + jogadores.get(0).getNomeClasse() + "!!! #####\n\n\n");
			}
			
			
		} while(menu != 0);
		scan.close();
	}
}
