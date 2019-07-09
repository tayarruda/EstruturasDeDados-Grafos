
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.util.Random;

public class Hierholzer {

    static private Graph graph;
    private LinkedList<Integer> eulerianCycle;

    static void slow() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Color getRadomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    private void InsertCycle(LinkedList<Integer> newCycle, int index) {
        this.eulerianCycle.addAll(index, newCycle);
    }

    private LinkedList<Integer> SearchSimpleCycle(int startVertice) {
        LinkedList<Integer> ciclo = new LinkedList<>();
        ciclo.add(startVertice);
        Edge proxAresta;
        int arestaAtual = startVertice;
        do {
            proxAresta = this.graph.getNeighbours(arestaAtual).getFirst();
            arestaAtual = proxAresta.getV();
            this.graph.removeEdge(proxAresta);
            ciclo.add(arestaAtual);
        } while (arestaAtual != startVertice);

        return ciclo;
    }

    public LinkedList<Integer> SearchEulerianCycle(int startVertice) {
        if (!graph.isEulerian()) {
            graph.turnEulerian();
        }
        eulerianCycle = SearchSimpleCycle(startVertice);
        int i = 0;
        do{
            graph.nEdges--;
            i++;
            if (!graph.getNeighbours(i).isEmpty()) {
                LinkedList<Integer> ciclo = SearchSimpleCycle(eulerianCycle.get(i));
                ciclo.removeFirst();
                InsertCycle(ciclo, i);
        }
        
        }while(graph.nEdges > 0);
        return eulerianCycle;
    }

    /**
     * ****************************************************************************************
     */
    public static String CycleToString(LinkedList<Integer> cycle) {
        String str = "";
        for (Integer integer : cycle) {
            str += integer + " -> ";
        }
        return str.substring(0, str.length() - 4);
    }

    public void test1() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 5);
        graph.addEdge(4, 1, 5);
        graph.addEdge(4, 2, 5);
        graph.addEdge(4, 3, 5);

        System.out.println("Lista de adjacencia do grafo:");
        System.out.println(graph.toString());
    }

    public void test2() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 5);
        graph.addEdge(4, 1, 5);
        graph.addEdge(4, 2, 5);
        graph.addEdge(4, 3, 5);

        System.out.println("Lista de adjacencia do grafo ANTES da remocao:");
        System.out.println(graph.toString());
        System.out.println();

        graph.removeEdge(new Edge(1, 4, 5));
        graph.removeEdge(new Edge(0, 4, 5));
        graph.removeEdge(new Edge(0, 2, 5));

        System.out.println("Lista de adjacencia do grafo DEPOIS da remocao:");
        System.out.println(graph.toString());
    }

    public void test3() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 5);
        graph.addEdge(4, 1, 5);
        graph.addEdge(4, 2, 5);
        graph.addEdge(4, 3, 5);

        System.out.println("Vertices vizinhos do vertice 0:");
        for (Edge e : graph.getNeighbours(0)) {
            System.out.print(e.getV() + " ");
        }
        System.out.println();

        System.out.println("Vertices vizinhos do vertice 2:");
        for (Edge e : graph.getNeighbours(2)) {
            System.out.print(e.getV() + " ");
        }
        System.out.println();;
    }

    public void test4() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 5);
        graph.addEdge(4, 1, 5);
        graph.addEdge(4, 2, 5);
        graph.addEdge(4, 3, 5);

        System.out.println("Lista de adjacencia do grafo 1:");
        System.out.print(graph.toString());
        System.out.println("Euleriano:" + graph.isEulerian() + "\n");

        graph = new Graph(6);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 4, 5);
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 5);
        graph.addEdge(2, 5, 5);
        graph.addEdge(3, 5, 5);
        graph.addEdge(4, 5, 5);

        System.out.println("Lista de adjacencia do grafo 2:");
        System.out.print(graph.toString());
        System.out.println("Euleriano: " + graph.isEulerian());
    }

    public void test5(int startVertice) {
        graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 4, 5);
        graph.addEdge(4, 1, 5);
        graph.addEdge(4, 2, 5);
        graph.addEdge(4, 3, 5);

        LinkedList<Integer> cycle = SearchSimpleCycle(startVertice);
        System.out.println("Ciclo Simples Encontrado: comecando de " + startVertice);
        System.out.println(CycleToString(cycle));
    }

    public void test6() {
        Hierholzer hierholzer = new Hierholzer();
        //graph = new Graph("grafo1.csv", "white.png", "Roteamento"); // Grafo nao Euleriano
        graph = new Graph("grafo2.csv", "white.png", "Roteamento"); // Grafo Euleriano
        System.out.println();
        LinkedList<Integer> eulerianCycle = hierholzer.SearchEulerianCycle(0);
        System.out.println("Cicloâ�£Final: ");
        System.out.println(Hierholzer.CycleToString(eulerianCycle));
        Graph graph2 = new Graph(graph.getGraphics(), "white.png", "Rotaâ�£Final");
        for (Integer i : eulerianCycle) {
            slow();
            slow();
            Color color = Hierholzer.getRadomColor();
            graph2.markNode(i, color);
        }
    }

    public void test7() {
        Hierholzer hierholzer = new Hierholzer();
        // hierholzer.test5(0);

        // graph = new Graph("grafo.csv", "white.png", "Roteamento");
        // graph = new Graph("grafo1.csv", "white.png", "Roteamento");
        // graph = new Graph("grafoMapa1.csv", "mapa1.png", "Roteamento");
        graph = new Graph("grafoMapa2.csv", "mapa2.png", "Roteamento");

        System.out.println();
        LinkedList<Integer> eulerianCycle = hierholzer.SearchEulerianCycle(0);
        System.out.println("Ciclo Final:");
        System.out.println(Hierholzer.CycleToString(eulerianCycle));

        // Graph graph2 = new Graph(graph.getGraphics(), "white.png", "Rota Final");
        // Graph graph2 = new Graph(graph.getGraphics(), "mapa1.png", "Rota Final");
        Graph graph2 = new Graph(graph.getGraphics(), "mapa2.png", "Rota Final");
        for (Integer i : eulerianCycle) {
            slow();
            Color color = Hierholzer.getRadomColor();
            graph2.markNode(i, color);
        }
    }

    public static void main(String[] args) {
        Hierholzer instance = new Hierholzer();
        instance.test6();
    }
}
