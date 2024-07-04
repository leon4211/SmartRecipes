package abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints;

import net.petafuel.mobile.android.rest.ResourceFactory;

public class IngredientsClient {
    static ResourceFactory resourceFactory = new ResourceFactory("https", "lebensmittelretter.iotrio.cloud", 443);
    static BackendAPI resource = resourceFactory.getResource(BackendAPI.class);
    public static IngredientResponse getIngredients(String base64picture) throws Exception {
        return resource.getIngredients("Bearer my-secret-token", 19000,  "data:image/jpeg;base64," + base64picture);
    }
}