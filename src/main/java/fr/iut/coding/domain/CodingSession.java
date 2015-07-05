package fr.iut.coding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.iut.coding.domain.util.CustomDateTimeDeserializer;
import fr.iut.coding.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CodingSession.
 */
@Entity
@Table(name = "T_CODINGSESSION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodingSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "theme")
    private String theme;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "session_start")
    private DateTime sessionStart;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "session_end")
    private DateTime sessionEnd;

    @ManyToOne
    private User animateur;

    @ManyToOne
    private Etat_session etat_session;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public DateTime getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(DateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    public DateTime getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(DateTime sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public User getAnimateur() {
        return animateur;
    }

    public void setAnimateur(User user) {
        this.animateur = user;
    }

    public Etat_session getEtat_session() {
        return etat_session;
    }

    public void setEtat_session(Etat_session etat_session) {
        this.etat_session = etat_session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodingSession codingSession = (CodingSession) o;

        if ( ! Objects.equals(id, codingSession.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CodingSession{" +
                "id=" + id +
                ", theme='" + theme + "'" +
                ", sessionStart='" + sessionStart + "'" +
                ", sessionEnd='" + sessionEnd + "'" +
                '}';
    }
}
