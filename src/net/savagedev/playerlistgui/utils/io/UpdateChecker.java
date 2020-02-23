package net.savagedev.playerlistgui.utils.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateChecker {
    private static final String SPIGOT_API_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private final String currentVersion;
    private final int resourceId;

    public UpdateChecker(final int resourceId, final String currentVersion) {
        this.currentVersion = currentVersion;
        this.resourceId = resourceId;
    }

    public CompletableFuture<Result> check() {
        CompletableFuture<Result> future = new CompletableFuture<>();
        EXECUTOR.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(String.format(SPIGOT_API_URL, this.resourceId)).openConnection().getInputStream()))) {
                String version = reader.readLine();
                future.complete(new Result(!this.currentVersion.equalsIgnoreCase(version), version));
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
            future.complete(new Result(false, this.currentVersion));
        });
        return future;
    }

    public class Result {
        private final boolean updateAvailable;
        private final String version;

        private Result(boolean updateAvailable, String version) {
            this.updateAvailable = updateAvailable;
            this.version = version;
        }

        public boolean isUpdateAvailable() {
            return this.updateAvailable;
        }

        public String getVersion() {
            return this.version;
        }
    }
}
