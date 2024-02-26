package me.lrxh.practice.commands.user.duels;

import me.lrxh.practice.profile.Profile;
import me.lrxh.practice.profile.meta.ProfileRematchData;
import me.lrxh.practice.util.CC;
import me.lrxh.practice.util.command.command.CommandMeta;
import org.bukkit.entity.Player;

@CommandMeta(label = "rematch")
public class RematchCommand {

    public void execute(Player player) {
        if (player.hasMetadata("frozen")) {
            player.sendMessage(CC.RED + "You cannot duel while frozen.");
            return;
        }

        Profile profile = Profile.getByUuid(player.getUniqueId());
        ProfileRematchData rematchData = profile.getRematchData();

        if (rematchData == null) {
            player.sendMessage(CC.RED + "You do not have anyone to rematch.");
            return;
        }

        rematchData.validate();

        if (rematchData.isCancelled()) {
            player.sendMessage(CC.RED + "You can no longer send that player a rematch.");
            return;
        }

        if (rematchData.isReceive()) {
            rematchData.accept();
        } else {
            if (rematchData.isSent()) {
                player.sendMessage(CC.RED + "You have already sent a rematch to that player.");
                return;
            }

            rematchData.request();
        }
    }

}