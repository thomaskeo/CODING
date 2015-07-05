package fr.iut.coding.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AjouterVideo.
 */
@Entity
@Table(name = "T_AJOUTERVIDEO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AjouterVideo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "sujet")
    private String sujet;

    @Column(name = "url")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AjouterVideo ajouterVideo = (AjouterVideo) o;

        if ( ! Objects.equals(id, ajouterVideo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AjouterVideo{" +
                "id=" + id +
                ", titre='" + titre + "'" +
                ", categorie='" + categorie + "'" +
                ", sujet='" + sujet + "'" +
                ", url='" + url + "'" +
                '}';
    }
}
