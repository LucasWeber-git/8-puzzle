package br.com.quebra.cabeca;

import java.util.List;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Estado inicial = Util.geraInicial();
        System.out.println("Estado inicial: " + inicial + "\n");

        System.out.println("Iniciando busca por amplitude\n");
        realizaBusca(Busca::amplitude, inicial);

        System.out.println("\n-------------------------------------------------------\n");

        System.out.println("Iniciando busca por melhor escolha\n");
        realizaBusca(Busca::melhorEscolha, inicial);
    }

    /**
     * Realiza a busca pelo método indicado, realizando medições de desempenho.
     *
     * @param busca função de busca.
     * @param inicial estado inicial do quebra-cabeça.
     */
    private static void realizaBusca(Function<Estado, Estado> busca, Estado inicial) {
        long memoriaAntes = getMemoriaUsada();
        long tempoAntes = System.currentTimeMillis();

        Estado estadoFinal = busca.apply(inicial);

        long memoriaDepois = getMemoriaUsada();
        long tempoDepois = System.currentTimeMillis();

        long memoriaConsumida = memoriaDepois - memoriaAntes;
        long tempoTotal = tempoDepois - tempoAntes;

        if (estadoFinal == null) {
            System.out.println("Erro ao realizar busca.");
            return;
        }

        List<Estado> caminho = estadoFinal.getCaminho();

        for (Estado e : caminho) {
            System.out.println(e);
        }

        System.out.println("\nTamanho da árvore gerada: " + estadoFinal.getProfundidade());
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
