# 🎓 **AvaliAlfa** - Sistema de Correção Automatizada de Provas

**AvaliAlfa** é uma plataforma acadêmica desenvolvida para automatizar a correção de provas objetivas, acelerar a entrega de resultados aos alunos e permitir a integração futura com sistemas acadêmicos institucionais.

O projeto foi idealizado com foco em **eficiência, usabilidade, escalabilidade** e adoção de **boas práticas de desenvolvimento web**, utilizando Java com Spring Boot no backend e Flutter no frontend mobile.

O projeto permite por parte do administrador criar, listar, editar e excluir os professores, alunos, turmas, disciplinas e usuários. Permite também fazer o vinculo entre o mesmos criando uma turma completa.

Por parte do profesor é possível criar provas, separando as mesmas por disciplina e por bimestre, podendo escolher quantas questões ele quer, qual o enunciado, quais as alternativas e qual a correta, além de abribuir nota individual a cada questão. E ele pode tambem ver a estatistica daquela turma naquela prova em específico no geral. O professor também tem acesso ao App em flutter que ele utiliza para enviar as respostas dos alunos para a API realizar a correção.

E por parte do aluno, ele tem acesso ao sistema web para consultar as suas notas, filtrando por turma e disciplina.

---

## 🛠️ Tecnologias Utilizadas

### Backend (Java)
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security (perfis: Administrador, Professor, Aluno)
- Thymeleaf
- MySQL
- Bootstrap

### Frontend (Mobile)
- Flutter
- HTTP Client para integração com API
- Interface limpa e amigável, focada na usabilidade dos alunos

---

## Funcionalidades da Aplicação

### Autenticação
- Login com perfis: **Administrador**, **Professor**, **Aluno**
- Controle de acesso baseado em permissões

### Gestão Acadêmica
- Cadastro e gerenciamento de:
    - Turmas
    - Disciplinas
    - Alunos
    - Associação de alunos às turmas

### Provas e Correção
- Criação de **provas objetivas**, com identificação por turma, disciplina e data
- Importação das respostas dos alunos via API REST (consumida pelo app Flutter)
- Processamento automático das respostas com:
    - Contagem de acertos/erros
    - Cálculo da nota final por aluno

### Resultados e Estatísticas
- Professores podem:
    - Consultar notas dos alunos por prova
    - Visualizar estatísticas da turma (média, distribuição de notas)
- Alunos podem:
    - Consultar suas notas e desempenho

---

## Integração com Flutter

- Endpoints REST seguros e documentados
- App Flutter consome a API para envio de respostas e consulta de notas
- Integração em tempo real para maior eficiência

---

- ## ▶️ Como Executar o Projeto

### Backend (Spring Boot)

1. Clone o repositório:
   ```bash
   git clone https://github.com/mariaguirelli/Hackathon2025.git

### 2. Configure o application.properties com seu banco MySQL:

spring.application.name=java
spring.datasource.url=jdbc:mysql://localhost:3306/hackathon2025
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.tomcat.persist-session=false

jwt.chave=uma-chave-super-secreta-com-pelo-menos-32-caracteres1234
jwt.expiracao=3600000

### 3. Execute o projeto
    ./mvnw spring-boot:run

### 4. Acesse
http://localhost:8080/home

---
### Frontend (Flutter)

### 1. Navegue para o app
    cd ../flutter_app  

### 2. Instale dependências
    flutter pub get

### 3. Execute:
    flutter run







