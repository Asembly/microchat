package asembly.util;

import java.util.UUID;

public class GeneratorId {
    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }
}
