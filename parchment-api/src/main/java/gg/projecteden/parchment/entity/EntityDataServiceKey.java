package gg.projecteden.parchment.entity;

public final class EntityDataServiceKey<S> {
    private final Class<S> serviceType;
    private S service;

    public EntityDataServiceKey(Class<S> serviceType) {
        this.serviceType = serviceType;
    }

    public S get() {
        if (this.service == null) {
            throw new IllegalStateException("Service is not initialized.");
        }

        return this.service;
    }

    public void set(S service) {
        if (this.service != null) {
            throw new IllegalStateException("Service is already initialized.");
        }

        if (!this.serviceType.isInstance(service)) {
            throw new IllegalArgumentException("Value does not implement service contract.");
        }

        this.service = service;
    }
}