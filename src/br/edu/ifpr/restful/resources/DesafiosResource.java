package br.edu.ifpr.restful.crud.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.edu.ifpr.restful.crud.model.DesafioBean;

@Singleton
@Path("desafio")
public class DesafiosResource
{
    private static final Logger LOGGER = Logger.getLogger(DesafioBeanResource.class.getName());
    
    @Context UriInfo uriInfo;
    
    private Map<Integer, DesafioBean> desafios;
    
    public DesafiosResource()
    {
        LOGGER.info("");
        populate();
    }

    private void populate()
    {
        LOGGER.info("");
        
        desafios = new HashMap<>();
        
        desafios.put(1, new DesafioBean(1,"Difícil", "Tente beber um copo de água usando os seus pés"));
        desafios.put(2, new DesafioBean(2,"Fácil", "Finja ser um gato e se esfregue nas pessoas ao lado"));
        desafios.put(3, new DesafioBean(3,"Fácil", "Deixe que as pessoas preparem um drink para você com 3 ingredientes aleatórios"));
        desafios.put(4, new DesafioBean(4,"Médio", "Massageie os pés da pessoa a sua direita"));
        desafios.put(5, new DesafioBean(5,"Difícil", "Deixe a pessoa a sua esquerda fazer uma transformação em você")); 
        desafios.put(6, new DesafioBean(6,"Fácil", "Leia em voz alta as mensagens das 10 primeiras conversas do seu celular"));
        desafios.put(7, new DesafioBean(7,"Médio", "Mande uma mensagem pro seu chefe perguntando 'e aí, qual a boa de hj?'"));
        desafios.put(8, new DesafioBean(8,"Difícil", "Faça um chá com alguma coisa estranha(tipo uma meia) e depois beba"));
        desafios.put(9, new DesafioBean(9,"Médio", "Faça 20 abdominais"));
        desafios.put(10, new DesafioBean(10,"Fácil", "Ligue para a última pessoa que te ligou e peça 'perdão pelo q te fiz'"));
        desafios.put(11, new DesafioBean(11,"Fácil", "Mande uma mensagem para a última pessoa que te ligou perguntando 'qual é o seu problema?'"));
        desafios.put(12, new DesafioBean(12,"Fácil", "Troque o seu sobrenome no Facebook para Junior, Jr."));
        desafios.put(13, new DesafioBean(13,"Médio", "Ligue para um restaurante e tente manter o atendente em linha por cinco minutos, imitanto um gringo falando português"));
        desafios.put(14, new DesafioBean(14,"Fácil", "Vista sua roupa ao contrário"));
        desafios.put(15, new DesafioBean(15,"Médio", "A próxima vez que você for ao banheiro, ligue para sua mãe e converse com ela durante todo o processo"));
        desafios.put(16, new DesafioBean(16,"Médio", "Ligue para uma lanchonete ou pizzaria e tente desabafar com atendente, dizendo que sua (seu) namorada (o) terminou contigo"));
        desafios.put(14, new DesafioBean(14,"Fácil", ""));
    }
    
    @POST
    public Response insertDesafioBean(@BeanParam DesafioBeanBean DesafioBeanBean)
    {
        int id = DesafioBeans.size()+1;
        DesafioBean DesafioBean = new DesafioBean(DesafioBeanBean);
        DesafioBeans.put(id,DesafioBean);
        return Response.ok().build();
    }
    
    @PUT
    public Response updateDesafioBean(@BeanParam DesafioBeanBean DesafioBeanBean)
    {
        int id = DesafioBeanBean.getId();
        DesafioBean DesafioBean = new DesafioBean(DesafioBeanBean);
        DesafioBeans.replace(id,DesafioBean);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{id:[0-9]}")
    public Response deleteDesafioBean(@PathParam("id") Integer id)
    {
        LOGGER.info("");
        DesafioBeans.remove(id);
        return Response.ok().build();
    }
    
    @GET @Path("{id:[0-9]}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getDesafioBean(@PathParam("id") Integer id)
    {
        DesafioBean DesafioBean = DesafioBeans.get(id);
        if(DesafioBean != null)
        {
            GenericEntity<DesafioBean> entity = new GenericEntity<DesafioBean> (DesafioBeans.get(id)){};
            return Response.ok().entity(entity).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getDesafioBeans()
    {
        List<DesafioBean> listDesafioBeans = new ArrayList<>(DesafioBeans.values());
        GenericEntity<List<DesafioBean>> entity = new GenericEntity<List<DesafioBean>> (listDesafioBeans){};
        return Response.ok().entity(entity).build();
    }
}