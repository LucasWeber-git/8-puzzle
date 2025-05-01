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

public class Estado implements Comparable<Estado> {

    private final int[][] tabuleiro;
    private final Estado pai;

    private int linhaZero;
    private int colunaZero;

    private int profundidade;
    private int distanciaHeuristica;

    public Estado(int[][] tabuleiro, Estado pai) {
        this.tabuleiro = tabuleiro;
        this.pai = pai;
        this.profundidade = pai == null ? 0 : (pai.getProfundidade() + 1);

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
     * Retorna o caminho desde o estado inicial até este estado.
     *
     * @return Lista ordenada com o caminho percorrido
     */
    public List<Estado> getCaminho() {
        List<Estado> caminho = new ArrayList<>();
        Estado proximo = this;

        while (proximo != null) {
            caminho.add(proximo);
            proximo = proximo.getPai();
        }

        Collections.reverse(caminho);

        return caminho;
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

        if (Util.isPosicaoValida(linha, coluna)) {
            int[][] novoTab = Util.copiarTabuleiro(tabuleiro);
            novoTab[linhaZero][colunaZero] = novoTab[linha][coluna];
            novoTab[linha][coluna] = 0;

            return novoTab;
        }
        return null;
    }

    public boolean isObjetivo() {
        return this.equals(OBJETIVO);
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public Estado getPai() {
        return pai;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public int getDistanciaHeuristica() {
        return distanciaHeuristica;
    }

    public void setDistanciaHeuristica(int distanciaHeuristica) {
        this.distanciaHeuristica = distanciaHeuristica;
    }

    @Override
    public boolean equals(final Object outro) {
        if (this == outro) {
            return true;
        }
        if (outro == null || getClass() != outro.getClass()) {
            return false;
        }

        final Estado estado = (Estado) outro;
        return Arrays.deepEquals(tabuleiro, estado.getTabuleiro());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tabuleiro);
    }

    @Override
    public int compareTo(Estado outro) {
        int pesoEste = this.profundidade + this.distanciaHeuristica;
        int pesoOutro = outro.getProfundidade() + outro.getDistanciaHeuristica();

        return Integer.compare(pesoEste, pesoOutro);
    }

    @Override
    public String toString() {
        return Arrays.stream(tabuleiro)
            .flatMapToInt(Arrays::stream)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",", "[", "]"));
    }

}
