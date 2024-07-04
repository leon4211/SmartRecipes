package abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints;


import net.petafuel.mobile.android.rest.RestResource;
import net.petafuel.mobile.android.rest.util.CallTimeout;
import net.petafuel.mobile.android.rest.util.ContentType;
import net.petafuel.mobile.android.rest.util.Header;
import net.petafuel.mobile.android.rest.util.POST;
import net.petafuel.mobile.android.rest.util.Path;
import net.petafuel.mobile.android.rest.util.PostParam;
import net.petafuel.mobile.android.rest.util.Produces;
import net.petafuel.mobile.android.rest.util.ReadTimeout;
import net.petafuel.mobile.android.rest.util.WriteTimeout;

@Path("")
public interface BackendAPI extends RestResource {

    @POST
    @Produces(ContentType.JSON)
    @Path("/ingredients")
    IngredientResponse getIngredients(@Header("Authorization") String myToken, @CallTimeout int callTimeOut,
                                      @PostParam("picture") String base64Picture) throws Exception;

    @POST
    @Produces(ContentType.JSON)
    @Path("/recipes")
    RecipesResponse getRecipes(@Header("Authorization") String myToken, @ReadTimeout @WriteTimeout int readAndWriteTimeOut,
                               @PostParam("primaryIngredients") String[] primaryIngredients,
                               @PostParam("optionalIngredients") String[] optionalIngredients) throws Exception;
}