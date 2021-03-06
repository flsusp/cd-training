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
Feito isso, você estará com seu banco de dados preparado para ser acessado pela aplicação que trabalharemos e pelo pgadmin. Precisamos agora configurar o apache para que o exercício funcione corretamente.
```
sudo su -
apt-get install apache2
a2enmod proxy proxy_http proxy_balancer
```
Crie o arquivo /etc/apache2/conf.d/continuous-delivery com o seguinte conteúdo:
```
<VirtualHost *:80>
        <Proxy balancer://mycluster>
                BalancerMember http://localhost:8081 loadfactor=1
                BalancerMember http://localhost:8082 loadfactor=2
        </Proxy>
        ProxyPass / balancer://mycluster/
</VirtualHost>
```

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

Para testar o cenário com dois servidores no ar, acessando pelo apache:
```
mvn mycontainer:start -Dmyc01
mvn mycontainer:start -Dmyc02
```
