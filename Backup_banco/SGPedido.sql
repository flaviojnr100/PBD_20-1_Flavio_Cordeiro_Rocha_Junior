PGDMP         /                y            SGPedido    12.2    12.2 O    n           0    0    ENCODING    ENCODING         SET client_encoding = 'LATIN1';
                      false            o           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            p           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            q           1262    34027    SGPedido    DATABASE     �   CREATE DATABASE "SGPedido" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
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
       public         heap    postgres    false            r           0    0    TABLE pedido    ACL     I   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido TO funcionario;
          public          postgres    false    210            �            1259    51287    financia_mensal    VIEW     �   CREATE VIEW public.financia_mensal AS
 SELECT (p.data_pedido)::date AS data_pedido,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY ((p.data_pedido)::date);
 "   DROP VIEW public.financia_mensal;
       public          postgres    false    210    210    210            s           0    0    TABLE financia_mensal    ACL     9   GRANT SELECT ON TABLE public.financia_mensal TO usuario;
          public          postgres    false    218            �            1255    51307 8   buscar_entre_datas(character varying, character varying)    FUNCTION     �  CREATE FUNCTION public.buscar_entre_datas(inicio character varying, fim character varying) RETURNS SETOF public.financia_mensal
    LANGUAGE plpgsql
    AS $$

begin
	if(inicio>fim) then
		raise exception 'Erro, inicio tem que ser menor que fim';
	else
		return query select * from financia_mensal as fm where fm.data_pedido >= inicio::DATE and fm.data_pedido::DATE <=fim::DATE;
	end if;
	return;
	 
end;
$$;
 Z   DROP FUNCTION public.buscar_entre_datas(inicio character varying, fim character varying);
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
       public         heap    postgres    false            t           0    0    TABLE auditoria    ACL     S   GRANT SELECT,INSERT,UPDATE ON TABLE public.auditoria TO usuario WITH GRANT OPTION;
          public          postgres    false    216            �            1259    51244    auditoria_id_seq    SEQUENCE     �   CREATE SEQUENCE public.auditoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.auditoria_id_seq;
       public          postgres    false    216            u           0    0    auditoria_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.auditoria_id_seq OWNED BY public.auditoria.id;
          public          postgres    false    215            v           0    0    SEQUENCE auditoria_id_seq    ACL     C   GRANT SELECT,USAGE ON SEQUENCE public.auditoria_id_seq TO usuario;
          public          postgres    false    215            �            1259    51291    financia_anual    VIEW     �  CREATE VIEW public.financia_anual AS
 SELECT row_number() OVER (ORDER BY (sum(p.total))) AS id,
    date_part('year'::text, (p.data_pedido)::date) AS ano,
    date_part('month'::text, (p.data_pedido)::date) AS mes,
    sum(p.total) AS total
   FROM public.pedido p
  WHERE ((p.status)::text = 'pago'::text)
  GROUP BY (date_part('year'::text, (p.data_pedido)::date)), (date_part('month'::text, (p.data_pedido)::date));
 !   DROP VIEW public.financia_anual;
       public          postgres    false    210    210    210            w           0    0    TABLE financia_anual    ACL     8   GRANT SELECT ON TABLE public.financia_anual TO usuario;
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
       public         heap    postgres    false            x           0    0    TABLE funcionario    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.funcionario TO funcionario;
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
       public          postgres    false    204    204    204    204    204    204    204    204    204    204    204            y           0    0    TABLE funcionarios_view    ACL     ;   GRANT SELECT ON TABLE public.funcionarios_view TO usuario;
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
       public         heap    postgres    false            z           0    0    TABLE item_cardapio    ACL     P   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.item_cardapio TO funcionario;
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
       public         heap    postgres    false            {           0    0 
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
       public         heap    postgres    false            |           0    0    TABLE pedido_cardapio    ACL     R   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pedido_cardapio TO funcionario;
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
       public         heap    postgres    false            }           0    0    TABLE senha_reset    ACL     N   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.senha_reset TO funcionario;
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
       public          postgres    false    216    215    216            k          0    51246 	   auditoria 
   TABLE DATA           }   COPY public.auditoria (id, usuario, operacao, endereco_ip, nome_tabela, dados_new, dados_old, criacao_alteracao) FROM stdin;
    public          postgres    false    216   �j       _          0    51105    funcionario 
   TABLE DATA           �   COPY public.funcionario (id, cpf, is_logado, is_permissao, is_reset, login, nome, senha, sobrenome, telefone, tipo_acesso, ultimo_acesso) FROM stdin;
    public          postgres    false    204   �s       a          0    51115    item_cardapio 
   TABLE DATA           M   COPY public.item_cardapio (id, descricao, is_ativo, nome, preco) FROM stdin;
    public          postgres    false    206   Rw       c          0    51125    mesa 
   TABLE DATA           4   COPY public.mesa (id, is_livre, numero) FROM stdin;
    public          postgres    false    208   Bx       e          0    51132    pedido 
   TABLE DATA           Y   COPY public.pedido (id, data_pedido, status, total, funcionario_id, mesa_id) FROM stdin;
    public          postgres    false    210   �x       g          0    51139    pedido_cardapio 
   TABLE DATA           J   COPY public.pedido_cardapio (id, item_cardapio_id, pedido_id) FROM stdin;
    public          postgres    false    212   �y       i          0    51146    senha_reset 
   TABLE DATA           ?   COPY public.senha_reset (id, data, funcionario_id) FROM stdin;
    public          postgres    false    214   �y       ~           0    0    auditoria_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.auditoria_id_seq', 127, true);
          public          postgres    false    215                       0    0    funcionario_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.funcionario_id_seq', 18, true);
          public          postgres    false    203            �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public          postgres    false    202            �           0    0    item_cardapio_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.item_cardapio_id_seq', 14, true);
          public          postgres    false    205            �           0    0    mesa_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.mesa_id_seq', 9, true);
          public          postgres    false    207            �           0    0    pedido_cardapio_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pedido_cardapio_id_seq', 61, true);
          public          postgres    false    211            �           0    0    pedido_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pedido_id_seq', 20, true);
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
       public          postgres    false    204    237            �
           2620    51257    funcionario trigger_auditoria    TRIGGER     �   CREATE TRIGGER trigger_auditoria AFTER INSERT OR DELETE OR UPDATE ON public.funcionario FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 6   DROP TRIGGER trigger_auditoria ON public.funcionario;
       public          postgres    false    238    204            �
           2620    51262 (   item_cardapio trigger_auditoria_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.item_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 A   DROP TRIGGER trigger_auditoria_cardapio ON public.item_cardapio;
       public          postgres    false    238    206            �
           2620    51263    mesa trigger_auditoria_mesa    TRIGGER     �   CREATE TRIGGER trigger_auditoria_mesa AFTER INSERT OR DELETE OR UPDATE ON public.mesa FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 4   DROP TRIGGER trigger_auditoria_mesa ON public.mesa;
       public          postgres    false    238    208            �
           2620    51264    pedido trigger_auditoria_pedido    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido AFTER INSERT OR DELETE OR UPDATE ON public.pedido FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 8   DROP TRIGGER trigger_auditoria_pedido ON public.pedido;
       public          postgres    false    210    238            �
           2620    51265 1   pedido_cardapio trigger_auditoria_pedido_cardapio    TRIGGER     �   CREATE TRIGGER trigger_auditoria_pedido_cardapio AFTER INSERT OR DELETE OR UPDATE ON public.pedido_cardapio FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 J   DROP TRIGGER trigger_auditoria_pedido_cardapio ON public.pedido_cardapio;
       public          postgres    false    212    238            �
           2620    51266 )   senha_reset trigger_auditoria_senha_reset    TRIGGER     �   CREATE TRIGGER trigger_auditoria_senha_reset AFTER INSERT OR DELETE OR UPDATE ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_auditoria();
 B   DROP TRIGGER trigger_auditoria_senha_reset ON public.senha_reset;
       public          postgres    false    238    214            �
           2620    51216    senha_reset trigger_reset    TRIGGER        CREATE TRIGGER trigger_reset BEFORE INSERT ON public.senha_reset FOR EACH ROW EXECUTE FUNCTION public.trigger_validar_reset();
 2   DROP TRIGGER trigger_reset ON public.senha_reset;
       public          postgres    false    214    221            �
           2606    51165 +   pedido_cardapio fk2i4n8rrs0drx67l0bebekt98l    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l FOREIGN KEY (item_cardapio_id) REFERENCES public.item_cardapio(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fk2i4n8rrs0drx67l0bebekt98l;
       public          postgres    false    2756    206    212            �
           2606    51175 '   senha_reset fk4ptvpighqhvr0afk9bg9o0oyq    FK CONSTRAINT     �   ALTER TABLE ONLY public.senha_reset
    ADD CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 Q   ALTER TABLE ONLY public.senha_reset DROP CONSTRAINT fk4ptvpighqhvr0afk9bg9o0oyq;
       public          postgres    false    214    2750    204            �
           2606    51160 "   pedido fkf32po93klqxcumfjsf303g2vl    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fkf32po93klqxcumfjsf303g2vl FOREIGN KEY (mesa_id) REFERENCES public.mesa(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fkf32po93klqxcumfjsf303g2vl;
       public          postgres    false    210    2758    208            �
           2606    51155 "   pedido fki23ikc3j8n2eng9xk4qrgt3w5    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5 FOREIGN KEY (funcionario_id) REFERENCES public.funcionario(id);
 L   ALTER TABLE ONLY public.pedido DROP CONSTRAINT fki23ikc3j8n2eng9xk4qrgt3w5;
       public          postgres    false    210    204    2750            �
           2606    51170 +   pedido_cardapio fkktyrjeounh5k0bokdcuf2mtpr    FK CONSTRAINT     �   ALTER TABLE ONLY public.pedido_cardapio
    ADD CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);
 U   ALTER TABLE ONLY public.pedido_cardapio DROP CONSTRAINT fkktyrjeounh5k0bokdcuf2mtpr;
       public          postgres    false    212    2760    210            k   �  x��[o�6���_Q�9Qyx�޺:Űyo�Q2��җ!�}��ئ͐�l��$����ǟx���<�.V�U>���>�4�� ,R��G�3�����l<)g��Ln���ـPƅL�\&��iy�ߖ��.�.����b�0Y,r��X���C��.�n����4]֬|(6�����\_�Br=����'� ����"��Z�=/~����٤���bZܕ3sm�"���cy���Ţ4�m?����t9y���l5�>`9_������W@>���0Nb�y�dcj��w����T�'�7��ׯ^�1��X��X�Nn_����m��o����ŏ0ʐ�IA`��r�з<��b�,L��2�2�.wC�1E���C������o_w#��t��7G~3����+0�&��as}]#�����c�� �׿]��Cw �ȸ�P& ��2�� ��6��|�s����|�χ�!����TQ
Ja�������L��I�$�mI���s�=r�pʮ��ˀ��Rk8��˨��zU�r6���핆n��P<UX?6���%��@FԺx�_G�0d�BT������9A�J���BD�aS�!�
rD��:"
X���0.D��.^d�S�N��J]��V�QJ3�ьp���e�J�z�?v0�V�Qi!h���c�X���Q��G�Ĵ_$m<�7~��P3~R%C���L����A(\�M�h٦)1H0�!(2_O���?S�!�7��cĢ��!�!KFEJDh�olE��;D��G�GzET^�O�\$eHF�m#j�>"�H��
�#=�����:Z������io��GIFQ�bs���&U��GE�rG�i�S=|��Ӝ�5)�>>ŧ��U[Q6)�>>�m�X��=D,CzU�$��(�>c�V�R�V��2V�χ(ʪ_�x���wܠ@]db�y�`GTJ%�dB�7iͻ�h�N�mөٟ+��P�����)6�l>�t���'�C��s�t���/�Mn���l>���3p�:�����uڼç~m����1�Љ2�sX�F��=:�8'bJa��8s>�uitg��s�sX���e�~���ϺLD��z��7��|X�����o<��Dyssoz5���N�7wbWü��2*�	���N�jX|����Į��'Ν;��a�r���k���y��n�R�	E<a�;{��ޝ�|zw�����G�~w�q1͛�O!@2�
	�_�����,چT�E�g���~�v@z�>mC�ݧ�a����F�B��ֻ� ��T1�Kx���D����Z����@�։���;�.>*�L1���T���;�~>�N��O�S���B�����>��3���*MD}��|dU��|Zh���b|Z'�'�C��(if�.��#�SB�	\��ьZ��F,�Q�=��(p¢�Q�]��(pp�!�v���P���| ���	E����mڋ��i/�ޥ=p*-��V�3"0E)KT����`���H;uA�-D�v �G[�}��m�-� �զ8�z2�5��P/�@�J �2���i,�9���i�Q�2̅`R@��:!�6��R; uB�mH�v@�TېZ}�Zm#�gk��cԭ#��`�=sFR�1�!��8 U:Y���;>�k͉A��~ �R� ����S�3tA��C�\���wOP�="��+ؔ?�!���`��t<u蛢A
�u�d�;��T�t}�Nק�D�T��z���{P�N��z�Ҍ�ɹ��|n�%�`"���H̱�����\X�̞/?��}��9����Ϻ�)���A\ia��ܺ����d6OV���|f�Iؔ9|�Ngv_���Ke��� F&/��(I��~:�G ��Y�xik�`ĜQ-��P7�������m��BM�������<uN>Rʽm��@��Gz^�2���C^dϛ:�m�q�=��a3����Q8�-F�d���� ���T�/������\S9`z#9Rz�H|�A(~a
ۊ�&&�����8[=��,���7��p03K*!1��Ic�Qo�Ƅ|�z�9�������襔H�$D�g�7���#���1��yV~髟�^4/�s������~8�_g��o� �.k��N
n���`�R�Tc���T7��dɷ4I��Cp5>      _   �  x����n�8���S�4%��|�,�O0O��x�&���Ч_�q�N���*0Bđ>��OP��R��,�Ӹ�#{T�� !��!Y��Z�@��A=��q�����S����|<�ޣ2����;�� {=�΀.x#� ӆ]Ƨ�a�@Z~H6kK	�1�B��i޿��!�`��~��c�x��[9�}��˸<cU�'�PB���њh�qf�(���uT+D��	�
�$wy����������±d��	�l	Q�W��p>�G�C����i��1@+2謑���u|X�k6[�R�, ��ֹSB65� )g��nyTBGܮ7�G�\��n�:`��k��N��:����J�)�$1����L�����q�Iv�/iػУ�Y׉	�!�l@���y<��k$�oKB��|*���,;��.4����Y��(����4m�p�$dR�}m���~<5�(���Z��8�apb���p>��iY�yi�q���?��-*Z��n��8�:�oesb�P���}BI���EQ�ZѦ�xE���Y�kڙ`:1��N���=���e�����{k+X���;�TTW�%H�oa�}�6�!(݁�f���ڱ��6����ͺ?��YwU)s`G�0!W$�����x�.l�}�$^���Ɲ�Ӂ�d�ևJ������#!��}�:�aM���_���k���ٖLX���Š�1����,�ߗ�|\�If�)>Y�)�ކ���t9]��������N{�R
%���Lr�U:���E9۔�ܣ�A��r��"����b��x���[,���
��毀IZ��I]ţUK�Oӛ>��MS�]��[j-R��W�3.W@��ŕ@;��T/���Yjp��W�0���;�C�:\���7><�W�a �E�Y� �9y山��Xq��}���\N�-GwF7b�w`�����o�:��      a   �   x�uPIr�0<ϼE����\!�C�����,�$�^����0��%KFrԓ��'#���w2��!½��g�-60{�wu�g��@�@�b:'uٷ��4[���}<�:$8��c
��q8	��a�)D�*����t�lX�6i��Ԣ�ך"��A��%�Vރ�Q�f�w�lW(�V*��J�hC��Z�X����^)Z�:v\:���o&�鞏my��	��@��      c   2   x���  �7[�	���X��ǛǸ=s���N�ޔ.Z7���G� >"V	s      e   �   x�]�9n�0Ek���ܴ�gIcL�4A��W��f,@����%12ݰܨm�~����ć�I2�R�m���(P�N�řڤ�7B���`H'?$��<����96车��}���ߏ`N�I.��bN��s$���t�T+4Y��ԠcA��uZT1�ݦ��[���ũ@S�P�����б<��É���aj����Ǡ'K�^�]H����=/R�j�C�,<vd�Gz�}
T������ ��nG      g   ^   x�%���0ߨ��9�ǽ��:���Z�FKxZ؁���=�/��c������;2l_�Ϫ�<QT;��^�l�{�����������      i   4   x�3�4202�50�54W02�22�2��36�4�22��345������� HH
{     