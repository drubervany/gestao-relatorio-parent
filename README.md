| Data | Autor | Comentários | Versão |
| --- | --- | --- | --- |
| 09/03/2017 | Danilo Rubervany Dias | Versão inicial | 1.0 |  

# Sobre esta aplicação

Este projeto é responsável por consultar, ativar e desativar .

#Sobre a arquitetura 
Este projeto está modelado com base na arquitetura hexagonal com spring boot.

A arquitetura hexagonal está modelada da seguinte forma:

> gestao-relatorio-parent

>> relatorio-adapter-inbound-parent

>>> relatorio-activemq-adapter-inbound

>> relatorio-adapter-outbound-parent

>>> relatorio-dynamodb-adapter-outbound

>> relatorio-build

>> relatorio-core-parent

>>> relatorio-core-application

>>> relatorio-core-port-commons

>>> relatorio-core-port-inbound

>>> relatorio-core-port-outbound



	* gestao-relatorio-parent
   
   Projeto centralizador do microserviço consultar, ativar e desativar .

	* relatorio-adapter-inbound-parent

   Projeto pai (Inbound/Primary Adapters), com os possíveis projetos filhos de implementação dos adapters. 

	* relatorio-activemq-adapter-inbound

   Adapter de entrada do core-application. Implementação da chamada em que o adapter converte um JSON para o DTO e enviar para o core-application.

	* relatorio-adapter-outbound-parent
   
   Projeto pai (Outbound/Secondary Adapters), com os possíveis projetos filhos de implementação dos adapters.

	* relatorio-dynamodb-adapter-outbound
	
   Implementação do Adapter de port-outbound, aplicando uma lista de regras de validação nos dados recebidos da fila, gravando no Dynamodb, em caso de sucesso na validação.

	* relatorio-build

   Projeto que gera um runnable jar com que foi definido no pom.xml. Adapter de entrada e Adapter de saída podem ser alterados.
   
	* relatorio-core-parent

   Projeto core centralizador das dependências do core e dos modulos (commons, port inboud, port outbound, core application)

	* relatorio-core-application

   Projeto core-application responsável pela centralização das regras de negócio.   

	* relatorio-core-port-commons

   Projeto port-commons responsável pelos objetos comuns entre todos os modulos do core (commons, port inboud, port outbound, core application)

	* relatorio-core-port-inbound

   Projeto port-inbound responsável pelas interfaces primárias do projeto core (Primary Ports)

	* relatorio-core-port-outbound

   Projeto port-outbound responsável pelas interfaces secundárias do projeto core (Secondary ports)

	
	
# run application 

   Para executar esta aplicação basta ir na pasta de target do projeto build e executar o comando: java -jar ${aplicacao}.jar
   

