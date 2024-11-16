
# Atividade 15 - Relacionamento Many-to-Many

Este projeto faz parte das atividades propostas pela **Codifica Edu** e tem como objetivo implementar uma aplicação utilizando Spring Boot para gerenciar o relacionamento Many-to-Many entre entidades `Aluno` e `Curso`, com suporte a operações básicas de CRUD e listagem.

## 📋 Descrição do Projeto

O cenário envolve a modelagem de um sistema escolar onde alunos podem se matricular em vários cursos e cursos podem conter diversos alunos. Para isso, foi implementado um relacionamento Many-to-Many utilizando JPA e Spring Boot.

### Objetivos
- Implementar e gerenciar o relacionamento Many-to-Many entre as entidades `Aluno` e `Curso`.
- Criar APIs REST para operações de CRUD e consultas específicas.
- Modelar a base de dados utilizando JPA e gerar a tabela de junção automaticamente.

## 🚀 Tecnologias Utilizadas

- **Framework:** Spring Boot 3.x
- **Banco de Dados:** H2 (desenvolvimento) e/ou MySQL (produção)
- **Linguagem:** Java 17+
- **Dependências:** Spring Data JPA, Spring Web, Spring Boot Starter

## 📁 Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com/
│   │   │   ├── maisprati/
│   │   │   │   ├── tarefa15/
│   │   │   │   │   ├── entities/
│   │   │   │   │   │   ├── Aluno.java      # Entidade Aluno
│   │   │   │   │   │   ├── Curso.java      # Entidade Curso
│   │   │   │   │   ├── repositories/
│   │   │   │   │   │   ├── AlunoRepository.java  # Repositório Aluno
│   │   │   │   │   │   ├── CursoRepository.java  # Repositório Curso
│   │   │   │   │   ├── services/
│   │   │   │   │   │   ├── AlunoService.java     # Lógica de negócios para Alunos
│   │   │   │   │   │   ├── CursoService.java     # Lógica de negócios para Cursos
│   │   │   │   │   ├── controllers/
│   │   │   │   │   │   ├── AlunoController.java  # Endpoints relacionados a Alunos
│   │   │   │   │   │   ├── CursoController.java  # Endpoints relacionados a Cursos
│   └── resources/
│       ├── application.properties  # Configurações da aplicação
```

## 🛠️ Funcionalidades Implementadas

- **CRUD de Alunos e Cursos:** 
  - Adicionar, atualizar e remover alunos e cursos.

- **Matrícula em Cursos:**
  - Adicionar/remover um aluno de um curso.

- **Listagem e Consulta:**
  - Listar cursos de um aluno.
  - Listar alunos de um curso.
  - Buscar alunos por e-mail e cursos por nome (desafio adicional).

## 🔧 Como Executar o Projeto

### Pré-requisitos
- **Java 17** ou superior instalado.
- Maven configurado na máquina.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/fernando-angeli/maisPraTi_tarefa15.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd maisPraTi_tarefa15
   ```
3. Configure as dependências e compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse a aplicação em `http://localhost:8080`.

## 🧪 Endpoints Disponíveis

### Alunos
- **POST /api/alunos:** Criar um novo aluno.
- **GET /api/alunos/{id}/cursos:** Listar os cursos de um aluno.
- **POST /api/alunos/{id}/cursos/{cursoId}:** Matricular aluno em um curso.
- **DELETE /api/alunos/{id}/cursos/{cursoId}:** Remover matrícula de um curso.

### Cursos
- **POST /api/cursos:** Criar um novo curso.
- **GET /api/cursos/{id}/alunos:** Listar os alunos de um curso.

### Consultas Adicionais (Opcional)
- **GET /api/alunos/search?email={email}:** Buscar alunos por e-mail.
- **GET /api/cursos/search?nome={nome}:** Buscar cursos por nome.

## 🧪 Testes

Para executar os testes:
```bash
mvn test
```

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir **issues** ou enviar **pull requests**.

## 📜 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---
