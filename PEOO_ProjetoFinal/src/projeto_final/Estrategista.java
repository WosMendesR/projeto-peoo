package projeto_final;

public class Estrategista extends PersonagemJogavel {
	
	public Estrategista(String nome) {
		
		this.nome = nome;
		this.nomeClasse = "Estrategista";
		this.ataque = 1;
		this.defesa = 1;
		this.municaoMax = 5;
		this.municaoAtual = this.municaoMax;
		this.vidaMax = this.defesa + 2;
		this.vidaAtual = this.vidaMax;
		this.usouEspecial = false;
	}
	
	@Override
	public void usarEspecial(PersonagemJogavel personagem) {
		personagem.recuperarMunicao(-3);
		this.recuperarMunicao(3);
		this.usouEspecial = true;

	}
	
	@Override
	public String textoEspecial() {
		return "Rouba para si 3 de Munição do próximo personagem da rodada";
	}

}
