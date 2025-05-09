package br.com.quebra.cabeca;

import static br.com.quebra.cabeca.Constantes.HEURISTICA_DISTANCIAS;
import static br.com.quebra.cabeca.Constantes.HEURISTICA_PECAS_FORA_LUGAR;
import static br.com.quebra.cabeca.Constantes.OBJETIVO;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Busca {

    /**
     * Realiza a busca por amplitude.
     *
     * @param inicial o estado inicial do quebra-cabeça.
     * @return o estado final do quebra-cabeça.
     */
    public static Estado porAmplitude(Estado inicial) {
        List<Estado> abertos = new ArrayList<>(singletonList(inicial));
        List<Estado> fechados = new ArrayList<>();

        while (!abertos.isEmpty()) {
            Estado x = abertos.remove(0);

            if (x.isObjetivo()) {
                System.out.printf("Estados abertos: %d; Estados fechados: %d\n", abertos.size(), fechados.size());
                return x;
            } else {
                Set<Estado> filhos = x.gerarFilhos();
                fechados.add(x);

                filhos.removeIf(filho -> abertos.contains(filho) || fechados.contains(filho));
                abertos.addAll(filhos);
            }
        }
        return null;
    }

    public static Estado porPecasForaDoLugar(Estado inicial) {
        return melhorEscolha(inicial, HEURISTICA_PECAS_FORA_LUGAR);
    }

    public static Estado porDistancias(Estado inicial) {
        return melhorEscolha(inicial, HEURISTICA_DISTANCIAS);
    }

    /**
     * Realiza a busca por melhor escolha.
     *
     * @param inicial o estado inicial do quebra-cabeça.
     * @return o estado final do quebra-cabeça.
     */
    private static Estado melhorEscolha(Estado inicial, String heuristica) {
        List<Estado> abertos = new ArrayList<>(singletonList(inicial));
        List<Estado> fechados = new ArrayList<>();

        while (!abertos.isEmpty()) {
            Estado x = abertos.remove(0);

            if (x.isObjetivo()) {
                System.out.printf("Estados abertos: %d; Estados fechados: %d\n", abertos.size(), fechados.size());
                return x;
            } else {
                Set<Estado> filhos = x.gerarFilhos();

                for (Estado filho : filhos) {
                    if (!abertos.contains(filho) && !fechados.contains(filho)) {
                        filho.setDistanciaHeuristica(calcularHeuristica(filho, heuristica));
                        abertos.add(filho);
                    } else if (abertos.contains(filho)) {
                        int pos = abertos.indexOf(filho);
                        Estado equivalente = abertos.get(pos);

                        if (filho.getProfundidade() < equivalente.getProfundidade()) {
                            equivalente.setProfundidade(filho.getProfundidade());
                            abertos.set(pos, equivalente);
                        }
                    } else if (fechados.contains(filho)) {
                        int pos = fechados.indexOf(filho);
                        Estado equivalente = fechados.get(pos);

                        if (filho.getProfundidade() < equivalente.getProfundidade()) {
                            fechados.remove(equivalente);
                            abertos.add(filho);
                        }
                    }
                }

                fechados.add(x);
                Collections.sort(abertos);
            }
        }

        return null;
    }

    /**
     * Define a heurística a ser utilizada para o cálculo.
     *
     * @param atual o estado a ser analisado
     * @param heuristica o método de cálculo heurístico
     * @return o valor estimado da distância
     */
    private static int calcularHeuristica(Estado atual, String heuristica) {
        if (HEURISTICA_PECAS_FORA_LUGAR.equals(heuristica)) {
            return calcularHeuristicaPecasForaDoLugar(atual);
        } else if (HEURISTICA_DISTANCIAS.equals(heuristica)) {
            return calcularHeuristicaDistancias(atual);
        }
        return -1;
    }

    /**
     * Calcula a estimativa heurística da distância entre um estado e o objetivo com base na heurística da quantidade de
     * peças fora do lugar.
     *
     * @param atual o estado a ser analisado
     * @return o valor estimado da distância
     */
    private static int calcularHeuristicaPecasForaDoLugar(Estado atual) {
        int pecasForaDoLugar = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (atual.getTabuleiro()[i][j] != OBJETIVO.getTabuleiro()[i][j]) {
                    pecasForaDoLugar++;
                }
            }
        }
        return pecasForaDoLugar;
    }

    /**
     * Calcula a estimativa heurística da distância entre um estado e o objetivo com base na heurística da distância.
     *
     * @param atual o estado a ser analisado
     * @return o valor estimado da distância
     */
    private static int calcularHeuristicaDistancias(Estado atual) {
        int distancia = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                distancia += calculaDistancia(atual.getTabuleiro()[i][j], i, j);
            }
        }
        return distancia;
    }

    private static int calculaDistancia(int peca, int lin, int col) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (peca == OBJETIVO.getTabuleiro()[i][j]) {
                    return (Math.abs(lin - i) + Math.abs(col - j));
                }
            }
        }
        return 0;
    }

}