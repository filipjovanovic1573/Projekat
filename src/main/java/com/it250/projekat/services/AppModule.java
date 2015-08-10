package com.it250.projekat.services;

import com.it250.projekat.dao.CommentDao;
import com.it250.projekat.dao.CommentDaoImpl;
import com.it250.projekat.dao.GenericDao;
import com.it250.projekat.dao.GenericDaoImpl;
import com.it250.projekat.dao.SongDao;
import com.it250.projekat.dao.SongDaoImpl;
import com.it250.projekat.dao.UserDao;
import com.it250.projekat.dao.UserDaoImpl;
import com.it250.projekat.entities.User;
import java.io.IOException;
import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.*;
import org.slf4j.Logger;


public class AppModule {

    public static void bind(ServiceBinder binder) {
        binder.bind(GenericDao.class, GenericDaoImpl.class);
        binder.bind(UserDao.class, UserDaoImpl.class);
        binder.bind(SongDao.class, SongDaoImpl.class);
        binder.bind(CommentDao.class, CommentDaoImpl.class);
    }

    public static void contributeFactoryDefaults(
            MappedConfiguration<String, Object> configuration) {
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        configuration.override(SymbolConstants.PRODUCTION_MODE, false);
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en, sr_RS");
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "1234qwer");
    }

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
//		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
        configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
    }

    public RequestFilter buildTimingFilter(final Logger log) {
        return new RequestFilter() {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException {
                long startTime = System.currentTimeMillis();

                try {
                    return handler.service(request, response);
                } finally {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

    public void contributeComponentRequestHandler(OrderedConfiguration<ComponentRequestFilter> configuration) {
        configuration.addInstance("PageProtectionFilter", PageProtectionFilter.class);
    }

    public void contributeComponentEventResultProcessor(MappedConfiguration<Class<?>, ComponentEventResultProcessor<?>> configuration, Response response) {
        configuration.add(OutputStreamResponse.class, new OutputStreamResponseResultProcessor(response));
    }
    
    public static void contributeValueEncoderSource(MappedConfiguration<Class<User>, ValueEncoderFactory<User>> configuration) { 
        configuration.overrideInstance(User.class, UserEncoder.class);
    }

}
