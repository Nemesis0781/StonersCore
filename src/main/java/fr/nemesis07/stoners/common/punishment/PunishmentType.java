package fr.nemesis07.stoners.common.punishment;

public enum PunishmentType {
    BAN("Ban", null, false, "ab.ban.perma"),
    TEMP_BAN("Tempban", BAN, true, "ab.ban.temp"),
    IP_BAN("Ipban", BAN, false, "ab.ipban.perma"),
    TEMP_IP_BAN("Tempipban", BAN, true, "ab.ipban.temp"),
    MUTE("Mute", null, false, "ab.mute.perma"),
    TEMP_MUTE("Tempmute", MUTE, true, "ab.mute.temp"),
    WARNING("Warn", null, false, "ab.warn.perma"),
    TEMP_WARNING("Tempwarn", WARNING, true, "ab.warn.temp"),
    KICK("Kick", null, false, "ab.kick.use"),
    NOTE("Note", null, false, "ab.note.use");

    private final String name;
    private final String perms;
    private final PunishmentType basic;
    private final boolean temp;

    PunishmentType(String name, PunishmentType basic, boolean temp, String perms) {
        this.name = name;
        this.perms = perms;
        this.basic = basic;
        this.temp = temp;
    }

    public static PunishmentType fromCommandName(String cmd) {
        switch (cmd) {
            case "ban":
                return BAN;
            case "tempban":
                return TEMP_BAN;
            case "ban-ip":
            case "banip":
                return IP_BAN;
            case "tempban-ip":
            case "tempbanip":
                return TEMP_IP_BAN;
            case "mute":
                return MUTE;
            case "tempmute":
                return TEMP_MUTE;
            case "warn":
                return WARNING;
            case "note":
                return NOTE;
            case "tempwarn":
                return TEMP_WARNING;
            case "kick":
                return KICK;
            default:
                return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getPerms() {
        return perms;
    }

    public PunishmentType getBasic() {
        return basic == null ? this : basic;
    }

    public boolean isTemp() {
        return temp;
    }

    public String getConfSection(String path) {
        return name+"."+path;
    }

    public PunishmentType getPermanent() {
        if(this == IP_BAN || this == TEMP_IP_BAN)
            return IP_BAN;

        return getBasic();
    }

    public boolean isIpOrientated() {
        return this == IP_BAN || this == TEMP_IP_BAN;
    }
}
