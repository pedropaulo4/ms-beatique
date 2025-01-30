```markdown
# Projeto CQRS com Spring Boot, Docker, MongoDB, PostgreSQL e RabbitMQ

## Descrição

Este projeto é uma implementação simples utilizando a arquitetura **CQRS** (Command Query Responsibility Segregation), que busca separar as responsabilidades de leitura e escrita em um sistema. Utiliza **Java 17**, **Spring Boot**, **Docker**, **MongoDB**, **PostgreSQL** e **RabbitMQ** para demonstrar como a arquitetura CQRS pode ser aplicada em um ambiente de microserviços.

### Arquitetura CQRS

A arquitetura **CQRS** divide o sistema em duas partes:

- **Command Side**: Responsável por todas as operações de escrita. Cada comando é tratado separadamente, o que permite que a lógica de negócios e a validação de escrita sejam mais complexas e independentes da parte de leitura.
- **Query Side**: Responsável pelas operações de leitura, com foco em consultas otimizadas, sem a necessidade de aplicar regras de negócio complexas ou validar os dados.

Esse padrão oferece uma melhor escalabilidade, permitindo que as partes de leitura e escrita possam ser escaladas de maneira independente, além de melhorar a performance e garantir maior consistência em sistemas mais complexos.

## Tecnologias Usadas

- **Java 17**: Linguagem de programação.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Docker**: Containerização da aplicação.
- **MongoDB**: Banco de dados NoSQL para armazenar dados de leitura.
- **PostgreSQL**: Banco de dados relacional para armazenar dados de escrita.
- **RabbitMQ**: Mensageria para comunicação assíncrona entre os serviços.

## Funcionalidades

- **Leitura (Query)**: Utiliza MongoDB para armazenar dados otimizados para leitura. Isso melhora a performance das consultas.
- **Escrita (Command)**: Utiliza PostgreSQL para garantir a integridade dos dados e possibilitar transações.
- **Mensageria**: RabbitMQ é usado para gerenciar as mensagens entre os serviços e garantir que as operações sejam feitas de forma assíncrona e desacoplada.

## Como Executar

### Pré-requisitos

- Java 17
- Docker
- Docker Compose

### Descrição Detalhada da Arquitetura CQRS:
Você pode destacar a divisão de responsabilidades na aplicação.
O lado de **Command** cuida das operações de gravação (inserção e atualização de dados) e pode se beneficiar de transações complexas e validações de regras de negócios.
Já o lado de **Query** foca em otimizar as consultas e garantir que as respostas sejam rápidas e com baixo custo de processamento, mesmo que o volume de dados seja grande.
