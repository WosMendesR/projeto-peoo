package projeto_final;

public abstract class PersonagemAbs {
	
	protected int vidaMax;
	protected int ataque;
	protected int defesa;
	
	//gets e sets
	public int getVidaMax() {
		return vidaMax;
	}
	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getDefesa() {
		return defesa;
	}
	public void setDefesa(int defesa) {
		this.defesa = defesa;
	}
	
	//outros métodos
	
	public abstract void sofrerDano(int dano);
		

}
