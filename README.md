## Funcionalidades da API

A API do ForumHub Alura oferece os seguintes endpoints para gerenciamento de tópicos:

* **Cadastro de Tópico (`POST /topicos`)**:
    * Cria um novo tópico no fórum.
    * **Validações**: Título e mensagem devem ser únicos.
    * **Retorna**: `201 Created` e os detalhes do tópico criado.
    * **Exemplo de Corpo da Requisição (JSON)**:
        ```json
        {
            "titulo": "Novo Tópico",
            "mensagem": "Conteúdo da mensagem...",
            "autor": "Nome do Autor",
            "curso": "Nome do Curso"
        }
        ```

* **Listagem de Tópicos (`GET /topicos`)**:
    * Retorna uma lista paginada de todos os tópicos **ativos**.
    * **Paginação**: Suporta parâmetros de paginação (`page`, `size`, `sort`).
        * Ex: `/topicos?page=0&size=10&sort=dataCriacao,desc` (primeira página, 10 itens, ordenados pela data de criação decrescente).
    * **Retorna**: `200 OK` e uma página de tópicos.

* **Detalhamento de Tópico (`GET /topicos/{id}`)**:
    * Retorna os detalhes de um tópico específico, **apenas se estiver ativo**.
    * **Retorna**: `200 OK` e os detalhes do tópico, ou `404 Not Found` se o ID não existir ou o tópico estiver inativo.

* **Atualização de Tópico (`PUT /topicos/{id}`)**:
    * Atualiza as informações de um tópico existente, **apenas se estiver ativo**.
    * **Retorna**: `200 OK` e os detalhes atualizados do tópico, ou `404 Not Found` se o ID não existir ou o tópico estiver inativo.
    * **Exemplo de Corpo da Requisição (JSON)**:
        ```json
        {
            "titulo": "Título Atualizado (opcional)",
            "mensagem": "Mensagem Atualizada (opcional)",
            "status": false, 
            "autor": "Autor Atualizado (opcional)",
            "curso": "Curso Atualizado (opcional)"
        }
      
        ```

* **Exclusão de Tópico (`DELETE /topicos/{id}`)**:
    * Realiza uma **exclusão lógica (soft delete)**, marcando o tópico como inativo, **apenas se ele estiver ativo**. O registro permanece no banco de dados.
    * **Retorna**: `204 No Content` em caso de sucesso, ou `404 Not Found` se o ID não existir ou o tópico já estiver inativo.

## Configuração de Ambiente (Perfis)

A aplicação suporta múltiplos perfis de ambiente para facilitar o desenvolvimento e a implantação:

* **`dev` (Desenvolvimento - Padrão)**:
    * Utiliza um banco de dados **H2 em memória**.
    * O schema do banco é atualizado automaticamente (`ddl-auto=update`).
    * O console do H2 pode ser acessado em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:forumhub_alura_dev`, User: `sa`, Password: ` `).
    * Ativado por padrão via `spring.profiles.active=dev` no `application.properties`.

* **`prod` (Produção)**:
    * Utiliza o banco de dados **MySQL**.
    * O schema é validado (`ddl-auto=validate`), e as migrations são gerenciadas pelo Flyway.
    * Para ativar este perfil, altere `spring.profiles.active=prod` no `application.properties` ou use a VM Option `-Dspring.profiles.active=prod` ao iniciar a aplicação.

### Como Rodar a Aplicação

**Pré-requisitos:**
* Java 17 (ou superior)
* Maven (para gerenciar dependências)
* MySQL (para o perfil `prod`)

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/forumhubalura.git](https://github.com/seu-usuario/forumhubalura.git)
    cd forumhubalura
    ```
2.  **Configurar o Banco de Dados (MySQL - para perfil `prod`):**
    * Certifique-se de que o MySQL esteja rodando e que as credenciais em `src/main/resources/application-prod.properties` estejam corretas.
    * As migrations do Flyway criarão a tabela necessária.

3.  **Rodar a aplicação:**
    * **Perfil de Desenvolvimento (H2 - Padrão):**
        ```bash
        ./mvnw spring-boot:run
        ```
      Ou inicie diretamente pela sua IDE (IntelliJ, Eclipse, etc.).

    * **Perfil de Produção (MySQL):**
        ```bash
        ./mvnw spring-boot:run -Dspring.profiles.active=prod
        ```
      Ou configure nas VM Options da sua IDE (`-Dspring.profiles.active=prod`).

A API estará disponível em `http://localhost:8080`.

Desenvolvido por Luciane de Castro para o Desafio Forum Hub Alura - Fase 3 Tech Foundation - Cursos Spring Boot