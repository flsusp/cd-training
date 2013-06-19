Green Field
===========

No mundo de desenvolvimento de software, chamamos de green-field aqueles cenários aonde tudo é belo, tudo é maravilhoso e tudo fica muito simples e fácil de acontecer. Para entedermos melhor alguns conceitos do Continuous Delivery, decidimos criar um ambiente aonde fosse possível exercitar essa dinâmica.

Para esse treinamento, nosso green-field será uma intranet desenvolvida em java e hospedada no [GAE](https://developers.google.com/appengine/?hl=pt-br "Google Application Engine"). De uma forma resumida, essa aplicação está estruturada em serviços [Rest](http://www.jboss.org/resteasy "RestEasy") que responde dados em formato [JSON](http://www.json.org/ "JSON"). Esses serviços (*RS.java) comunicam-se com uma base dados [NoSQL](https://developers.google.com/appengine/docs/java/datastore/?hl=pt-br "DataStore") para a persistência das informações. Do lado client, por sua vez, as informações são exibidas e manipuladas através de templates HTML utilizando CSS e JavaScript.

Ambiente de Desenvolvimento
---------------------------

Para construir seu ambiente de desenvolvimento, você precisará gerar os arquivos do [Eclipse](http://www.eclipse.org/):
```
mvn eclipse:eclipse
```

Para subir a aplicação e testá-la localmente, existem duas possibilidades:
```
mvn mycontainer:start
```
ou
```
mvn appengine:devserver
```

A aplicação está configurada tanto para utilizar o [mycontainer](http://code.google.com/p/mycontainer/) quanto o próprio servidor de desenvolvimento provido pelo GAE. O mycontainer não suporta alguns recursos do GAE, como o upload de arquivos. O devserver (GAE), por sua vez, provê todos os recursos do GAE mas não tem hotdeploy para HTML, CSS e JS.

Ambiente de Integração Contínua
-------------------------------

Para essa aplicação, existe um ambiente de integração contínua hospedado no [CloudBees](http://www.cloudbees.com/). Para acessá-lo, utilize as seguintes informações:

* URL: [https://grandcentral.cloudbees.com](https://grandcentral.cloudbees.com)
* Usuário: leandro.guimaraes@dextra-sw.com
* Senha: continuous-delivery

A configuração está feita para que a cada push realizado no repositório, um novo deploy em produção é realizado.

Ambiente de Deploy
------------------

A aplicação está configurada para utilizar o seguinte ambiente de deploy:
[http://dextraining-intranet-cd.appspot.com/](http://dextraining-intranet-cd.appspot.com/)
