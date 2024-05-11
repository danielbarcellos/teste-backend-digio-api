# TESTE | BACKEND | DANIEL RIBEIRO BARCELLOS

Crie um microserviço com os seguintes endpoints após consumir os dados dos mocks abaixo e retorne o que está sendo solicitado

* GET: /compras - Retornar uma lista das compras ordenadas de forma crescente por valor, deve conter o nome dos clientes, cpf dos clientes, dado dos produtos, quantidade das compras e valores
 totais de cada compra.

* GET: /maior-compra/ano - (Exemplo: /maior_compra/2016) - Retornar a maior compra do ano informando os dados da compra disponibilizados, deve ter o nome do cliente, cpf do cliente, dado do
 produto, quantidade da compra e seu valor total.

* GET: /clientes-fieis - Retornar o Top 3 clientes mais fieis, clientes que possuem mais compras recorrentes com maiores valores.

* GET: /recomendacao/cliente/tipo - Retornar uma recomendação de vinho baseado nos tipos de vinho que o cliente mais compra.

Entrega da prova: enviar o link do repositório no GitHub.

Stack de tecnologias: a prova deve ser feita em Spring Boot com versão Java >= 11

## Getting Started

Clone de [GIT Hub](https://github.com/danielbarcellos/teste-backend-digio-api) e então use Maven 3.* e Java 11:

```
git clone ...
mvn -U clean install
java -jar target/teste-backend-digio-api
```
## API's
* [Lista de Produtos](https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json) - Lista de Produtos.
* [Lista de Clientes e Compras](https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json) - Lista de Clientes e Compras.

## Swagger URL

http://localhost:8000/digio/api/swagger-ui/index.html

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - Plataforma de aplicações.

## Versionamento

We use [GIT Hub](https://github.com) for versioning. For the versions available, see the [tags on this repository](https://github.com/danielbarcellos/teste-backend-digio-api). 

## Authors

* **Daniel Barcellos** - *Initial work* - [Daniel Barcellos](https://github.com/danielbarcellos)

## Agradecimentos

* My family
* Google
