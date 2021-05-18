back_end(servidor da aplicação)
	IDE: netbeans
	SGBD: POSTGRESQL
	versão do SGBD: Postgresql 12
	bibliotecas: MAVEN gerencia as dependências	
	configuração banco de dados:
			Usuario:postgres
			senha:postgres
			nome do banco de dados: SGPedido

software
	IDE: netbeans
	modificações:
		no diretorio "\PBD_20-1_Flavio_Cordeiro_Rocha_Junior\software_desktop\SistemaGerenciamentoPedidos\src\repository\RepositoryUtils.java"
		em "public static String ip = "localhost";", caso utilizar o software em outra maquina diferente na do servidor, modificar esse campo e colocar o ip da
	 	maquina do servidor
	bibliotecas:
		no diretorio "\PBD_20-1_Flavio_Cordeiro_Rocha_Junior\software_desktop\SistemaGerenciamentoPedidos\src\lib", contém todas as bibliotecas utilizadas no software

mobile
	IDE: visual studio
	emulador: emulador do android studio (Start pixel 3a XL API 29)
	modificações:
		no diretorio "\PBD_20-1_Flavio_Cordeiro_Rocha_Junior\mobile\projeto_gerenciamento_pedido\lib\repository\RepositoryUtils.dart"
		Em "static const String IP_MAQUINA = "192.168.100.24";" colocar o ip da maquina, pois por se tratar de um emulador mobile e
		o endereço localhost(127.0.0.1) é do proprio dispositivo e não da maquina que está oferencendo o serviço REST, portanto tem
		que colocar o ip da maquina em que está sendo executado

	
