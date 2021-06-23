PGDMP          #                y            SGPedido    12.2    12.2 Q    p           0    0    ENCODING    ENCODING         SET client_encoding = 'LATIN1';
                      false            q           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            r           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            s           1262    34027    SGPedido    DATABASE     �   CREATE DATABASE "SGPedido" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
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
       public         heap    postgres    false            t           0    0    TABLE pedido    ACL     I   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido TO funcionario;
          public          postgres    false    210            �            1259    51287    financia_mensal    VIEW     �   CREATE VIEW public.financia_mensal AS
 SELECT (f.data_pedido)::date AS data_pedido,
    sum(f.total) AS total
   FROM public.pedido f
  WHERE ((f.status)::text = 'pago'::text)
  GROUP BY ((f.data_pedido)::date)
  ORDER BY ((f.data_pedido)::date);
 "   DROP VIEW public.financia_mensal;
       public          postgres    false    210    210    210            u           0    0    TABLE financia_mensal    ACL     9   GRANT SELECT ON TABLE public.financia_mensal TO usuario;
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
       public          postgres    false            �            1255    51215    trigger_validar_reset()    FUNCTION       CREATE FUNCTION public.trigger_validar_reset() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
	
	IF (select count(id) from senha_reset where funcionario_id=new.funcionario_id )>0
	then
	raise exception 'Reset j� registrado';
	
	end if;
	return new;
end;
$$;
 .   DROP FUNCTION public.trigger_validar_reset();
       public          postgres    false            �            1255    51312    verificar_mesas(numeric)    FUNCTION       CREATE FUNCTION public.verificar_mesas(tamanho numeric) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
declare
verificacao numeric;
begin
	verificacao := (select count(m.id) from mesa as m);
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
       public         heap    postgres    false            v           0    0    TABLE auditoria    ACL     S   GRANT SELECT,INSERT,UPDATE ON TABLE public.auditoria TO usuario WITH GRANT OPTION;
          public          postgres    false    216            �            1259    51244    auditoria_id_seq    SEQUENCE     �   CREATE SEQUENCE public.auditoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.auditoria_id_seq;
       public          postgres    false    216            w           0    0    auditoria_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.auditoria_id_seq OWNED BY public.auditoria.id;
          public          postgres    false    215            x           0    0    SEQUENCE auditoria_id_seq    ACL     C   GRANT SELECT,USAGE ON SEQUENCE public.auditoria_id_seq TO usuario;
          public          postgres    false    215            �            1259    51291    financia_anual    VIEW     �  CREATE VIEW public.financia_anual AS
 SELECT row_number() OVER (ORDER BY (sum(p.total))) AS id,
    date_part('year'::text, (p.data_pedido)::date) AS ano,
    date_part('month'::text, (p.data_pedido)::date) AS mes,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY (date_part('year'::text, (p.data_pedido)::date)), (date_part('month'::text, (p.data_pedido)::date));
 !   DROP VIEW public.financia_anual;
       public          postgres    false    210    210    210            y           0    0    TABLE financia_anual    ACL     8   GRANT SELECT ON TABLE public.financia_anual TO usuario;
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
       public         heap    postgres    false            z           0    0    TABLE funcionario    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.funcionario TO funcionario;
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
       public          postgres    false    204    204    204    204    204    204    204    204    204    204    204            {           0    0    TABLE funcionarios_view    ACL     ;   GRANT SELECT ON TABLE public.funcionarios_view TO usuario;
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
       public         heap    postgres    false            |           0    0    TABLE item_cardapio    ACL     P   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.item_cardapio TO funcionario;
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
       public         heap    postgres    false            }           0    0 
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
       public         heap    postgres    false            ~           0    0    TABLE pedido_cardapio    ACL     R   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido_cardapio TO funcionario;
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
       public         heap    postgres    false                       0    0    TABLE senha_reset    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.senha_reset TO funcionario;
          public          postgres    false    214            �            1259    51144    senha_reset_id_seq    SEQUENCE     �   ALTER TABLE public.senha_reset ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.senha_reset_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �
           2604    51249    auditoria id    DEFAULT     l   ALTER TABLE ONLY public.auditoria ALTER COLUMN id SET DEFAULT nextval('public.auditoria_id_seq'::regclass);
 ;   ALTER TABLE public.auditoria ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            m          0    51246 	   auditoria 
   TABLE DATA           }   COPY public.auditoria (id, usuario, operacao, endereco_ip, nome_tabela, dados_new, dados_old, criacao_alteracao) FROM stdin;
    public          postgres    false    216   p       a          0    51105    funcionario 
   TABLE DATA           �   COPY public.funcionario (id, cpf, is_logado, is_permissao, is_reset, login, nome, senha, sobrenome, telefone, tipo_acesso, ultimo_acesso) FROM stdin;
    public          postgres    false    204   K       c          0    51115    item_cardapio 
   TABLE DATA           M   COPY public.item_cardapio (id, descricao, is_ativo, nome, preco) FROM stdin;
    public          postgres    false    206   �       e          0    51125    mesa 
   TABLE DATA           4   COPY public.mesa (id, is_livre, numero) FROM stdin;
    public          postgres    false    208   ��       g          0    51132    pedido 
   TABLE DATA           Y   COPY public.pedido (id, data_pedido, status, total, funcionario_id, mesa_id) FROM stdin;
    public          postgres    false    210   ]�       i          0    51139    pedido_cardapio 
   TABLE DATA           J   COPY public.pedido_cardapio (id, item_cardapio_id, pedido_id) FROM stdin;
    public          postgres    false    212   )�       k          0    51146    senha_reset 
   TABLE DATA           ?   COPY public.senha_reset (id, data, funcionario_id) FROM stdin;
    public          postgres    false    214   �       �           0    0    auditoria_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.auditoria_id_seq', 212, true);
          public          postgres    false    215            �           0    0    funcionario_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.funcionario_id_seq', 18, true);
          public          postgres    false    203            �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public          postgres    false    202            �           0    0    item_cardapio_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.item_cardapio_id_seq', 14, true);
          public          postgres    false    205            �           0    0    mesa_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.mesa_id_seq', 18, true);
          public          postgres    false    207            �           0    0    pedido_cardapio_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pedido_cardapio_id_seq', 84, true);
          public          postgres    false    211            �           0    0    pedido_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pedido_id_seq', 35, true);
          public          postgres    false    209            �           0    0    senha_reset_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.senha_reset_id_seq', 26, true);
          public          postgres    false    213            �
           2606    51255    auditoria auditoria_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.auditoria
    ADD CONSTRAINT auditoria_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.auditoria DROP CONSTRAINT auditoria_pkey;
       public            postgres    false    216            �
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
       public          postgres    false    239    204            �
           2620    51257    funcionario trigger_auditoria    TRIGGER     �   CREATE TRIGGER trigger_auditoria AFTER INSERT OR DELETE OR UPDATE ON public.funcionario FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 6   DROP TRIGGER trigger_auditoria ON public.funcionario;
       public          postgres    false    204    240            �
           2620    51262 (   item_cardapio trigger_auditoria_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.item_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 A   DROP TRIGGER trigger_auditoria_cardapio ON public.item_cardapio;
       public          postgres    false    206    240            �
           2620    51263    mesa trigger_auditoria_mesa    TRIGGER     �   CREATE TRIGGER trigger_auditoria_mesa AFTER INSERT OR DELETE OR UPDATE ON public.mesa FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 4   DROP TRIGGER trigger_auditoria_mesa ON public.mesa;
       public          postgres    false    240    208            �
           2620    51264    pedido trigger_auditoria_pedido    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido AFTER INSERT OR DELETE OR UPDATE ON public.pedido FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 8   DROP TRIGGER trigger_auditoria_pedido ON public.pedido;
       public          postgres    false    240    210            �
           2620    51265 1   pedido_cardapio trigger_auditoria_pedido_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.pedido_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 J   DROP TRIGGER trigger_auditoria_pedido_cardapio ON public.pedido_cardapio;
       public          postgres    false    240    212            �
           2620    51266 )   senha_reset trigger_auditoria_senha_reset    TRIGGER     �   CREATE TRIGGER trigger_auditoria_senha_reset AFTER INSERT OR DELETE OR UPDATE ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 B   DROP TRIGGER trigger_auditoria_senha_reset ON public.senha_reset;
       public          postgres    false    240    214            �
           2620    51216    senha_reset trigger_reset    TRIGGER        CREATE TRIGGER trigger_reset BEFORE INSERT ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_validar_reset();
 2   DROP TRIGGER trigger_reset ON public.senha_reset;
       public          postgres    false    221    214            �
           2606    51165 +   pedido_cardapio fk2i4n8rrs0drx67l0bebekt98l    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l FOREIGN KEY (item_cardapio_id) REFERENCES public.item_cardapio(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l;
       public          postgres    false    206    2758    212            �
           2606    51175 '   senha_reset fk4ptvpighqhvr0afk9bg9o0oyq    FK CONSTRAINT     �   ALTER TABLE ONLY public.senha_reset
    ADD CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 Q   ALTER TABLE ONLY public.senha_reset DROP CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq;
       public          postgres    false    214    204    2752            �
           2606    51160 "   pedido fkf32po93klqxcumfjsf303g2vl    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fkf32po93klqxcumfjsf303g2vl FOREIGN KEY (mesa_id) REFERENCES public.mesa(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fkf32po93klqxcumfjsf303g2vl;
       public          postgres    false    2760    210    208            �
           2606    51155 "   pedido fki23ikc3j8n2eng9xk4qrgt3w5    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5 FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5;
       public          postgres    false    210    2752    204            �
           2606    51170 +   pedido_cardapio fkktyrjeounh5k0bokdcuf2mtpr    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr;
       public          postgres    false    210    2762    212            m   5  x��Mo9��ѯ|NzX��͎3���b�{ ��JV�
�1����V�,K�M��[RO,	3�E!_=d_�$�wOO����ݿ�~��էw��P����tVix����z���������Ei>\\�rQ^hc��:ċ�Ϸ��ӛ�E�ez�0�>�>��6x��>��=�_?��4��Ҿ�N��/�4*�n�m�������?S��S��ӥ��~՗��稬�~����v��ſ�g/��Ow��=}�8��}Y��g�����������Âk[�R����������=��>������~T���=�R���ʂs�1;��<���љ�TP����3���~��U�3Y��ﳛ�ͪA�pq3}�~^~J
��_�*U(�.�#������}����fv�8c�����E��æ��\4~��6{�V���~�mS���H��1Н�����L��>
�~��q����F�����˟)
0�.?��S�9L�"�ih�, Z�0L�v��w���)w�f�s���<�C�R���"�B��3˓�ڝ)R_
���4fևC����VWJeK��7�N�_�B��w׷O��A�ۼG,A�����lf�/!��F� J��ێl[�I!VWkt�P_����"��mL�\��^�ko�`���aR�lN6�K�BŽ RW�I�H�H��7#�����pU��5�k'YC�5�	�w��_i(Z1*K����k�BO��O_j���� ��D�nJ��;ԗ $WW'��l��z�,L��!J�쪡RY�y���K�mF��1���V�p�i)�/���aױ���H�.G��"�Ũ��'{.]XBB�\w����!����p����HZ!D�5�!5&���(�^����a��V5�'��3����g���U/>���1Mj���3���Q�iPs|�����l�hV��=ѽ<��p��*�@���N���Χ2�X�����> p�
����s���ؿ�:��\O�����#xV\��/,����t�F��U�C}x/_G��������:�;��s���[�1���H�������;sx��Ҿu>���c�|�� U�sp������û����������:�7��7��r�oaz��Yn�	�9�����s�obf��a������ﳜ�sN}��Te*m���sN}g��s�;���������k6�6��~��$���@a��'��o>��F�O�=$'�ը���O"�Q���;����:��wn�ni��N�&�읳|��;����s���;���;_O�g����>���@ o`�o�w��g��:�û� �YV���V:����uH�ӻ0��Lu���aOw�h�h�Sv��Y��?B{�HG豇����3��Gt�T�����h���s�:�眩��9g��|Ι��#4��3��=���J����f��O�O8B+�W>G袇�b|��@���n��W��+��3�&��ft�:���ft�>:�H8�1��Ƚt�8�q� Q��5� ��n��v�~|v�M>��Mm��I8��x��Y<g;��sv�8��ʜX2|��`���(x��S��5D��O :+_C$8��Sp�5D��n#:]tv�-<��-<8h0;��,Pg#- :[i��Lg�v=�%}zvڔ��{K�C��t��I��	H'a��S��t��I��mH�`���7֘�P�֡O�=X��nu��u)iMҠ��[=���*NJ�� ʘ0��\��L�N�i�����Jq�/�Yo�M��-��_�+����nqH:�[I�k��G�.\��!�umb�.�(+����� =��P-FH1kV���[�:#�:b#\!�H�(�+G��}P��/ ڈ�|]�Ph,?�N�$I�~ǅ���-����q?[]�w��mvO? �s�o�E��Fc�w����W�"8�1q�]]��}�Ʋ���2�o����C�2��G�&/���b~qh�\���������E��x����q������Kظ�oy�`䓫��}�ݿ�w�&�׸�{���~��.<���$�׾���Y��M
��	�}v�cD{~�f
�t5�ظ�7�B�n��\�Y*��T>Xa$�,m���u�^9�lm\+Iom�R�-ͨda��ͫ����}5_��V_M�x]��h���4r�v:��mz��1	�Y�Rl�A˳a�5�pGf9���ܒcM:�iR�7Z���i����V�I�����T��m���*o�O�@�h숤&�gy� �a~%
PHu��?0��N{�t��)�b�����;�\�a�����_T���n�e��
"���t �U��U]�'r�<�s:-ܒd��Zu��Sչ*M�	�vS��ә�t�:�H��
A:�Ii�f2��6jSm��A*0F���Y��TgS����Fi�u�gu�]��⣊��N��o���">
ߒ�U��VZZ��N�|B�5������Z�\� 
%�&d�H���Q��"ͅ��r�@d�<��T�0#�?#Rd]��"")�^����hk,=�F����*v��6d������8
�A���U�� γ�5?@Ķ4/$��Jq:�� !�_��eF���4:<(#��LY�.�㚼�\*�����-���<�<\���\���Tʗ%{�;�����[9L�P|=�"+�y7k=��w_��FҐ�.�@�G�J�k�-x��u�H��C
�����N��m�t�l>�yne|�7�Z3�u����������<����:(�����!�W
�Y��˲\4�S+�=u���3�G�݃[�h$L�{�R�^�伒�u##�^���v������M�}���w���2��	E��X:i��݁�;c�GE'��/j4�x�I[+G`����¨��]
�>�i[�dm�����0�Wa=�+V���;������{ d��T4�xa=���A�bu8��KB�gT)�X��]��/$A+E�ؾu�5������h���a�ڗe�8�����In�k�(��@H�f��ʴ�@�u.4wX��kک��2/b��9�Ծ.[S� ���-x�E��%�Q}O�:�{Є7zk5H�*pEo�'T�p�@5$L��3v���MTX�x�� �W	9��A�����5�J5���AO )��Ai�^o��-v�>�#�mB�H��,Ҡ�b}��A�����-H��{ˋ3AXxH@41�Փ��Nu�7Y��n#=h���5K0�!�*C��CϤ�J:h&�҅�Y� $3ƍ���twW-]Үt7I�����=s�n?��m�ٲ�E���m3�K�8�
��99ɴ+�V ��I4���W^���b����l_�YJ)�D�xQX,ɱK&�S�(
�6i����BŗQI���bos#u���]68Q�X�V�B��l�V�i�Q��vc�b���Ri�ߞ����H��o)/y�f}��Nu���|�O���W�t,t ��X�R�BkXx~�ȢK�C��.5b7�C~
�v'�DW�� �V����O�_�*pJ5
K�|!�̉u|ZV ,��$3"ܧzI@%��l��v��j���2a�u�{cWҫ�]�,���Ѣ������
�=�{��z�^�7vk5@��2�4'A���&�:ŉ�#S��c	�ƨ	V�q�<�[�ou����I�b_ӝ4B/Ec#I�>����������Crz+���Ɋ�@����~uP�_Q*4����\~����ژϯz5�E��@*Lt���V��v����=�L�`�M�㄃9����ފ��%�����	K�}7�E�[Ī`��F�J���L-5���M��^L&����_      a   �  x����r�(���)�⢁��@�q�`.�D�hʱ�d{y�md9�6�U�K�.K��*x�Q��z�yc����$D�1$��_+a��8���e�+f&f����p?2���6p���7��B��3໠���"ȴa���xh�5P��A����Rǅ8g��rFu��F��0����XA0޲C�VVo���2.���B��	-�P#x�y�&Zc�4
�xy�
�ƺ�����3�S�@����*��jM,+@f.q�!��ɖ5yu��q$9$YN=M�t8O�Z1�Ag��v�����Ok6[�R�, ��ֹSB65� )g��n�*�#n�+�}�\��n�:`�oc��L��2����J�)�$1����L�����q-��p_Ұw�G���ZC�ـv�=��xX��H�oKB��|*���,3��.4������)z�ܿZRҴ����Iٷ��������k��j�kɒJ�Hh���Y�.����e���I%��.`h������q���Z�vW6�!&%�ه!�d���.H���ډWĽ�������	��:���Яܳz,�o�-T�[[�߈�EQ])� 	r�5���]����t��Q�7�cÝmn��ϫuL�y���R*����0!W$���\�Y�վp�N�^�N�Ӂ�d^�;�W����Bu$d��/�2o���<��.����� %*%����ޤ�B���/1���*�,ߚ�sK���i��G���;���~���tH��,5vNl6�i
����;]~O��tyg�3�1����@���nܼ"����އ�p=Ao��L�Ur���+`�F$p��8�j��4���s?`���/O�>?��M���ɇ��+ ��œ@;��Tf9dg��f���aL�wv�Թ�M�Ȏ���������V	[2a�6g�F��E_k!XR�.���l_ ۚ�cj�w]������      c   �   x�uPIr�0<ϼE����\!�C�����,�$�^����0��%KFrԓ��'#���w2��!½��g�-60{�wu�g��@�@�b:'uٷ��4[���}<�:$8��c
��q8	��a�)D�*����t�lX�6i��Ԣ�ך"��A��%�Vރ�Q�f�w�lW(�V*��J�hC��Z�X����^)Z�:v\:���o&�鞏my��	��@��      e   P   x�̹�@�@[�J�_�(�˿�q�^�Kz��`��]nNyhec���|���Fsż�0!;&�������uc����K�      g   �  x�e�Kn�@D��S�!�i��,���Y��Ȗ�ڀV��*ead�@���"��,._��a��J;��Y�KCźaj�/�x%l�1T���6N_�WJ-ֆJI����.�ޘ
$��Ԥ6JPs�J�w6,NX�d�0�dP��\ͤTH��L���IFi��r�'����&.
�nJ�ԙ�^,�r����e�`��%����MZ�FԭF��a|	�_[w�a�E }Jٱ�敩iq)�y��>�?��oϒ)�a��Z����aL���+#B������e)�<(�3_�q+&&bI���.�to8�03�4% N�j1p9�f��2�=3�����{߮D�զ�r#��1�	��@�*���;)�.i���9H�A���6��^�0���HP�����љN�JI<�H�ڴ�>-������YKH�X; .v�ef9|B�?�D      i   �   x�%��0ߨ���q/鿎 �!�%š.&D�Q�遶ig�
���P������Sꈤې�w _?�E_!wz�F^
u�`�`���(�f��rdʈ9��_6�����%��ڤR/�Qo��[��A'����E���AO��+*n�#W�1��x����?��������/�      k   4   x�3�4202�50�54W02�22�2��36�4�22��345������� HH
{     