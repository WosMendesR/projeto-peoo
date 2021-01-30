package projeto_final;

public class Defensor extends PersonagemJogavel {
	
	
	public Defensor(String nome) {
		
		this.nome = nome;
		this.nomeClasse = "Defensor";
		this.ataque = 1;
		this.defesa = 2;
		this.municaoMax = 4;
		this.municaoAtual = this.municaoMax;
		this.vidaMax = this.defesa + 2;
		this.vidaAtual = this.vidaMax;
		this.usouEspecial = false;
	}
	
	@Override
	public void usarEspecial(PersonagemJogavel personagem) {
		System.out.println("Defensor");
		this.defender(3);
		this.usouEspecial = true;

	}
	
	@Override
	public String textoEspecial() {
		return "Recupera 3 de Vida";
	}

}
