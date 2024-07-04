package abschlussprojektrezeptbonde.SmartRecipes.application.ServerEndpoints;

import java.io.Serializable;
import java.util.Map;

public class RecipesResponse implements Serializable {

   private Map<String, String> recipes;

    public Map<String, String> getRecipes() {
        return recipes;
    }
}
