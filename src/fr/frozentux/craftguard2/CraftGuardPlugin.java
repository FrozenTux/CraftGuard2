package fr.frozentux.craftguard2;

import fr.frozentux.craftguard2.commands.CgCommandExecutor;
import fr.frozentux.craftguard2.config.CraftGuardConfig;
import fr.frozentux.craftguard2.list.ListLoader;
import fr.frozentux.craftguard2.list.ListManager;
import fr.frozentux.craftguard2.logger.CraftGuardLogger;
import fr.frozentux.craftguard2.module.CraftGuardModule;
import fr.frozentux.craftguard2.module.breaking.BreakListener;
import fr.frozentux.craftguard2.module.craft.CraftListener;
import fr.frozentux.craftguard2.module.place.PlaceListener;
import fr.frozentux.craftguard2.module.repair.RepairListener;
import fr.frozentux.craftguard2.module.smelt.SmeltListener;
import fr.frozentux.craftguard2.module.use.UseListener;
import fr.frozentux.craftguard2.smeltingmanager.SmeltFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * CraftGuard 2 Plugin
 *
 * @author FrozenTux
 */
public class CraftGuardPlugin extends JavaPlugin {

    private Metrics metrics;

    private CraftGuardLogger craftGuardLogger;

    private ModuleRegistry registry;

    private CraftGuardConfig config;

    private ListLoader listLoader;
    private File listFile;
    private ListManager listManager;

    private SmeltFile smeltFile;

    private HashSet<CraftGuardModule> enabled;

    public void onLoad() {
        registry = new ModuleRegistry(this);
    }

    public void onEnable() {

        //Logger init
        craftGuardLogger = new CraftGuardLogger(this);

        initConfig();
        initModules();
        initMetrics();
        initLists();

        //Smelting init
        smeltFile = new SmeltFile(new YamlConfiguration(), new File(this.getDataFolder().getAbsolutePath() + File.separator + "smelting.yml"), this);
        smeltFile.load();

        //Commands init
        this.getCommand("cg").setExecutor(new CgCommandExecutor(this));

        craftGuardLogger.info("CraftGuard version " + this.getDescription().getVersion() + " has been enabled");
    }

    public void onDisable() {
        craftGuardLogger.info("CraftGuard version " + this.getDescription().getVersion() + " has been disabled");
    }

    /*
     * Private init methods
     */
    private void initConfig() {
        config = new CraftGuardConfig(this);
        config.load();
        if (config.getBooleanKey("debug") == true) craftGuardLogger.enableDebug();
    }

    public void initModules() {
        //STEP 1 : Adding all the modules to the registry
        registry.add(new CraftGuardModule("craft", new CraftListener(this), this));
        registry.add(new CraftGuardModule("smelt", new SmeltListener(this), this));
        registry.add(new CraftGuardModule("break", new BreakListener(this), this));
        registry.add(new CraftGuardModule("place", new PlaceListener(this), this));
        registry.add(new CraftGuardModule("use", new UseListener(this), this));
        registry.add(new CraftGuardModule("repair", new RepairListener(this), this));

        //STEP 2 : Selecting the modules to enable, and enable them
        @SuppressWarnings("unchecked")
        ArrayList<String> toEnable = (ArrayList<String>) config.getKey("modules");
        ArrayList<String> browseCopy = new ArrayList<String>();
        browseCopy.addAll(toEnable);
        enabled = new HashSet<CraftGuardModule>();

        for (String element : browseCopy) {
            if (!registry.containsModule(element)) {
                toEnable.remove(element);
                craftGuardLogger.warning("Module " + element + " does not exist ! Ignoring it...");
            } else {
                CraftGuardModule module = registry.getModule(element);
                this.getServer().getPluginManager().registerEvents(module.getListener(), this);
                enabled.add(module);
            }
        }
    }

    private void initLists() {
        listFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "lists.yml");
        listLoader = new ListLoader(this, new YamlConfiguration(), listFile);
        listManager = new ListManager(this, listLoader);
        listManager.init();
    }

    private void initMetrics() {
        try {
            metrics = new Metrics(this);

            Graph modules = metrics.createGraph("Modules enabled");

            for (CraftGuardModule module : enabled) {
                modules.addPlotter(new Metrics.Plotter(module.getType()) {

                    public int getValue() {
                        return 1;
                    }
                });
            }

            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CraftGuardLogger getCraftGuardLogger() {
        return craftGuardLogger;
    }

    public CraftGuardConfig getConfiguration() {
        return config;
    }

    public ListManager getListManager() {
        return listManager;
    }

    public SmeltFile getSmeltFile() {
        return smeltFile;
    }

    public ModuleRegistry getModuleRegistry() {
        return registry;
    }

    public HashSet<CraftGuardModule> getEnabledModules() {
        return enabled;
    }

}
