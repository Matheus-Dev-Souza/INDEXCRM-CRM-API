# 🚀 Index CRM API

O **Index CRM** é uma plataforma SaaS Multi-tenant focada em gestão de relacionamento com clientes, automação de marketing e vendas. O sistema oferece funcionalidades de Kanban, integração com WhatsApp (via Evolution API) e um construtor de fluxos de automação.

## 📋 Índice

- [🚀 Index CRM API](#-index-crm-api)
  - [📋 Índice](#-índice)
  - [✨ Funcionalidades](#-funcionalidades)
  - [🏗 Arquitetura](#-arquitetura)
  - [🛠 Tecnologias](#-tecnologias)
  - [📂 Estrutura do Projeto](#-estrutura-do-projeto)

## ✨ Funcionalidades

* **Multi-tenancy:** Isolamento de dados por empresa (SaaS).
* **Autenticação Segura:** Login via JWT com Spring Security.
* **CRM de Vendas:**
    * Gestão de Leads.
    * Pipelines Dinâmicos e Fases (Kanban).
    * 
* **Automação de Marketing:**
    * Construtor de Fluxos (Nodes & Edges).
    * Disparos automáticos (WhatsApp, E-mail).
    * Agendador de Tarefas (Scheduler).
* **Integrações:**
    * **WhatsApp:** Conexão via Evolution API.
    * **Webhooks:** Recebimento de leads de plataformas externas (Hotmart, Monetizze).

## 🏗 Arquitetura

O projeto segue uma arquitetura em camadas bem definida para garantir escalabilidade e manutenção:

* **Controller:** Pontos de entrada da API REST.
* **Service:** Regras de negócio complexas.
* **Repository:** Acesso a dados (Spring Data JPA).
* **Domain:** Entidades do banco de dados.
    * 

[Image of CRM Entity Relationship Diagram]

* **DTO:** Objetos de transferência de dados (Request/Response).
* **Integration:** Clientes HTTP para serviços externos.

## 🛠 Tecnologias

* **Java 17+**
* **Spring Boot 3** (Web, Security, Data JPA, Validation)
* **PostgreSQL** (Banco de dados)
* **Docker** (Containerização)
* **Maven** (Gerenciamento de dependências)
* **Evolution API** (Gateway de WhatsApp)
* **Swagger / OpenAPI** (Documentação)

## 📂 Estrutura do Projeto

A estrutura de pastas segue o padrão de domínio:

```text
com.indexcrm
├── config       # Configurações de Segurança, CORS e Swagger
├── controller   # Endpoints da API
├── domain       # Entidades JPA (SaaS, Sales, Automation, User)
├── dto          # Objetos de Entrada e Saída
├── integration  # Clientes externos (WhatsApp, Email)
├── mapper       # Conversores (Entity <-> DTO)
├── repository   # Interfaces de Banco de Dados
├── security     # Filtros e Provedores JWT
├── service      # Lógica de Negócios e Agendadores
└── resources    # Configurações (application.properties)