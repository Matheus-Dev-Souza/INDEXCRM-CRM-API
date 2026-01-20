package com.indexcrm.context;

/**
 * Contexto de tenant para uso em aplicações multi-tenant.
 * Guarda o identificador do tenant em ThreadLocal (herdável por threads filhas).
 */
public final class TenantContext {
    private static final ThreadLocal<String> TENANT = new InheritableThreadLocal<>();

    private TenantContext() {}

    public static void setTenantId(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("tenantId não pode ser nulo ou vazio");
        }
        TENANT.set(tenantId);
    }

    public static String getTenantId() {
        return TENANT.get();
    }

    public static String getTenantIdOrDefault(String defaultTenant) {
        String t = TENANT.get();
        return (t == null || t.isBlank()) ? defaultTenant : t;
    }

    public static boolean hasTenant() {
        String t = TENANT.get();
        return t != null && !t.isBlank();
    }

    public static void clear() {
        TENANT.remove();
    }

    /**
     * Executa uma Runnable temporariamente com o tenant fornecido, restaurando o anterior ao final.
     */
    public static void runWithTenant(String tenantId, Runnable action) {
        String previous = TENANT.get();
        try {
            setTenantId(tenantId);
            action.run();
        } finally {
            if (previous == null) {
                TENANT.remove();
            } else {
                TENANT.set(previous);
            }
        }
    }
}