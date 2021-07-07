PGDMP     .                    y            SGPedido    12.2    12.2 V    z           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            {           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            |           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            }           1262    34027    SGPedido    DATABASE     �   CREATE DATABASE "SGPedido" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE "SGPedido";
                postgres    false            �            1259    51132    pedido    TABLE     �   CREATE TABLE public.pedido (
    id integer NOT NULL,
    data_pedido timestamp without time zone,
    status character varying(20),
    total double precision NOT NULL,
    funcionario_id integer,
    mesa_id integer
);
    DROP TABLE public.pedido;
       public         heap    postgres    false            ~           0    0    TABLE pedido    ACL     I   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido TO funcionario;
          public          postgres    false    210            �            1259    51287    financia_mensal    VIEW     �   CREATE VIEW public.financia_mensal AS
 SELECT (f.data_pedido)::date AS data_pedido,
    sum(f.total) AS total
   FROM public.pedido f
  WHERE ((f.status)::text = 'pago'::text)
  GROUP BY ((f.data_pedido)::date)
  ORDER BY ((f.data_pedido)::date);
 "   DROP VIEW public.financia_mensal;
       public          postgres    false    210    210    210                       0    0    TABLE financia_mensal    ACL     9   GRANT SELECT ON TABLE public.financia_mensal TO usuario;
          public          postgres    false    218            �            1255    51308 �   buscar_entre_datas(character varying, character varying, character varying, character varying, character varying, character varying)    FUNCTION     �  CREATE FUNCTION public.buscar_entre_datas(dia1 character varying, mes1 character varying, ano1 character varying, dia2 character varying, mes2 character varying, ano2 character varying) RETURNS SETOF public.financia_mensal
    LANGUAGE plpgsql
    AS $$

begin
	
	return query select * from financia_mensal as fm where fm.data_pedido::DATE >= (dia1||'-'||mes1||'-'||ano1)::DATE and fm.data_pedido::DATE <=(dia2||'-'||mes2||'-'||ano2)::DATE order by data_pedido::DATE;
	
	return;
	 
end;
$$;
 �   DROP FUNCTION public.buscar_entre_datas(dia1 character varying, mes1 character varying, ano1 character varying, dia2 character varying, mes2 character varying, ano2 character varying);
       public          postgres    false    218            �            1255    51286    efetuar_cancelamento(integer)    FUNCTION     �   CREATE FUNCTION public.efetuar_cancelamento(mesa integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
begin
	update pedido set status='cancelado' where mesa_id = mesa and (status = 'pendente' or status = 'concluido');
end;
$$;
 9   DROP FUNCTION public.efetuar_cancelamento(mesa integer);
       public          postgres    false            �            1255    51285    efetuar_pagamento(integer)    FUNCTION     �   CREATE FUNCTION public.efetuar_pagamento(mesa integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
begin
	update pedido set status='pago' where mesa_id = mesa and (status = 'pendente' or status = 'concluido');
end;
$$;
 6   DROP FUNCTION public.efetuar_pagamento(mesa integer);
       public          postgres    false            �            1255    51231    teste()    FUNCTION     �   CREATE FUNCTION public.teste() RETURNS character varying
    LANGUAGE plpgsql
    AS $$
declare
adm varchar(30);
begin
	adm := (select nome from funcionario where tipo_acesso = 'superusuario');
	return adm;
end;

$$;
    DROP FUNCTION public.teste();
       public          postgres    false            �            1255    51229    trigger_adm()    FUNCTION     J  CREATE FUNCTION public.trigger_adm() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare
	adm varchar(30);
begin
	adm := (select login from funcionario where tipo_acesso = 'superusuario');
	if (user <> 'postgres') then
		if (TG_OP = 'INSERT') then
			if (new.login <> adm) then
				return new;
			else
				return null;
			end if;
		elsif (TG_OP = 'UPDATE') then
			if (old.login = adm) and (old.nome = new.nome) and (old.sobrenome = new.sobrenome) and (old.login = new.login) and (old.cpf = new.cpf) and (old.is_permissao = new.is_permissao) and (old.is_reset = new.is_reset) and (old.telefone = new.telefone) and (old.tipo_acesso = new.tipo_acesso) and (old.senha = new.senha) then
				return new;
			elsif (old.login <> adm) then
				return new;
			else
				return null;
			end if;
		elsif (TG_OP = 'DELETE') then
			if (old.login <> adm) and (old.tipo_acesso <> 'superusuario') then
				return old;
			else
				return null;
			end if;
		end if;
	else
		if (TG_OP = 'INSERT') then
			return new;
		elsif (TG_OP = 'UPDATE') then
			return new;
		else
			return old;
		end if;
	end if;
end;
$$;
 $   DROP FUNCTION public.trigger_adm();
       public          postgres    false            �            1255    51256    trigger_auditoria()    FUNCTION     �  CREATE FUNCTION public.trigger_auditoria() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
	if (TG_OP = 'INSERT') then
		insert into auditoria(usuario,operacao,endereco_ip,nome_tabela,dados_new,dados_old) values (user,TG_OP,inet_client_addr()||':'||inet_client_port(),TG_TABLE_NAME,row_to_json(new.*),null);
		return new;
	elsif (TG_OP = 'UPDATE') then
		if (TG_TABLE_NAME = 'funcionario')then
			if (new.is_logado = old.is_logado)then
				insert into auditoria(usuario,operacao,endereco_ip,nome_tabela,dados_new,dados_old) values (user,TG_OP,inet_client_addr()||':'||inet_client_port(),TG_TABLE_NAME,row_to_json(new.*),row_to_json(old.*));
				return new;
			else
				return null;
			end if;
		else
			insert into auditoria(usuario,operacao,endereco_ip,nome_tabela,dados_new,dados_old) values (user,TG_OP,inet_client_addr()||':'||inet_client_port(),TG_TABLE_NAME,row_to_json(new.*),row_to_json(old.*));
			return new;
		end if;
	elsif (TG_OP = 'DELETE') then
		insert into auditoria(usuario,operacao,endereco_ip,nome_tabela,dados_new,dados_old) values (user,TG_OP,inet_client_addr()||':'||inet_client_port(),TG_TABLE_NAME,null,row_to_json(old.*));	
		return old;
	end if;
	return null;
end;
$$;
 *   DROP FUNCTION public.trigger_auditoria();
       public          postgres    false            �            1255    51215    trigger_validar_reset()    FUNCTION     	  CREATE FUNCTION public.trigger_validar_reset() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
	
	IF (select count(id) from senha_reset where funcionario_id=new.funcionario_id )>0
	then
	raise exception 'Reset já registrado';
	
	end if;
	return new;
end;
$$;
 .   DROP FUNCTION public.trigger_validar_reset();
       public          postgres    false            �            1255    51312    verificar_mesas(numeric)    FUNCTION     0  CREATE FUNCTION public.verificar_mesas(tamanho numeric) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
declare
verificacao numeric;
begin
	verificacao := (select count(m.id) from mesa as m where m.is_livre = true);
	if(verificacao = tamanho) then
		return true;
	else
		return false;
	end if;
	
end;
$$;
 7   DROP FUNCTION public.verificar_mesas(tamanho numeric);
       public          postgres    false            �            1255    51311    verificar_pedidos(numeric)    FUNCTION     8  CREATE FUNCTION public.verificar_pedidos(tamanho numeric) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
declare
verificacao numeric;
begin
	verificacao := (select count(p.id) from pedido as p where p.status = 'pendente');
	if(verificacao = tamanho) then
		return true;
	else
		return false;
	end if;
	
end;
$$;
 9   DROP FUNCTION public.verificar_pedidos(tamanho numeric);
       public          postgres    false            �            1259    51246 	   auditoria    TABLE     i  CREATE TABLE public.auditoria (
    id integer NOT NULL,
    usuario character varying(30) NOT NULL,
    operacao character varying(10) NOT NULL,
    endereco_ip character varying(20) NOT NULL,
    nome_tabela character varying(30) NOT NULL,
    dados_new json,
    dados_old json,
    criacao_alteracao timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.auditoria;
       public         heap    postgres    false            �           0    0    TABLE auditoria    ACL     S   GRANT SELECT,INSERT,UPDATE ON TABLE public.auditoria TO usuario WITH GRANT OPTION;
          public          postgres    false    216            �            1259    51244    auditoria_id_seq    SEQUENCE     �   CREATE SEQUENCE public.auditoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.auditoria_id_seq;
       public          postgres    false    216            �           0    0    auditoria_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.auditoria_id_seq OWNED BY public.auditoria.id;
          public          postgres    false    215            �           0    0    SEQUENCE auditoria_id_seq    ACL     C   GRANT SELECT,USAGE ON SEQUENCE public.auditoria_id_seq TO usuario;
          public          postgres    false    215            �            1259    51351    configuracao    TABLE        CREATE TABLE public.configuracao (
    id integer NOT NULL,
    alteracao boolean NOT NULL,
    hora character varying(255)
);
     DROP TABLE public.configuracao;
       public         heap    usuario    false            �            1259    51349    configuracao_id_seq    SEQUENCE     �   ALTER TABLE public.configuracao ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.configuracao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          usuario    false    221            �            1259    51291    financia_anual    VIEW     �  CREATE VIEW public.financia_anual AS
 SELECT row_number() OVER (ORDER BY (sum(p.total))) AS id,
    date_part('year'::text, (p.data_pedido)::date) AS ano,
    date_part('month'::text, (p.data_pedido)::date) AS mes,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY (date_part('year'::text, (p.data_pedido)::date)), (date_part('month'::text, (p.data_pedido)::date));
 !   DROP VIEW public.financia_anual;
       public          postgres    false    210    210    210            �           0    0    TABLE financia_anual    ACL     8   GRANT SELECT ON TABLE public.financia_anual TO usuario;
          public          postgres    false    219            �            1259    51105    funcionario    TABLE     �  CREATE TABLE public.funcionario (
    id integer NOT NULL,
    cpf character varying(255),
    is_logado boolean NOT NULL,
    is_permissao boolean NOT NULL,
    is_reset boolean NOT NULL,
    login character varying(255),
    nome character varying(255),
    senha character varying(255),
    sobrenome character varying(255),
    telefone character varying(255),
    tipo_acesso character varying(255),
    ultimo_acesso timestamp without time zone
);
    DROP TABLE public.funcionario;
       public         heap    postgres    false            �           0    0    TABLE funcionario    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.funcionario TO funcionario;
          public          postgres    false    204            �            1259    51103    funcionario_id_seq    SEQUENCE     �   ALTER TABLE public.funcionario ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.funcionario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    204            �            1259    51267    funcionarios_view    VIEW     �  CREATE VIEW public.funcionarios_view AS
 SELECT funcionario.id,
    funcionario.cpf,
    funcionario.is_logado,
    funcionario.is_permissao,
    funcionario.is_reset,
    funcionario.login,
    funcionario.nome,
    funcionario.sobrenome,
    funcionario.telefone,
    funcionario.tipo_acesso,
    funcionario.ultimo_acesso
   FROM public.funcionario
  WHERE ((funcionario.tipo_acesso)::text = 'funcionario'::text);
 $   DROP VIEW public.funcionarios_view;
       public          postgres    false    204    204    204    204    204    204    204    204    204    204    204            �           0    0    TABLE funcionarios_view    ACL     ;   GRANT SELECT ON TABLE public.funcionarios_view TO usuario;
          public          postgres    false    217            �            1259    42241    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public          postgres    false            �            1259    51115    item_cardapio    TABLE     �   CREATE TABLE public.item_cardapio (
    id integer NOT NULL,
    descricao character varying(255),
    is_ativo boolean NOT NULL,
    nome character varying(255),
    preco double precision NOT NULL
);
 !   DROP TABLE public.item_cardapio;
       public         heap    postgres    false            �           0    0    TABLE item_cardapio    ACL     P   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.item_cardapio TO funcionario;
          public          postgres    false    206            �            1259    51113    item_cardapio_id_seq    SEQUENCE     �   ALTER TABLE public.item_cardapio ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.item_cardapio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    206            �            1259    51125    mesa    TABLE     r   CREATE TABLE public.mesa (
    id integer NOT NULL,
    is_livre boolean NOT NULL,
    numero integer NOT NULL
);
    DROP TABLE public.mesa;
       public         heap    postgres    false            �           0    0 
   TABLE mesa    ACL     G   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.mesa TO funcionario;
          public          postgres    false    208            �            1259    51123    mesa_id_seq    SEQUENCE     �   ALTER TABLE public.mesa ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.mesa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    208            �            1259    51139    pedido_cardapio    TABLE     �   CREATE TABLE public.pedido_cardapio (
    id integer NOT NULL,
    item_cardapio_id integer NOT NULL,
    pedido_id integer NOT NULL
);
 #   DROP TABLE public.pedido_cardapio;
       public         heap    postgres    false            �           0    0    TABLE pedido_cardapio    ACL     R   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido_cardapio TO funcionario;
          public          postgres    false    212            �            1259    51137    pedido_cardapio_id_seq    SEQUENCE     �   ALTER TABLE public.pedido_cardapio ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.pedido_cardapio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    212            �            1259    51130    pedido_id_seq    SEQUENCE     �   ALTER TABLE public.pedido ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    210            �            1259    51146    senha_reset    TABLE        CREATE TABLE public.senha_reset (
    id integer NOT NULL,
    data timestamp without time zone,
    funcionario_id integer
);
    DROP TABLE public.senha_reset;
       public         heap    postgres    false            �           0    0    TABLE senha_reset    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.senha_reset TO funcionario;
          public          postgres    false    214            �            1259    51144    senha_reset_id_seq    SEQUENCE     �   ALTER TABLE public.senha_reset ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.senha_reset_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �
           2604    51492    auditoria id    DEFAULT     l   ALTER TABLE ONLY public.auditoria ALTER COLUMN id SET DEFAULT nextval('public.auditoria_id_seq'::regclass);
 ;   ALTER TABLE public.auditoria ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            u          0    51246 	   auditoria 
   TABLE DATA           }   COPY public.auditoria (id, usuario, operacao, endereco_ip, nome_tabela, dados_new, dados_old, criacao_alteracao) FROM stdin;
    public          postgres    false    216   �u       w          0    51351    configuracao 
   TABLE DATA           ;   COPY public.configuracao (id, alteracao, hora) FROM stdin;
    public          usuario    false    221   �       i          0    51105    funcionario 
   TABLE DATA           �   COPY public.funcionario (id, cpf, is_logado, is_permissao, is_reset, login, nome, senha, sobrenome, telefone, tipo_acesso, ultimo_acesso) FROM stdin;
    public          postgres    false    204   �       k          0    51115    item_cardapio 
   TABLE DATA           M   COPY public.item_cardapio (id, descricao, is_ativo, nome, preco) FROM stdin;
    public          postgres    false    206   �       m          0    51125    mesa 
   TABLE DATA           4   COPY public.mesa (id, is_livre, numero) FROM stdin;
    public          postgres    false    208   y�       o          0    51132    pedido 
   TABLE DATA           Y   COPY public.pedido (id, data_pedido, status, total, funcionario_id, mesa_id) FROM stdin;
    public          postgres    false    210   �       q          0    51139    pedido_cardapio 
   TABLE DATA           J   COPY public.pedido_cardapio (id, item_cardapio_id, pedido_id) FROM stdin;
    public          postgres    false    212   l�       s          0    51146    senha_reset 
   TABLE DATA           ?   COPY public.senha_reset (id, data, funcionario_id) FROM stdin;
    public          postgres    false    214   ��       �           0    0    auditoria_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.auditoria_id_seq', 484, true);
          public          postgres    false    215            �           0    0    configuracao_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.configuracao_id_seq', 3, true);
          public          usuario    false    220            �           0    0    funcionario_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.funcionario_id_seq', 29, true);
          public          postgres    false    203            �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public          postgres    false    202            �           0    0    item_cardapio_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.item_cardapio_id_seq', 30, true);
          public          postgres    false    205            �           0    0    mesa_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.mesa_id_seq', 22, true);
          public          postgres    false    207            �           0    0    pedido_cardapio_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.pedido_cardapio_id_seq', 142, true);
          public          postgres    false    211            �           0    0    pedido_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pedido_id_seq', 48, true);
          public          postgres    false    209            �           0    0    senha_reset_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.senha_reset_id_seq', 26, true);
          public          postgres    false    213            �
           2606    51255    auditoria auditoria_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.auditoria
    ADD CONSTRAINT auditoria_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.auditoria DROP CONSTRAINT auditoria_pkey;
       public            postgres    false    216            �
           2606    51355    configuracao configuracao_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.configuracao
    ADD CONSTRAINT configuracao_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.configuracao DROP CONSTRAINT configuracao_pkey;
       public            usuario    false    221            �
           2606    51112    funcionario funcionario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_pkey;
       public            postgres    false    204            �
           2606    51122     item_cardapio item_cardapio_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.item_cardapio
    ADD CONSTRAINT item_cardapio_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.item_cardapio DROP CONSTRAINT item_cardapio_pkey;
       public            postgres    false    206            �
           2606    51129    mesa mesa_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.mesa
    ADD CONSTRAINT mesa_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.mesa DROP CONSTRAINT mesa_pkey;
       public            postgres    false    208            �
           2606    51143 $   pedido_cardapio pedido_cardapio_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT pedido_cardapio_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT pedido_cardapio_pkey;
       public            postgres    false    212            �
           2606    51136    pedido pedido_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.pedido DROP CONSTRAINT pedido_pkey;
       public            postgres    false    210            �
           2606    51150    senha_reset senha_reset_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.senha_reset
    ADD CONSTRAINT senha_reset_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.senha_reset DROP CONSTRAINT senha_reset_pkey;
       public            postgres    false    214            �
           2606    51154 (   funcionario uk_mtj01s2onxljf2lntddqdsgqx 
   CONSTRAINT     d   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT uk_mtj01s2onxljf2lntddqdsgqx UNIQUE (login);
 R   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT uk_mtj01s2onxljf2lntddqdsgqx;
       public            postgres    false    204            �
           2606    51152 (   funcionario uk_rxosr8731eb3gbnlbt2mqfan8 
   CONSTRAINT     b   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT uk_rxosr8731eb3gbnlbt2mqfan8 UNIQUE (cpf);
 R   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT uk_rxosr8731eb3gbnlbt2mqfan8;
       public            postgres    false    204            �
           2620    51230    funcionario trigger_adm    TRIGGER     �   CREATE TRIGGER trigger_adm BEFORE INSERT OR DELETE OR UPDATE ON public.funcionario FOR EACH ROW EXECUTE FUNCTION public.trigger_adm();
 0   DROP TRIGGER trigger_adm ON public.funcionario;
       public          postgres    false    204    241            �
           2620    51257    funcionario trigger_auditoria    TRIGGER     �   CREATE TRIGGER trigger_auditoria AFTER INSERT OR DELETE OR UPDATE ON public.funcionario FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 6   DROP TRIGGER trigger_auditoria ON public.funcionario;
       public          postgres    false    242    204            �
           2620    51262 (   item_cardapio trigger_auditoria_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.item_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 A   DROP TRIGGER trigger_auditoria_cardapio ON public.item_cardapio;
       public          postgres    false    242    206            �
           2620    51263    mesa trigger_auditoria_mesa    TRIGGER     �   CREATE TRIGGER trigger_auditoria_mesa AFTER INSERT OR DELETE OR UPDATE ON public.mesa FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 4   DROP TRIGGER trigger_auditoria_mesa ON public.mesa;
       public          postgres    false    242    208            �
           2620    51264    pedido trigger_auditoria_pedido    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido AFTER INSERT OR DELETE OR UPDATE ON public.pedido FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 8   DROP TRIGGER trigger_auditoria_pedido ON public.pedido;
       public          postgres    false    242    210            �
           2620    51265 1   pedido_cardapio trigger_auditoria_pedido_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.pedido_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 J   DROP TRIGGER trigger_auditoria_pedido_cardapio ON public.pedido_cardapio;
       public          postgres    false    242    212            �
           2620    51266 )   senha_reset trigger_auditoria_senha_reset    TRIGGER     �   CREATE TRIGGER trigger_auditoria_senha_reset AFTER INSERT OR DELETE OR UPDATE ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 B   DROP TRIGGER trigger_auditoria_senha_reset ON public.senha_reset;
       public          postgres    false    242    214            �
           2620    51216    senha_reset trigger_reset    TRIGGER        CREATE TRIGGER trigger_reset BEFORE INSERT ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_validar_reset();
 2   DROP TRIGGER trigger_reset ON public.senha_reset;
       public          postgres    false    223    214            �
           2606    51165 +   pedido_cardapio fk2i4n8rrs0drx67l0bebekt98l    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l FOREIGN KEY (item_cardapio_id) REFERENCES public.item_cardapio(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l;
       public          postgres    false    206    2764    212            �
           2606    51175 '   senha_reset fk4ptvpighqhvr0afk9bg9o0oyq    FK CONSTRAINT     �   ALTER TABLE ONLY public.senha_reset
    ADD CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 Q   ALTER TABLE ONLY public.senha_reset DROP CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq;
       public          postgres    false    2758    214    204            �
           2606    51160 "   pedido fkf32po93klqxcumfjsf303g2vl    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fkf32po93klqxcumfjsf303g2vl FOREIGN KEY (mesa_id) REFERENCES public.mesa(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fkf32po93klqxcumfjsf303g2vl;
       public          postgres    false    208    210    2766            �
           2606    51155 "   pedido fki23ikc3j8n2eng9xk4qrgt3w5    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5 FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5;
       public          postgres    false    2758    204    210            �
           2606    51170 +   pedido_cardapio fkktyrjeounh5k0bokdcuf2mtpr    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr;
       public          postgres    false    210    212    2768            u      x��}�n$9��s�W$�[�7�wƛ�����n�� �h�*[�T#��$0��؇������5���4��G�n]9�i��yxh4��fJ�_���7����]�~�����QrnA����OwW7�w�o����瓹��\�����ć�?'���<�v{�e���d������ɟ�_o��>\?^�� ���;����� ~��������������ą�ނ7J/�0gp�.ܙ;U�yP����}�^�ۏ7�ߗ��o׷׿��я���jk���͟��-���Ic��wO��n�6�V
	��(�')�����}�cOR�=<�BNv����T�:00S�o����]��O��)��׏�5�V,�|���u��������/��/����sm� *)��a2;������7�7��ד��o��V?���~�z��ύ��I������#�ʟ�w�����|����]	D���j!���&V���@N����t���b�]->/���P�S��v�u��zD�����W�d�-���?_��! ?9C �`��~��n��-_��jV㬅�f��pO�����'P���.��ܷ���GE�p@u����R-�4����B)�F�䏧����X� �4S#wO��?�#�Q�/a�MaRs���TAkr���
�|�+L���%ʒ�˒�6:mZ���J|���l׺�N�_����C]��]�>ѯ�R����h-��VA������;q��0W�������B�)�ƍ�9�
�KR���a&�`�t��sa+�&�h�X�Q�Az7�0�"����ws���P4f��N͑
9��9��Ң�*[��}�Ѹ���#[9z&�/�?~��Gц����"����MO��O7W�����Ҭ�L�j����x	����)4�ue��NJ=�!d[��0)��,�3`����r�AA/d�$b�kW)�öP�F�ץ�.ǐ�P���Q�ds�ʈ�0�hϝm�EnE����P����	��V1�aGҘ;�`�K~Kc��q��1M�?a?cL���9����G�A�ώ;�����3Ɔ�Q�@��#_��8n��"3�U�A�� ��eX��̐V0�4�����T�_�"�R�n$P�
p�"�9��2PpO�Z+�cI�z�i�(�O�Y�?�[*u����^Ğ�ujb��HҧZ�i�!䯏��w7z(�a��Kp;F���E�<���|G��?��bf�0��fXQ���J�3uxo�Mͳw�Z��k�s`l�חc��N�1��S^���	;f�~r~�ᝰ��o�sx'�A�)����	��y�W����_���\�?�7���� ���� ��%8��O6�0!H;��,����s���_�K:t/��y����SS(�4��ˋ,?���"����E��Av�����cg���&�a��2�9%gf���&�-~�e���F��a��j��d;����6;�s�����Y����w�9ˏ}�������y~�m�;�����wW׷��i�) s�J83{xz/�~��&��V�^$�,S�M��M�I�ߞn�tp{z�~�Qݦ���vwD��K]c�03�
-��Ir�м���Whc�Ӂ9�^��==E��?��&og�=R���=R���=R���=R�c���#���g�s�*��z�^�==%?��ғ��
����?�΀���|MV<�no�~�p�l�4Uq�y=��WnC'82�9z�vt�#�Jg��WnKw9�=��</xF�?�L���*�n���FR1���UR�7K*�\nk*��_�*/7J�J��\@] �
!1���c� Q�N��a��4���23��I��-c8HYi)5Uv˸a��j�����;:b�R��
)��Z���U��_Z���P������
�������7�X~��~�cmU�����7T ����}��<�����?Ԙ!�WD�tJJ�3��>���q��MP깔)
W�'(&��:�Mb�JTT�����|�v	3�P)e��m�WOS�!e�&�Z:Ck�����Bn�~�eeu �G���*���y�e��./(	���!�|�1f+�d��,��y�sy�W���s�K�4��m���O_�_�h�������/�ڶa\cD�Q
\�=��dj4i�,O5V�ѵ��kQǞ�]�j��Va��ˇ��G�}ׅ��B-¥�?Z�X�s8��N����@�j�0��d&�
�K��PS��_�J��~X�rJ�E��i)��ѸyMފ^��ttr��?�o\�:�d�#�R��d,Ήe��s�ַRW�|e�W��Ƹ��a�{��1�����a��<�)��$4�@
�Bb���A��C�]���� -�.\)����	�TE34ԫK�s��.�7~�?o�\�Jz �p�L������j��h2�S^���cМvh'Z��ɋ\CGw^	 �a�F}�ҫ���~_~����#�U��(Q�%yy4n�n�W~�&G��`�h�UrF�I�B`��n%doq�laptFrŁ�k�]Tz�X#�%!n%H���k$��*Gmg��l���������&0��Y�V�Ͳa^zm���0�7����Y�[yyz�.����蜳�%��4�F�Ī����t��cOJ��E2���Q ݄���� �V��O���ؕ����ms��q�W��q��D-��ҝHt:N�c@P����}\�}�}�Us� �9\/c��o�n�n�&�y$>h�QMp!��{jd|��L�kz�3#s����,=�y�Yz��"3�0�����*ZQ�a�6��$E&(zI�-��0A�[ȉlQ������EL��K��Gl�nEw��8l��ҽ}x7����x7���Mi��wc:KcNKGm�ߞ9��B�H�A��:Aқ0��$1&u��7aR�Ib��Io¨n�Ę�]�ނYݦ(�<����mu�1�г�*	��$��$�j����0�<Q
��Z�@>�!%L�ڡt�z���R<��?��$d����0��I���h+�n~���Jh&��O�wN"�|�&�8U���o�n�М�<�<H�|G[�GFV�K�Y3�R�e����(�C��l*ɶ�,O'3`���$����.�R�ډ\����W�6�t�~���m ��4�4 SA0Z�Y��`��m06��޻�r9\���ZU8��,���-/����	2���I^!B5�њR�^#��;�5]Y�j?l�n��G4�c���)}5'~s�r&m��A�O;a)��z�Z�H�<�]�B��xPP/� ��=���[���=��t8t?�On��< lӖ#A��)�p�!o�yi�L2O�	hr��=4Q��y�vprz��2����b�?�j������Z1o�Ύ��.��# �����YlG{�jK�dB�h��O� <'I���� 0�Y�P���y<V%s2H����ȏ�.�����A� i��'�Fyh���\���G=�Og���膊#4��T����qhԗ�TLY��p*3�J�)�܅Z�|$�;�Ό֩x��IAY�2�K5p8�Τ�R�^j�hvz�p���Α�� ����r�����kD�Sܠ�+�V�QZ� �"@.��J㮤 �b4�Q(��F�b�	R�"Oދ��E��{���*l�J�g���:
G���J*#�DDL]��pd�@��h�{���u�� �BP(�t�Q8����+�Uc��G�HRoK ���eJS�#��ΔBP8�)%u�4�?��!���O����#2!e��ޓ�����
Q;�s㶊����A�}s5(�S#_}�	K՗����@މ��Tŵc(<���q���Rf�I}�i���.,w�;�)��O ��3tA� AހNݩ�&���O)�t�:��Qyt�܁������<2yc�	�C�I���� 0��*@�h��!�;!�T~��"p�I&�T�/Qǖ�Qx"M{6���U����Md$ػZ�v?�ɖ��X�����B��^F.ѡF#��K@=�}���a�5ЩȽ�?
Edx�K7�K�<G��W2(�(K��c<G��1Yj[�2��Q8�We�W�(�[�A��9�w T��Z<�8���hGT    }�J(�2as%tx�jD*����8&Z#j����k)?��U��)�ہq("�p�}�m���Qm`��4�T�ȣ0<&ȚA��7�v0Ʈ�>ΰ�=yϫ2BP�@��L��1�ݷ*��d$4�w�K^�G&)���qW�� &��1j����b��Uc�D*��@O]15������В���a'��i'��&j+1��$�@��3!�A��0#��/�ÑrTk�+�$1Q�I�\E�Z�tn�8?�C�֕p���g.$�rLI����Xc���#C�&�FC�홨Y��Q5�zn�ӧ=z������d�8
9:A�u�APv-@Pn$m��3�45��ta��5�GvL�Ƨ���Ϥm��+�{���C�����|����j���:���$�]2`�6��%)e���,T�>�U`g�K7�௿l���Ģ�u���+�#�r�������<*���>���T� �syK�	���*%����O��o3n�S�b�F�Ί���4���ϊ,u�x��K�����OQ�oI���30������I�K�)����}>�	m����h�!HFDH�A�����`���YA�J���h�87v���������j��yL��D�����{�"��h��H�T�&P	�����5Sĕ%�	A!Y��y.���Q��L�'�ľ�;i�?��	��$��Ȥ��Ы�D��A�`L
�D�؈�6�|<ۯM��'J����$���+ɺhލ���W�.�
�P)���b�Z�t��Qg8dM�J�zrLa�a�m߭w{� �TAKC���ȡ�/�!Ҵ"Fx#q� �з��s����u�R���G���������n���wx`p'�F�#��G�n"vQ^R/�F)���?_��i��Î�,���#(��	 &��O���gp
�!HF��gp
�!Hd�`̥�-�-9�{��Q�D��v&o��l�B��"��Jl��A��f0�F��)�&��'� �*SML^$���D�+�)G��J��^���h��p�����}���g��$g� b�bq!�EX��3{g��7$�G3���a�.H"��F[G�W^������P�꾴m�ҵ��f ڷ�3�g(P^qy�jʜ!���ކ������w�:��j��q�Yau%�I`{��������MՔ�:{��;�H)&�����*��pp��u�(p��|�i�3(�ω�'T�4�P�B�o[�����I }."�.P�lWu��
ؠ% ��	�*i2��Mk�a�f����*@*i�hBb�
�!����ې�\/�$$�蹪:1�L=�]P=�G�p4]%W�X*�&��:VD��2հ�i���Y4��R�#�8�BS=�����[~�����qy���\��T;����Ʈ|3���2�T���L2d���0����{��W���P��0� �m��j��	[���~��1�*����f�cz�p)��3�T���4��&�=�}������q�x�"�~��6�<a,x�4��N݅;s��:�26�nn�G��6��j�3�_dQ�{�Z9U�V��r�%e�7��)H*����I����u��{2��#�}��dz�TN-\:ݲ����&B0��I�i�����-xL�jC�G��<�W`�F^șo�Ԑ\M�А+K/�ю����H��2L�JRo�
�I�hw�DH�-��F�M+׶5n�hl���u��	�������o�<>]��|�a�o���b�D@�
��a.���6��u	��eh�zE�ZIq��ܐ����-��P�����G�e�Q�"�k�� ޟXC%m*Ƒph�A�i(�=�� �	 �)WJ�/��gL�Q�?ȼ� �3�����)�a��&@GX���˛~p��^��Mzӏj�s����+d	�6�6h}��F+9�W7�W������ی������	���ř4�[}j���J�~4��pmdmV��V1��TK�x��\xP1��u��Y������?�m�����]��@�L1��3�{����i���V�A;��yv,���1��c�Gށ���{��4��󋳋�˳3���pz��,��k�p�t}��h�H�5�B�Ȍ�2�M�s���ۢd$Uau���`bYz�d��ԑ%��o���ZA-KI�ş���o3z��+k���y�xtԅ
D���=�μ����=�`�ǡrG������NO��s��� ��&(ϝ�o�nq����q7��+��uP)Nl�WO	�ʋVo%r+���=o���J��A���曑e]�
=�|/�ޜVm�����x�]�FC|7~��F��$�!��R�����(���`�a��!�yo��G���t�v��x�`UC���%CB��F�G�1J�5Sܠ�V��#��T�:;;�X���������.�3_���t�i�h�]�-+�2t�4s���gZp�QF�hO��_�	�e�TO�e�Mn	�*EW2�F;�M0b���\n�g��6l�/��?��D��޹�P(爈�j@D�`y[D+��є�b����_�������f���Y��x�[a�2���u�Ji����X��dY��,�e0u��d:��v��������A��,�(�A��}t�� �'Ð-�lg!4hR�^�8�K�&�6�z4������T�1�@�]�u}�՗�F"p&H� �٣I(ڣ���aF0W��U����MzDݑW*bi5-��H�:�Nۗ��K�E��8r�RR�'))m\#����(m�s���r�����цb���c��*�|�eI�*9cG4�w�+�	�R�&B��Gʝ��:)'�=-�]v�֪I�v�9�G�g�a'j���*��9�r��LbE�����1�J�L�Ԍ*>�&���i"I��ҍ��p�2�3����׃'3���d7>�L|�>ѫF/��;�>�P(#�����K6���'�+�EP~hv%YR�DP����#�{���ςc�f��z�P���-��R�X9\��RIֿ(��6�����ܼ��m��2�lvx�����S�V
�W
+������+�+�2Yi"`r�F��*��(+�f��z�~���bd�p���1#+ ���aiC`jF�o"��"�e2�l󴙇d�5.�FK�je!0�무$c-�҂��!-��P�$-�Ig��z��<1E�8i)\�,&�����v)iiiC��نK��/�uM'�M�i��HE儥p���T���$��%�҄�E�4n�Q�X���:Q����*�<g%��|I1�#�I�T��T�4���F�Q[(0Y�� �E�;��zښ��_<�*��9@X&�O4� ����|��SRZFŎ#L+�$���c�����(iѦ�e"���T��k��
��cVZF�#L������!G�Q�1��1�VL��)u�D�񴚇�$�#��2*�A`ތg�el 9��$��O�����+��ɤ3�i=%k��dVZFE��$�O)+-cC����iY�c;�I4�tF<���P�S>��J˨Xr��ۗ������:��`�(��`�h�e2���T��Zg�'�}m𔔖Q��SA%	a��bl49�P~nV����f������~2D�;��_vj�ȴ�m5(Id<��J��*4Q���'����yxK5$�~e��g!��P���ja�C9f�%�#�{��[;��:7T��4U�`�xN��Z=�'�P�m�GRU�kJ���),�:����
�������0�y��ꈨ�D\��M�uƗ�� ��n@i�q{�gl����1���@Hٜ�sP��wԛ+�  UQ��F����7�׉�5^�8P],���T��>ŝ��������g�<���?�_���(c�Ao��]h���HV��A/����ԵAS�,<"��ڀL!�i��2����%KZ!�x!c(��}�{ZȺ�1��B%FVZq��SE	Z�Ͷ��˪ �6o�u]�DW�� �E��Z�}����5��������������Ğ��r�y�|z�{��L����:giܵP�1n 7	  ����x`�6R�vԛt�T��H�+�?~��R��@�s�H?�2��ݮմ���n���ˈV�������hSs7FB=X��,Z摓c_Y[�'��^$�IE�A&>���r�`��
	`^O� 	������6����L�e*}�(e�+UҏT&��հ�z���H��������P���@/}W���:^�+4uM]�v��=�VG�5�SA�,��Z�P�f��ֵ��a]���oϗ�nN�R�va*���&9�,D�r�-��.5�&����J䁊w3/���d��M�F� �Q��j(����XZ�������B~�7J�^;5�:�HKM^�����Zr�+i�v"U�T��y�+}p���+8�TpJm��i����Kމe��s�ַR$Kr.me���@����+� K�7������.	�񆢚�;���)1�Pc$�P���Y7+�qB�<s`s\����̅#NX���z.R�̀\�r4�J�i���bȿ�Hc�<)L&�U�nc`<z��Miq�Pc�r�S�2��jl�b��:�z�Jơ�V��Jʹ��iK�mr�&RQ!���CZ�Dr�S�m7;#�F�xl-f��V���Du�[�.פ��
v�'��N	z�8z�Vو�᫡��K勡�LX=^���+���ɔ����1%�:{Nb�ې��(r��2���9U��^���uu-��L|W���00��m\�4�x<]j��T�Z�FV��f���B��m�(�f�F��F�X3L�]���tI]ח��c��mL"�4ws�"�j"¢d��s5~�:dIUO�i��@��ē1ea.�X�rB��u8WJY$�y̑�Q��,�)y�4Q�i�{�%�dl&��
>���c�m?ߡd%���!]L��Dt����L�^�.U��A [H��KU�M0��~���BQ����Ut�3�}KU2b�&Ƴ�Ҥz��9�d�:f�	����hz�h	������>j+�y�~�ǡO��Np�q��j;r�O�ܙ��gh�S�i�ъ�w���ԟ9r��O�e�:�����Mo��PAzz���2�6D�{n�q~�ړ��7��ZD�?i	���K�=�:N͹��Ak�N�YLd:�����-.��Ț�V�]�NǊ�Fϭ:V��sK�\� TJ�p̥1���.j�%ŏBTmH�~l�h�L��R���'���ۿ5'���Wx�< $������C�U<�d��|-ЃGH�m�[����@R�R����$�G���p��!�L��qd�V��hh���=����m����l��I�%�@�%���~$-��:�,=t��[RKLs�"�Cd$-Bd� }q�ARL�κ1c�@R8�F�t+VyG��)\�.$d��P�4TyO�2rI��(4xP��B��Jy[*P�Zߨ��GRޔ�XIG�S��	2�:�rt�D��A;<*�5Z���F�N�7�v!��M!`��U�L��P�zv=��#Kf�� ��lg��U�)�g�:@!�h��q�(}��ao5S��T0 4����>ohZ(���W-�b���yՒ�����j0��3`�*:��SJ�Z�,������t$���fڴ$W�Q�Tp��Ne�B��B`��e7Y��h�1��8}�5K6OB�1���p^3�$T�ۻ���Y����aHv���9����������3��O�[�Tgm�6����5��F-��ۺt[e10�8��*�0;9��*��ؙ��_�Kn��%!YձD�F��`Y5�J ��LBQ>L����J�}q4��RevxҺ��+3��w2�ÝDO4Mx�EG�RV�z�R*�Ý�S֝��p��^�a�#!|�Af��N�9GŰ�Z��|*��A�p�-f�B/�\A%,SW��#H�_^+��o�����҅�D[KYs`d1$�5(���2;�1Ɨ,[ş!9_��hJ��L�����b��A`Ir
�&��q_�b�vdK�NS$!it��&��E�4��><���޻��mW��R��j�Nl!�*�՗�`W~vt�K:I��10A��4_*�gR�7���s�� T'	c�y���E]"jG��C�T�����,m;����q\�W/_a�s��e��ɩlW^}�?n����b�ԗ.z��yL���aU�y2���ٔG\����\����cZH2&S�e1�a/�tp�Df���.����3�m*�}B��p�tȒ�"׌���.S������]�����a��
=Ya�H�����\�6YJ�+m  $k����!�e+'e?�*�L%Y�@/b"Q
(��0��j6���;\      w      x�3�,�42�26����� �q      i   �  x��VKr�8]S���"H �#Ej9'�:Qz�r�=�d�י����d'��gZ�R�X��hP9$B<�QO�,���;�������s_R�CJ@C�g����L����ye�CcQ�����a>���,���5�k��Y�k�&(+��i>���
���:h�}�Y[J���BΨN��e�؇��o`��Ѱ�`�e���^?��e<>.�PžxB%���<Z�1���p�9�Dɏ@^��$��Wހ��燱⬑�)��� ������&`�%DM^����p�8BI�Sߦ�?O�r�!J��E�|=��N�x|��9ms�|�P2I�|϶ש�������ʺ�Z(�7�Θ��N�V4�
_���TQN�+�Z#Y����v.t�Z�T�$Z�ߐVfo8��')��� FH�d5Ў��_iW]�w����u�Z� (k=��n���<��k$)�%!�|*���,+��E��_SK�~�ЇXɺl�er���׆��<��%P`��Cɒ������u]��A=M��4�B�HG����m���4:l0χq���l��8�l`�>��$�E��"�T}�"�E��.�Aϊr�5�4�)<:�v�j����_���[���v �H��Q2��%A���
Z��,�;#I��Pˆ���ְWo9|�����R0́]u@y@��19Ɂ�W%���'��E��il�>x!s��d����~�Ց ��F`�����A��u�ӤDh<I��Zc�ȅG+�7۬[q�~�����N��A�>ъ��u�q���x��mi]B��e�8�<cJ�F*�C��c&u��/�b%����чl�!:��5�M UU��?�Ɨy_#%��V\�`sv1ht~��Z���e?��̰�~1�B�-W!;e^��1���.�3-�*H�J�!�Գ7)�P"��K�$`���:�G�`��5wh[�1A�&���+��|>+��<xy/u& ��k���FJo�p��_6�Y�!�hLc���E$�t%<��U�K�dp�$��4k���w��Ob�+Y�>��3���������q3�Y��w	�e����3D��0ľ6θL&�O�^'�D�� �Ru�A��:��S��~='\c?� ���d��Iܟ�I+�-z����7a����n�|���I����-�����	16v02T��в�t�1/�ˇ�--ܒv���Ur��\u�9Ľ����{$�]�*1��~�>��ҙ2̠;h��M�����3      k   \  x�uR[r�0��O�&Ϋp��(�3!N�`(� \�2vf�~EҮ�+9%���ys�2�8`��@3�L_u��:kpp˝�5���0i��f4҉3���аM��9?)�M�I��/�k��E#�?�|�>`֙�:9�1��ǣ^��Wl�	Jy�m/��
ƴf������x�x��g�I�Ye)%⤅Ѕ�:�y���eb��r4T���y-9��c�B�X����f��|>P��a�k��)��"���$�|x��M !�KϪnDt��mp�4����& �d=y[���=��{�܇gj�V�P�1>�+jYU����!]�zO�o*���[�����V      m   \   x�͹AA�&���0��8g`�ٟ�����.vus��Y���E�F)+�Q�vD�H��B�QHN�s��,���'|?��7      o   w  x�e�I�1E��)�8i<Ko�4�Y$��T%�|�)���# �A�-Dc�)�����׏��ġ�<a�PsY1G�<��7�sciL�����|���\4A�MJ��_�X�q�������(*�=��m�D���uŬ y -�rC
p��ނ��]�aiPխ�d\ґ��a�&�A�$������sz�C�O���J���T.ևZ!5nA+m(B��`���X�� yѲ�M��;XL��xvX��΁h#	�j1OIP�C�ͷ��
�ϦU�Ո�d�y��=M���5�G�8��՞�)�3�5=ʄ�>1����`O�	�YM�X5��}���b�1d�әB�`� �P`��MCXgJLL4��lYj4�8X��bs��Vt�:F��Zl�9�7�����㓀qoe��%�b�V��=~ޏ����d�Fˡ� !U��^%�B���c�}G�+C<a̺�A�c���Kr��69b@J'5�ϻ��b��wK�O���L�׹h^��eOj?I�3�K�C�"�W�>I���~�g��;��a�6M��)J��l����%O��"�mFZT�֫�f�����!����p���&��@��d�^ʬ�o(�<������U�      q     x�%QɵE1ZK1�D�������C�
�p��Ҳ�^���BZY�nhzZ؅b�������0��J�%z��ݑa�0?t򐤺�m����'v�E�B�F=�Z��U����X��B5Gk��6��D���n�Q�xUW��݄�xc4s������lb+�b���0G�t�5�#p�]��q^����a�Ua����LID�%2N��A��G�sm�j)�
<|܏�,�|Yb�,��|�D����n�!۟�O�O�����
P      s   4   x�3�4202�50�54W02�22�2��36�4�22��345������� HH
{     