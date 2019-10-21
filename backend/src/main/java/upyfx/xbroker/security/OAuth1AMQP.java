package upyfx.xbroker.security;

import upyfx.xbroker.model.DeviceAccessToken;
import org.apache.activemq.artemis.spi.core.security.jaas.RolePrincipal;
import org.apache.activemq.artemis.spi.core.security.jaas.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class OAuth1AMQP implements LoginModule {

    private CallbackHandler callbackHandler;
    private Subject subject;
    private boolean succeeded = false;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {

        Callback[] callbacks = new Callback[2];

        callbacks[0] = new NameCallback("Username");
        callbacks[1] = new PasswordCallback("Password", false);
        try {
            callbackHandler.handle(callbacks);
        } catch (IOException ioe) {
            throw (LoginException)new LoginException().initCause(ioe);
        } catch (UnsupportedCallbackException uce) {
            throw (LoginException)new LoginException().initCause(uce);
        }

        String password;

        String username = ((NameCallback)callbacks[0]).getName();
        if (username == null)
            return false;

        if (((PasswordCallback)callbacks[1]).getPassword() != null)
            password = new String(((PasswordCallback)callbacks[1]).getPassword());
        else
            password="";

        succeeded =  authenticate(username, password);
        return succeeded;
    }

    private boolean authenticate(String username, String password) {
        System.out.println(username + password);
        Query query = new Query()
                .addCriteria(Criteria.where("_id").is(username))
                .addCriteria(Criteria.where("accessToken").is(password))
                .addCriteria(Criteria.where("active").is(true));

        DeviceAccessToken deviceAccessToken = mongoTemplate.findOne(query, DeviceAccessToken.class);
        // if (deviceAccessToken == null) { return false; }
        // Device device = deviceAccessToken.getDevice();
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        if (succeeded) {
            subject.getPrincipals().add(new UserPrincipal("guest"));
            RolePrincipal groupPrincipal = new RolePrincipal("guest");
            subject.getPrincipals().add(groupPrincipal);
        }
        return succeeded;
    }

    @Override
    public boolean abort() throws LoginException {
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
