package codes.recursive.cnms.user;

import io.helidon.common.CollectionsHelper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Simple Application that managers users.
 */
@ApplicationScoped
@ApplicationPath("/")
public class ProductApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return CollectionsHelper.setOf(
                ProductResource.class,
                JacksonFeature.class
        );
    }

}