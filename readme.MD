# Backend do Teste-Java

Este é o backend do aplicativo Teste-Java, desenvolvido em Java utilizando o framework Spring Boot. O backend fornece APIs e serviços para processamento de dados do aplicativo.
Base: Java 17 / Spring boot 3.0.1
## Dependências

O backend utiliza as seguintes dependências:

- **spring-boot-starter-data-jpa:** Fornece suporte para acesso e persistência de dados usando o JPA (Java Persistence API).
- **spring-boot-starter-security:** Fornece recursos de segurança para proteger as APIs e os dados.
- **spring-boot-starter-validation:** Adiciona validação de dados usando as anotações do Bean Validation.
- **spring-boot-starter-web:** Fornece suporte para criar APIs RESTful e serviços web.
- **flyway-core:** Gerencia as migrações do banco de dados.
- **flyway-mysql:** Adiciona suporte específico para o MySQL nas migrações do Flyway.
- **mysql-connector-j:** Fornece o driver JDBC para conexão com o banco de dados MySQL.
- **lombok:** Biblioteca que simplifica a criação de classes Java reduzindo a quantidade de código boilerplate.
- **spring-boot-starter-test:** Fornece suporte para testes unitários e de integração do Spring Boot.
- **spring-security-test:** Fornece suporte para testes de segurança do Spring Security.
- **jakarta.persistence-api:** Fornece a API de persistência Jakarta Persistence, baseada no JPA.
- **java-jwt:** Biblioteca para manipulação de tokens JWT (JSON Web Tokens).
- **jjwt-api:** API do JJWT (Java JWT), uma implementação de tokens JWT para Java.
- **jjwt-impl:** Implementação do JJWT.
- **jjwt-jackson:** Integração do JJWT com a biblioteca Jackson para serialização e desserialização de objetos JWT.

## Funcionalidades

O backend oferece as seguintes funcionalidades:

- **Persistência de Dados:** Utiliza o JPA para persistir os dados em um banco de dados MySQL. As entidades e repositórios são configurados para realizar operações de CRUD (create, read, update, delete) nos dados do aplicativo.
- **Segurança:** Utiliza o Spring Security para fornecer autenticação e autorização nas APIs do aplicativo. O backend utiliza tokens JWT para autenticar as requisições dos usuários.
- **Validação de Dados:** Utiliza a biblioteca Bean Validation e suas anotações para validar os dados de entrada nas APIs.
- **Migrações de Banco de Dados:** Utiliza o Flyway para gerenciar as migrações do banco de dados. As migrações são executadas automaticamente durante o processo de inicialização do aplicativo.
- **Testes:** Fornece suporte para testes unitários e de integração utilizando o Spring Boot e o Spring Security Test.

## Executando o Backend

Para executar o backend, siga as etapas abaixo:

1. Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina e Java 17.

1.1. Crie um novo scheema no banco de dados MySQL com o nome: teste_wk

1.2. Defina a senha e usuário da conexão local do banco de dados como: 
```shell
      username: root
      password: root
```
ou

Acesse o arquivo application-dev.yaml e altere os dados do parametro:
 
 ```shell
  spring:
  datasource:
    url: jdbc:mysql://localhost:3306/"_nome do banco desejado_"?useSSL=false
    username: "_usuario desejado_"
    password: "_senha desejada_"
 ```

2. Clone o repositório do aplicativo para o seu ambiente local.

```shell
git clone https://github.com/bryanwac/teste_java_wk.git
```

3. Navegue até o diretório raiz do backend e execute com sua IDE preferida (Recomenddado: IntelliJ).


4. Compile e execute o aplicativo usando o Maven.

```shell
Execute o projeto utilizando sua IDE ou executando a classe principal.

```

5. O backend será iniciado e estará acessível em `http://localhost:8080`.

Agora você pode integrar o frontend do aplicativo com o backend para obter todas as funcionalidades do Teste-Java.

## Contribuição

Contribuições para melhorar o backend são bem-vindas. Se você encontrar problemas ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue no repositório ou enviar um pull request.

Esperamos que o backend forneça todas as funcionalidades necessárias para o aplicativo Teste-Java.
