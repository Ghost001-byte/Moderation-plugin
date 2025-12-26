package me.emmanuele.moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class ModerationPlugin extends JavaPlugin implements Listener, CommandExecutor {

    private Set<UUID> mutedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        
        saveDefaultConfig();
        loadMutes();
        
        
        getCommand("ban").setExecutor(this);
        getCommand("unban").setExecutor(this);
        getCommand("mute").setExecutor(this);
        getCommand("unmute").setExecutor(this);
        getCommand("blacklist").setExecutor(this);
        getCommand("unblacklist").setExecutor(this);
        
        
        getServer().getPluginManager().registerEvents(this, this);
        
        getLogger().info("ModerationPlugin abilitato correttamente!");
    }

    @Override
    public void onDisable() {
        saveMutes();
        getLogger().info("ModerationPlugin disabilitato.");
    }

    private void loadMutes() {
        if (getConfig().contains("muted-players")) {
            mutedPlayers = getConfig().getStringList("muted-players").stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        }
    }

    private void saveMutes() {
        getConfig().set("muted-players", mutedPlayers.stream().map(UUID::toString).collect(Collectors.toList()));
        saveConfig();
    }

    public String getMessage(String path) {
        String msg = getConfig().getString("messages." + path);
        return msg != null ? ChatColor.translateAlternateColorCodes('&', msg) : "Messaggio non trovato: " + path;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "ban":
                return handleBan(sender, args);
            case "unban":
                return handleUnban(sender, args);
            case "mute":
                return handleMute(sender, args);
            case "unmute":
                return handleUnmute(sender, args);
            case "blacklist":
                return handleBlacklist(sender, args);
            case "unblacklist":
                return handleUnblacklist(sender, args);
            default:
                return false;
        }
    }

    private boolean handleBan(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("ban-usage"));
            return true;
        }
        String targetName = args[0];
        String reason = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : "Nessun motivo";
        
        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, reason, null, sender.getName());
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            target.kickPlayer(getMessage("ban-kick-message").replace("%reason%", reason));
        }
        sender.sendMessage(getMessage("ban-success").replace("%player%", targetName).replace("%reason%", reason));
        return true;
    }

    private boolean handleUnban(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("unban-usage"));
            return true;
        }
        String targetName = args[0];
        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(targetName);
        sender.sendMessage(getMessage("unban-success").replace("%player%", targetName));
        return true;
    }

    private boolean handleMute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("mute-usage"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(getMessage("player-not-found"));
            return true;
        }
        mutedPlayers.add(target.getUniqueId());
        saveMutes();
        sender.sendMessage(getMessage("mute-success").replace("%player%", target.getName()));
        return true;
    }

    private boolean handleUnmute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("unmute-usage"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(getMessage("player-not-found"));
            return true;
        }
        if (mutedPlayers.remove(target.getUniqueId())) {
            saveMutes();
            sender.sendMessage(getMessage("unmute-success").replace("%player%", target.getName()));
        }
        return true;
    }

    private boolean handleBlacklist(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("blacklist-usage"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        String reason = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : "Nessun motivo";

        if (target != null) {
            String ip = target.getAddress().getAddress().getHostAddress();
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(ip, reason, null, sender.getName());
            target.kickPlayer(getMessage("blacklist-kick-message").replace("%reason%", reason));
            sender.sendMessage(getMessage("blacklist-success").replace("%player%", target.getName()).replace("%reason%", reason));
        } else {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
            sender.sendMessage(getMessage("blacklist-success").replace("%player%", args[0]).replace("%reason%", reason) + " (Solo NAME ban)");
        }
        return true;
    }

    private boolean handleUnblacklist(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(getMessage("unblacklist-usage"));
            return true;
        }
        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(args[0]);
        sender.sendMessage(getMessage("unblacklist-success").replace("%player%", args[0]));
        return true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (mutedPlayers.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(getMessage("muted-message"));
        }
    }
}
