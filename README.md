# Banco-api

O projeto deve permitir as seguintes operações:

- Consulta de saldo;
- Transferência entre contas;
- Reverter uma transferência;
- Programar uma transferência futura parcelada. 

(Ex: Usuário informa que quer transferir 300,00 em 3x. Isso deve gerar 3 registros em outra entidade com cada transferência a ser realizada. Não é necessário implementar nada referente a efetivação dessas transferências futuras. Queremos ver somente a listagem de transferências futuras.)


# Banco de dados
* Aplicação com banco de dados em memória.

* URL para acesso ao banco de dados: <http://localhost:8080/banco/h2-console> (user: sa password: root)

* O arquivos schema.sql contém as definições das tabelas e o cadastro relativo a duas contas.


# Aplicação

- Aplicação desenvolvida com spring boot com spring data JPA

- **java.version:** 8
- **context-Path:** /banco
- **port:** 8080

   
- Imagem docker: `		docker run -p 8080:8080 thiagobsn/banco-api`

   
- O arquivo **banco-api.postman_collection.json** contém a colletion com os endpoints criados. 
- Link Collection Postman: <https://www.getpostman.com/collections/d437742c72f974f0fc27>


- Executar local:`	mvn clean install spring-boot:run`











