# Automação de Testes - Projeto Leilão 🧪

Projeto de testes automatizados End-to-End (E2E) desenvolvido para validar fluxos críticos de uma aplicação de Leilões. Focado em robustez, arquitetura limpa e boas práticas de QA Engineering.

## 🛠 Tecnologias Utilizadas
* **Java 25**
* **Selenium WebDriver 4.26**
* **JUnit 5**
* **Maven**
* **Page Object Model (POM)**

## 🔥 Destaques Técnicos
* **Arquitetura POM:** Separação clara entre a lógica de teste e a interação com a página.
* **Explicit Waits:** Uso de `WebDriverWait` para eliminar *flaky tests* e problemas de sincronismo.
* **Chrome Options:** Configuração avançada para evitar detecção de automação e bloqueio de popups de segurança.
* **XPaths Dinâmicos:** Estratégias robustas para localizar elementos em tabelas dinâmicas.

## 🚀 Como rodar o projeto
1. Clone o repositório.
2. Certifique-se de ter o JDK instalado.
3. Execute via Maven ou IntelliJ: `Run 'NovoLeilaoTest'`