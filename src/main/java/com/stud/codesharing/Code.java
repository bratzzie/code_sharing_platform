package com.stud.codesharing;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Table(name = "codes")
@Entity
public class Code {

    @Column
    private String code;

    @Column
    private String date;

    private final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
    @Temporal(TemporalType.TIMESTAMP)
    private Date notFormattedDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateToDeleteSnippet;

    @Id
    @Column(nullable = false)
    private String id;

    @Column
    private int views;
    @Column
    private long time;

    @Column
    private Boolean viewsRestricted;

    @Column
    private Boolean timeRestricted;

    public Code() {
    }

    public Code(String code) {
        this.code = code;
    }

    public Code(String codeSnippet, int views, long time) {
    }

    @JsonIgnore
    public Date getDateToDeleteSnippet() {
        return dateToDeleteSnippet;
    }

    public void setDateToDeleteSnippet() {
        dateToDeleteSnippet =  Timestamp.valueOf(notFormattedDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime().plusSeconds(time));
    }
    public String getCode() {
        return code;
    }

    public void setCode(String codeSnippet) {
        code = codeSnippet;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        LocalDateTime uploadedTime = getNotFormattedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        date = uploadedTime.format(formatter);
    }

    @JsonIgnore
    public Date getNotFormattedDate() {
        return notFormattedDate;
    }

    public void setNotFormattedDate() {
        this.notFormattedDate = Timestamp.valueOf(LocalDateTime.now());
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        if (views <= 0) {
            this.views = 0;
            this.viewsRestricted = false;
        } else {
            this.views = views;
            this.viewsRestricted = true;
        }
    }

    public long getTime() {
        return getTimeRestricted() ? Duration.between(LocalDateTime.now(), dateToDeleteSnippet.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime()).toSeconds() : time;
    }

    public void setTime(long time) {
        System.out.println(time);
        if (time <= 0) {
            this.time = 0;
            this.timeRestricted = false;
        } else {
            this.time = time;
            this.timeRestricted = true;
        }
    }

    @JsonIgnore
    public boolean getViewsRestricted() {
        return viewsRestricted;
    }

    public void setViewsRestricted(boolean viewsRestricted) {
        this.viewsRestricted = viewsRestricted;
    }

    @JsonIgnore
    public boolean getTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
