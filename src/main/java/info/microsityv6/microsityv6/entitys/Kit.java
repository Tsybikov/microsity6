/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.microsityv6.microsityv6.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class Kit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String kitHexId;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ESP> esps;
    private boolean sold=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getKitHexId() {
        return kitHexId;
    }

    public void setKitHexId(String kitHexId){
        this.kitHexId=kitHexId;
    }
    
    public void genKitHexId() {
        String value="";
        while(value.length()<8){
            Random rnd=new Random();
            int i=rnd.nextInt(16);
            switch(i){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8: value+=i+1;break;
                case 9: value+="a";break;
                case 10: value+="b";break;
                case 11: value+="c";break;
                case 12: value+="d";break;
                case 13: value+="e";break;
                case 14: value+="f";break;
                default:break;
            }
        }
        this.kitHexId = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ESP> getEsps() {
        return esps;
    }

    public void setEsps(List<ESP> esps) {
        this.esps = esps;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void addEsp(ESP esp){
        if(esps==null)esps=new ArrayList<>();
        esps.add(esp);
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kit)) {
            return false;
        }
        Kit other = (Kit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.microsityv6.microsityv6.entitys.Kit[ idHex=" + kitHexId + " "+"@"+(sold?"Sold!":"freeToSold")+" ]";
    }
    
}
