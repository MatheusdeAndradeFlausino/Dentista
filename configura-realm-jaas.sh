#!/bin/bash
path="/home/vanderlei/glassfish-4.1/bin"  
cd "$path"
./asadmin create-auth-realm --classname com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property user-name-column=login:password-column=senha:group-name-column=nomePermissao:jaas-context=jdbcRealm:datasource-jndi="DentistaFonteDados":group-table=USUARIO_GRUPO_PERMISSAO_VIEW:user-table=USUARIO_GRUPO_PERMISSAO_VIEW:digest-algorithm=MD5:encoding=Hex "DentistaSecurity"

