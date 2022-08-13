# Family Budget Control
Challenge Back-end do Alura, sobre API REST de Controle de Orçamento Familiar.
A aplicação deve permitir que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.

![image](https://user-images.githubusercontent.com/101612046/184511808-8b5c0290-5f8e-4ddf-89bf-564dca910a63.png)

</hr>

## DESENVOLVIMENTO

* Spring Boot
* DozerMapper
* MySQL
* Flyway
* Postman
* JUnit 5 and Mockito

## NOVIDADES
Pretendo continuar evoluindo o projeto colocando Swagger, Teste de Integração e JWT.

## ENDPOINT's

A seguir os endpoint's:

</br>

- Criar receita ou despesa
</br>

   ```json
    {
    "descricao": "Salário",
    "data": "2022-08-12",
    "valor": "2660.00"
    }
   ```
</br>

- Atualizar receita ou despesa
Passando `ID` via path 
</br>

   ```json
    {
    "descricao": "Salário",
    "data": "2022-08-12",
    "valor": "2660.00"
    }
   ```
</br>

- Deletar despesa ou receita
Passando `ID` via path, não possui retorno.
</br>

- Listar todas as despesas e receitas
</br>

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
Passando `descricao` via requestParam e `mes and ano` via path
</br>

   ```json
[
    {
        "descricao": "Alimentação",
        "categoria": "Saúde",
        "data": "2022-08-13",
        "valor": 200.0
    }
]
   ```
</br>

- Buscar receitas e despesas pelo ID
Passando `ID` via path 
</br>

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
Passando `mes and ano` via path
</br>

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
    ]
}
   ```
</br>
