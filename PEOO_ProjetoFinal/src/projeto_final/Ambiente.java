package projeto_final;

public class Ambiente { //BATALHA
	
	private int[][] mapa = new int[4][3];
	private int jogadorAssociado;
	
	public int getMapa(int i, int j) {
		return mapa[i][j];
	}
	
	public Ambiente(int jogadorAssociado) {
		this.jogadorAssociado = jogadorAssociado;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 3; j++) {
				if(i >= 1) {
					mapa[i][j] = 0;
				} else {
					mapa[i][j] = 1;
				}
			}
		}
	}
	
	//outros métodos
	
	public void mostrarAmbiente(String jogador) {
		System.out.print("   [0][1][2]\n");
		for(int i = 0; i < mapa.length; i++) {
			System.out.print("["+i+"] ");
			for(int j = 0; j < mapa[0].length; j++) {
				System.out.print(mapa[i][j]+"  ");
			}
			System.out.println();
		}
		System.out.println("--- " + jogador + "\n");
	}
	
	public int andarInimigos() {
		System.out.println("\n - NARRADOR: E os inimigos estão se aproximando!!!\n");
		int dano = 0;
		for(int i = mapa.length-1; i >= 0; i--) {
			for(int j = mapa[0].length-1; j >= 0; j--) {
				if(mapa[i][j] > 0) {
					if(i == mapa.length-1) {
						dano += mapa[i][j];
					} else {
						mapa[i+1][j] += mapa[i][j];
						mapa[i][j] = 0;
					}
				}
			}
		}
		return dano;
	}
	
	public int causarDano(int i, int j, int atq, int mun) {
		int inimigoTransf = 0;
		if(mun <= 0) {
			System.out.println("\n - NARRADOR: Desesperado(a), não percebeu que estava sem munição! Atirou e atirou e nada aconteceu!\n");
			return inimigoTransf;
		} else {
			if(mapa[i][j] == 0) {
				System.out.println("\n - NARRADOR: Nossa, que tiro horroroso! Errou o alvo! (risos) \n");
				return inimigoTransf;
			} else {
				System.out.println("\n - NARRADOR: Aí sim!!! Acertou em cheio! O inimigo será Teleportado para o Coleguinha Hahahahaha\n");
				if(atq == 1 || mapa[i][j] == 1) {
					inimigoTransf = 1;
				} else {
					inimigoTransf = 2;
				}
				mapa[i][j] -= atq;
				if(mapa[i][j] <= 0) {
					mapa[i][j] = 0;
				}
				return inimigoTransf;
			}
		}
		
		
	}
	
	public void transferirInimigo(int i, int j, int inimigos) {
		mapa[i][j] = mapa[i][j] +inimigos;
		
	}
	
}
