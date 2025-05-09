package br.com.quebra.cabeca;

import java.util.List;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Estado inicial = Util.geraInicial();

        //Estado inicial = new Estado(new int[][]{{2, 1, 3}, {8, 0, 4}, {7, 5, 6}}, null); //cenario 1
        //Estado inicial = new Estado(new int[][]{{1, 3, 2}, {6, 7, 4}, {5, 8, 0}}, null); //cenario 2
        //Estado inicial = new Estado(new int[][]{{2, 8, 1}, {3, 7, 0}, {4, 6, 5}}, null); //cenario 3
        //Estado inicial = new Estado(new int[][]{{3, 8, 5}, {7, 1, 0}, {4, 6, 2}}, null); //cenario 4
        //Estado inicial = new Estado(new int[][]{{2, 7, 1}, {4, 5, 3}, {8, 0, 6}}, null); //cenario 5

        System.out.println("Estado inicial: " + inicial + "\n");

        System.out.println("Iniciando busca por amplitude");
        realizaBusca(Busca::porAmplitude, inicial);

        System.out.println("\n-------------------------------------------------------\n");

        System.out.println("Iniciando busca por melhor escolha com a heurística de peças fora do lugar");
        realizaBusca(Busca::porPecasForaDoLugar, inicial);

        System.out.println("\n-------------------------------------------------------\n");

        System.out.println("Iniciando busca por melhor escolha com a heurística das distâncias");
        realizaBusca(Busca::porDistancias, inicial);
    }

    /**
     * Realiza a busca pelo método indicado, realizando medições de desempenho.
     *
     * @param busca função de busca.
     * @param inicial estado inicial do quebra-cabeça.
     */
    private static void realizaBusca(Function<Estado, Estado> busca, Estado inicial) {
        long tempoAntes = System.currentTimeMillis();

        Estado estadoFinal = busca.apply(inicial);

        long tempoDepois = System.currentTimeMillis();
        long tempoTotal = tempoDepois - tempoAntes;

        if (estadoFinal == null) {
            System.out.println("Erro ao realizar busca.");
            return;
        }

        List<Estado> caminho = estadoFinal.getCaminho();

        System.out.println("Tempo: " + tempoTotal + " ms\n");

        for (Estado e : caminho) {
            System.out.println(e);
        }
    }

}
