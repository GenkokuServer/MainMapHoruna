package city.genkoku.mainmaphoruna;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MMHListener implements Listener {

  @EventHandler
  public void onBlockBroken(BlockBreakEvent event) {

    if (event.getPlayer().hasPermission("mainmaphoruna.exceptrestriction")
    || event.getPlayer().isOp()) return;

    String world = event.getBlock().getWorld().getName();

    if (!MainMapHoruna.getThresholds().containsKey(world)) return;

    int y = MainMapHoruna.getThresholds().get(world);

    if (event.getBlock().getLocation().getBlockY() <= y) {
      event.setCancelled(true);

      TextComponent msg1 = new TextComponent("You may not break the block located y:");
      msg1.setColor(ChatColor.RED);

      TextComponent msg2 = new TextComponent(String.valueOf(y));
      msg2.setColor(ChatColor.RED);
      msg2.setItalic(true);

      TextComponent msg3 = new TextComponent(" or less.");
      msg3.setColor(ChatColor.RED);

      msg1.addExtra(msg2);
      msg1.addExtra(msg3);

      event.getPlayer().sendMessage(msg1);
    }

  }

}
