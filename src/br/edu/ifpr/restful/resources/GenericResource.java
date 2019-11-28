package br.edu.ifpr.restful.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.edu.ifpr.restful.beans.DesafioBean;

@Path("generic")
public class GenericResource {
   @GET
//   @Produces("application/xml")
// OU
   @Produces(MediaType.APPLICATION_XML)
   // o valor retornado como padr�o para uma requisição GET
//   @Path("xml")
   public String getXml() {
      return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
             "<contatos>" +
             "<contato id=\"1\">" +
             "<nome>Ana Neri</nome>" +
             "</contato>" +
             "<contato id=\"2\">" +
             "<nome>Albert Einstein</nome>" +
             "</contato>" +
             "<contato id=\"3\">" +
             "<nome>Ada Lovelace</nome>" +
             "</contato>" +
             "</contatos>";
   }

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   // para evitar a anotação a seguir, o cabeçalho
   // "Accept:application/json" deve ser usado.
//   @Path("json")
   public String getJson() {
      return "{ \"contatos\": {" +
             "\"contato\": [" +
             "{ \"id\": 1, \"nome\": \"Ana Neri\" }," +
             "{ \"id\": 2, \"nome\": \"Albert Einstein\" }," +
             "{ \"id\": 3, \"nome\": \"Ada Lovelace\" }" +
             "]}}";
   }
   
   @GET @Path("/id/{id}")
   public Response getById(@PathParam("id") Long id) {
      return Response.status(Response.Status.OK)
                     .entity("Metodo: getUserById() [" + "id:" + id + "]")
                     .build();
   }

   @GET @Path("/id/{id}/{nome}")
   public Response getByIdName(@PathParam("id") Long id,
                               @PathParam("nome") String nome) {
      return Response.status(Response.Status.OK)
                     .entity("Metodo: getUserByIdName() [" + "id:" + id +
                             ", nome:" + nome + "]")
                     .build();
   }

   @GET @Path("/query1")
   public Response getQuery1(@DefaultValue("1") @QueryParam("from") Integer from,
                             @DefaultValue("100") @QueryParam("to") Integer to,
                             @QueryParam("orderBy") List<String> orderBy) {
      return Response.status(200).entity("Metodo: getUsers()::query1 [" +
                                         "from:" + from +
                                         ", to:" + to +
                                         ", orderBy:" + orderBy.toString() +
                                         "]"
                                        ).build();
   }

   @GET @Path("/query2")
   public Response getQuery2(@Context UriInfo info) {
      String       from    = info.getQueryParameters().getFirst("from");
      String       to      = info.getQueryParameters().getFirst("to");
      List<String> orderBy = info.getQueryParameters().get("orderBy");


      if (from == null || from.isEmpty()) from = "NULL";
      if (to == null || to.isEmpty()) to = "NULL";
      if (orderBy == null || (orderBy.isEmpty()))
         orderBy = Arrays.asList("NULL");

      return Response.ok().entity("Metodo: getUsers()::query2 [" +
                                  "from:" + from +
                                  ", to:" + to +
                                  ", orderBy:" + orderBy.toString() + "]"
                                 ).build();
   }

   @GET @Path("{ano}")
   public Response getAno(@PathParam("ano") int ano,
                          @MatrixParam("estado") String estado,
                          @MatrixParam("cidade") String cidade) {
      return Response.ok().entity("Metodo getUsers()::MatrixParam [ano:" +
                                  ano + ", estado:" + estado + ", cidade:" +
                                  cidade + "]").build();
   }

   @GET @Path("{ano}/{mes}")
   public Response getAnoMes(@PathParam("ano") int ano,
                             @MatrixParam("estado") String estado,
                             @MatrixParam("cidade") String cidade) {
      String str = "Metodo getUsers()::MatrixParam [ano:" +ano + ", estado:" +
                   estado + ", cidade:" + cidade + "]";

      return Response.ok().entity(str).build();
   }

   @GET @Path("/headers")
//   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
   public Response getHeaders(@Context HttpHeaders headers) {
      MultivaluedMap<String, String> requestHeaders =
                                                    headers.getRequestHeaders();

      return Response.ok().entity("CABECALHOS DA REQUISICAO: " +
                                  requestHeaders.toString()).build();
   }

   @POST @Path("/form")
   public Response userInput(@FormParam("nome") String nome,
                             @FormParam("idade") int idade) {
      return Response.ok().entity("Método userInput()::@FormParam: [" +
                                  "nome: " + nome + ", idade: " + idade + "]"
                                 ).build();
   }

   @POST
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public Response userInput(MultivaluedMap<String, String> formParams) {
      if (formParams == null || formParams.size() == 0)
         return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados inválidos (1)").build();

      if (formParams.size() != 2)
         return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados inválidos (2)")
                        .build();

      String        temp;
      StringBuilder sb = new StringBuilder("Método userInput()::" +
                                           "MediaType.FORM_URLENCODED\n[\n");
      Set<String>   keys  = formParams.keySet();
      for (String key : keys) {
         if (key.equals("nome")) {
            temp = formParams.get(key).get(0);
            if (temp == null || temp.trim().isEmpty())
               return Response.status(Response.Status.BAD_REQUEST)
                              .entity("Dados inválidos (3): '" + key + "'")
                              .build();

            sb.append("nome: ").append(temp).append('\n');
         }
         else
            if (key.equals("idade")) {
               temp = formParams.get(key).get(0);
               if (temp == null || temp.trim().isEmpty())
                  return Response.status(Response.Status.BAD_REQUEST)
                                 .entity("Dados inválidos (4): '" + key + "'")
                                 .build();
   
               sb.append("idade: ").append(temp).append('\n');
            }
            else
               return Response.status(Response.Status.BAD_REQUEST)
                              .entity("Dados inválidos (2)")
                              .build();
      }
      return Response.accepted().entity(sb.append("]").toString()).build();
   }
/*----------------------------------------------------------------------------------------------------------*/
   
   @POST @Path("/bean")
   public Response userInput(@BeanParam DesafioBean pessoa) {
      String str = "Método userInput()::@BeanParam\n[\n" + pessoa + "\n]";
      System.out.println("@BeanParam [" + pessoa + "]");
      return Response.ok().entity(str).build();
   }

   @GET @Path("/texto")
   @Produces("text/plain")
   public Response getTextFile() {
      File file = new File("c:/arquivo.txt");

      ResponseBuilder response = Response.ok((Object) file);

      response.header("Content-Disposition","attachment; filename=arquivo.txt");

      return response.build();
   }

   @GET @Path("/imagem")
   @Produces("image/png")
   public Response getImageFile() {
      File file = new File("c:/arquivo.png");

      ResponseBuilder response = Response.ok((Object) file);

      response.header("Content-Disposition","attachment; filename=arquivo.png");

      return response.build();
   }

   @GET @Path("/file")
   // para arquivos texto
//   @Produces("text/plain")
   // para imagens PNG
//   @Produces("image/png")
   // para arquivos PDF
   @Produces("application/pdf")
   public Response getPdfFile() {
      String filePath     = "c:/";
      String fileName     = "arquivo";
      String fileExt      = "pdf"; // txt OU png OU pdf
      String fileFullName = fileName + "." + fileExt;
      String filez        = filePath + "/" + fileFullName;
      File   file         = new File(filez);

      ResponseBuilder response = Response.ok(file);

      response.header("Content-Disposition","attachment; filename=" +
                      fileFullName);

      return response.build();
   }

   @POST @Path("/upload")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public Response upload(@DefaultValue("") @FormDataParam("tags") String tags,
                          @FormDataParam("file") InputStream is,
                    @FormDataParam("file") FormDataContentDisposition content) {
      String  fileName = content.getFileName();
      String  str      = "Arquivo [" + fileName + "] carregado com sucesso.";
      boolean erro     = false;

      try { save(is,fileName); }
      catch (IOException ex) {
         str  = ex.getMessage();
         erro = true;
      }

      if (erro)
         return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(str).build();

      return Response.ok().entity(str).build();
   }

   private void save(InputStream is, String fileName) throws IOException {
      // Forma 1) usando a nova API de I/O do JDK
//      java.nio.file.Path path = FileSystems.getDefault()
//                                     .getPath("C:/desenvolvimento/" + fileName);
//      Files.copy(is,path);

      // Forma 2) usando a API de I/O antiga
      OutputStream os    = new FileOutputStream(new File("C:/desenvolvimento/" +
                                                         fileName));
      byte[]       dados = new byte[1024];

      while (is.read(dados) != -1)
         os.write(dados);

      os.flush();
      os.close();
   }
}
