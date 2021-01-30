package projeto_final;

public class PersonagemJogavel extends PersonagemAbs implements Especial {
	
	//Atributos
		protected String nome;
		protected int vidaAtual;
		protected int municaoAtual;
		protected int municaoMax;
		protected boolean usouEspecial;
		
		protected String nomeClasse;
		
		
	// gets e sets
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getMunicaoMax() {
		return municaoMax;
	}

	public void setMunicaoMax(int municaoMax) {
		this.municaoMax = municaoMax;
	}

	public int getMunicaoAtual() {
		return municaoAtual;
	}

	public void setMunicaoAtual(int municaoAtual) {
		this.municaoAtual = municaoAtual;
	}

	public boolean getUsouEspecial() {
		return usouEspecial;
	}

	public void setUsouEspecial(boolean usouEspecial) {
		this.usouEspecial = usouEspecial;
	}
	
	//outros métodos
	
	public void sofrerDano(int dano) {
		System.out.println("- NARRADOR: "+this.nome + " sofreu dano");
		vidaAtual -= dano;
		if(vidaAtual < 0) {
			vidaAtual = 0;
		}
	}

	public void usarEspecial(PersonagemJogavel personagem) {
		System.out.println("usou especial");
		System.out.println("Jogavel");
	}
	
	public void atirar() { //Desconta munição
		if(municaoAtual > 0) {
			municaoAtual--;
		}
	}
	
	public void defender(int vida) {
		this.vidaAtual =+ vida;
		if(this.vidaAtual > vidaMax) {
			this.vidaAtual = vidaMax;
			System.out.println("Você tentou recuperar 1 de vida, mas já estava com a vida cheia!");
		} else {
			System.out.println("Você recuperou 1 de Vida!");
		}
	}
	
	public void recuperarMunicao(int municao) {
		this.municaoAtual += municao;
		if(this.municaoAtual < 0) {
			this.municaoAtual = 0;
		}
		if(this.municaoAtual > municaoMax) {
			this.municaoAtual = municaoMax;
			System.out.println("Sua munição está cheia!");
		} else {
			System.out.println("Você recuperou Munição!");
		}
	}
	
	public String textoEspecial() {
		return "Especial";
	}
}
