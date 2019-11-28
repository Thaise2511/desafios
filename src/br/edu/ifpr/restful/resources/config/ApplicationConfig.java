package br.edu.ifpr.restful.resources.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import br.edu.ifpr.restful.resources.GenericResource;

@ApplicationPath("/desafios")

public class ApplicationConfig extends Application {
   @Override
   public Set<Class<?>> getClasses() {
      Set<Class<?>> resources = new HashSet<>();

      addRestResourceClasses(resources);

      return resources;
   }

   private void addRestResourceClasses(Set<Class<?>> resources) {
      // classe(s) de recursos
      resources.add(GenericResource.class);
   }   
}
