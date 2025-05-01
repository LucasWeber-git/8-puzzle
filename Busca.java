package br.com.quebra.cabeca;

import static br.com.quebra.cabeca.Constantes.OBJETIVO;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Busca {

    /**
     * Realiza a busca por amplitude.
     *
     * @param inicial o estado inicial do quebra-cabeça.
     * @return o estado final do quebra-cabeça.
     */
    public static Estado amplitude(Estado inicial) {
        LinkedHashSet<Estado> abertos = new LinkedHashSet<>(singletonList(inicial));
        LinkedHashSet<Estado> fechados = new LinkedHashSet<>();

        while (!abertos.isEmpty()) {
            Estado x = abertos.iterator().next();
            abertos.remove(x);

            if (x.isObjetivo()) {
                return x;
            } else {
                Set<Estado> filhos = x.gerarFilhos();

                fechados.add(x);

                filhos.removeAll(abertos);
                filhos.removeAll(fechados);

                abertos.addAll(filhos);
            }
        }
        return null;
    }

    /**
     * Realiza a busca por melhor escolha.
     *
     * @param inicial o estado inicial do quebra-cabeça.
     * @return o estado final do quebra-cabeça.
     */
    public static Estado melhorEscolha(Estado inicial) {
        List<Estado> abertos = new ArrayList<>(singletonList(inicial));
        List<Estado> fechados = new ArrayList<>();

        while (!abertos.isEmpty()) {
            Estado x = abertos.remove(0);

            if (x.isObjetivo()) {
                return x;
            } else {
                Set<Estado> filhos = x.gerarFilhos();

                for (Estado filho : filhos) {
                    if (!abertos.contains(filho) && !fechados.contains(filho)) {
                        filho.setDistanciaHeuristica(calcularHeuristica(filho));
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
     * Calcula a estimativa heurística da distância entre um estado e o objetivo. Neste caso, a heurística é a
     * quantidade de peças fora do lugar.
     *
     * @param atual o estado a ser analisado
     * @return o valor estimado da distância
     */
    private static int calcularHeuristica(Estado atual) {
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

}