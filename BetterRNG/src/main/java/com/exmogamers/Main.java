package com.exmogamers;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    public static Main mainInstance;
    public static Main getInstance() {
        return mainInstance;
    }
    @Override
    public void onLoad() {
        //mainInstance = this;
    }

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new Listener(), this);

    }
}
