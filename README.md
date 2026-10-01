# 💈 Dark Barber — Sistema de Agendamentos com Notificação via WhatsApp

Sistema completo de gerenciamento e agendamento de serviços de barbearia em arquitetura monorepo (Spring Boot + Angular), com envio de notificações em tempo real para o WhatsApp do cliente.

---

## 🚀 Destaques do Projeto

* **📱 Notificação Ativa no WhatsApp:** Integração com a API da Twilio para confirmação de agendamentos.
* **⚡ Processamento Assíncrono:** Uso de `@Async` no Spring Boot para disparar mensagens em segundo plano sem travar a API.
* **🔒 Sanitização de Dados:** Mapeamento dinâmico de telefone no padrão E.164 e injeção segura de parâmetros em templates.
* **🏗️ Arquitetura Monorepo:** Backend e Frontend organizados e versionados em um único repositório.

---

## 🛠 Tecnologias Utilizadas

### Backend (`barber-shop-api`)
* Java 21 & Spring Boot 3.4.2
* Spring Data JPA & PostgreSQL
* Twilio SDK

### Frontend (`barber-shop-ui`)
* Angular 19
* TypeScript & RxJS
* HTML5 / CSS3 / Tailwind

---

## 📂 Estrutura do Repositório
dark-barber/
├── barber-shop-api/    # API Restful em Spring Boot
│   ├── src/main/java/  # Regras de negócio e Gateways
│   └── build.gradle    # Configurações do Gradle
└── barber-shop-ui/     # Aplicação Frontend em Angular
├── src/app/        # Componentes e serviços
└── package.json    # Dependências

---

## ⚙️ Configuração e Execução

### 1. Pré-requisitos
* Java 21+
* Node.js (v18+) e Angular CLI
* PostgreSQL

### 2. Variáveis de Ambiente (Twilio)
No arquivo `application.properties` da API, configure suas credenciais:

```` properties
twilio.account-sid=${TWILIO_ACCOUNT_SID:SEU_ACCOUNT_SID}
twilio.auth-token=${TWILIO_AUTH_TOKEN:SEU_AUTH_TOKEN}
twilio.whatsapp-from=${TWILIO_WHATSAPP_FROM:whatsapp:+14155238886}
````
3. Executando o Backend
Bash
cd barber-shop-api
./gradlew bootRun

4. Executando o Frontend


cd barber-shop-ui
npm install
ng serve


Desenvolvido por: Guilherme Junque Karabedrossian - Junior Java Developer
Bash

