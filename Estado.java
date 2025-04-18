package br.com.quebra.cabeca;

import static br.com.quebra.cabeca.Constantes.DIRECOES;
import static br.com.quebra.cabeca.Constantes.OBJETIVO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Estado {

    private final Estado pai;
    private final int[][] tabuleiro;

    private int linhaZero;
    private int colunaZero;

    public Estado(int[][] tabuleiro, Estado pai) {
        this.tabuleiro = tabuleiro;
        this.pai = pai;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.tabuleiro[i][j] == 0) {
                    this.linhaZero = i;
                    this.colunaZero = j;
                }
            }
        }
    }

    /**
     * Gera um estado inicial válido.
     *
     * @return estado inicial.
     */
    public static Estado geraInicial() {
        return new Estado(new int[][]{{2, 1, 3}, {8, 0, 4}, {7, 5, 6}}, null);
    }

    public boolean isObjetivo() {
        return this.equals(OBJETIVO);
    }

    /**
     * Gera os filhos deste estado com base nos movimentos possíveis no tabuleiro.
     *
     * @return Set de estados filhos
     */
    public Set<Estado> gerarFilhos() {
        Set<Estado> filhos = new HashSet<>();

        for (int[] direcao : DIRECOES) {
            int[][] novoTab = movePeca(direcao);

            if (novoTab != null) {
                filhos.add(new Estado(novoTab, this));
            }
        }
        return filhos;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Move o espaço vazio na direção indicada, caso seja um movimento válido.
     *
     * @param direcao direção desejada.
     * @return tabuleiro após a movimentação; null se o movimento for inválido.
     */
    private int[][] movePeca(int[] direcao) {
        int linha = linhaZero + direcao[0];
        int coluna = colunaZero + direcao[1];

        if (isPosicaoValida(linha, coluna)) {
            int[][] novoTab = copiarTabuleiro();
            novoTab[linhaZero][colunaZero] = novoTab[linha][coluna];
            novoTab[linha][coluna] = 0;

            return novoTab;
        }
        return null;
    }

    /**
     * Indica se a posição é válida para o tabuleiro 3x3.
     *
     * @param lin linha.
     * @param col coluna.
     * @return boolean indicando se a posição é válida.
     */
    private boolean isPosicaoValida(int lin, int col) {
        return lin >= 0 && lin < 3 && col >= 0 && col < 3;
    }

    /**
     * Realiza uma cópia profunda do tabuleiro atual.
     *
     * @return cópia.
     */
    private int[][] copiarTabuleiro() {
        int[][] novo = new int[3][3];
        for (int i = 0; i < 3; i++) {
            novo[i] = tabuleiro[i].clone();
        }
        return novo;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Estado estado = (Estado) other;
        return Arrays.deepEquals(tabuleiro, estado.getTabuleiro());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tabuleiro);
    }

    @Override
    public String toString() {
        return Arrays.stream(this.tabuleiro)
            .flatMapToInt(Arrays::stream)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",", "[", "]"));
    }

}
