# Family Budget Control
Challenge Back-end do Alura, sobre API REST de Controle de Orçamento Familiar.
A aplicação deve permitir que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.

![image](https://user-images.githubusercontent.com/101612046/185710169-0a9de933-0484-4036-8896-14fd596125c4.png)
![image](https://user-images.githubusercontent.com/101612046/185710265-cb43dfda-6841-4aa0-bcb7-69d21431b976.png)

</hr>

## DESENVOLVIMENTO

* <b>Spring Boot</b> (JPA, Test, Validation, Actuator, Cache, CORS e Hateoas)
* <b>DozerMapper</b> para conversão de VO para Entity
* <b>MySQL</b> e <b>Flyway</b> para banco de dados
* <b>Postman</b> para teste dos endpoints
* <b>JUnit 5</b>, <b>MockMVC</b> e <b>Testcontainers</b> para testes unitários
e de integração
* <b>Apache POI</b> para geração de XLSX
* <b>Swagger</b> para documentação dos endpoints
* <b>JWT</b> com security para autenticação via token

* <b>Docker</b> para build/run em containers
[![Docker Hub Repo](https://img.shields.io/docker/pulls/mulhermarav/familybudgetcontrol.svg)](https://hub.docker.com/repository/docker/mulhermarav/familybudgetcontrol)

## VERSÃO
 
 Java 17 JDK


## ENDPOINT's

A seguir os endpoint's:

</br>

- Criar receita ou despesa

 ```json
    {
      "descricao": "Salário",
      "data": "2022-08-12",
      "valor": "2660.00"
    }
 ```
</br>

- Atualizar receita ou despesa
<p></p>
Passando `ID` via path

```json
    {
      "descricao": "Salário",
      "data": "2022-08-12",
      "valor": "2660.00"
    }
```
</br>

- Deletar despesa ou receita
<p></p>
Passando `ID` via path, não possui retorno.
</br>
</br>
</br>

- Listar todas as despesas e receitas

```json
    {
        "descricao": "Alimentação",
        "categoria": "Saúde",
        "data": "2022-08-13",
        "valor": 200.0
    },
    {
        "descricao": "Gasolina",
        "categoria": "Outras",
        "data": "2022-08-13",
        "valor": 250.0
    },
    {
        "descricao": "Diesel",
        "categoria": "Outras",
        "data": "2022-08-13",
        "valor": 350.0
    }
 ```
</br>

- Listar despesas e receitas por descrição e mês
<p></p>
Passando `descricao` via requestParam e `mes and ano` via path

```json
    {
       "descricao": "Alimentação",
       "categoria": "Saúde",
       "data": "2022-08-13",
       "valor": 200.0
    }
```
</br>

- Buscar receitas e despesas pelo ID
<p></p>
 Passando `ID` via path

```json
  {
     "descricao": "Alimentação",
     "categoria": "Saúde",
     "data": "2022-08-13",
     "valor": 200.0
   }
```
</br>

- Resumo mensal das receitas e despesas
<p></p>
Passando `mes and ano` via path

```json
{
    "valor_total_receitas": 2660.0,
    "valor_total_despesas": 800.0,
    "saldo_final": 1860.0,
    "valor_total_por_categoria": [
        {
            "categoria": "Saúde",
            "total": 200.0
        },
        {
            "categoria": "Outras",
            "total": 600.0
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/budget-control/resumo/downloadCsv/2022/8"
        }
    }
}
```
</br>

- Resumo mensal das receitas e despesas em XLSX
<p></p>
Passando `mes and ano` via path, é gerado um XLSX.

![image](https://user-images.githubusercontent.com/101612046/184937592-39e0f087-27c6-45ac-8eec-28a85765a261.png)

</br>

- Autenticação via credenciais
<p></p>
Passando `email and senha` via body em JSON

```json
{
    "email": "dyane.aaraujo@gmail.com",
    "autenticado": true,
    "criado": "2022-08-19T21:21:05.062+00:00",
    "expiracao": "2022-08-19T22:21:05.062+00:00",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWFuZS5hYXJhdWpvQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNVQVJJTyIsIlJPTEVfQURNSU5JU1RSQURPUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE2NjA5NDc2NjUsImlhdCI6MTY2MDk0NDA2NX0.e_ZpaHAY91eTFUhb7L9U93R-VWkY4HuebOG85wtCSbU",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWFuZS5hYXJhdWpvQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNVQVJJTyIsIlJPTEVfQURNSU5JU1RSQURPUiJdLCJleHAiOjE2NjA5NTQ4NjUsImlhdCI6MTY2MDk0NDA2NX0.dWoEIqeezDdaYLURoxqTRNgrW6Mph4OaW90UVtWA1w0"
}
```
</br>

- Refresh token
<p></p>
Passando `nome` via path e o `token refresh` no header

```json
{
    "email": "dyane.aaraujo@gmail.com",
    "autenticado": true,
    "criado": "2022-08-19T21:00:49.604+00:00",
    "expiracao": "2022-08-19T22:00:49.604+00:00",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWFuZS5hYXJhdWpvQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNVQVJJTyIsIlJPTEVfQURNSU5JU1RSQURPUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE2NjA5NDY0NDksImlhdCI6MTY2MDk0Mjg0OX0.vNEaViTQS9J0FWeKVbxtL1VQcP0l0dsGXVOG9PvvxL4",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWFuZS5hYXJhdWpvQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNVQVJJTyIsIlJPTEVfQURNSU5JU1RSQURPUiJdLCJleHAiOjE2NjA5NTM2NDksImlhdCI6MTY2MDk0Mjg0OX0.5EUNNtpFNih2QGaEdgTBpjRvIz7y91aRvwVut466mpk"
}
```
</br>

</hr>

## SWAGGER

![image](https://user-images.githubusercontent.com/101612046/185709535-782a854f-9742-48c0-be71-bf2c0ad7547e.png)
![image](https://user-images.githubusercontent.com/101612046/185709596-ed2d2e45-f616-41e0-995b-6646d2450913.png)
![image](https://user-images.githubusercontent.com/101612046/185709682-e13e13a2-1e58-4c81-93da-451e907906ed.png)



