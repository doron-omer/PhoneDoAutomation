package Extensions;

import java.util.HashMap;
import java.util.Map;

public class ScenarioDataStore {
    private static Map<ScenarioContext.Key<?>, Object> dataStore = new HashMap<>();

    public static <T> void put(ScenarioContext.Key<T> key, T value) {
        dataStore.put(key, value);
    }

    public static <T> T get(ScenarioContext.Key<T> key) {
        return key.typeClass.cast(dataStore.get(key));
    }
}

