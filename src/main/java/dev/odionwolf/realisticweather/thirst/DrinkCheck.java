package dev.odionwolf.realisticweather.thirst;

import dev.odionwolf.realisticweather.RealisticWeather;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.UUID;

public class DrinkCheck implements Listener {

    RealisticWeather realisticWeather;

    public DrinkCheck(RealisticWeather realisticWeather) {
        Bukkit.getPluginManager().registerEvents(this, realisticWeather);
        this.realisticWeather = realisticWeather;
    }

    public HashMap<UUID, BossBar> thirstPlayerList = new HashMap<>();

    double thirstLevel = 1;
    double thirstUrgeLevel = 5;
    double thirstExhaustion = 0;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BossBar bossBar = Bukkit.createBossBar("Thirst", BarColor.BLUE, BarStyle.SOLID);
        if (!thirstPlayerList.containsKey(player.getUniqueId())) {
            thirstUrgeLevel = 5;
            thirstLevel = 1;
            bossBar.setProgress(thirstLevel);
            bossBar.addPlayer(player);
            thirstPlayerList.put(player.getUniqueId(), bossBar);
            }

        else {
            bossBar.setProgress(thirstPlayerList.get(player.getUniqueId()).getProgress());
            bossBar.addPlayer(player);
        }

    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        thirstManager(player);


        Location beforeLocation = event.getFrom();
        Location afterLocation = event.getTo();

        assert afterLocation != null;
        boolean meterMoved = (beforeLocation.getBlock().equals(afterLocation.getBlock()));

        if (player.isSwimming() && meterMoved) {
            thirstExhaustion += 1;//0.01;
            meterMoved = false;
        }

        if (player.isSprinting() && meterMoved) {
            thirstExhaustion += 1;//0.1;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        thirstManager(player);
        thirstExhaustion += 0.005;
    }


    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(meta);
        if (event.getItem().equals(bottle)) {
            thirstLevel += 0.8;
            if (thirstPlayerList.get(player.getUniqueId()).getProgress() + thirstLevel > 1) {
                thirstPlayerList.get(player.getUniqueId()).setProgress(1);
            }
            thirstUrgeLevel = 4.8;
            thirstPlayerList.get(player.getUniqueId()).setProgress(thirstLevel);
        }
    }

    public void thirstManager(Player player) {


        if (thirstUrgeLevel <= 0 && thirstExhaustion == 4) {
            thirstLevel -= 0.1;
            thirstUrgeLevel = 0;
            thirstPlayerList.get(player.getUniqueId()).setProgress(thirstLevel);
        }

        if (thirstExhaustion >= 4) {
            thirstUrgeLevel -= 1;
            thirstExhaustion = 0;

        }
    }

}
