package city.genkoku.mainmaphoruna;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MMHCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("mainmaphoruna.operation")) {
      TextComponent nopermMsg = new TextComponent("You are not permitted to execute this command");
      nopermMsg.setColor(ChatColor.DARK_RED);
      sender.sendMessage(nopermMsg);
      return true;
    }
    if (args.length == 0) {
      TextComponent msgUsage = new TextComponent("Usage: /mmh <reload|set|unset|view>");
      msgUsage.setColor(ChatColor.GRAY);
      sender.sendMessage(msgUsage);
      return true;
    }
    switch (args[0].toLowerCase()) {
      case "reload":
        MainMapHoruna.reloadMMHConfig();
        TextComponent reloaded = new TextComponent("Reloaded MMH configuration.");
        reloaded.setColor(ChatColor.GRAY);
        sender.sendMessage(reloaded);
        break;
      case "set":
        if (args.length <= 1) {
          // too few arg
          TextComponent msgFewArg = new TextComponent("Usage: /mmh set <value> (world)");
          msgFewArg.setColor(ChatColor.GRAY);
          sender.sendMessage(msgFewArg);
          return true;
        }
        if (NumberUtils.isDigits(args[1])) {

          String worldName;

          if (args.length <= 2) {
            // <=2: world not specified
            if (!(sender instanceof Player)) {
              // CANNOT EXECUTE WITHOUT SPECIFYING A WORLD IF CONSOLE OR CB
              TextComponent msgNotSpecifiedWorld = new TextComponent("Cannot execute this command without specifying a world if you are a console or command block.");
              msgNotSpecifiedWorld.setColor(ChatColor.DARK_RED);
              sender.sendMessage(msgNotSpecifiedWorld);
              return true;
            } else {
              // WORLD NOT SPECIFIED, BUT EXECUTED BY PLAYER
              worldName = ((Player) sender).getWorld().getName();
            }
          } else {
            // world is specified
            if (Bukkit.getServer().getWorlds().stream().map(World::getName).noneMatch(w -> w.equals(args[2]))) {
              // specified world is not found
              TextComponent msgWorldNotFound = new TextComponent("The world you specified is not found.");
              msgWorldNotFound.setColor(ChatColor.DARK_RED);
              sender.sendMessage(msgWorldNotFound);
              return true;
            }
            worldName = args[2];
          }

          MainMapHoruna.setThreshold(worldName, Integer.parseInt(args[1]));

          TextComponent msgSetHead = new TextComponent("Threshold of world \"");
          msgSetHead.setColor(ChatColor.GRAY);

          TextComponent msgSetWorld = new TextComponent(worldName);
          msgSetWorld.setColor(ChatColor.GRAY);
          msgSetWorld.setItalic(true);

          TextComponent msgSetFoot = new TextComponent("\" has been set to ");
          msgSetFoot.setColor(ChatColor.GRAY);

          TextComponent msgSetValue = new TextComponent(args[1]);
          msgSetValue.setColor(ChatColor.GRAY);
          msgSetValue.setItalic(true);

          msgSetHead.addExtra(msgSetWorld);
          msgSetHead.addExtra(msgSetFoot);
          msgSetHead.addExtra(msgSetValue);
          sender.sendMessage(msgSetHead);
        } else {
          TextComponent msgInvalidValue = new TextComponent("The value you entered is invalid.");
          msgInvalidValue.setColor(ChatColor.DARK_RED);
          sender.sendMessage(msgInvalidValue);
        }
        return true;
      case "unset":
        String worldName;

        if (args.length <= 1) {
          // <=1: world not specified
          if (!(sender instanceof Player)) {
            // CANNOT EXECUTE WITHOUT SPECIFYING A WORLD IF CONSOLE OR CB
            TextComponent msgNotSpecifiedWorld = new TextComponent("Cannot execute this command without specifying a world if you are a console or command block.");
            msgNotSpecifiedWorld.setColor(ChatColor.DARK_RED);
            sender.sendMessage(msgNotSpecifiedWorld);
            return true;
          } else {
            // WORLD NOT SPECIFIED, BUT EXECUTED BY PLAYER
            worldName = ((Player) sender).getWorld().getName();
          }
        } else {
          // world is specified
          if (Bukkit.getServer().getWorlds().stream().map(World::getName).noneMatch(w -> w.equals(args[1]))) {
            // specified world is not found
            TextComponent msgWorldNotFound = new TextComponent("The world you specified is not found.");
            msgWorldNotFound.setColor(ChatColor.DARK_RED);
            sender.sendMessage(msgWorldNotFound);
            return true;
          }
          worldName = args[1];
        }
        if (MainMapHoruna.getThresholds().keySet().stream().noneMatch(w -> w.equals(args[1]))) {
          // specified world has no restriction
          TextComponent msgNoRestrictionFound = new TextComponent("The world has no restriction.");
          msgNoRestrictionFound.setColor(ChatColor.GRAY);
          sender.sendMessage(msgNoRestrictionFound);
          return true;
        }
        MainMapHoruna.unsetThreshold(worldName);

        TextComponent msgSetHead = new TextComponent("Threshold of world \"");
        msgSetHead.setColor(ChatColor.GRAY);

        TextComponent msgSetWorld = new TextComponent(worldName);
        msgSetWorld.setColor(ChatColor.GRAY);
        msgSetWorld.setItalic(true);

        TextComponent msgSetFoot = new TextComponent("\" has been unset.");
        msgSetFoot.setColor(ChatColor.GRAY);

        msgSetHead.addExtra(msgSetWorld);
        msgSetHead.addExtra(msgSetFoot);
        sender.sendMessage(msgSetHead);
        return true;
      case "view":
        if (MainMapHoruna.getThresholds().isEmpty()) {
          TextComponent msg1 = new TextComponent("No restriction set.");
          msg1.setColor(ChatColor.GRAY);
          sender.sendMessage(msg1);
          return true;
        }
        sender.sendMessage(new TextComponent("================"));
        MainMapHoruna.getThresholds().forEach((key, value) -> {
          TextComponent msg1 = new TextComponent(key);
          msg1.setColor(ChatColor.GRAY);
          TextComponent msg2 = new TextComponent(": ");
          msg2.setColor(ChatColor.DARK_GRAY);
          TextComponent msg3 = new TextComponent(String.valueOf(value));
          msg3.setColor(ChatColor.GRAY);
          msg1.addExtra(msg2);
          msg1.addExtra(msg3);
          sender.sendMessage(msg1);
        });
        sender.sendMessage(new TextComponent("================"));
        return true;
      default:
        TextComponent msgUsage = new TextComponent("Usage: /mmh <reload|set|unset|view>");
        msgUsage.setColor(ChatColor.GRAY);
        sender.sendMessage(msgUsage);
        return true;
    }
    return true;
  }



}
