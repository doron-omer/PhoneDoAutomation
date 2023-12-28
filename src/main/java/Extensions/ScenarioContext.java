package Extensions;

import java.util.List;

public class ScenarioContext {
    public static class Key<T> {
        final Class<T> typeClass;

        protected Key(Class<T> typeClass) {
            this.typeClass = typeClass;
        }
    }

    public static final Key<Integer> initialBalance = new Key<>(Integer.class);
    public static final Key<List<Double>> pricesBeforeSorting = new Key<>((Class<List<Double>>) (Class<?>) List.class);
    public static final Key<List<Double>> pricesAfterSorting = new Key<>((Class<List<Double>>) (Class<?>) List.class);

    public static <T> void setInDataStore(Key<T> key, T value) {
        ScenarioDataStore.put(key, value);
    }

    public static <T> T getFromDataStore(Key<T> key) {
        T value = key.typeClass.cast(ScenarioDataStore.get(key));
        if (null == value) {
            throw new IllegalArgumentException(String.format("No %s was saved for the scenario. Save %s using relevant step.", key.typeClass, key));
        }

        return value;
    }

    private ScenarioContext() {
    }
}
