package fr.nemesis07.stoners.bukkit.languages;

import javafx.util.Pair;
import java.util.List;

public abstract class Lang {

    public abstract String getLanguage();

    public abstract Pair<String, String> prefix();
    public abstract Pair<String, String> mustBePlayer();
    public abstract Pair<String, String> noPerm();

    //HELP
    public abstract Pair<String, List<String>> help();
    //PERMISSION:
    //error
    public abstract Pair<String, String> PermissionErrorAlreadyHasPermission();
    //add
    public abstract Pair<String, String> PermissionAddDescription();
    public abstract Pair<String, String> PermissionAddSyntax();
    public abstract Pair<String, String> PermissionAddSuccess();
    //remove
    public abstract Pair<String, String> PermissionRemoveDescription();
    public abstract Pair<String, String> PermissionRemoveSyntax();
    public abstract Pair<String, String> PermissionRemoveSuccess();
    //edit
    public abstract Pair<String, String> PermissionEditDescription();
    public abstract Pair<String, String> PermissionEditSyntax();
    public abstract Pair<String, String> PermissionEditSuccess();
    //list
    public abstract Pair<String, String> PermissionListDescription();
    public abstract Pair<String, String> PermissionListSyntax();
    public abstract Pair<String, List<String>> PermissionListSuccess();


    //RANK:
    //error
    public abstract Pair<String, String> RankErrorPowerNotInteger();
    public abstract Pair<String, String> RankErrorUnknownArgs();
    public abstract Pair<String, String> RankErrorPowerNotPositive();
    public abstract Pair<String, String> RankErrorRankNotExist();
    public abstract Pair<String, String> RankErrorTargetNotFound();
    public abstract Pair<String, String> RankErrorPrefixAndSuffixTooLong();
    //create
    public abstract Pair<String, String> RankCreateDescription();
    public abstract Pair<String, String> RankCreateSyntax();
    public abstract Pair<String, String> RankCreateSuccess();
    //delete
    public abstract Pair<String, String> RankDeleteDescription();
    public abstract Pair<String, String> RankDeleteSyntax();
    public abstract Pair<String, String> RankDeleteSuccess();
    //edit
    public abstract Pair<String, String> RankEditDescription();
    public abstract Pair<String, String> RankEditSyntax();
    public abstract Pair<String, String> RankEditSuccess();
    //list
    public abstract Pair<String, String> RankListDescription();
    public abstract Pair<String, String> RankListSyntax();
    public abstract Pair<String, List<String>> RankListSuccess();
    //set
    public abstract Pair<String, String> RankSetDescription();
    public abstract Pair<String, String> RankSetSyntax();
    public abstract Pair<String, String> RankSetSuccess();


    //FRIEND:
    //help:
    public abstract Pair<String, List<String>> FriendHelp();
    //error
    public abstract Pair<String, String> FriendErrorNoRequest();
    public abstract Pair<String, String> FriendErrorNotFound();
    public abstract Pair<String, String> FriendErrorAlreadyFriend();
    public abstract Pair<String, String> FriendErrorNotFriend();
    //accept
    public abstract Pair<String, String> FriendAcceptSuccess();
    //deny
    public abstract Pair<String, String> FriendDenySuccessPlayer();
    public abstract Pair<String, String> FriendDenySuccessTarget();

    //TABLIST
    //header
    public abstract Pair<String, List<String>> TablistHeader();
    //footer
    public abstract Pair<String, List<String>> TablistFooter();


    //MENUS
    //main items
    public abstract Pair<String, String> ItemPreviousName();
    public abstract Pair<String, List<String>> ItemPreviousLore();

    public abstract Pair<String, String> ItemCloseName();
    public abstract Pair<String, List<String>> ItemCloseLore();

    public abstract Pair<String, String> ItemValidateName();
    public abstract Pair<String, List<String>> ItemValidateLore();

    public abstract Pair<String, String> ItemNextName();
    public abstract Pair<String, List<String>> ItemNextLore();

    //RANK MENU
    //main
    public abstract Pair<String, String> MenuRankMainName();
    //create
    public abstract Pair<String, String> MenuRankCreateName();
    public abstract Pair<String, String> MenuRankCreateItem_RankDetail_Name();
    public abstract Pair<String, List<String>> MenuRankCreateItem_RankDetail_Lore();
    public abstract Pair<String, String> MenuRankCreateItem_RankSetName_Name();
    public abstract Pair<String, List<String>> MenuRankCreateItem_RankSetName_Lore();
    public abstract Pair<String, String> MenuRankCreateItem_RankSetPower_Name();
    public abstract Pair<String, List<String>> MenuRankCreateItem_RankSetPower_Lore();
    public abstract Pair<String, String> MenuRankCreateItem_RankSetPrefix_Name();
    public abstract Pair<String, List<String>> MenuRankCreateItem_RankSetPrefix_Lore();
    public abstract Pair<String, String> MenuRankCreateItem_RankSetSuffix_Name();
    public abstract Pair<String, List<String>> MenuRankCreateItem_RankSetSuffix_Lore();

}
