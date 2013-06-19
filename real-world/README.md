Real World
==========

No green-field tudo é muito fácil e muito bonito. Mas e no mundo real?! Como implementar e abordar as questões de Continuous Delivery?

Para tentar responder essa pergunta e debater assuntos que não foram falados no exemplo green-field, criamos essa aplicação que simula um ambiente mais real e cotidiano de desenvolvimento.

Preparação do Ambiente
----------------------

Para poder utilizar esse exemplo, você precisará preparar seu ambiente instalando um [PostgreSQL](http://www.postgresql.org/) e um [Tomcat](http://tomcat.apache.org/). Comecemos pelo banco de dados:
```
sudo apt-get install postgresql-9.1 pgadmin3
```
Pelo comando acima, além de instalar o PostgreSQL, propriamente dito, instalaremos, também, o PgAdmin, uma ferramenta visual para gerenciar bases de dados PostgreSQL. Vamos criar um usuário para acessar a base de dados:
```
sudo su - postgres
psql
create user dextraining with createdb login password 'dextraining';
create database "continuous-delivery" owner dextraining;
\q
```
Nesse ponto temos um usuário dextraining com um banco de dados chamado continuous-delivery. Para habilitar a conexão via pgadmin, precisamos acertar as configurações do PostgreSQL:
```
sudo su - postgres
vim /etc/postgresql/9.1/main/pg_hba.conf
```
Dentro do arquivo garanta que as configurações abaixo estarão da seguinte forma:
```
# Database administrative login by Unix domain socket
# Deixar como TRUST
local   all             all                                     trust

# "local" is for Unix domain socket connections only
# Comentar a linha peer
#local   all             all                                     peer
```
Para garantir que as atualizações sejam carregadas, execute o comando abaixo:
```
sudo su - postgres
pg_ctlcluster 9.1 main reload
```
Feito isso, você estará com seu banco de dados preparado para ser acessado pela aplicação que trabalharemos e pelo pgadmin.

Ambiente de Desenvolvimento
---------------------------

Para construir seu ambiente de desenvolvimento, você precisará gerar os arquivos do [Eclipse](http://www.eclipse.org/):
```
mvn eclipse:eclipse
```

Para subir a aplicação e testá-la localmente:
```
mvn mycontainer:start
```

Ambiente de Deploy
------------------

A aplicação está configurada para utilizar o seguinte ambiente de deploy:
[http://dextraining-intranet-cd.appspot.com/](http://dextraining-intranet-cd.appspot.com/)
