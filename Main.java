package br.com.quebra.cabeca;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Estado inicial = Estado.geraInicial();
        System.out.println("Estado inicial: " + inicial + "\n");

        System.out.println("Iniciando busca por amplitude\n");
        disparaBusca(Busca::amplitude, inicial);

        System.out.println("\n-------------------------------------------------------\n");

        System.out.println("Iniciando busca por melhor escolha\n");
        disparaBusca(Busca::melhorEscolha, inicial);
    }

    /**
     * Realiza a busca pelo método indicado, realizando medições de desempenho.
     *
     * @param busca função de busca.
     * @param inicial estado inicial do quebra-cabeça.
     */
    private static void disparaBusca(Function<Estado, Integer> busca, Estado inicial) {
        long memoriaAntes = getMemoriaUsada();
        long tempoAntes = System.currentTimeMillis();

        Integer iteracoes = busca.apply(inicial);

        long memoriaDepois = getMemoriaUsada();
        long tempoDepois = System.currentTimeMillis();

        long memoriaConsumida = memoriaDepois - memoriaAntes;
        long tempoTotal = tempoDepois - tempoAntes;

        System.out.println("\nQuantidade de iterações: " + iteracoes);
        System.out.println("Memória consumida: " + memoriaConsumida + " bytes");
        System.out.println("Tempo: " + tempoTotal + " ms");
    }

    /**
     * Retorna a quantidade de memória em uso.
     */
    private static long getMemoriaUsada() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

}
