//dataSource {
//    pooled = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""
//}

dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            //dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			username = "root"
			password = ""
			url = "jdbc:mysql://localhost:3306/goot"
			dbCreate = 'update'
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
		
    production {
        dataSource {
			//jndiName = "java:comp/env/gootdb"
			username = "root"
			password = "root"
			url = "jdbc:mysql://localhost:3306/goot"
			dbCreate = 'update'
        }
    }
}
