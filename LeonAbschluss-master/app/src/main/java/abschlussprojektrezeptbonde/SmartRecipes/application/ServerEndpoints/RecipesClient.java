package abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints;

import net.petafuel.mobile.android.rest.ResourceFactory;

public class RecipesClient {
    static ResourceFactory resourceFactory = new ResourceFactory("https", "lebensmittelretter.iotrio.cloud", 443);
    static BackendAPI resource = resourceFactory.getResource(BackendAPI.class);
    public static RecipesResponse getRecipes(String[] primaryIngredients, String[] optionalIngredients) throws Exception {
        return resource.getRecipes("Bearer my-secret-token", 35000, primaryIngredients, optionalIngredients);
    }
}