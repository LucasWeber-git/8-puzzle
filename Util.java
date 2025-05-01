package br.com.quebra.cabeca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    /**
     * Indica se a posição é válida para o tabuleiro 3x3.
     *
     * @param lin linha.
     * @param col coluna.
     * @return boolean indicando se a posição é válida.
     */
    public static boolean isPosicaoValida(int lin, int col) {
        return lin >= 0 && lin < 3 && col >= 0 && col < 3;
    }

    /**
     * Realiza uma cópia profunda do tabuleiro atual.
     *
     * @return cópia.
     */
    public static int[][] copiarTabuleiro(int[][] tabuleiro) {
        int[][] novo = new int[3][3];
        for (int i = 0; i < 3; i++) {
            novo[i] = tabuleiro[i].clone();
        }
        return novo;
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

}
