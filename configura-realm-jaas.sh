#!/bin/bash
path="/usr/local/glassfish-4.0/glassfish/bin/"  
cd "$path"
./asadmin create-auth-realm --classname com.sun.enterprise.security.ee.auth.realm.jdbc.JDBCRealm --property user-name-column=login:password-column=senha:group-name-column=nomePermissao:jaas-context=jdbcRealm:datasource-jndi="DentistaFonteDados":group-table=USUARIO_GRUPO_PERMISSAO_VIEW:user-table=USUARIO_GRUPO_PERMISSAO_VIEW:digest-algorithm=MD5:encoding=Hex "DentistaSecurity"

