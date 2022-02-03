package fr.nemesis07.stoners.common.punishment;

import java.sql.Date;

public class Punishment {

    private final String name, uuid, operator;
    private final Date start, end;
    private final PunishmentType type;

    private String reason;
    private final int id;

    public Punishment(String name, String uuid, String reason, String operator, PunishmentType type, Date start, Date end, int id) {
        this.name = name;
        this.uuid = uuid;
        this.reason = reason;
        this.operator = operator;
        this.type = type;
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getOperator() {
        return operator;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public PunishmentType getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isActivate() {
        return getEnd().after(new Date(new java.util.Date().getTime()));
    }

    public long remainingTime() {
        return getStart().getTime()-getEnd().getTime();
    }
}
