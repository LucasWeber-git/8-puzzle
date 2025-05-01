package br.com.quebra.cabeca;

import static java.util.Collections.singletonList;

import java.util.LinkedHashSet;
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
        return null;
    }

}