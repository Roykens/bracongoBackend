package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.royken.bracongo.survey.resource.impl.AuthenticationResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.BoissonResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.MaterielResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.PlvResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.PointDeVenteResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.QuestionResourceImpl.class);
        resources.add(com.royken.bracongo.survey.resource.impl.ReponseResourceImpl.class);
    }
    
}
