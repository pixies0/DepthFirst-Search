# Buscador de Componentes Biconexas em Grafos

Esta aplicação determina as componentes biconexas de um grafo utilizando um algoritmo de Busca em Profundidade (DFS). Ela identifica pontos de articulação como um passo intermediário. A implementação segue rigorosamente os algoritmos e conceitos discutidos nos slides da "Aula 7" e na Seção 4 do livro de Jayme.

## Funcionalidades

    Carregamento de Grafo: Lê matrizes de adjacência de um arquivo grafo.txt. O arquivo pode conter até 20 matrizes, separadas por uma linha em branco.

Seleção de Matriz: Após o carregamento, o programa informa ao usuário quantas matrizes foram carregadas e o solicita a selecionar uma para manipular.

Visualização do Grafo: Apresenta o grafo correspondente à matriz de adjacência selecionada, com os vértices numerados a partir de 1.

Menu Interativo: Fornece um painel de seleção múltipla para várias operações:

1. Apresentar Grafo: Mostra a representação gráfica do grafo.

2. Apresentar Árvore de Busca em Profundidade: Pergunta qual será o vértice raiz da busca, lista os vértices candidatos e, em seguida, apresenta a árvore de busca em profundidade com suas arestas de retorno (preferencialmente em cor diferente).
3. Apresentar Tabela Lowpt(v) e g(v): Apresenta uma tabela com os mapeamentos de lowpt(v) e g(v).
4. Listar Articulações: Apresenta a lista de todas as articulações do grafo com seus respectivos demarcadores.
5. Apresentar Componentes Biconexas: Apresenta cada componente biconexa do grafo (representação gráfica enraizada por Tw).

# Uso

    Prepare o arquivo grafo.txt: Crie um arquivo grafo.txt no mesmo diretório da aplicação. Preencha-o com as matrizes de adjacência, garantindo que cada matriz seja separada por uma linha em branco.

    Exemplo de conteúdo do grafo.txt:

    0011000100
    0000001000
    1001000000
    1010010000
    0000011010
    0001100000
    0100100100
    1000001001
    0000100001
    0000000110

    010
    101
    010

Execute a aplicação.
Siga as instruções na tela para selecionar um grafo e realizar as operações.