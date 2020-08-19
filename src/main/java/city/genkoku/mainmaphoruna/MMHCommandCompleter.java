package city.genkoku.mainmaphoruna;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MMHCommandCompleter implements TabCompleter {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (!sender.hasPermission("mainmaphoruna.moderation"))
      return Collections.emptyList();

    if (args.length <= 1)
      return ImmutableList.of("reload", "set", "unset", "view");

    if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("view"))
      return Collections.emptyList();

    if (args[0].equalsIgnoreCase("unset"))
      return new ArrayList<>(MainMapHoruna.getThresholds().keySet());

    if (args[0].equalsIgnoreCase("set") && args.length == 3)
      return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());

    return Collections.emptyList();
  }

}
