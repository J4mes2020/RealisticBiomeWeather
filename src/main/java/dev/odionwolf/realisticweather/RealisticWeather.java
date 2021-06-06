package dev.odionwolf.realisticweather;

import dev.odionwolf.realisticweather.checks.BiomeCheck;
import dev.odionwolf.realisticweather.checks.WeatherCheck;
import dev.odionwolf.realisticweather.commands.WindCommand;
import dev.odionwolf.realisticweather.disasters.SinkHole;
import dev.odionwolf.realisticweather.thirst.ThirstManager;
import dev.odionwolf.realisticweather.disasters.Tornado;
import dev.odionwolf.realisticweather.wind.WindGenerator;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class RealisticWeather extends JavaPlugin {

    WeatherCheck weatherCheck;
    Tornado tornado;
    SinkHole sinkHole;
    WindGenerator windGenerator;
    public String weather = "sun";
    private ThirstManager drinkCheck;


    private File customConfigBarFile;
    private FileConfiguration customConfigBar;


    public FileConfiguration getConfigBar() {
        return customConfigBar;
    }

    private void createConfigBar() {
        customConfigBarFile = new File(getDataFolder(), "bar.yml");
        if (!customConfigBarFile.exists()) {
            customConfigBarFile.getParentFile().mkdirs();
            saveResource("bar.yml", false);
        }

        customConfigBar = new YamlConfiguration();
        try {
            customConfigBar.load(customConfigBarFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfigBar() throws IOException {
        customConfigBar.save(customConfigBarFile);
    }


    @Override
    public void onEnable() {
        createConfigBar();
        tornado = new Tornado(this);
        sinkHole = new SinkHole(this);

        windGenerator = new WindGenerator(this);
        weatherCheck = new WeatherCheck(this, tornado, windGenerator, sinkHole);
        new BiomeCheck(this, weatherCheck);
        drinkCheck = new ThirstManager(this);
        new WindCommand(this, windGenerator);


        /*
        if (!(getConfigBar().getStringList("Information").isEmpty())) {
            Map<String, Object> thirstBarInfo = getConfigBar().getConfigurationSection("Information").getValues(false);
            for (Map.Entry<String, Object> thirstValues : thirstBarInfo.entrySet()) {
                //if (thirstValues.getValue() instanceof BossBar) {
                drinkCheck.thirstPlayerList.put(UUID.fromString(thirstValues.getKey()), (BossBar) thirstValues.getValue());
            }// FIX SO IT REMEMBERS THEIR THIRST WHEN THE SERVER RESTARTS
            System.out.println(drinkCheck.thirstPlayerList);
        }
    }
             */
    }



    @Override
    public void onDisable() {
        for (Map.Entry<UUID, BossBar> thirstBarInfo : drinkCheck.thirstPlayerList.entrySet()) {
            UUID playerUUID = thirstBarInfo.getKey();
            BossBar bossBarValue = thirstBarInfo.getValue();
            //getConfigBar().set("Information." + playerUUID, bossBarValue);

            try {
                saveConfigBar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
