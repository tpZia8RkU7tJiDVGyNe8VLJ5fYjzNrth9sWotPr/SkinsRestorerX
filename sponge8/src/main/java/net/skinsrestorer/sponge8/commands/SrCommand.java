/*
 * SkinsRestorer
 *
 * Copyright (C) 2022 SkinsRestorer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package net.skinsrestorer.sponge8.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.sponge.contexts.OnlinePlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.skinsrestorer.api.SkinVariant;
import net.skinsrestorer.api.interfaces.ISRPlayer;
import net.skinsrestorer.api.property.GenericProperty;
import net.skinsrestorer.api.property.IProperty;
import net.skinsrestorer.shared.commands.ISRCommand;
import net.skinsrestorer.sponge8.SkinsRestorer;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import java.util.List;
import java.util.stream.Collectors;

import static net.skinsrestorer.sponge8.utils.WrapperSponge.wrapCommandSender;
import static net.skinsrestorer.sponge8.utils.WrapperSponge.wrapPlayer;

@RequiredArgsConstructor
@CommandAlias("sr|skinsrestorer")
@CommandPermission("%sr")
public class SrCommand extends BaseCommand implements ISRCommand {
    @Getter
    private final SkinsRestorer plugin;

    @HelpCommand
    @Syntax(" [help]")
    public void onHelp(CommandCause source, CommandHelp help) {
        onHelp(wrapCommandSender(source), help);
    }

    @Subcommand("reload")
    @CommandPermission("%srReload")
    @Description("%helpSrReload")
    public void onReload(CommandCause source) {
        onReload(wrapCommandSender(source));
    }

    @Subcommand("status")
    @CommandPermission("%srStatus")
    @Description("%helpSrStatus")
    public void onStatus(CommandCause source) {
        onStatus(wrapCommandSender(source));
    }

    @Subcommand("drop|remove")
    @CommandPermission("%srDrop")
    @CommandCompletion("PLAYER|SKIN @players @players @players")
    @Description("%helpSrDrop")
    @Syntax(" <player|skin> <target> [target2]")
    public void onDrop(CommandCause source, PlayerOrSkin playerOrSkin, String target) {
        onDrop(wrapCommandSender(source), playerOrSkin, target);
    }

    @Subcommand("props")
    @CommandPermission("%srProps")
    @CommandCompletion("@players")
    @Description("%helpSrProps")
    @Syntax(" <target>")
    public void onProps(CommandCause source, @Single OnlinePlayer target) {
        onProps(wrapCommandSender(source), wrapPlayer(target.getPlayer()));
    }

    @Subcommand("applyskin")
    @CommandPermission("%srApplySkin")
    @CommandCompletion("@players")
    @Description("%helpSrApplySkin")
    @Syntax(" <target>")
    public void onApplySkin(CommandCause source, @Single OnlinePlayer target) {
        onApplySkin(wrapCommandSender(source), wrapPlayer(target.getPlayer()));
    }

    @Subcommand("createcustom")
    @CommandPermission("%srCreateCustom")
    @CommandCompletion("@skinName @skinUrl")
    @Description("%helpSrCreateCustom")
    @Syntax(" <skinName> <skinUrl> [classic/slim]")
    public void onCreateCustom(CommandCause source, String name, String skinUrl, @Optional SkinVariant skinVariant) {
        onCreateCustom(wrapCommandSender(source), name, skinUrl, skinVariant);
    }

    @Subcommand("setskinall")
    @CommandCompletion("@Skin")
    @Description("Set the skin to evey player")
    @Syntax(" <Skin / Url> [classic/slim]")
    public void onSetSkinAll(CommandCause source, String skin, @Optional SkinVariant skinVariant) {
        onSetSkinAll(wrapCommandSender(source), skin, skinVariant);
    }

    @Override
    public String getPlatformVersion() {
        return plugin.getGame().platform().minecraftVersion().name();
    }

    @Override
    public String getProxyMode() {
        return "Sponge-Plugin";
    }

    @Override
    public List<IProperty> getPropertiesOfPlayer(ISRPlayer player) {
        return player.getWrapper().get(ServerPlayer.class).profile().properties().stream()
                .map(property -> new GenericProperty(property.name(), property.value(), property.signature().orElse("")))
                .collect(Collectors.toList());
    }
}