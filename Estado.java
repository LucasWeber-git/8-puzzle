package br.com.quebra.cabeca;

import static br.com.quebra.cabeca.Constantes.DIRECOES;
import static br.com.quebra.cabeca.Constantes.OBJETIVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
     * Gera um estado inicial resolvível.
     *
     * @return estado inicial.
     */
    public static Estado geraInicial() {
        List<Integer> pecas = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            pecas.add(i);
        }

        do {
            Collections.shuffle(pecas);
        } while (!isResolvivel(pecas.toArray(new Integer[0])));

        int[][] tabInicial = new int[3][3];
        for (int i = 0; i < 9; i++) {
            tabInicial[i / 3][i % 3] = pecas.get(i);
        }

        return new Estado(tabInicial, null);
    }

    /**
     * Verifica se o tabuleiro é resolvível.
     *
     * @param array tabuleiro em formato linear
     * @return boolean indicando se é ou não resolvível
     */
    public static boolean isResolvivel(Integer[] array) {
        int inversoes = 0;
        for (int i = 0; i < 9; i++) {
            if (array[i] == 0) {
                continue;
            }

            for (int j = i + 1; j < 9; j++) {
                if (array[j] == 0) {
                    continue;
                }
                if (array[i] > array[j]) {
                    inversoes++;
                }
            }
        }

        return inversoes % 2 != 0;
    }

    public boolean isObjetivo() {
        return this.equals(OBJETIVO);
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
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
        return Arrays.stream(tabuleiro)
            .flatMapToInt(Arrays::stream)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",", "[", "]"));
    }

}
