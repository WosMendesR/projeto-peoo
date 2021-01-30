package projeto_final;

public class AtiradorDeElite extends PersonagemJogavel {

	
	
	public AtiradorDeElite(String nome) {
		this.nome = nome;
		this.nomeClasse = "Atirador de Elite";
		this.ataque = 2;
		this.defesa = 1;
		this.municaoMax = 3;
		this.municaoAtual = this.municaoMax;
		this.vidaMax = this.defesa + 2;
		this.vidaAtual = this.vidaMax;
		this.usouEspecial = false;
	}
	
	@Override
	public void usarEspecial(PersonagemJogavel personagem) {
		System.out.println("Atirador");
		personagem.sofrerDano(1);
		this.usouEspecial = true;
	}
	
	@Override
	public String textoEspecial() {
		return "Causa 1 de Dano no próximo personagem da rodada";
	}

	

}
