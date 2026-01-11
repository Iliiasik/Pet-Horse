package pethorses;

import pethorses.commands.HorseCommandExecutor;
import pethorses.config.ConfigManager;
import pethorses.config.LocalizationManager;
import pethorses.listeners.*;
import pethorses.menus.HorseCustomizationMenu;
import pethorses.services.HorseBackpackService;
import pethorses.services.HorseService;
import pethorses.services.PassengerService;
import pethorses.storage.HorseData;
import pethorses.storage.HorseDataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PetHorses extends JavaPlugin {
    private ConfigManager configManager;
    private LocalizationManager localizationManager;
    private HorseDataManager horseDataManager;
    private HorseService horseService;
    private PassengerService passengerService;
    private ChatInputListener chatInputListener;
    private HorseBackpackService horseBackpackService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        initServices();
        getCommand("horse").setExecutor(new HorseCommandExecutor(this));
        registerListeners();
        startAutoSaveTask();
        getLogger().info(localizationManager.getMessage("plugin.enabled"));
    }

    @Override
    public void onDisable() {
        for (UUID playerId : horseDataManager.getAllPlayerIds()) {
            HorseData data = horseDataManager.getHorseData(playerId);
            if (data != null && data.getHorseId() != null) {
                org.bukkit.entity.Entity entity = org.bukkit.Bukkit.getEntity(data.getHorseId());
                if (entity instanceof org.bukkit.entity.Horse horse) {
                    for (org.bukkit.entity.Entity passenger : horse.getPassengers()) {
                        horse.removePassenger(passenger);
                    }
                }
            }
            horseService.hideHorse(data);
        }
        horseDataManager.saveAllData();
        horseDataManager.close();
        getLogger().info(localizationManager.getMessage("plugin.disabled"));
    }

    private void initServices() {
        this.configManager = new ConfigManager(this);
        this.localizationManager = new LocalizationManager(this);
        this.horseDataManager = new HorseDataManager(this);
        this.horseService = new HorseService(this);
        this.passengerService = new PassengerService(this);
        this.chatInputListener = new ChatInputListener(this);
        this.horseBackpackService = new HorseBackpackService(this);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new HorseEventListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseMovementListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseStatsMenuListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseCustomizationMenu(this), this);
        getServer().getPluginManager().registerEvents(chatInputListener, this);
        getServer().getPluginManager().registerEvents(new HorseBackpackListener(this), this);
        getServer().getPluginManager().registerEvents(new BackpackInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseArmorListener(this), this);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LocalizationManager getLocalizationManager() {
        return localizationManager;
    }

    public HorseDataManager getHorseDataManager() {
        return horseDataManager;
    }

    public HorseService getHorseService() {
        return horseService;
    }

    public PassengerService getPassengerService() {
        return passengerService;
    }

    public ChatInputListener getChatInputListener() {
        return chatInputListener;
    }

    private void startAutoSaveTask() {
        long interval = 20L * 60L * configManager.getAutosaveIntervalMinutes();
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            horseDataManager.saveAllData();
            if (configManager.getAutosaveLog()) {
                getLogger().info(localizationManager.getMessage("plugin.autosave"));
            }
        }, interval, interval);
    }
}