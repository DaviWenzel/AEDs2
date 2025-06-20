import java.util.Scanner;
// CR7 É o maior DE TODOS (tirando o capanema)
public class Q11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testes = sc.nextInt();

        for (int t = 0; t < testes; t++) {
            int l1 = sc.nextInt();
            int c1 = sc.nextInt();
            Matriz m1 = new Matriz(l1, c1);
            for (int i = 0; i < l1; i++) {
                for (int j = 0; j < c1; j++) {
                    m1.setElemento(i, j, sc.nextInt());
                }
            }
            int l2 = sc.nextInt();
            int c2 = sc.nextInt();
            Matriz m2 = new Matriz(l2, c2);
            for (int i = 0; i < l2; i++) {
                for (int j = 0; j < c2; j++) {
                    m2.setElemento(i, j, sc.nextInt());
                }
            }

            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();

            Matriz soma = m1.soma(m2);
            soma.mostrar();

            Matriz mult = m1.multiplicacao(m2);
            mult.mostrar();
        }

        sc.close();
    }
}

class Matriz {
    
    private class Celula {
        int elemento;
        Celula sup, inf, esq, dir;
    
        Celula() {
            elemento = 0;
            sup = null;
            inf = null;
            esq = null;
            dir = null;
        }
    }

    int linhas, colunas;
    Celula inicio;

    Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        construir();
    }

    private void construir() {
        inicio = new Celula();
        Celula linhaAtual = inicio;
        Celula atual = linhaAtual;
        for (int j = 1; j < colunas; j++) {
            atual.dir = new Celula();
            atual.dir.esq = atual;
            atual = atual.dir;
        }
        for (int i = 1; i < linhas; i++) {
            Celula novaLinha = new Celula();
            novaLinha.sup = linhaAtual;
            linhaAtual.inf = novaLinha;

            Celula cel = novaLinha;
            Celula acima = linhaAtual.dir;

            for (int j = 1; j < colunas; j++) {
                cel.dir = new Celula();
                cel.dir.esq = cel;
                cel = cel.dir;

                cel.sup = acima;
                acima.inf = cel;
                acima = acima.dir;
            }

            linhaAtual = novaLinha;
        }
    }

    void setElemento(int i, int j, int valor) {
        Celula cel = getCelula(i, j);
        cel.elemento = valor;
    }

    int getElemento(int i, int j) {
        return getCelula(i, j).elemento;
    }

    private Celula getCelula(int i, int j) {
        Celula cel = inicio;
        for (int x = 0; x < i; x++) cel = cel.inf;
        for (int y = 0; y < j; y++) cel = cel.dir;
        return cel;
    }

    Matriz soma(Matriz m) {
        if (this.linhas != m.linhas || this.colunas != m.colunas) {
            throw new IllegalArgumentException("Dimensões incompatíveis para soma.");
        }

        Matriz resp = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int valor = this.getElemento(i, j) + m.getElemento(i, j);
                resp.setElemento(i, j, valor);
            }
        }
        return resp;
    }

    Matriz multiplicacao(Matriz m) {
        if (this.colunas != m.linhas) {
            throw new IllegalArgumentException("Dimensões incompatíveis para multiplicação.");
        }

        Matriz resp = new Matriz(this.linhas, m.colunas);

        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < m.colunas; j++) {
                int soma = 0;
                for (int k = 0; k < this.colunas; k++) {
                    soma += this.getElemento(i, k) * m.getElemento(k, j);
                }
                resp.setElemento(i, j, soma);
            }
        }

        return resp;
    }

    void mostrarDiagonalPrincipal() {
        Celula cel = inicio;
        while (cel != null) {
            System.out.print(cel.elemento + " ");
            cel = (cel.inf != null && cel.dir != null) ? cel.inf.dir : null;
        }
        System.out.println();
    }

    void mostrarDiagonalSecundaria() {
        Celula cel = inicio;
        for (int i = 0; i < colunas - 1; i++) {
            cel = cel.dir;
        }

        while (cel != null) {
            System.out.print(cel.elemento + " ");
            cel = (cel.inf != null && cel.esq != null) ? cel.inf.esq : null;
        }
        System.out.println();
    }

    void mostrar() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(getElemento(i, j) + " ");
            }
            System.out.println();
        }
    }
}
