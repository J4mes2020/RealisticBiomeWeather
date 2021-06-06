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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

public class ThirstManager implements Listener {

    RealisticWeather realisticWeather;

    public ThirstManager(RealisticWeather realisticWeather) {
        Bukkit.getPluginManager().registerEvents(this, realisticWeather);
        this.realisticWeather = realisticWeather;
    }

    public HashMap<UUID, BossBar> thirstPlayerList = new HashMap<>();

    double thirstLevel = 1;
    double thirstUrgeLevel = 5;
    double thirstExhaustion = 0;


    public BossBar getBar(Player player) {
        return thirstPlayerList.get(player.getUniqueId());
    }

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
            bossBar.setProgress(getBar(player).getProgress());
            bossBar.addPlayer(player);
        }
        System.out.println(thirstPlayerList);

    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();


        Location beforeLocation = event.getFrom();
        Location afterLocation = event.getTo();

        assert afterLocation != null;
        boolean meterMoved = (beforeLocation.getBlock().equals(afterLocation.getBlock()));

        if (player.isSwimming() && meterMoved) {
            thirstExhaustion += 0.01;
            meterMoved = false;
        }

        if (player.isSprinting() && meterMoved) {
            thirstExhaustion += 0.1;
        }
        thirstManager(player);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            thirstExhaustion += 0.1;
            thirstManager(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        thirstExhaustion += 0.005;
        thirstManager(player);
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
            if (getBar(player).getProgress() + thirstLevel > 1) {
                getBar(player).setProgress(1);
            } else {
                thirstUrgeLevel = 4.8;
                getBar(player).setProgress(getBar(player).getProgress() + thirstLevel);
            }
        }
    }

    public void thirstManager(Player player) {

        if (thirstLevel <= 0) {
            getBar(player).setProgress(0);
        }

        if (thirstUrgeLevel <= 0 && thirstExhaustion >= 4) {
            thirstLevel -= 0.1;
            thirstUrgeLevel = 0;
            thirstExhaustion = 0;
            getBar(player).setProgress(thirstLevel);
        }

        if (thirstExhaustion >= 4) {
            thirstUrgeLevel -= 1;
            thirstExhaustion = 0;

        }
    }

}
