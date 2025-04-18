package br.com.quebra.cabeca;

public class Constantes {

    public static final int[] CIMA = {-1, 0};
    public static final int[] BAIXO = {1, 0};
    public static final int[] ESQUERDA = {0, -1};
    public static final int[] DIREITA = {0, 1};

    public static final int[][] DIRECOES = {CIMA, BAIXO, ESQUERDA, DIREITA};

    public static final Estado OBJETIVO = new Estado(new int[][]{{1, 2, 3}, {8, 0, 4}, {7, 6, 5}}, null);

}
