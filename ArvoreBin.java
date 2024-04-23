import java.io.*;
import java.util.*;

class Nodo {
    int info;
    Nodo esq, dir, pai;
}

public class ArvoreBin {
    private Nodo raiz;

    public ArvoreBin() {
        raiz = null;
    }

    public Nodo alocarNodo(int valor) {
        Nodo novoNodo = new Nodo();
        novoNodo.info = valor;
        novoNodo.esq = novoNodo.dir = novoNodo.pai = null;
        return novoNodo;
    }

    public void inserir(int valor) {
        raiz = inserir(valor, raiz);
    }

    private Nodo inserir(int valor, Nodo no) {
        if (no == null) {
            return alocarNodo(valor);
        }

        if (valor < no.info) {
            no.esq = inserir(valor, no.esq);
            no.esq.pai = no;
        } else if (valor > no.info) {
            no.dir = inserir(valor, no.dir);
            no.dir.pai = no;
        }

        return no;
    }

    public void preOrdem() {
        preOrdem(raiz);
    }

    private void preOrdem(Nodo no) {
        if (no != null) {
            System.out.print(no.info + " ");
            preOrdem(no.esq);
            preOrdem(no.dir);
        }
    }

    public void central() {
        central(raiz);
    }

    private void central(Nodo no) {
        if (no != null) {
            central(no.esq);
            System.out.print(no.info + " ");
            central(no.dir);
        }
    }

    public void posOrdem() {
        posOrdem(raiz);
    }

    private void posOrdem(Nodo no) {
        if (no != null) {
            posOrdem(no.esq);
            posOrdem(no.dir);
            System.out.print(no.info + " ");
        }
    }

    public void remover(int valor) {
        raiz = remover(valor, raiz);
    }

    private Nodo remover(int valor, Nodo no) {
        if (no == null)
            return no;

        if (valor < no.info) {
            no.esq = remover(valor, no.esq);
        } else if (valor > no.info) {
            no.dir = remover(valor, no.dir);
        } else {
            if (no.esq == null) {
                return no.dir;
            } else if (no.dir == null) {
                return no.esq;
            }

            no.info = minValor(no.dir);
            no.dir = remover(no.info, no.dir);
        }

        return no;
    }

    private int minValor(Nodo no) {
        int minValor = no.info;
        while (no.esq != null) {
            minValor = no.esq.info;
            no = no.esq;
        }
        return minValor;
    }

    public Nodo buscar(int valor) {
        return buscar(valor, raiz);
    }

    private Nodo buscar(int valor, Nodo no) {
        if (no == null || no.info == valor) {
            return no;
        }

        if (valor < no.info) {
            return buscar(valor, no.esq);
        }

        return buscar(valor, no.dir);
    }

    public void gerarArqDot(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("digraph ArvoreBin {\n");
            escreverPreOrdemDot(raiz, out);
            out.write("}");
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo DOT: " + e.getMessage());
        }
    }

    private void escreverPreOrdemDot(Nodo no, BufferedWriter out) throws IOException {
        if (no != null) {
            out.write("\t" + no.info + ";\n");
            if (no.esq != null) {
                out.write("\t" + no.info + " -> " + no.esq.info + ";\n");
            }
            if (no.dir != null) {
                out.write("\t" + no.info + " -> " + no.dir.info + ";\n");
            }
            escreverPreOrdemDot(no.esq, out);
            escreverPreOrdemDot(no.dir, out);
        }
    }

    public static void main(String[] args) {
        ArvoreBin arvore = new ArvoreBin();
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(15);
        arvore.inserir(2);
        arvore.inserir(6);
        arvore.inserir(10);
        arvore.remover(10);

        System.out.println("Caminhamento pré-ordem:");
        arvore.preOrdem();

        System.out.println("\nCaminhamento em ordem:");
        arvore.central();

        System.out.println("\nCaminhamento pós-ordem:");
        arvore.posOrdem();

        // Salvar no arquivo dot pra visualização
        arvore.gerarArqDot("arvoreBin.dot");
    }
}
