package br.com.quebra.cabeca;

import static java.util.Collections.singletonList;

import java.util.LinkedHashSet;
import java.util.Set;

public class Busca {

    /**
     * Realiza a busca por amplitude.
     *
     * @param inicial o estado inicial do quebra-cabeça.
     * @return a quantidade de iterações realizadas.
     */
    public static int amplitude(Estado inicial) {
        LinkedHashSet<Estado> abertos = new LinkedHashSet<>(singletonList(inicial));
        LinkedHashSet<Estado> fechados = new LinkedHashSet<>();

        int i = 0;
        while (!abertos.isEmpty()) {
            Estado x = abertos.iterator().next();
            abertos.remove(x);

            i++;
            System.out.println(x);

            if (x.isObjetivo()) {
                return i;
            } else {
                Set<Estado> filhos = x.gerarFilhos();

                fechados.add(x);

                filhos.removeAll(abertos);
                filhos.removeAll(fechados);

                abertos.addAll(filhos);
            }
        }
        return -1;
    }

    public static int melhorEscolha(Estado inicial) {
        return 0;
    }

}