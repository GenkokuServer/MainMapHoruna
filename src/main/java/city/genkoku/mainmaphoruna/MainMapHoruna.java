package city.genkoku.mainmaphoruna;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class MainMapHoruna extends JavaPlugin {

  private static Map<String, Integer> threshold;

  private static MainMapHoruna instance;

  @Override
  public void onEnable() {
    instance = this;
    saveDefaultConfig();
    threshold = (Map) getConfig().getConfigurationSection("threshold").getValues(false);
    getServer().getPluginManager().registerEvents(new MMHListener(), this);
    getServer().getPluginCommand("mmh").setExecutor(new MMHCommand());
    getServer().getPluginCommand("mmh").setTabCompleter(new MMHCommandCompleter());
  }

  static void setThreshold(String world, int y) {
    threshold.put(world, y);
    instance.getConfig().createSection("threshold", threshold);
    instance.saveConfig();
  }

  static void unsetThreshold(String world) {
    threshold.remove(world);
    instance.getConfig().createSection("threshold", threshold);
    instance.saveConfig();
  }

  static Map<String, Integer> getThresholds() {
    return threshold;
  }

  static void reloadMMHConfig() {
    threshold = (Map) instance.getConfig().getConfigurationSection("threshold").getValues(false);
  }

}
