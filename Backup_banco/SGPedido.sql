PGDMP         $                y            SGPedido    12.2    12.2 N    m           0    0    ENCODING    ENCODING         SET client_encoding = 'LATIN1';
                      false            n           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            o           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            p           1262    34027    SGPedido    DATABASE     �   CREATE DATABASE "SGPedido" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE "SGPedido";
                postgres    false            �            1255    51286    efetuar_cancelamento(integer)    FUNCTION     �   CREATE FUNCTION public.efetuar_cancelamento(mesa integer) RETURNS void
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
       public         heap    postgres    false            q           0    0    TABLE auditoria    ACL     S   GRANT SELECT,INSERT,UPDATE ON TABLE public.auditoria TO usuario WITH GRANT OPTION;
          public          postgres    false    216            �            1259    51244    auditoria_id_seq    SEQUENCE     �   CREATE SEQUENCE public.auditoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.auditoria_id_seq;
       public          postgres    false    216            r           0    0    auditoria_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.auditoria_id_seq OWNED BY public.auditoria.id;
          public          postgres    false    215            s           0    0    SEQUENCE auditoria_id_seq    ACL     C   GRANT SELECT,USAGE ON SEQUENCE public.auditoria_id_seq TO usuario;
          public          postgres    false    215            �            1259    51132    pedido    TABLE     �   CREATE TABLE public.pedido (
    id integer NOT NULL,
    data_pedido timestamp without time zone,
    status character varying(20),
    total double precision NOT NULL,
    funcionario_id integer,
    mesa_id integer
);
    DROP TABLE public.pedido;
       public         heap    postgres    false            t           0    0    TABLE pedido    ACL     I   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido TO funcionario;
          public          postgres    false    210            �            1259    51291    financia_anual    VIEW     �  CREATE VIEW public.financia_anual AS
 SELECT row_number() OVER (ORDER BY (sum(p.total))) AS id,
    date_part('year'::text, (p.data_pedido)::date) AS ano,
    date_part('month'::text, (p.data_pedido)::date) AS mes,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY (date_part('year'::text, (p.data_pedido)::date)), (date_part('month'::text, (p.data_pedido)::date));
 !   DROP VIEW public.financia_anual;
       public          postgres    false    210    210    210            u           0    0    TABLE financia_anual    ACL     8   GRANT SELECT ON TABLE public.financia_anual TO usuario;
          public          postgres    false    219            �            1259    51287    financia_mensal    VIEW     �   CREATE VIEW public.financia_mensal AS
 SELECT (p.data_pedido)::date AS data_pedido,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY ((p.data_pedido)::date);
 "   DROP VIEW public.financia_mensal;
       public          postgres    false    210    210    210            v           0    0    TABLE financia_mensal    ACL     9   GRANT SELECT ON TABLE public.financia_mensal TO usuario;
          public          postgres    false    218            �            1259    51105    funcionario    TABLE     �  CREATE TABLE public.funcionario (
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
       public         heap    postgres    false            w           0    0    TABLE funcionario    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.funcionario TO funcionario;
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
       public          postgres    false    204    204    204    204    204    204    204    204    204    204    204            x           0    0    TABLE funcionarios_view    ACL     ;   GRANT SELECT ON TABLE public.funcionarios_view TO usuario;
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
       public         heap    postgres    false            y           0    0    TABLE item_cardapio    ACL     P   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.item_cardapio TO funcionario;
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
       public         heap    postgres    false            z           0    0 
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
       public         heap    postgres    false            {           0    0    TABLE pedido_cardapio    ACL     R   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido_cardapio TO funcionario;
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
       public         heap    postgres    false            |           0    0    TABLE senha_reset    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.senha_reset TO funcionario;
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
       public          postgres    false    216    215    216            j          0    51246 	   auditoria 
   TABLE DATA           }   COPY public.auditoria (id, usuario, operacao, endereco_ip, nome_tabela, dados_new, dados_old, criacao_alteracao) FROM stdin;
    public          postgres    false    216   Ih       ^          0    51105    funcionario 
   TABLE DATA           �   COPY public.funcionario (id, cpf, is_logado, is_permissao, is_reset, login, nome, senha, sobrenome, telefone, tipo_acesso, ultimo_acesso) FROM stdin;
    public          postgres    false    204   Ro       `          0    51115    item_cardapio 
   TABLE DATA           M   COPY public.item_cardapio (id, descricao, is_ativo, nome, preco) FROM stdin;
    public          postgres    false    206   s       b          0    51125    mesa 
   TABLE DATA           4   COPY public.mesa (id, is_livre, numero) FROM stdin;
    public          postgres    false    208   t       d          0    51132    pedido 
   TABLE DATA           Y   COPY public.pedido (id, data_pedido, status, total, funcionario_id, mesa_id) FROM stdin;
    public          postgres    false    210   It       f          0    51139    pedido_cardapio 
   TABLE DATA           J   COPY public.pedido_cardapio (id, item_cardapio_id, pedido_id) FROM stdin;
    public          postgres    false    212   8u       h          0    51146    senha_reset 
   TABLE DATA           ?   COPY public.senha_reset (id, data, funcionario_id) FROM stdin;
    public          postgres    false    214   �u       }           0    0    auditoria_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.auditoria_id_seq', 113, true);
          public          postgres    false    215            ~           0    0    funcionario_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.funcionario_id_seq', 18, true);
          public          postgres    false    203                       0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public          postgres    false    202            �           0    0    item_cardapio_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.item_cardapio_id_seq', 14, true);
          public          postgres    false    205            �           0    0    mesa_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.mesa_id_seq', 9, true);
          public          postgres    false    207            �           0    0    pedido_cardapio_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pedido_cardapio_id_seq', 59, true);
          public          postgres    false    211            �           0    0    pedido_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pedido_id_seq', 19, true);
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
       public          postgres    false    204    236            �
           2620    51257    funcionario trigger_auditoria    TRIGGER     �   CREATE TRIGGER trigger_auditoria AFTER INSERT OR DELETE OR UPDATE ON public.funcionario FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 6   DROP TRIGGER trigger_auditoria ON public.funcionario;
       public          postgres    false    237    204            �
           2620    51262 (   item_cardapio trigger_auditoria_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.item_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 A   DROP TRIGGER trigger_auditoria_cardapio ON public.item_cardapio;
       public          postgres    false    237    206            �
           2620    51263    mesa trigger_auditoria_mesa    TRIGGER     �   CREATE TRIGGER trigger_auditoria_mesa AFTER INSERT OR DELETE OR UPDATE ON public.mesa FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 4   DROP TRIGGER trigger_auditoria_mesa ON public.mesa;
       public          postgres    false    208    237            �
           2620    51264    pedido trigger_auditoria_pedido    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido AFTER INSERT OR DELETE OR UPDATE ON public.pedido FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 8   DROP TRIGGER trigger_auditoria_pedido ON public.pedido;
       public          postgres    false    237    210            �
           2620    51265 1   pedido_cardapio trigger_auditoria_pedido_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.pedido_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 J   DROP TRIGGER trigger_auditoria_pedido_cardapio ON public.pedido_cardapio;
       public          postgres    false    212    237            �
           2620    51266 )   senha_reset trigger_auditoria_senha_reset    TRIGGER     �   CREATE TRIGGER trigger_auditoria_senha_reset AFTER INSERT OR DELETE OR UPDATE ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 B   DROP TRIGGER trigger_auditoria_senha_reset ON public.senha_reset;
       public          postgres    false    237    214            �
           2620    51216    senha_reset trigger_reset    TRIGGER        CREATE TRIGGER trigger_reset BEFORE INSERT ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_validar_reset();
 2   DROP TRIGGER trigger_reset ON public.senha_reset;
       public          postgres    false    214    221            �
           2606    51165 +   pedido_cardapio fk2i4n8rrs0drx67l0bebekt98l    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l FOREIGN KEY (item_cardapio_id) REFERENCES public.item_cardapio(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l;
       public          postgres    false    2755    206    212            �
           2606    51175 '   senha_reset fk4ptvpighqhvr0afk9bg9o0oyq    FK CONSTRAINT     �   ALTER TABLE ONLY public.senha_reset
    ADD CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 Q   ALTER TABLE ONLY public.senha_reset DROP CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq;
       public          postgres    false    214    2749    204            �
           2606    51160 "   pedido fkf32po93klqxcumfjsf303g2vl    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fkf32po93klqxcumfjsf303g2vl FOREIGN KEY (mesa_id) REFERENCES public.mesa(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fkf32po93klqxcumfjsf303g2vl;
       public          postgres    false    208    210    2757            �
           2606    51155 "   pedido fki23ikc3j8n2eng9xk4qrgt3w5    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5 FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5;
       public          postgres    false    210    2749    204            �
           2606    51170 +   pedido_cardapio fkktyrjeounh5k0bokdcuf2mtpr    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr;
       public          postgres    false    212    210    2759            j   �  x��[o�6���_�9Qyx'�
8Űyo�V:�e���!�}��\hӤ)[J,	�C�(<ǟ��O�"�ڬ6�rZ\�������
�H���֜!W��xZ̷G�7�N��Ƌ��ʸ���T���tu?+�g�b���*߶,���t�ʬ�e���/懦s���,�wZ��L_��1i2_���ߙi& �rv�����B�
�_�CB@^Z�����?���X��u>��yٶ�Y
e�tQ�g�|�*�h���|o3[O_�;��fO�X/7���1�p��-�kP� �q*��&SXv~2�&�Ri9N ��_��q���L�cs�"�L'����`�����&���Gi$5#)\~�u�ެ�!�|>���̾Xg�����m��e��f𘯲������׷��v�L�f��3�g�I�x�
�|��:|i�Fd7��������Kû_�v�;�e\;	�2��2vt�q�;}�̹��1�y��롻�a��Д��RP���&SXv~2�&�J�،%�Kج��r����4���RA�UN��ET)�j\�ǳMyx��M�1ր4�Tas�h*��C+�# MԶ{�#��2u!�kou;"�����\��?mJ5� gAT)��Q�R$��B�|i���"uDUb9fZeFD)�G3Sk.#Uʷu��bT-�Ii!h��բX����虑���1��I���O�	-�'U2T?g�m���xB�p6!bd��L0� �4��@�|�T"@D�?g�tJ�}FG��1bQ��`�0B�䢩H�-���QT)�>�p8!E耨<��ҹHʐ

T��A�X<"�H�
�#S��v�QB-T�WHU":f�}�d�*6WmnRU}|T�**w�F<�Ç�(>�y\����Q|�8\�+�&���M���A�42WHR 	�r��0�j}�����R<�(�>��[e�ۿqE���Ć9��� �RI8���M�F��%ڢS�D�tj����#T�@7��z�M3�O����������6]m>�t������O���������yg��m~ç~m��\�1��e̗pq=�w���9S
�ƙ�E\�FOf�8w��K�ع��o<������Ԟ~��ˇ��~>�Ƴ�O�77w�W�7����ys'v5��SF� 8aq�܉]�O���հ�Ĺs'v5,>Q�܁[~m:�;��1ÒA*8��'�wg/޻��O��~>�;������l>�gYs��DA�@PHx�}���hR�}��RiR�*���}چT�O��轤�F�B��6�w a�i��%��f}vH��z}~H-t�j5��u�}~D�N���J)SL�D�;�~>�N��O�S����T��P���4�����F"e�
E�B�>'�B�>+�Zt��X��	�Y��M�,W�.��3#�SB�'p%�F3j�C;�hF-�h���Z��F��3j�O;�>��Py?�H@"{���Q�M{��2��ӻ�N@�%�ۊ��ʎ���%*`�D]0iQ@��� ���G;u��-D��G��� ������� N�Y�z���ԋt P��@�L{�t��9���i�)N�B0) �H�jR@��:��6��T; uB�mH�އ������BǨ[�\b�����I1Ƹ��a�R�'9n������rb�11' Q* �[[\���3t�A}��T�8���{���Gdq:p���G�5"[A�7��M�/]���/;|���+��u}�~]�
g�s��{�k�D�1b8����|K�$�|��      ^   �  x����n�8���S���HJ�I�t�'��'ug=H�{��/�8M�3��*0B ?��OP�}�gu�祟����zG�]Ɛ�W|���#Sc�^��?(f&f���|܏���=)�<j|� �Zh�w|#Xc��t��O�*�Ꜥw����	P��)�vl�z��~���JɥՏa���N�cZ��;�&�?�7rr�v�_Nǅ���:h�]�Y[J���BΨ���WW �_j��W�
�����}�Os?=����Oh���{ȣ5����(���ޫkUƺ�A~):x&��7���_u[#U�)��c��%v�E0��&����"���$�>Z(Њ�:k�k�~�����Z���-@�u.Ɣ�MM�A��c�F�J舗�w�� ��j�� ��������}T�]I%֔�jR�BSљ(O�%�U&���H�օ�κF�o�g:��ԏ�q^#9�-	���`>��H�E�P���˫�)zQ�_�@ʴ����I���ȇy߯��
����,����뜘%�b ��y��a��8QR�_�V����nc��ԯ:.�����ġd5�Ѕ�饋�����v�q��g}�ig�iĸ<:A;�+��~�ߧq8,�-T�[[�߈����R.A
�x�mЗ��J���4�ZC;6���m������a�l�]CUJ���Ҙ�+��~��I\�Y�lp�N�V�N�i�K1�S�����vՑ���t[$�"�Jx>�o��)g�%�jsv1ht��l-K��|O�v���_,㔹��K���0o��������N{�R
%���L��*�p�+����rvQNs�v�7F��a��O/�t�Hk9}�,NvN�Ell52zn�Ӻ�<��|�?�R� �Zy��oH-/q�\᷻E������-����J-X�\00ɸ8�N�j�<|X�k�횦�����      `   �   x�uPIr�0<ϼE����\!�C�����,�$�^����0��%KFrԓ��'#���w2��!½��g�-60{�wu�g��@�@�b:'uٷ��4[���}<�:$8��c
��q8	��a�)D�*����t�lX�6i��Ԣ�ך"��A��%�Vރ�Q�f�w�lW(�V*��J�hC��Z�X����^)Z�:v\:���o&�鞏my��	��@��      b   2   x���  �7[�	���X��ǛǸ=s���N�ޔ.Z7GBK/� >"�	s      d   �   x�]�;n1Dk�{��y7'H$�Ik����Ù�D@x�zC� bN�ۿ~�C����h�)��T�����vRHB�Rq���u��zj�XiM�c�y|~�S�f�0{�7=V�P�,�H������/�ۺ/��eܦ-��b"
�Y�0\�g(mf�+%c�x{�d������/n!V����W#uD<�9��%��kZf�      f   V   x�%���0��x�^�\v��s�����d��m0-��v|�e5��Kg�BH��:���n1���C>B'T�+��},h��� ?l��      h   4   x�3�4202�50�54W02�22�2��36�4�22��345������� HH
{     