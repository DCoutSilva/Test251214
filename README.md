# Test251214
Teste para Natixis e Mozantech

## Requisitos
 Desenvolva APIs que permitam incluir, alterar, deletar e consultar agendamento de transações bancárias.

Para cada transação, uma taxa de transferencia deve ser cobrada da seguinte forma:

Taxa A (valor da transferencia entre 0€ e 1000€)
  - Data do Agendamento igual Data Atual - 3% do valor da transação + 3€
    
Taxa B (valor da transferencia entre 1001€ e 2000€)
  - Data de agendamento entre 1 e 10 dias da data atual - 9%
    
Taxa C (valor da transferencia maior que 2000€)
  - Data de agendamento entre 11 e 20 dias da data atual - 8.2% do valor da transação
  - Data de agendamento entre 21 e 30 dias da data atual - 6.9% do valor da transação
  - Data de agendamento entre 31 e 40 dias da data atual - 4.7% do valor da transação
  - Data de agendamento maior que 40 dias da data atual - 1.7% do valor da transação


## Como executar
 - Clonar repositório
 - Utilizar um terminal ou cmd para navegar até a pasta raíz do projeto clonado
 - executar cd .\test
 - executar mvn clean install -DskipTest 
   (ainda sem testes unitarios nesta versão)
 - executar java -jar .\target\test-0.0.1-SNAPSHOT.jar

## Endpoints
[GET] http://localhost:8080/transfers
- lista as transacoes cadastradas

[GET] http://localhost:8080/transfer/{id}
- retorna a transacao do id especificado

[POST] http://localhost:8080/transfer
{
  "transferDate": <date>,
  "transferValue": <value>
}
- cadastra uma transação

[PATCH]http://localhost:8080/transfer/{id}
{
  "transferDate": <date>,
  "transferValue": <value>
}
- altera uma transação com o id especificado

[DELETE]http://localhost:8080/transfer/{id}
- Apaga a transação com o id especificado

- No repositório Git há uma postman collection com todos os endpoints disponíveis para teste e exemplos


## Swagger docs
 Acessíveis em http://localhost:8080/swagger-ui/index.html


## TODO
JUnit e fix status codes
