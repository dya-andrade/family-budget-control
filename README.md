# Family Budget Control
Challenge Back-end do Alura, sobre API REST de Controle de Orçamento Familiar.
A aplicação deve permitir que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.

![image](https://user-images.githubusercontent.com/101612046/184932167-c1ff7262-ea5d-4bfa-9ab6-053fa4c55f73.png)


</hr>

## DESENVOLVIMENTO

* <b>Spring Boot</b> (JPA, Test, Validation, Actuator, Cache e Hateoas)
* <b>DozerMapper</b> para conversão de VO para Entity
* <b>MySQL</b> e <b>Flyway</b> para banco de dados
* <b>Postman</b> para teste dos endpoints
* <b>JUnit 5</b>, <b>MockMVC<b/> e <b>Mockito</b> para testes unitários
* <b>Apache POI</b> para geração de XLSX

## NOVIDADES
Pretendo continuar evoluindo o projeto colocando Swagger, Teste de Integração e JWT (Security, Roles e Profile).

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
</br>

![image](https://user-images.githubusercontent.com/101612046/184937592-39e0f087-27c6-45ac-8eec-28a85765a261.png)
