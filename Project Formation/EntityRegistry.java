import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// --- Parametric Polymorphism: Generic Registry ---
class EntityRegistry<T extends Entity> {
    private final List<T> entities = new ArrayList<>();

    public void register(T entity) { 
        entities.add(entity); 
    }
    
    public List<T> getEntities() { 
        return Collections.unmodifiableList(entities); 
    }
}