package fr.nemesis07.stoners.bukkit.languages;

import fr.nemesis07.stoners.bukkit.Core;
import javafx.util.Pair;
import fr.nemesis07.stoners.bukkit.languages.Lang;

import java.util.Arrays;
import java.util.List;

public class US extends Lang {

    @Override
    public String getLanguage() {
        return "us";
    }

    @Override
    public Pair<String, String> prefix() {
        return new Pair<>("prefix", "&6[&eStoners&6] ");
    }

    @Override
    public Pair<String, String> mustBePlayer() {
        return new Pair<>("mustBePlayer", "&cYou must be a player to perform this command !");
    }

    @Override
    public Pair<String, String> noPerm() {
        return new Pair<>("no-permission", "&cYou don't have permission for that!");
    }

    @Override
    public Pair<String, List<String>> help() {
        return new Pair<>("help", Arrays.asList(
                "\n&e------------------------------",
                "&7/%command% &e%arg% &7- %desc%",
                "&e------------------------------\n\n"));
    }

    //PERMISSION
    //error
    @Override
    public Pair<String, String> PermissionErrorAlreadyHasPermission() {
        return new Pair<>("permission.error.alreadyHasPermission", "&cLe rang %rank% possède déjà la permission, est-elle activé: %active%");
    }

    //add
    @Override
    public Pair<String, String> PermissionAddDescription() {
        return new Pair<>("permission.add.description", "&cPermission descirption");
    }

    @Override
    public Pair<String, String> PermissionAddSyntax() {
        return new Pair<>("permission.add.syntax", "&c/permission add &e<Rank> <Permission> &a[boolean]&7 | boolean vrai par default");
    }

    @Override
    public Pair<String, String> PermissionAddSuccess() {
        return new Pair<>("permission.add.success", "&aAjout de la permission %perm% &aà %rank% &aavec succès !");
    }

    //remove
    @Override
    public Pair<String, String> PermissionRemoveDescription() {
        return new Pair<>("permission.remove.description", "&cPermission descirption");
    }

    @Override
    public Pair<String, String> PermissionRemoveSyntax() {
        return new Pair<>("permission.remove.syntax", "&c/permission remove &e<Rank> <Permission>");
    }

    @Override
    public Pair<String, String> PermissionRemoveSuccess() {
        return new Pair<>("permission.remove.success", "&cSuppression de la permission %permission% !");
    }

    //edit
    @Override
    public Pair<String, String> PermissionEditDescription() {
        return new Pair<>("permission.edit.description", "&cPermission descirption");
    }

    @Override
    public Pair<String, String> PermissionEditSyntax() {
        return new Pair<>("premission.edit.syntax", "&c/permission edit &e<Rank> <Permission>");
    }

    @Override
    public Pair<String, String> PermissionEditSuccess() {
        return new Pair<>("premission.edit.success", "&aModification de la permission %old% en permission %new% &a!");
    }

    //list
    @Override
    public Pair<String, String> PermissionListDescription() {
        return new Pair<>("permission.list.description", "&cPermission descirption");
    }

    @Override
    public Pair<String, String> PermissionListSyntax() {
        return new Pair<>("premission.list.syntax", "&c/permission list &e<Rank>");
    }

    @Override
    public Pair<String, List<String>> PermissionListSuccess() {
        return new Pair<>("premission.list.success", Arrays.asList(
                "\n&a------------------------------",
                "&e%index% &2- &b%permission% &7- %value%",
                "&a------------------------------\n\n"));
    }


    //RANK
    //error
    @Override
    public Pair<String, String> RankErrorPowerNotInteger() {
        return new Pair<>("rank.error.notInteger", "&c%args% n'est pas un chiffre !");
    }

    @Override
    public Pair<String, String> RankErrorUnknownArgs() {
        return new Pair<>("rank.error.unknownArgs", "&c%args% est un arguments inconnu. Essayé name, power, prefix ou suffix");
    }

    @Override
    public Pair<String, String> RankErrorPowerNotPositive() {
        return new Pair<>("rank.error.notPositive", "&c%power% ne peux pas être inférieur à 0 !");
    }

    @Override
    public Pair<String, String> RankErrorRankNotExist() {
        return new Pair<>("rank.error.notExists", "&cLe rank %name% n'existe pas !");
    }

    @Override
    public Pair<String, String> RankErrorTargetNotFound() {
        return new Pair<>("rank.error.notFound", "&cLe joueur %name% n'existe pas !");
    }

    @Override
    public Pair<String, String> RankErrorPrefixAndSuffixTooLong() {
        return new Pair<>("rank.error.prefixAndSuffixTooLong", "&cLa longueur du prefix et suffix est trop grande ! (%length% > 16)");
    }

    //create
    @Override
    public Pair<String, String> RankCreateDescription() {
        return new Pair<>("rank.create.description", "&cRank descirption");
    }

    @Override
    public Pair<String, String> RankCreateSyntax() {
        return new Pair<>("rank.create.syntax", "&c/rank create &8<Name> <Power> <Prefix> <Suffix>");
    }

    @Override
    public Pair<String, String> RankCreateSuccess() {
        return new Pair<>("rank.create.success", "&aCréation du grade %rank% &aavec succès !");
    }

    //delete
    @Override
    public Pair<String, String> RankDeleteDescription() {
        return new Pair<>("rank.delete.description", "&cRank descirption");
    }

    @Override
    public Pair<String, String> RankDeleteSyntax() {
        return new Pair<>("rank.delete.syntax", "&c/rank delete &8<Name>");
    }

    @Override
    public Pair<String, String> RankDeleteSuccess() {
        return new Pair<>("rank.delete.success", "&cSuppression du grade %rank% &aavec succès !");
    }

    //edit
    @Override
    public Pair<String, String> RankEditDescription() {
        return new Pair<>("rank.edit.description", "&cRank descirption");
    }

    @Override
    public Pair<String, String> RankEditSyntax() {
        return new Pair<>("rank.edit.syntax", "&c/rank edit &8<Name/Power/Prefix/Suffix> <RankName> <newValue>");
    }

    @Override
    public Pair<String, String> RankEditSuccess() {
        return new Pair<>("rank.edit.success", "&aEdition du grade %oldrank% &aen %newrank% &aavec succès !");
    }

    //list
    @Override
    public Pair<String, String> RankListDescription() {
        return new Pair<>("rank.list.description", "&cRank descirption");
    }

    @Override
    public Pair<String, String> RankListSyntax() {
        return new Pair<>("rank.list.syntax", "&c/rank list");
    }

    @Override
    public Pair<String, List<String>> RankListSuccess() {
        return new Pair<>("rank.list.success", Arrays.asList(
                "&a------------------------------",
                "&2A",
                "&a------------------------------"));
    }

    //set
    @Override
    public Pair<String, String> RankSetDescription() {
        return new Pair<>("rank.set.description", "&cRank descirption");
    }

    @Override
    public Pair<String, String> RankSetSyntax() {
        return new Pair<>("rank.set.syntax", "&c/rank set <Pseudo> <RankName>");
    }

    @Override
    public Pair<String, String> RankSetSuccess() {
        return new Pair<>("rank.set.success", "&6%player% &ane possède plus %oldrank% &amais possède %newrank%");
    }


    //FRIEND
    //help
    @Override
    public Pair<String, List<String>> FriendHelp() {
        return new Pair<>("friend.help", Arrays.asList(
                "&9----------------------------------------------------",
                "&aCommandes d'amis:",
                "&e/friend help &b- Affiche les commandes d'amis",
                "&e/friend add <pseudo> &b- Envoyer une demande d'ami",
                "&e/friend remove <pseudo> &b- Supprime un ami",
                "&e/friend accept <pseudo> &b- Accepter une demande d'ami",
                "&e/friend deny <pseudo> &b- Refuser une demande d'ami",
                "&e/friend requests &b- Affiche la liste de vos demande reçu",
                "&e/friend param &b- Affiche la liste de vos paramètre d'amis",
                "&e/friend list &b- Affiche la liste de vos amis",
                "&9----------------------------------------------------"));
    }

    //error
    @Override
    public Pair<String, String> FriendErrorNoRequest() {
        return new Pair<>("friend.error.noRequest", "&cVous n'avez pas de demande d'amis en attente !");
    }

    @Override
    public Pair<String, String> FriendErrorNotFound() {
        return new Pair<>("friend.error.notFound", "&cVotre amis n'existe pas ou n'est pas connecté !");
    }

    @Override
    public Pair<String, String> FriendErrorAlreadyFriend() {
        return new Pair<>("friend.error.alreadyFriend", "&cVous êtes déjà amis avec &e%player%");
    }

    @Override
    public Pair<String, String> FriendErrorNotFriend() {
        return new Pair<>("friend.error.notFriend", "&cVous n'êtes déjà pas amis avec &e%player%");
    }

    //accept
    @Override
    public Pair<String, String> FriendAcceptSuccess() {
        return new Pair<>("friend.accept.success", "&aVous êtes désormais amis avec &e%target%");
    }

    //deny
    @Override
    public Pair<String, String> FriendDenySuccessPlayer() {
        return new Pair<>("friend.deny.successPlayer", "&cVous avez refusé la demande d'amis de &e%target%");
    }

    @Override
    public Pair<String, String> FriendDenySuccessTarget() {
        return new Pair<>("friend.deny.successTarget", "&e%target% &cà refusé votre demande d'amis");
    }

    //TABLIST
    //header
    @Override
    public Pair<String, List<String>> TablistHeader() {
        return new Pair<>("tablist.header", Arrays.asList(
                " ", "&bGood games on &e&lPLAY.STONERS.NET", " "));
    }

    //footer
    @Override
    public Pair<String, List<String>> TablistFooter() {
        return new Pair<>("tablist.header", Arrays.asList(
                " ", "&aOur discord: &9&lDISCORD", "&aMore information on &e&lStoners.net"));
    }

    //MENUS
    //main items
    @Override
    public Pair<String, String> ItemPreviousName() {
        return new Pair<>("item.previous.name", "&ePage précédente");
    }

    @Override
    public Pair<String, List<String>> ItemPreviousLore() {
        return new Pair<>("item.previous.lore", Arrays.asList());
    }

    @Override
    public Pair<String, String> ItemCloseName() {
        return new Pair<>("item.close.name", "&c&lRetour");
    }

    @Override
    public Pair<String, List<String>> ItemCloseLore() {
        return new Pair<>("item.close.name", Arrays.asList());
    }

    @Override
    public Pair<String, String> ItemValidateName() {
        return new Pair<>("item.valide.name", "&a&lConfirmer");
    }

    @Override
    public Pair<String, List<String>> ItemValidateLore() {
        return new Pair<>("item.previous.name", Arrays.asList());
    }

    @Override
    public Pair<String, String> ItemNextName() {
        return new Pair<>("item.previous.name", "&ePage suivante");
    }

    @Override
    public Pair<String, List<String>> ItemNextLore() {
        return new Pair<>("item.previous.name", Arrays.asList());
    }


    //RANK MENU
    //main
    @Override
    public Pair<String, String> MenuRankMainName() {
        return new Pair<>("menu.rank.main.name", "&eRank:");
    }
    //create
    @Override
    public Pair<String, String> MenuRankCreateName() {
        return new Pair<>("menu.rank.create.name", "&2Création d'un rank:");
    }

    @Override
    public Pair<String, String> MenuRankCreateItem_RankDetail_Name() {
        return new Pair<>("menu.rank.create.detail.name", "&aNouveau grade:");
    }

    @Override
    public Pair<String, List<String>> MenuRankCreateItem_RankDetail_Lore() {
        return new Pair<>("menu.rank.create.detail.lore", Arrays.asList(
                "&eNom: %rankName%",
                "&bPower: %power%",
                "&7Prefix: %prefix%",
                "&7Suffix: %suffix%"));
    }

    @Override
    public Pair<String, String> MenuRankCreateItem_RankSetName_Name() {
        return new Pair<>("menu.rank.create.setName.name", "&aMettre un nom");
    }

    @Override
    public Pair<String, List<String>> MenuRankCreateItem_RankSetName_Lore() {
        return new Pair<>("menu.rank.create.setName.lore", Arrays.asList(
                "",
                "&7Ecrire le nom du rank",
                "&7dans l'enclume"));
    }

    @Override
    public Pair<String, String> MenuRankCreateItem_RankSetPower_Name() {
        return new Pair<>("menu.rank.create.setPower.name", "&aMettre un power");
    }

    @Override
    public Pair<String, List<String>> MenuRankCreateItem_RankSetPower_Lore() {
        return new Pair<>("menu.rank.create.setPower.lore", Arrays.asList(
                "",
                "&7Selectionner le power",
                "&7dans le menu"));
    }

    @Override
    public Pair<String, String> MenuRankCreateItem_RankSetPrefix_Name() {
        return new Pair<>("menu.rank.create.setPrefix.name", "&aMettre un prefix");
    }

    @Override
    public Pair<String, List<String>> MenuRankCreateItem_RankSetPrefix_Lore() {
        return new Pair<>("menu.rank.create.setPower.lore", Arrays.asList(
                "",
                "&7Ecrire le prefix du rank",
                "&7dans l'enclume",
                "",
                "&bPour des couleur:",
                "&bEsperluette + Code couleur"));
    }

    @Override
    public Pair<String, String> MenuRankCreateItem_RankSetSuffix_Name() {
        return new Pair<>("menu.rank.create.setPrefix.name", "&Mettre un suffix");
    }

    @Override
    public Pair<String, List<String>> MenuRankCreateItem_RankSetSuffix_Lore() {
        return new Pair<>("menu.rank.create.setPower.lore", Arrays.asList(
                "",
                "&7Ecrire le suffix du rank",
                "&7dans l'enclume",
                "",
                "&bPour des couleur:",
                "&bEsperluette + Code couleur"));
    }


}
