# Ferramentas do Ambiente de Desenvolvimento (Orion)

- [Git](https://git-scm.com/)
- [JDK](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Node LTS](https://nodejs.org/en/)
- [Eclipse](https://www.eclipse.org/downloads/)
- [VsCode](https://code.visualstudio.com/)
- [ConEmu](https://conemu.github.io/en/Downloads.html)


# Gerar build do spring boot (backend)

Acessar o diretório do projeto:

Exemplo: C:\live\workspace\orion-backend

## As propriedades com as configurações do ambiente de desenvolvimento e produção estão separadas nos arquivos:

### application-dev.properties (desenvolvimento)

spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.generate-ddl: false
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc\:oracle\:thin\:@172.16.1.100\:1521\:TESTE
spring.datasource.username=teste
spring.datasource.password=teste
spring.datasource.initialization-mode=always
server.port = 8080

### application-prod.properties (produção)

spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.generate-ddl: false
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc\:oracle\:thin\:@172.16.1.100\:1521\:DBSYSTEX
spring.datasource.username=SYSTEXTIL
spring.datasource.password=ORACLE
spring.datasource.initialization-mode=always
server.port = 8081

Rodar os comandos:

### `mvn clean install`

Estem comando irá gerar o arquivo jar executável na pasta target do projeto.

### `java -jar -Dspring.profiles.active=prod nome_arquivo.jar` 

Esse comando irá iniciar o servidor tomcat apontando para o arquivo de configuração do ambiente de produção.

# Gerar build do React (frontend)

Primeiro deve ser atualizado a pasta public/images com as imagens das referências:

\\AD02\Arquivos\Grupos\Marketing - Empresa\Fotos BI\


## As propriedades com as configurações do ambiente de desenvolvimento e produção estão nos arquivos:

.env
.env.production

-> baseURL: "http://172.16.1.23:8081"  // Produção - Micro 23

Após isso rodar os comandos:

### `npm run build`

Este comando irá gerar o build do frontend para o ambiente de produção.

### `npm install -g serve`

Este comando instala um servidor estático de páginas web.
Deve ser executado apenas uma vez para instalar.
Depois bastará executar o comando abaixo:

### `serve -s build -l 4000`

Inicia a aplicação frontend na porta 4000. 

## Acesso ao Sistema (Produção):

http://172.16.1.23:4000
