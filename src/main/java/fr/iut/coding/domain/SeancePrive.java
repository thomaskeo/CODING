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
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SeancePrive.
 */
@Entity
@Table(name = "T_SEANCEPRIVE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeancePrive implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "sujet")
    private String sujet;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "datedebut")
    private DateTime datedebut;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "datefin")
    private DateTime datefin;

    @Column(name = "nbutilisateur")
    private Integer nbutilisateur;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public DateTime getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(DateTime datedebut) {
        this.datedebut = datedebut;
    }

    public DateTime getDatefin() {
        return datefin;
    }

    public void setDatefin(DateTime datefin) {
        this.datefin = datefin;
    }

    public Integer getNbutilisateur() {
        return nbutilisateur;
    }

    public void setNbutilisateur(Integer nbutilisateur) {
        this.nbutilisateur = nbutilisateur;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeancePrive seancePrive = (SeancePrive) o;

        if ( ! Objects.equals(id, seancePrive.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeancePrive{" +
                "id=" + id +
                ", titre='" + titre + "'" +
                ", categorie='" + categorie + "'" +
                ", sujet='" + sujet + "'" +
                ", datedebut='" + datedebut + "'" +
                ", datefin='" + datefin + "'" +
                ", nbutilisateur='" + nbutilisateur + "'" +
                '}';
    }
}
